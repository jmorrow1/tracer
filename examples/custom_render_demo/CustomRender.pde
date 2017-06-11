class CustomRender extends Render {
  CustomRender() {
    super();
  }
  
  CustomRender(Point[] pts) {
    super(pts);
  }
  
  CustomRender(Tracer[] pts) {
    super(pts);
  }
  
  CustomRender(Collection<? extends Point> pts) {
    super(pts);
  }
  
  void draw(PGraphics g) {
    style.apply(g);
    for (Point pt : pts) {
      if (pt.y < height/2) {
        stroke(200, 100, 100);
      }
      else {
        stroke(100, 200, 200);
      }
      point(pt.x, pt.y);
    }
  }
}