# hofi.org &middot; [![NodeJS with Gulp](https://github.com/hofiorg/hofi.org/actions/workflows/npm-gulp.yml/badge.svg)](https://github.com/hofiorg/hofi.org/actions/workflows/npm-gulp.yml) &middot; [![Java CI with Gradle](https://github.com/hofiorg/hofi.org/actions/workflows/gradle.yml/badge.svg)](https://github.com/hofiorg/hofi.org/actions/workflows/gradle.yml)

## Content

Sourcecode for:
<code>[hofi.org](http://www.hofi.org)</code><br/>

## Installation

- Install npm (https://nodejs.org/)
- Install bower (http://bower.io/)
- Install vulcanize (https://github.com/Polymer/vulcanize)
- Install gulp (http://gulpjs.com/)

## Install hofi.org

```sh
git clone https://github.com/hofiorg/hofi.org.git
cd hofi.org
npm install
gulp install
```

## Start local server

```sh
gulp serve
```

## Deploy changes

```sh
gradle sftp
```

## Used Web Components

* jquery (https://jquery.com)
* polymer (https://www.polymer-project.org)
    * iron-elements (https://elements.polymer-project.org/browse?package=iron-elements)
    * paper-elements (https://elements.polymer-project.org/browse?package=paper-elements)

## Used Java library

* Apache Commons Net (https://commons.apache.org/proper/commons-net/)

## Contributing
Any suggestions, improvements or issues are welcome :)

## License
[MIT License](http://opensource.org/licenses/MIT)

Copyright &copy; 2021 [@hofiorg](https://github.com/hofiorg)
