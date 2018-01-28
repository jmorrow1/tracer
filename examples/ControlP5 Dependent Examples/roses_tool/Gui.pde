import controlP5.*;

public static class Gui extends PApplet {
  public static void main(String[] args) {
    PApplet.main(Gui.class.getName());
  }
  
  ControlP5 cp5;
  boolean receiveEvents;
  
  public void settings() {
    size(200, 275);
  }
  
  public void setup() {
    cp5 = new ControlP5(this);
    
    cp5.addSlider("renderMode")
       .setMin(0)
       .setMax(3)
       .setNumberOfTickMarks(4)
       .setValue(roses_tool.renderMode)
       .setHeight(15)
       .setPosition(10, 10);
       ;
    
    cp5.addSlider("freq1")
       .setMin(1)
       .setMax(20)
       .setNumberOfTickMarks(20)
       .setValue(roses_tool.freq1)
       .setHeight(15)
       .setPosition(10, 40)
       ;
       
    cp5.addSlider("freq2")
       .setMin(1)
       .setMax(20)
       .setNumberOfTickMarks(20)
       .setValue(roses_tool.freq2)
       .setHeight(15)
       .setPosition(10, 70)
       ;
       
    cp5.addSlider("tracerCount")
       .setMin(1)
       .setMax(50)
       .setNumberOfTickMarks(50)
       .setValue(roses_tool.tracerCount)
       .setHeight(15)
       .setPosition(10, 100)
       ;
       
    cp5.addSlider("speed")
       .setMin(0.00001)
       .setMax(0.001)
       .setValue(roses_tool.speed)
       .setHeight(15)
       .setPosition(10, 130)
       ;
       
    cp5.addToggle("drawMetapaths")
       .setValue(roses_tool.drawMetapaths ? 1 : 0)
       .setHeight(15)
       .setPosition(10, 160)
       ;
       
    cp5.addToggle("drawRender")
       .setValue(roses_tool.drawRender ? 1 : 0)
       .setHeight(15)
       .setPosition(100, 160)
       ;

    receiveEvents = true;
  }
  
  public void draw() {
    background(10);
  }
  
  public void controlEvent(ControlEvent e) {
    if (receiveEvents) {
      switch (e.getController().getName()) {
        case "renderMode" :
          roses_tool.renderMode = (int)e.getValue();
          break;
        case "freq1" :
          roses_tool.freq1 = (int)e.getValue();
          break;
        case "freq2" :
          roses_tool.freq2 = (int)e.getValue();
          break;
        case "tracerCount" :
          roses_tool.tracerCount = (int)e.getValue();
          break;
        case "speed" :
          roses_tool.speed = e.getValue();
          break;
        case "drawMetapaths" :
          roses_tool.drawMetapaths = e.getValue() != 0;
          break;
        case "drawRender" :
          roses_tool.drawRender = e.getValue() != 0;
          break;
      }
      roses_tool.startOver();
    }
  }
}