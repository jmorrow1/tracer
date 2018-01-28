import controlP5.*;

public static class Gui extends PApplet {  
  ControlP5 cp5;
  boolean receiveEvents;
  
  public void settings() {
    size(500, 250);
  }
  
  public void setup() {
    cp5 = new ControlP5(this);
    
    cp5.addSlider("component count")       
       .setMin(1)
       .setMax(10)
       .setNumberOfTickMarks(10)
       .setHeight(15)
       .setPosition(10, 10)
       .setValue(fractal_spirograph_construction_tool.componentCount)
       ;
       
    cp5.addSlider("radius decay")
       .setMin(0.1)
       .setMax(1)
       .setHeight(15)
       .setPosition(10, 40)
       .setValue(fractal_spirograph_construction_tool.radiusDecay)
       ;
       
    cp5.addSlider("base radius")
       .setMin(0.25 * min(WIDTH, HEIGHT))
       .setMax(0.5 * max(WIDTH, HEIGHT))
       .setHeight(15)
       .setPosition(10, 70)
       .setValue(fractal_spirograph_construction_tool.baseRadius)
       ;
       
    cp5.addSlider("base trace speed")
       .setMin(0.00001)
       .setMax(0.0005)
       .setHeight(15)
       .setPosition(10, 100)
       .setValue(fractal_spirograph_construction_tool.baseTraceSpeed)
       ;
       
    cp5.addSlider("trace speed multiplier")
       .setMin(-10)
       .setMax(10)
       .setNumberOfTickMarks(21)
       .setHeight(15)
       .setPosition(10, 130)
       .setValue(fractal_spirograph_construction_tool.traceSpeedMultiplier)
       ;
       
    cp5.addSlider("path type")
       .setMin(0)
       .setMax(5)
       .setNumberOfTickMarks(6)
       .setHeight(15)
       .setPosition(10, 160)
       .setValue(fractal_spirograph_construction_tool.pathType)
       ;
       
    cp5.addSlider("easing")
       .setMin(0)
       .setMax(Easings.getEasingCount()-1)
       .setPosition(10, 190)
       .setHeight(15)
       .setNumberOfTickMarks(Easings.getEasingCount())
       .setValue(fractal_spirograph_construction_tool.easingIndex);
       ;
       
    cp5.addSlider("offset 1d")
       .setMin(0)
       .setMax(1)
       .setPosition(10, 220)
       .setHeight(15)
       .setValue(fractal_spirograph_construction_tool.offset1d);
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
       
    cp5.addSlider("stpes per frame")
       .setMin(1)
       .setMax(100)
       .setNumberOfTickMarks(100)
       .setHeight(15)
       .setPosition(250, 100)
       .setValue(fractal_spirograph_construction_tool.stepsPerFrame)
       ;
       
    cp5.addSlider("poly order")
       .setMin(3)
       .setMax(15)
       .setNumberOfTickMarks(13)
       .setHeight(15)
       .setPosition(250, 130)
       .setValue(fractal_spirograph_construction_tool.polyOrder)
       ;

    cp5.addToggle("draw components")
       .setPosition(250, 160)
       .setHeight(15)
       .setValue(fractal_spirograph_construction_tool.drawComponents ? 1 : 0)
       ;
       
    cp5.addToggle("fill shape")
       .setPosition(250, 200)
       .setHeight(15)
       .setValue(fractal_spirograph_construction_tool.fillShape ? 1 : 0)
       ;
       
    cp5.addButton("save sketch")
       .setPosition(420, height-20)
       .setWidth(70)
       .setHeight(15)
       ;
    
    receiveEvents = true;
  }
  
  public void draw() {
    background(10);
  }
  
  public void controlEvent(ControlEvent e) {
    if (receiveEvents) {
      switch (e.getController().getName()) {
        case "component count" : 
          fractal_spirograph_construction_tool.componentCount = (int)e.getValue();
          break;
        case "radius decay" : 
          fractal_spirograph_construction_tool.radiusDecay = e.getValue();
          break;
        case "base radius" :
          fractal_spirograph_construction_tool.baseRadius = e.getValue();
          break;
        case "base trace speed" :
          fractal_spirograph_construction_tool.baseTraceSpeed = e.getValue();
          break;
        case "trace speed multiplier" :
          fractal_spirograph_construction_tool.traceSpeedMultiplier = (int)e.getValue();
          break;
        case "path type" :
          fractal_spirograph_construction_tool.pathType = (int)e.getValue();
          break;
        case "easing" :
          fractal_spirograph_construction_tool.easingIndex = (int)e.getValue();
          break;
        case "offset 1d" :
          fractal_spirograph_construction_tool.offset1d = e.getValue();
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
        case "draw components" :
          fractal_spirograph_construction_tool.drawComponents = e.getValue() != 0;
          return;
        case "steps per frame" :
          fractal_spirograph_construction_tool.stepsPerFrame = (int)e.getValue();
          break;
        case "poly order" :
          fractal_spirograph_construction_tool.polyOrder = (int)e.getValue();
          break;
        case "fill shape" :
          fractal_spirograph_construction_tool.fillShape = e.getValue() != 0;
          fractal_spirograph_construction_tool.spirograph.setFill(e.getValue() != 0);
          fractal_spirograph_construction_tool.spirograph.setStroke(e.getValue() == 0);
          return;
          
        case "save sketch" :
          fractal_spirograph_construction_tool.saveSketch = true;
          return; //don't call start over
          
      }
      fractal_spirograph_construction_tool.startOver();
    }
  }
}