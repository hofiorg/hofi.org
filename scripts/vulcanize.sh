#!/bin/sh

cd ..
vulcanize web/elements/elements.html -o web/vulcanized/elements.html --inline-scripts --inline-css --strip-comments
vulcanize web/elements/elements_article.html -o web/vulcanized/elements_article.html --inline-scripts --inline-css --strip-comments