# Tracer
A Processing library for creating procedural animations, currently in alpha.

# Basics
## Paths
Paths are basically curves in 2-dimensional space.

The main power of Paths is that they map a 1-dimensional coordinate to a 2-dimensional coordinate using the trace() method. The method trace() takes a single normal value (a value between 0 and 1) and returns a Point in 2-dimensional space.

``` {.java}
float x = 0.5f;
Point pt = path.trace(x); //pt is set to the 2-D coordinate halfway along the path
```

If you want to create an animation, for efficiency you may want to avoid allocating a new Point every time you trace a path. For this use case, there is another version of trace() that takes a Point and a 1-D value. Instead of returning a Point, this version of trace() stores the result of the computation in the given Point.

``` {.java}
float x = 0.5f;
Point pt = new Point(0, 0);
path.trace(pt, x); //pt is set to the same coordinate as in the previous example
```

To draw paths, use the draw() method.

``` {.java}
PGraphics g = this.g;
path.draw(g); //draws the path to the PGraphics object
```

## Tracers
A Tracer is like a Point that moves along a Path.

Tracers provide a way of creating animations with tracer.

## Easings
Easings are tweening curves that control the speed of an animation over time. For an introduction to easing functions, try [this](http://gizma.com/easing/).

## Renders
Renders provide a way of creating complex forms and animations with tracer.

A Render stores a List of Tracers and draws them in some way.

``` {.java}
List<Tracer> ts = new ArrayList<Tracer>();
Render r = new RenderShape(ts);
PGraphics g = this.g;
r.draw(g); //draws a polygonal form with the List of Tracers as vertices
           //using Processing's beginShape() and endShape()
```

# Documentation
[Javadoc](http://jamesmorrowdesign.com/tracer/doc/index.html)

# Installation
For use with the PDE: Add a folder called tracer to the libraries folder located in your sketchbook. Copy the folder called library from this repo to the tracer folder. After restarting Processing, tracer should be available to your sketch after adding an import statement.

``` {.java}
import paths.*;
import tracer.*;
import ease.*;
import render.*;
```

For use outside of the PDE, add tracer.jar along with the jar files associated with Processing 3 to your build path. 
