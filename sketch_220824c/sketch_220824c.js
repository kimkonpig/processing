const density = "Ñ@#W$9876543210?!abc;:+=-,._                    ";
// const density = '       .:-i|=+%O#@'
// const density = '        .:░▒▓█';

//let bbisoon;
let video;
let asciiDiv;

function setup() {
  noCanvas();
  video = createCapture(VIDEO);
  video.size(48, 48); /* resize */
  asciiDiv = createDiv();
}

function draw(){
  video.loadPixels();
  let asciiImage = '' ;
  for (let j = 0; j < video.height; j++){
    for (let i = 0; i < video.width; i++){
      const pixelIndex = (i + j * video.width) * 4; /* 4는 왜 곱하는거지? */
      const r = video.pixels[pixelIndex + 0];
      const g = video.pixels[pixelIndex + 1];
      const b = video.pixels[pixelIndex + 2];
      const avg = (r + g + b) / 3; /* grayscale version */
      const len = density.length;
      const charIndex = floor(map(avg, 0, 255, len, 0)); /* 정수값만 취하기 위해 floor처리 */
      /* 흰부분에 글자를 표기하기 위해 len, 0 순으로 작성 */
      /* character index is take the brightness value(avg) which has a range between zero and 255
      and map that to some number between zero and the length of the string. */
 
      const c = density.charAt(charIndex);
      if (c == ' '){
        asciiImage += '&nbsp;';
      }
      else{
        asciiImage += c;
      }
    }
    asciiImage += '<br/>';
  }
  asciiDiv.html(asciiImage);
}
