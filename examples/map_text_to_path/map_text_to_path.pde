import tracer.*;
import tracer.paths.*;

//style
int fgColor = #FFFFFF; //foreground color
int bgColor = #222222; //background color

//text
String text = "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin.";
float startu; //1D position where text starts
float dstartu = 0.00001; //1D text speed per millisecond

//path
Lissajous path;
float dphi = 0.0005; //change in phi per millisecond

//time
int prevt; //the previous time in milliseconds

void setup() {
  size(400, 400, P2D);
  
  //create lissajous path
  int freqX = 3;
  int freqY = 2;
  int radius = 125;
  float phi = HALF_PI;
  path = new Lissajous(width/2, height/2, radius, radius, freqX, freqY, phi);
  
  prevt = millis();
}

void draw() {
  //update change in time
  int dt = millis() - prevt;
  prevt = millis();
  
  //animate path
  path.setPhi(path.getPhi() + dphi * dt); //change phi angle
  startu += dstartu * dt; //shift 1D position where the text starts
  
  //draw background
  background(bgColor);
  
  //draw text
  noStroke();
  fill(fgColor);
  textSize(22);
  embedText(path, text);
}

void embedText(Path path, String text) {
  //create memory to store traced points (to limit the number of new points that have to be allocated)
  Point a = new Point(0, 0);
  Point b = new Point(0, 0);
  
  float textWidthAcc = 0; //the accumulated width of the text from the start of the loop to the end of the loop
  float textWidthTotal = textWidth(text); //the total width of the text
  
  for (int i=0; i<text.length(); i++) {
    char c = text.charAt(i);
    
    //update text width accumulator
    textWidthAcc += 0.5 * textWidth(c);
    
    //compute 1D coordinate of text character
    float u = (startu + map(textWidthAcc, 0, textWidthTotal, 0, 1)) % 1;
    
    //compute 2D coordinate of text character
    path.trace(a, u);
    
    //compute angle of rotation
    path.trace(b, (u + 0.000001) % 1.0);
    float angleOfRotation = atan2(b.y - a.y, b.x - a.x);
    
    //draw character
    drawChar(text.charAt(i), a.x, a.y, angleOfRotation);
    
    //update text width accumulator
    textWidthAcc += 0.5 * textWidth(c);
  }
}

void drawChar(char c, float x, float y, float angle) {
  textAlign(CENTER, CENTER);
  pushMatrix();
  translate(x, y);
  rotate(angle);
  text(c, 0, 0);
  popMatrix();
}