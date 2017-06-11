import tracer.*;
import tracer.paths.*;

//mouse locked to grid
Point quantizedMouse;

//grid
int cellSqrt = 25;

//tracks
Shape currTrack;
ArrayList<Shape> tracks = new ArrayList<Shape>();

//trains
ArrayList<Train> trains = new ArrayList<Train>();

void settings() {
  size(1200, 675);
}

void setup() {
  noCursor();  
  currTrack = new Shape();
  currTrack.setFill(false);
  quantizedMouse = new Point(quantize(mouseX, 0, cellSqrt), quantize(mouseY, 0, cellSqrt));
  currTrack.addVertex(quantizedMouse);
}

void draw() {
  background(255);
  drawGrid(cellSqrt);
  drawDot(3, color(0), mouseX, mouseY);
  drawDot(5, color(0), quantizedMouse.x, quantizedMouse.y);
  currTrack.draw(g);
  for (Shape track : tracks) {
    track.draw(g);
  }
  for (Train t : trains) {
    t.step();
    t.draw(g);
  }
}

void mouseMoved() {
  quantizedMouse.x = quantize(mouseX, 0, cellSqrt);
  quantizedMouse.y = quantize(mouseY, 0, cellSqrt);
}

void mousePressed() {
  if (mouseButton == LEFT) {   
    Point vtx = new Point(quantizedMouse);
    int i = currTrack.getVertexCount() - 1;
    currTrack.removeVertex(i);
    currTrack.addVertex(i, vtx);

    Point firstVtx = currTrack.getVertex2D(0);
   
    //if track is completed
    if (currTrack.getVertexCount() > 1 && vtx.x == firstVtx.x && vtx.y == firstVtx.y) { 
      //add train to completed track
      trains.add(new Train(currTrack));
      
      //add track to list
      tracks.add(currTrack);
      
      //initialize new track
      currTrack = new Shape();
      currTrack.setFill(false);
    }
    
    currTrack.addVertex(quantizedMouse);
    
  }
}

void drawDot(float strokeWeight, int c, float x, float y) {
  strokeWeight(strokeWeight);
  stroke(c);
  point(x, y);
}

void drawGrid(int cellSqrt) {
  strokeWeight(1);
  stroke(0, 100);

  int x = 0;
  while (x < width) {
    line(x, 0, x, height);
    x += cellSqrt;
  }

  int y = 0;
  while (y < height) {
    line(0, y, width, y);
    y += cellSqrt;
  }
}

static float quantize(float val, float min, float quantum) {
  val -= min;
  val /= quantum;
  val = round(val);
  return min + val * quantum;
}