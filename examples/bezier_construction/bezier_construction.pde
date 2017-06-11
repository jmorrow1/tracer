import tracer.*;
import tracer.paths.*;

//paths
Point[][] pts;
Shape bezier;

//animation
float amt;
float dAmt = 0.005f;
boolean constructBezier = true;

//draw mode
boolean randomStartingConditions = false;
boolean drawBezier = true; //if false, the sketch will just draw the apparatus that creates the bezier and not the bezier itself

//parameters
float radius = 200;
int order = 5; //order == 3 cooresponds to a cubic bezier, order == 4 cooresponds to a quadratic bezier, etc.

void setup() {
  size(600, 600, P2D);
  
  Point[] shell = randomStartingConditions ? makeRandomPoints(order) : makeRegularPolyline(width/2, height/2, radius, order);
  pts = fromInput(shell);
  bezier = new Shape();
  bezier.setStrokeColor(color(255, 0, 0));
  bezier.setStrokeWeight(4);
}

Point[] makeRandomPoints(int n) {
  Point[] pts = new Point[n];
  for (int i=0; i<n; i++) {
    pts[i] = new Point(random(width), random(height));
  }
  return pts;
}

static Point[] makeRegularPolyline(float x, float y, float r, int n) {
  Point[] pts = new Point[n];
  for (int i=0; i<n; i++) {
    float angle = PApplet.map(i, 0, n, 0, TWO_PI);
    pts[i] = new Point(x + r*PApplet.cos(angle), y + r*PApplet.sin(angle));
  }
  return pts;
}

static Point[][] fromInput(Point[] input) {
  Point[][] pts = new Point[input.length][];
  pts[0] = input;
  for (int i=1; i<pts.length; i++) {
    pts[i] = new Point[pts.length-i];
    for (int j=0; j<pts[i].length; j++) {
      pts[i][j] = new Point(0, 0);
    }
  }
  return pts;
}

void draw() {
  background(255);
  strokeWeight(4);
  
  if (drawBezier) {
    bezier.draw(g);
  }
  
  strokeWeight(2);
  stroke(0);
  fill(0);
  ellipseMode(CENTER);
  
  for (int i=0; i<pts.length-1; i++) {
    for (int j=0; j<pts[i].length-1; j++) {
      //draw
      line(pts[i][j].x, pts[i][j].y, pts[i][j+1].x, pts[i][j+1].y);
      
      //trace
      Line.trace(pts[i+1][j], pts[i][j], pts[i][j+1], amt); //trace the line segment defined by pts[i][j] and pts[i][j+1] by the amt and store the result in pts[i+1][j]
      ellipse(pts[i+1][j].x, pts[i+1][j].y, 10, 10); //draw an dot positioned at the result of Line.trace()
    }
  }
  
  if (constructBezier) {
    int i = pts.length-1;
    int j = pts[i].length-1;
    Point pt = new Point(pts[i][j]); //this point is the culmination of the whole procedure
    bezier.addVertex(pt);
  }
 
  amt += dAmt;
  if (amt > 1) {
    constructBezier = false;
    amt--;
  }
}