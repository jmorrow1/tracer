import paths.*;
import tracer.*;
import ease.*;
import render.*;

//parameters
float minRadius = 10;
float startRadius = 125;
float du = 0.002;
float minConnectionDist = 1;
float maxConnectionDist = 60;

//animation
float u = 0;

//paths
ArrayList<Point> tracers = new ArrayList<Point>();
ArrayList<Path> paths = new ArrayList<Path>();

//render
RenderMesh render;

//style
int bgColor = #ffffff;
int fgColor = #111111;

//draw modes
boolean showPaths = false;
boolean showPoints = false;

void settings() {
  size(500, 500);
}

void setup() {  
  createPaths(width/4, height/4, startRadius, minRadius, true);
  createPaths(3*width/4, height/4, startRadius, minRadius, true);
  createPaths(3*width/4, 3*height/4, startRadius, minRadius, true);
  createPaths(width/4, 3*height/4, startRadius, minRadius, true);
  
  createTracers();
  render = new RenderMesh(tracers, 20);
  render.setStrokeColor(fgColor);
  render.setStrokeRamp(maxConnectionDist);
  render.setStrokeWeight(1.5f);
}

void createPaths(float x, float y, float r, float minRadius, boolean skip) {
  if (!skip) {
    Path path = new Circle(x, y, r);
    path.setFill(false);
    path.setStrokeColor(100);
    paths.add(path);
  }

  float newR = r/2;
  if (newR >= minRadius) {
    float newX = x - newR;
    for (int i=0; i<2; i++) {
      float newY = y - newR;
      for (int j=0; j<2; j++) {
        createPaths(newX, newY, newR, minRadius, false);
        newY += r;
      }
      newX += r;
    }
  }
}

void createTracers() {
  for (Path p : paths) {
    //float speed = random(-du, du);
    float speed = (random(1) < 0.5) ? -du : du;
    Tracer t = new Tracer(p, random(1), speed, new Easing() {
      public float val(float t) {
        return t;
      }
    });
    tracers.add(t);
  }
}

void draw() {
  background(bgColor);
  
  float rads = u * TWO_PI;
  float connectionDist = map(sin(rads), 0, 1, minConnectionDist, maxConnectionDist);
  u += du;
  render.setMinDist(connectionDist);
  
  if (showPaths) {
    //draw paths
    for (Path p : paths) {
      p.draw(g);
    }
  }
  
  if (showPoints) {
    //draw points
    noStroke();
    fill(0);
    ellipseMode(RADIUS);
    for (Point pt : tracers) {
      ellipse(pt.x, pt.y, 2.5, 2.5);
    }
  }
  
  //draw renders
  render.step();
  render.draw(g);
}

void mousePressed() {
  showPoints = !showPoints;
  showPaths = !showPaths;
}