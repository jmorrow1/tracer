import tracer.*;
import tracer.paths.*;
import tracer.renders.*;
import tracer.easings.*;

//paramters
float minRadius = 16;
float startRadius = 128;
float minConnectionRadius = 20;
float du = 0.002;

//paths
ArrayList<Point> pts = new ArrayList<Point>();
ArrayList<Path> paths = new ArrayList<Path>();

//render
Render render;

//style
int bgColor = #ffffff;
int fgColor = #111111;

//draw mode
boolean showPoints = false;
boolean showPaths = false;

void settings() {
  size(512, 512);
}

void setup() {
  createPaths(width/4, height/4, startRadius, minRadius, true);
  createPaths(3*width/4, height/4, startRadius, minRadius, true);
  createPaths(3*width/4, 3*height/4, startRadius, minRadius, true);
  createPaths(width/4, 3*height/4, startRadius, minRadius, true);
  
  createTracers();
  render = new RenderMesh(pts, minConnectionRadius);
  render.setStrokeColor(fgColor);
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
    pts.add(t);
  }
}

void draw() {
  background(bgColor);
  
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
    for (Point pt : pts) {
      ellipse(pt.x, pt.y, 2, 2);
    }
  }
  
  //draw renders
  render.step();
  render.draw(g);
}

void mousePressed() {
  showPaths = !showPaths;
  showPoints = !showPoints;
}