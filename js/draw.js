  drawCanvas();

  var context, canvas, x, y, lastX, lastY;

  var r = Math.floor((Math.random() * 255) + 0); // red
  var rup = true;
  var g = Math.floor((Math.random() * 255) + 0); // green
  var gup = true;
  var b = Math.floor((Math.random() * 255) + 0); // blue
  var bup = true;

  var rectangleSize = 3;
  var nextPixelRange = 5;

  function drawCanvas(){
    canvas = document.getElementById('canvas');
    canvas.width  = window.innerWidth;
    canvas.height = window.innerHeight - 4;

    x = getRandomInt(1, canvas.width);
    y = getRandomInt(0, canvas.height);

    if(canvas.getContext){
      context = canvas.getContext('2d');
      setInterval(drawPixel, 0);
    }
  }

  function drawPixel(){
    lastX = x;
    lastY = y;

    calculateNextColor();
    var json = calculateMinMax();
    x = getRandomInt(json.minX, json.maxX);
    y = getRandomInt(json.minY, json.maxY);

    drawPixelToCanvas();
  }

  function drawPixelToCanvas() {
    var id = context.createImageData(rectangleSize,rectangleSize);
    var d  = id.data;
    for (var i=0;i < id.data.length;i+=4) {
      id.data[i+0]=r;
      id.data[i+1]=g;
      id.data[i+2]=b;
      id.data[i+3]=255;
    }
    context.putImageData(id, x, y);
  }

  function calculateNextColor() {
    rup ? r++ : r--;
    if(r === 255) rup = false;
    if(r ===   0) rup = true;

    gup ? g++ : g--;
    if(g === 255) gup = false;
    if(g ===   0) gup = true;

    bup ? b++ : b--;
    if(b === 255) bup = false;
    if(b ===   0) bup = true;
  }

  function calculateMinMax() {
    var minX = lastX - nextPixelRange;
    if(minX < 1) minX = 1;

    var maxX = lastX + nextPixelRange;
    if(maxX > canvas.width) maxX = canvas.width;

    var minY = lastY - nextPixelRange;
    if(minY < 1) minY = 1;

    var maxY = lastY + nextPixelRange;
    if(maxY > canvas.height) maxY = canvas.height;

    return {minX: minX, maxX: maxX, minY: minY, maxY: maxY};
  }

  function getRandomInt(min, max) {
      return Math.floor(Math.random() * (max - min + 1)) + min;
  }
