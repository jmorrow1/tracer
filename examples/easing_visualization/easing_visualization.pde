import tracer.*;
import tracer.paths.*;
import tracer.easings.*;

//time
int prevt;

//paths
ArrayList<Plot> plots = new ArrayList<Plot>();
ArrayList<Rect> rects = new ArrayList<Rect>();
ArrayList<Tracer> tracers = new ArrayList<Tracer>();

//easings
Easing[] easings;

//parameters
int rowSize = 6;
int colSize = 4;
int cellWidth = 150;
int cellHeight = 100;

//draw mode
boolean drawAnimations = true;
boolean drawPlots = true;

void settings() {
  size(rowSize*cellWidth, colSize*cellHeight, P2D);
}

void setup() {  
  background(255);

  Easing easing = Easings.getLinear();

  easings = new Easing[] {
    Easings.getLinear(), 

    Easings.getQuadEaseIn(), 
    Easings.getQuadEaseOut(), 
    Easings.getQuadEaseInOut(), 

    Easings.getCubicEaseIn(), 
    Easings.getCubicEaseOut(), 
    Easings.getCubicEaseInOut(), 

    Easings.getCircEaseIn(), 
    Easings.getCircEaseOut(), 
    Easings.getCircEaseInOut(), 

    Easings.getLinearBackAndForth(), 
 
    Easings.getQuadEaseInBackAndForth(), 
    Easings.getQuadEaseOutBackAndForth(), 
    Easings.getQuadEaseInOutBackAndForth(), 
    
    Easings.getCubicEaseInBackAndForth(), 
    Easings.getCubicEaseOutBackAndForth(), 
    Easings.getCubicEaseInOutBackAndForth(), 
    
    Easings.getCircEaseInBackAndForth(),
    Easings.getCircEaseOutBackAndForth(),
    Easings.getCircEaseInOutBackAndForth(),
  };

  float x = 0;
  float y = 0;
  for (int i=0; i<easings.length; i++) {
    Rect rect = new Rect(x, y, cellWidth, cellHeight, CORNER);
    rects.add(rect);
    
    Plot plot = new Plot(new Rect(x + cellWidth/2, y + cellHeight/2, 0.8*cellWidth, 0.8*cellHeight, CENTER), easings[i], 51);
    plot.setStrokeWeight(2);
    plots.add(plot);
    
    Tracer tracer = new Tracer(plot, 0, 0.0005);
    tracers.add(tracer);

    if (x + cellWidth < width) {
      x += cellWidth;
    } else {
      x = 0;
      y += cellHeight;
    }
  }

  prevt = millis();
}

void draw() {
  //compute time step
  int dt = millis() - prevt;
  prevt = millis();
  
  //update each tracer by the time step
  for (Tracer t : tracers) {
    t.step(dt);
  }

  //draw cells
  for (Rect r : rects) {
    r.draw(g);
  }
  
  if (drawPlots) {
    //draw plots of easings
    for (Plot p : plots) {
      p.draw(g);
    }
    
    //draw tracers
    strokeWeight(6);
    stroke(0);
    for (Tracer t : tracers) {
      point(t.x, t.y);
    }
  }
  
  if (drawAnimations) {
    //draw animations of each easing
    noStroke();
    fill(0, 50);
    ellipseMode(CENTER);
    for (int i=0; i<plots.size(); i++) {
      Easing easing = easings[i];
      Plot plot = plots.get(i);
      Rect rect = rects.get(i);
      Tracer t = tracers.get(i);
      
      float x = map(easing.val(t.getInput1D()), 0, 1, rect.getX1() + 9, rect.getX2() - 9);
      ellipse(x, rect.getCeny(), 18, 18);
    }
  }
}

String lastInfoString = "";
void mouseMoved() {
  int i = min(floor(map(mouseX, 0, width, 0, rowSize)), rowSize-1);
  int j = min(floor(map(mouseY, 0, height, 0, colSize)), colSize-1);
  int cellIndex = j*rowSize + i;
  if (0 <= cellIndex && cellIndex < easings.length) {
    Easing easing = easings[cellIndex];
    String pathName = easing.getClass().getSimpleName();
    String newInfoString = "The mouse is hovered over a " + pathName + ".";
    if (!lastInfoString.equals(newInfoString)) {
      println(newInfoString);
      lastInfoString = newInfoString;
    }
  }
}