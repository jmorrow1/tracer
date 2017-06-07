class Train {
  Path track;
  Point pt;
  float u, du, length1D;
  float w = 5;
  int carCount;
  
  Train(Path track) {
    this.track = track;   
    this.u = 0;
    this.du = 1 / track.getLength();
    this.length1D = 25 / track.getLength();
    this.pt = new Point(0, 0);  
    this.carCount = floor(random(2, 6));
  }
  
  void step() {
    u = Path.remainder(u + du, 1);
  }
  
  void draw(PGraphics g) {
    float v = u;
    
    for (int i=0; i<carCount; i++) {    
      track.trace(pt, v); //compute the coordinate of the back of the train
      float backX = pt.x;
      float backY = pt.y;
      track.trace(pt, Path.remainder(v - length1D, 1)); //compute the coordinate of the front of the train
      float frontX = pt.x;
      float frontY = pt.y;
      
      float cenx = 0.5 * (frontX + backX); //center x of the train car
      float ceny = 0.5 * (frontY + backY); //center y of the train car
      float w = 12; //width of the train car
      float h = 24; //height of the train car
      
      stroke(0);
      fill(255);
      strokeWeight(2);
      rectMode(CENTER);
      
      pushMatrix();
      translate(cenx, ceny);
      rotate(atan2(backY - frontY, backX - frontX));
      rect(0, 0, h, w);
      popMatrix();
      
      v = Path.remainder(v - 1.25*length1D, 1);
    }
  }
}