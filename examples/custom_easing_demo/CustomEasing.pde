class CustomEasing implements Easing {
  
  float noiseScale;
  
  CustomEasing(float noiseScale) {
    this.noiseScale = noiseScale;
  }
  
  float val(float u) {
    if (u < 0.5) {
      return noise(noiseScale*u);
    }
    else {
      return noise(noiseScale * (0.5 - (u - 0.5)));
    }
  }
}