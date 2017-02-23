import tracer.*;
import paths.*;
import render.*;
import ease.*;

int prevt;

ArrayList<Plot> plots = new ArrayList<Plot>();
ArrayList<Rect> rects = new ArrayList<Rect>();
ArrayList<Tracer> tracers = new ArrayList<Tracer>();

void setup() {
  size(400, 400, P2D);
  
  background(255);

  Easing easing = new Easing.Linear();
  
  Easing[] easings = new Easing[] {
    new Easing.Linear(),
    new Easing.QuadEaseIn(),
    new Easing.QuadEaseOut(),
    new Easing.QuadEaseInOut(),
    new Easing.CubicEaseIn(),
    new Easing.CubicEaseOut(),
    new Easing.CubicEaseInOut(),
    new Easing.CircEaseIn(),
    new Easing.CircEaseOut(),
    new Easing.CircEaseInOut(),
    new Easing.BackAndForth(),
  };
  
  float x = 0;
  float y = 0;
  int w = width/4;
  int h = height/4;
  for (int i=0; i<easings.length; i++) {
    Rect rect = new Rect(x, y, w, h, CORNER);
    rects.add(rect);
    Plot plot = new Plot(new Rect(x + w/2, y + h/2, 0.8*w, 0.8*h, CENTER), easings[i], 21);
    plots.add(plot);   
    Tracer tracer = new Tracer(plot, 0, 0.0005);
    tracers.add(tracer);
    
    if (x + w < height) {
      x += w;
    }
    else {
      x = 0;
      y += h;
    }
  }
  
  prevt = millis();
}

void draw() {
  int dt = millis() - prevt;
  prevt = millis();
  
  strokeWeight(1);
  for (Rect r : rects) {
    r.draw(g);
  }
  
  strokeWeight(2);
  for (Plot p : plots) {
    p.draw(g);
  }
  
  strokeWeight(6);
  for (Tracer t : tracers) {
    t.step(dt);
    point(t.x, t.y);
  }
}