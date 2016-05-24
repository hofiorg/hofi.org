var gulp = require('gulp'),
    exec = require('child_process').exec,
    runSequence = require('run-sequence'),
    browserSync = require('browser-sync'),
    reload = browserSync.reload;

gulp.task('watch', function() {
  gulp.watch('generator/articles/**/*.html', ['generate-articles']);
  gulp.watch('generator/template/**/*.html', ['generate-articles']);
});

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
    }
  });
  gulp.watch(['**/*.html', 'css/**/*.css'], {cwd: 'web'}, reload);
});