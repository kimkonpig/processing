import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_131119h extends PApplet {


 

Minim minim; 

AudioPlayer mySound; 
BeatDetect beat;

FFT fft;
float  angle = 0;
int num = 300;
float bg;
float soundlevel = 0;
float soundlevel2 = 0;  

Effect[] myEffect = new Effect[num];

public void setup() { 
  size(600, 600, P3D); 
  noStroke(); 

  for (int i =0 ; i < num;  i++)
  {
    myEffect[i] = new Effect(color(random(255)), random(0, 15), random(0.001f, 0.005f));
  }

  minim = new Minim(this); 
  mySound = minim.loadFile("no13_q cantabile.mp3"); 
  fft = new FFT(mySound.bufferSize(), mySound.sampleRate());  

  beat = new BeatDetect(mySound.bufferSize(), mySound.sampleRate());    
  mySound.play();
} 

public void draw() { 
  beat.detect(mySound.mix);
  if (beat.isSnare()==true) {
    bg = 220;
  }
  background(bg, 50, 50, 30);
  bg *= 0.95f;

  fft.forward(mySound.mix);
  fft.window(FFT.HAMMING); 

  for (int i =0 ; i < num-1;  i++)
  {
    myEffect[i].add_won_size = fft.getBand(i)/20+2;
    myEffect[i].add_num = i;
    myEffect[i].b =  myEffect[i].b + fft.getBand(i)/200;
    myEffect[i].mix_level = mySound.mix.level()*10;
    myEffect[i].display();
    myEffect[i].drive();
  }

  /*camera(-300+mouseX, 300, 900, // eyeX, eyeY, eyeZ
   300, 300, 0, // centerX, centerY, centerZ
   0.0, 1.0, 0.0); // upX, upY, upZ  
   */

  soundlevel = mySound.mix.level()/2;
  soundlevel2 = soundlevel + soundlevel2;

  translate(300, 300, 0); 

  lights();

  //rotateX(map(mouseY, 0, height, 0, TWO_PI));
  //rotateY(map(mouseX, 0, width, 0, TWO_PI));

  pushMatrix();    
  noStroke(); 
  scale(soundlevel*0.6f);
  stroke(200, 0, 0, 50);
  fill(150, 50, 50, 200);
  sphereDetail(2);
  sphere(200); 
  popMatrix();

  pushMatrix();    
  noStroke();   
  rotateX(map(soundlevel2*20, 0, height, 0, TWO_PI));
  rotateY(map(soundlevel2*20, 0, width, 0, TWO_PI));
  scale(0.7f);
  stroke(0, 0, 0, 80);
  fill(0, 0, 0, 50);
  if (key!='3') {
    sphereDetail(3);
    sphere(200);
  }
  popMatrix();

  pushMatrix();   
  rotateY(map(soundlevel2*10, 0, width, 0, TWO_PI));
  scale(1.2f);
  stroke(150, 150, 150, 30);
  fill(0, 0, 0, 0);
  if (key!='2'&&key!='3') {
    sphereDetail(10);
    sphere(200);
  }  
  popMatrix();
}


public void keyPressed() { 
  if ( key == 'm'|| key == 'M' ) { 
    if ( mySound.isMuted() ) { 
      mySound.unmute();
    } 
    else { 
      mySound.mute();
    }
  }
} 

public void stop() { 
  mySound.close(); 
  minim.stop(); 

  super.stop();
} 

class Effect
{
  float xpos, ypos;
  float won_size;
  float add_won_size;
  float speed;
  float speed_a = 0;
  float b = 0;
  float add_num;
  float mix_level;

  Effect(int temp_c, float temp_won_size, float temp_speed)
  {
    won_size = temp_won_size;
    speed = temp_speed;
  }

  public void display()
  {
        noStroke();
        fill(220, 0, 0, 70);
        ellipse(xpos, ypos, won_size + add_won_size, won_size + add_won_size);
        fill(100, 0, 0, 70);
        ellipse(xpos, ypos, won_size + add_won_size + mix_level*2, won_size + add_won_size + mix_level*2);
  }

  public void drive()
  {
    xpos = width/2 + sin(speed_a+(add_num))*(add_num * sin(b));

    ypos = height/2 + cos(speed_a+(add_num))*(add_num * sin(b));


    speed_a+=speed;
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_131119h" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
