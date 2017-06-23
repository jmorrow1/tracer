class Spiral extends Path {
  float x, y, bigRadius, smallRadius;
  int rotationCount;
  
  Spiral(float x, float y, float bigRadius, float smallRadius, int rotationCount) {
    this.x = x;
    this.y = y;
    this.bigRadius = bigRadius;
    this.smallRadius = smallRadius;
    this.rotationCount = rotationCount;
  }
  
  //copy constructor:
  Spiral(Spiral s) {
    this(s.x, s.y, s.bigRadius, s.smallRadius, s.rotationCount);
  }
 
  //the trace(target, u) method is how a Path maps 1D (normalized floating point coordinates) to 2D coordinates
  @Override
  void trace(Point target, float u) {
    if (u < 0.0 || u > 1.0) {
      u = Path.remainder(u, 1.0); //the argument is out of range (not a normalized float value), so we want to wrap it into range
    }
    
    //compute
    float angle = u * TWO_PI * rotationCount;
    float radius = map(u, 0, 1, bigRadius, smallRadius);
    
    //set target
    target.x = x + radius * cos(angle);
    target.y = y + radius * sin(angle);
    
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
    if (i == 0) {
      return 0; //Sprials contain a gap at 0.
    }
    else {
      return -1; //Invalid index; return error code
    }
  }
  
  //the getGapCount() method returns the number of gaps in the Path
  @Override
  int getGapCount() {
    return 1; //Spirals contain 1 gap
  }
  
  //the clone() method creates a copy of the Path and returns it
  @Override
  Spiral clone() {
    return new Spiral(this);
  }
}