class Square extends Path {
  float x, y, radius;
  
  Square(float x, float y, float sqrt) {
    this.x = x;
    this.y = y;
    this.radius = 0.5 * sqrt;
  }
  
  //copy constructor:
  Square(Square s) {
    this(s.x, s.y, 2.0 * s.radius);
  }
 
  //the trace(target, u) method is how a Path maps 1D (normalized floating point coordinates) to 2D coordinates
  @Override
  void trace(Point target, float u) {
    if (u < 0.0 || u > 1.0) {
      u = Path.remainder(u, 1.0); //the argument is out of range (not a normalized float value), so we want to wrap it into range
    }
    
    if (u < 0.25) {
      //position the target somewhere along the right edge of the square
      u = 4.0 * u; //re-normalize the 1D coordinate to map it to the right edge of the square
      target.x = x + radius;
      target.y = lerp(y - radius, y + radius, u);
    }
    else if (u < 0.5) {
      //position the target somewhere along the bottom edge of the square
      u = 4.0 * (u - 0.25); //re-normalize the 1D coordinate to map it to the bottom edge of the square
      target.x = lerp(x + radius, x - radius, u);
      target.y = y + radius;
    }
    else if (u < 0.75) {
      //position the target somewhere along the left edge of the square
      u = 4.0 * (u - 0.5); //re-normalize the 1D coordinate to map it to the left edge of the square
      target.x = x - radius;
      target.y = lerp(y + radius, y - radius, u);
    }
    else if (u < 1.0) {
      //position the target somewhere along the top edge of the square
      u = 4.0 * (u - 0.75); //re-normalize the 1D coordinate to map it to the top edge of the square
      target.x = lerp(x - radius, x + radius, u);
      target.y = y - radius;
    }
  }
  
  //it's not neccessary to implement a draw method. The base class will do it automatically.
  //but there are cases where it will be more accurate and efficient to override the draw method.
  @Override
  void draw(PGraphics g) {
    style.apply(g); //apply style settings
    rectMode(RADIUS);
    rect(x, y, radius, radius);
  }
  
  //the translate(dx, dy) method shifts the Path dx units in the x-direction and dy units in the y-direction
  @Override
  void translate(float dx, float dy) {
    this.x += dx;
    this.y += dy;
  }
  
  //the getGap(i) method returns a normalized floating point value giving the 1D location of the ith gap in the Path.
  @Override
  float getGap(int i) {
    return -1; //Squares contain no gaps. Return the error code -1. If there was an ith gap, we would return the 1D location of the gap.
  }
  
  //the getGapCount() method returns the number of gaps in the Path
  @Override
  int getGapCount() {
    return 0; //Squares contains zero gaps, they are completely continuous.
  }
  
  //the clone() method creates a copy of the Path and returns it
  @Override
  Square clone() {
    return new Square(this);
  }
}