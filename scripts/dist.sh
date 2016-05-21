#!/bin/sh

cd ..

rm -R bower_components

bower install

rm -R web/bower_components
rm -R web/vulcanized

mkdir web/vulcanized

vulcanize elements/elements.html -o web/vulcanized/elements.html --inline-scripts --inline-css --strip-comments
vulcanize elements/elements_article.html -o web/vulcanized/elements_article.html --inline-scripts --inline-css --strip-comments

mkdir web/bower_components
cp -R bower_components/jquery web/bower_components/jquery