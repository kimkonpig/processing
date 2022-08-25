/**
 * Processing Sound Library, Example 6
 *
 * This sketch shows how to use the Amplitude class to analyze the changing
 * "loudness" of a stream of sound. In this case an audio sample is analyzed.
 *
 * Load this example with included sound files from the Processing Editor:
 * Examples > Libraries > Sound > Analysis > PeakAmplitude
 */

import processing.sound.*;

// Declare the processing sound variables
SoundFile sample;
Amplitude rms;

// Declare a smooth factor to smooth out sudden changes in amplitude.
// With a smooth factor of 1, only the last measured amplitude is used for the
// visualisation, which can lead to very abrupt changes. As you decrease the
// smooth factor towards 0, the measured amplitudes are averaged across frames,
// leading to more pleasant gradual changes
float smoothingFactor = 0.25;

// Used for storing the smoothed amplitude value
float sum;
float angle;
float jitter;

public void setup() {
  size(640, 360);

  //Load and play a soundfile and loop it
  sample = new SoundFile(this, "2.aiff");
  sample.loop();

  // Create and patch the rms tracker
  rms = new Amplitude(this); //Amplitude = 진폭
  rms.input(sample); // 노래 집어넣기
}

public void draw() {
  // Set background color, noStroke and fill color
  background(20, 20, 20);
  noStroke();
  
  // smooth the rms data by smoothing factor
  sum += (rms.analyze() - sum) * smoothingFactor;

  // rms.analyze() return a value between 0 and 1. It's
  // scaled to height/2 and then multiplied by a fixed scale factor
  float rms_scaled = sum * (height/2) * 3;

  fill(150, rms_scaled/2, 150);
  
  // We draw a circle whose size is coupled to the audio analysis
  ellipse(width/2, height/2, rms_scaled, rms_scaled);
  
  // line draw
  stroke(255, 255, 255);
  translate(width/2, height/2);
  rotate(rms_scaled/100);
  line(0, 0, rms_scaled/3, rms_scaled/3);

}
