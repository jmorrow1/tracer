package traceables;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 * @param <T>
 * @param <U>
 */
public class Composite<T extends IPath, U extends IPath> extends Path {
	private T a;
	private U b;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Composite(T a, U b) {
		this.a = a;
		this.b = b;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void trace(Point pt, float amt) {
		if (amt < 0.5f) {
			a.trace(pt, 2f*amt);
		}
		else {
			b.trace(pt, 2f*(amt - 0.5f));
		}
	}

	@Override
	public void display(PApplet pa) {
		a.display(pa);
		b.display(pa);
	}

	@Override
	public void translate(float dx, float dy) {
		a.translate(dx, dy);
		b.translate(dx, dy);
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/

	public T getA() {
		return a;
	}
	
	public void setA(T a) {
		this.a = a;
	}
	
	public U getB() {
		return b;
	}
	
	public void setB(U b) {
		this.b = b;
	}
}
