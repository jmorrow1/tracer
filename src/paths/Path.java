package paths;

/**
 * 
 * @author James Morrow
 *
 */
public abstract class Path implements IPath {
	protected boolean reversed;
	
	public boolean isReversed() {
		return reversed;
	}
	
	@Override
	public void reverse() {
		reversed = !reversed;
	}
}
