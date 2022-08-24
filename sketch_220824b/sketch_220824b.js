const density = "Ñ@#W$9876543210?!abc;:+=-,._                    ";
// const density = '       .:-i|=+%O#@'
// const density = '        .:░▒▓█';

let bbisoon;

/* bbisoon 이미지 로드를 위한 메소드 */
/* 해상도가 작을 수록 pixel to ascii가 잘 먹힘 */
function preload(){
  bbisoon = loadImage("bbisoon48.jpg")
}

function setup() {
  //createCanvas(400, 400);
  noCanvas();
//}

//function draw() { /*정적인 이미지를 그리므로 draw loop는 삭제  */
  background(0);
  //background(220); /* text로 그리기 위해 주석처리 */
  //image(bbisoon, 0, 0, width, height); /* text로 그리기 위해 주석처리 */
  
  let w = width / bbisoon.width;
  let h = height / bbisoon.height;
  bbisoon.loadPixels();
  
  for (let j = 0; j < bbisoon.height; j++){
    /* And then I can accumulate all of the characters into a string for each row. */
    /* 모든 문자를 각 행에 대한 문자열로 누적할 수 있다. */
    let row = '' ;
    
    for (let i = 0; i < bbisoon.width; i++){
      const pixelIndex = (i + j * bbisoon.width) * 4; /* 4는 왜 곱하는거지? */
      const r = bbisoon.pixels[pixelIndex + 0];
      const g = bbisoon.pixels[pixelIndex + 1];
      const b = bbisoon.pixels[pixelIndex + 2];
      const avg = (r + g + b) / 3; /* grayscale version */
      
      const len = density.length;
      const charIndex = floor(map(avg, 0, 255, len, 0)); /* 정수값만 취하기 위해 floor처리 */
      /* 흰부분에 글자를 표기하기 위해 len, 0 순으로 작성 */
      /* character index is take the brightness value(avg) which has a range between zero and 255
      and map that to some number between zero and the length of the string. */
 
      const c = density.charAt(charIndex);
      if (c == ' '){
        row += '&nbsp;';
      }
      else{
        row += c;
      }
    }
    createDiv(row);
    //console.log(row);
  }
}
