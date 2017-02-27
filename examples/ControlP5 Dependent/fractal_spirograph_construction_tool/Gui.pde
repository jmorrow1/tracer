import controlP5.*;

public static class Gui extends PApplet {
  public static void main(String[] args) {
    PApplet.main(Gui.class.getName());
  }
  
  ControlP5 cp5;
  boolean receiveEvents;
  
  public void settings() {
    size(500, 200);
  }
  
  public void setup() {
    cp5 = new ControlP5(this);
    
    cp5.addSlider("componentCount")       
       .setMin(1)
       .setMax(10)
       .setNumberOfTickMarks(10)
       .setHeight(15)
       .setPosition(10, 10)
       .setValue(fractal_spirograph_construction_tool.componentCount)
       ;
       
    cp5.addSlider("radiusDecay")
       .setMin(0.1)
       .setMax(1)
       .setHeight(15)
       .setPosition(10, 40)
       .setValue(fractal_spirograph_construction_tool.radiusDecay)
       ;
       
    cp5.addSlider("baseRadius")
       .setMin(0.25 * min(WIDTH, HEIGHT))
       .setMax(0.5 * max(WIDTH, HEIGHT))
       .setHeight(15)
       .setPosition(10, 70)
       .setValue(fractal_spirograph_construction_tool.baseRadius)
       ;
       
    cp5.addSlider("baseTraceSpeed")
       .setMin(0.00001)
       .setMax(0.0005)
       .setHeight(15)
       .setPosition(10, 100)
       .setValue(fractal_spirograph_construction_tool.baseTraceSpeed)
       ;
       
    cp5.addSlider("traceSpeedMultiplier")
       .setMin(-10)
       .setMax(10)
       .setNumberOfTickMarks(21)
       .setHeight(15)
       .setPosition(10, 130)
       .setValue(fractal_spirograph_construction_tool.traceSpeedMultiplier)
       ;
       
    cp5.addSlider("pathType")
       .setMin(0)
       .setMax(4)
       .setNumberOfTickMarks(5)
       .setHeight(15)
       .setPosition(10, 160)
       .setValue(fractal_spirograph_construction_tool.pathType)
       ;
       
    cp5.addSlider("freqX")
       .setMin(1)
       .setMax(10)
       .setNumberOfTickMarks(10)
       .setHeight(15)
       .setPosition(250, 10)
       .setValue(fractal_spirograph_construction_tool.freqX)
       ;
       
    cp5.addSlider("freqY")
       .setMin(1)
       .setMax(10)
       .setNumberOfTickMarks(10)
       .setHeight(15)
       .setPosition(250, 40)
       .setValue(fractal_spirograph_construction_tool.freqY)
       ;
       
    cp5.addSlider("phi")
       .setMin(0)
       .setMax(360)
       .setHeight(15)
       .setPosition(250, 70)
       .setValue(fractal_spirograph_construction_tool.freqY)
       ;
       
    cp5.addSlider("stepsPerFrame")
       .setMin(1)
       .setMax(50)
       .setNumberOfTickMarks(50)
       .setHeight(15)
       .setPosition(250, 100)
       .setValue(fractal_spirograph_construction_tool.stepsPerFrame)
       ;
       
    cp5.addToggle("drawComponents")
       .setPosition(250, 130)
       .setValue(fractal_spirograph_construction_tool.drawComponents ? 1 : 0)
       ;
    
    receiveEvents = true;
  }
  
  public void draw() {
    background(10);
  }
  
  public void controlEvent(ControlEvent e) {
    if (receiveEvents) {
      switch (e.getController().getName()) {
        case "componentCount" : 
          fractal_spirograph_construction_tool.componentCount = (int)e.getValue();
          break;
        case "radiusDecay" : 
          fractal_spirograph_construction_tool.radiusDecay = e.getValue();
          break;
        case "baseRadius" :
          fractal_spirograph_construction_tool.baseRadius = e.getValue();
          break;
        case "baseTraceSpeed" :
          fractal_spirograph_construction_tool.baseTraceSpeed = e.getValue();
          break;
        case "traceSpeedMultiplier" :
          fractal_spirograph_construction_tool.traceSpeedMultiplier = (int)e.getValue();
          break;
        case "pathType" :
          fractal_spirograph_construction_tool.pathType = (int)e.getValue();
          break;
        case "freqX" :
          fractal_spirograph_construction_tool.freqX = (int)e.getValue();
          break;
        case "freqY" :
          fractal_spirograph_construction_tool.freqY = (int)e.getValue();
          break;
        case "phi" :
          fractal_spirograph_construction_tool.phi = radians(e.getValue());
          break;
        case "drawComponents" :
          fractal_spirograph_construction_tool.drawComponents = e.getValue() != 0;
          return;
        case "stepsPerFrame" :
          fractal_spirograph_construction_tool.stepsPerFrame = (int)e.getValue();
          break;
      }
      fractal_spirograph_construction_tool.startOver();
    }
  }
}