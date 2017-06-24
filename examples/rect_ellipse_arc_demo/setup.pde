void settings() {
  size(400, 400);
}

void setup() {
  ab = new Point(width/2, height/2);
  cd = new Point(150, 125);

  createPath();

  textSize(16);
}

void createPath() {
  switch (pathMode) {
  case RECT :
    path = new Rect(ab, cd, modes[modeIndex]);
    break;
  case ELLIPSE :
    path = new Ellipse(ab, cd, modes[modeIndex]);
    break;
  case ARC :
    path = new Arc(ab, cd, 0.1f * TWO_PI, 0.7f * TWO_PI, modes[modeIndex]);
    break;
  }
  style(path);
}

void style(Path path) {
  path.setStrokeColor(color(20));
  path.setStrokeWeight(4);
  path.setFill(false);
}