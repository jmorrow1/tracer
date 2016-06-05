package demos;

import functions.Polygonize;
import paths.*;
import paths2.*;
import processing.core.PApplet;

public class Blending2D extends PApplet {
	private Blender<Blender, Blender> p1;
	private Path p2;
	private Point pt = new Point(0, 0);
	private float amt = 0;
	
	public static void main(String[] args) {
		PApplet.main("demos.Blending2D");
	}
	
	public void settings() {
		size(600, 600, P2D);
	}
	
	public void setup() {
		float cenx = width/2;
		float ceny = height/2;
		float r = 120;
		p1 = new Blender(new Blender(new InfinitySymbol(cenx, ceny, r, 0.75f*r, 50),
				                     new Superellipse(cenx, ceny, r, r, 0.4f, 50), 0.5f, 75),
				         new Blender(new Ellipse(cenx, ceny, 2*r, r, RADIUS),
				        		     new InfinitySymbol(cenx, ceny, r, 1.25f*r, 50), 0.5f, 75),
				         0.5f,
				         150);
		p2 = new InfinitySymbol(cenx, ceny, width/2f, width/4f, 50);
		//p2 = new Flower(cenx, ceny, width/2f, 3, 4, 100);
	}
	
	public void draw() {
		background(255);
		
		p2.trace(pt, amt);
		dot(pt.x, pt.y);
		amt = (amt + 0.005f) % 1f;
		
		stroke(0);
		noFill();
		float blendAmt1 = map(pt.x, 0, width, 0, 1);
		Blender a = p1.getA();
		a.setBlendAmt(blendAmt1);
		
		float blendAmt2 = map(pt.y, 0, height, 0, 1);
		Blender b = p1.getB();
		b.setBlendAmt(blendAmt2);
		
		p1.display(this);
	}
	
	private void dot(float x, float y) {
		noStroke();
		fill(50);
		ellipseMode(CENTER);
		ellipse(x, y, 10, 10);
	}
}
