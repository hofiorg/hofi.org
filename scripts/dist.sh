#!/bin/sh

# remove old installation
rm -rf bower_components
rm -rf web/bower_components
rm -rf web/vulcanized

# bower
bower install

# copy jquery to web
mkdir web/bower_components
cp -R bower_components/jquery web/bower_components/jquery
cp -R bower_components/webcomponentsjs web/bower_components/webcomponentsjs

# vulcanize for production
mkdir web/vulcanized
vulcanize elements/elements.html -o web/vulcanized/elements.html --inline-scripts --inline-css --strip-comments
vulcanize elements/elements_article.html -o web/vulcanized/elements_article.html --inline-scripts --inline-css --strip-comments