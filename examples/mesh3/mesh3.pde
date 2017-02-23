import paths.*;
import tracer.*;
import ease.*;
import render.*;

//parameters
int w = 12; //circles per row
int h = 12; //circles per column

//animation
float t = 0;
float dt = 0.003;

//paths
Path[] paths = new Path[w*h];
Point[] tracers = new Point[w*h];

//render
RenderMesh render;

//draw modes
boolean showPaths = false;
boolean showPoints = false;

void setup() {
  size(500, 500);
  
  float dw = (width-5)/w;
  float dh = (height-5)/h;
  int n = 0;
  for (int i=0; i<w; i++) {
    for (int j=0; j<h; j++) {
      float x1 = i*dw + 2;
      float y1 = j*dh + 2;
      float dt_ = random(-dt, dt);
      //float dt_ = (random(1) < 0.5) ? -dt : dt;
      paths[n] = new Ellipse(x1 , y1, dw, dh, CORNER);
      paths[n].setFill(false);
      paths[n].setStrokeColor(100);
      tracers[n] = new Tracer(paths[n], random(1), dt_, new Easing() {
        public float val(float t) {
          return t;
        }
      });
      n++;
    }
  }
  
  ArrayList<Point> ts = new ArrayList<Point>();
  for (Point t : tracers) {
    ts.add(t);
  }
  
  render = new RenderMesh(ts, 50);
  render.setStrokeColor(color(25));
  render.setStrokeRamp(50);
}

void draw() {
  background(255);
  
  float rads = t * PI;
  float connectionDist = map(sin(rads), 0, 1, 10, 60);
  t += dt;
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
  showPaths = !showPaths;
  showPoints = !showPoints;
}