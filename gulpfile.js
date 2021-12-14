var gulp = require('gulp'),
    exec = require('child_process').exec,
    browserSync = require('browser-sync'),
    reload = browserSync.reload,
    del = require('del'),
    bower = require('gulp-bower'),
    vulcanize = require('gulp-vulcanize'),
    gutil = require('gulp-util'),
    ftp = require('vinyl-ftp'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    fs = require('fs');

gulp.task('compile-articles', function(cb) {
  exec('javac generator/src/ArticleGenerator.java -d generator/out', function (err, stdout, stderr) {
    console.log(stdout);
    console.log(stderr);
    cb(err);
  });
});

gulp.task('run-articles-generator', function(cb) {
  exec('java -cp generator/out ArticleGenerator', function (err, stdout, stderr) {
    console.log(stdout);
    console.log(stderr);
    cb(err);
  });
});

gulp.task('generate-articles', gulp.series('compile-articles', 'run-articles-generator', function(done) {
  done();
}));

gulp.task('serve', function() {
  browserSync({
    server: {
      baseDir: 'web'
    },
    startPath: "/article",
    browser: 'Google Chrome Canary',
  });
  gulp.watch('generator/articles/**/*.html', gulp.parallel('generate-articles'));
  gulp.watch('generator/template/**/*.html', gulp.parallel('generate-articles'));
  gulp.watch(['**/*.html', 'css/**/*.css'], {cwd: 'web'}, reload);

  gulp.watch('js/**/index.js', uglify_js);
  gulp.watch('js/**/draw.js', uglify_draw_js);

  uglify_js();
  uglify_draw_js();
});

function uglify_js() {
  gulp.src('js/**/index.js')
    .pipe(concat('index.min.js'))
    .pipe(uglify())
    .pipe(gulp.dest('./web/'))
}

function uglify_draw_js() {
  gulp.src('js/**/draw.js')
    .pipe(concat('draw.min.js'))
    .pipe(uglify())
    .pipe(gulp.dest('./web/'))
}

gulp.task('clean', function() {
  return del([
    'bower_components',
    'web/bower_components',
    'web/vulcanized'
  ]);
});

gulp.task('bower', function() {
  return bower();
});

gulp.task('copy-bower', function(done) {
  gulp.src(['bower_components/jquery/**/*']).pipe(gulp.dest('web/bower_components/jquery'));
  gulp.src(['bower_components/webcomponentsjs/**/*']).pipe(gulp.dest('web/bower_components/webcomponentsjs'));
  done();
});

gulp.task('vulcanize', function () {
  return gulp.src('elements/*.html')
    .pipe(vulcanize({
      abspath: '',
      excludes: [],
      stripComments: true,
      inlineScripts: true,
      inlineCss: true
    }))
    .pipe(gulp.dest('web/vulcanized'));
});

gulp.task('install', gulp.series('clean', 'bower', 'copy-bower', 'vulcanize', function(done) {
  done();
}));

function createFTPConnection() {
  var json = JSON.parse(fs.readFileSync('ftp.json'));
  return ftp.create({
    host:     json.host,
    user:     json.user,
    password: json.password,
    parallel: 10,
    log:      gutil.log
  });
}

gulp.task('ftp-cleanup', function (cb) {
  var conn = createFTPConnection();
  function cbRmdir(err) {
    cb();
  }
  conn.rmdir('/', cbRmdir);
});

gulp.task('deploy', function () {
  uglify_js();

  var conn = createFTPConnection()

  var globs = [
    'web/**'
  ];

  return gulp.src(globs, { base: 'web', buffer: false })
           .pipe(conn.newer('/'))
           .pipe(conn.dest('/'));
});
