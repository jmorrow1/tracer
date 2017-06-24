void draw() {
  background(255);

  //display ellipse/rect mode
  textAlign(CENTER, TOP);
  fill(0);
  text("< " + Ellipse.ellipseModeToString(modes[modeIndex]) + " Mode"+ " >", width/2, 10);

  //draw the path
  path.draw(g);

  //draw the control points
  drawDot(ab);
  drawDot(cd);

  //display information about what the mouse is doing
  textAlign(CENTER, BOTTOM);
  fill(0);
  text(mouseBehavior, width/2, height - 10);
}

void drawDot(Point pt) {
  strokeWeight(8);
  stroke(0);
  point(pt.x, pt.y);
}

String abToString() {
  switch (modes[modeIndex]) {
  case CORNER:
  case CORNERS:
    return "left corner";
  case CENTER:
  case RADIUS:
    return "center";
  default :
    return "nothing";
  }
}

String cdToString() {
  switch (modes[modeIndex]) {
  case CORNERS:
    return "right corner";
  case CORNER:
  case CENTER:
    return "width and height";
  case RADIUS:
    return "x-radius and y-radius";
  default:
    return "nothing";
  }
}