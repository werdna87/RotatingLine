/**
 * Provides definitions for Rotatable Shape objects
 * @author Andrew Chen
 */
	
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;

public abstract class RotatableShape implements Rotatable, Shape{
	
	protected int centerX;
	protected int centerY;
	
	/**Class constructor.*/
	public RotatableShape(){
		this(0, 0, 0, 0);
	}
	
	/**Constructs a RotatableShape fitting within the bounds of a rectangle with a diagonal defined by two points
	 * @param X1 x-coordinate of the first endpoint
	 * @param Y1 y-coordinate of the first endpoint
	 * @param X2 x-coordinate of the latter endpoint
	 * @param Y2 y-coordinate of the latter endpoint
	 */
	public RotatableShape(int x1, int y1, int x2, int y2){}
	
	/**Constructs a RotatableShape fitting within the bounds of the dimensions
	 * @param dimensions dimensions of a plane containing the RotatableShape
	 */
	public RotatableShape(Dimension dimension){}
	
	
	/**Returns the x-coordinate of the center
	 * @return x-coordinate of the center
	 */
	public int getCenterX(){
		return centerX;
	}
	
	/**Returns the y-coordinate of the center
	 * @return y-coordinate of the center
	 */
	public int getCenterY(){
		return centerY;
	}
	
	/**Sets the x-coordinate of the center
	 * @param centerX the x-coordinate of the center
	 */
	public void setCenterX(int centerX){
		this.centerX = centerX;
	}
	
	/**Sets the y-coordinate of the center
	 * @param centerY the y-coordinate of the center
	 */
	public void setCenterY(int centerY){
		this.centerY = centerY;
	}
	
	public abstract void rotate(double radians);
		
	public abstract boolean contains(Point2D p);
	public abstract boolean contains(Rectangle2D r);
	public abstract boolean contains(double x, double y);
	public abstract boolean contains(double x, double y, double w, double h);
	public abstract Rectangle getBounds();
	public abstract Rectangle2D getBounds2D();
	public abstract PathIterator getPathIterator(AffineTransform at);
	public abstract PathIterator getPathIterator(AffineTransform at, double flatness);
	public abstract boolean intersects(Rectangle2D r);
	public abstract boolean intersects(double x, double y, double w, double h);
	
}
