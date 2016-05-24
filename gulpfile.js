var gulp = require('gulp'),
    exec = require('child_process').exec,
    runSequence = require('run-sequence'),
    browserSync = require('browser-sync'),
    reload = browserSync.reload,
    del = require('del'),
    bower = require('gulp-bower'),
    vulcanize = require('gulp-vulcanize');

gulp.task('generate-articles', function(cb) {
  runSequence('compile-articles',
              'run-articles-generator',
              cb);
});

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

gulp.task('serve', function() {
  browserSync({
    server: {
      baseDir: 'web'
    },
    startPath: "/article"
  });
  gulp.watch('generator/articles/**/*.html', ['generate-articles']);
  gulp.watch('generator/template/**/*.html', ['generate-articles']);
  gulp.watch(['**/*.html', 'css/**/*.css'], {cwd: 'web'}, reload);
});

gulp.task('install', function(cb) {
  runSequence('clean',
              'bower',
              'copy-bower',
              'vulcanize',
               cb);
});

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

gulp.task('copy-bower', function() {
  gulp.src(['bower_components/jquery/**/*']).pipe(gulp.dest('web/bower_components/jquery'));
  gulp.src(['bower_components/webcomponentsjs/**/*']).pipe(gulp.dest('web/bower_components/webcomponentsjs'));
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

gulp.task('dist', function () {

  // TODO: dist
  // - https://github.com/morris/vinyl-ftp

});

