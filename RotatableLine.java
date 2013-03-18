/**
 * Defines a rotatable line segment specified by polar coordinates
 * centered at a point specified by rectangular coordinates
 * 
 * @author Andrew Chen
 * 
 * Works consulted: Java API - Shape, Rectangle2D, and PathIterator.
 */

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RotatableLine extends RotatableShape{
	
	private int radius;
	private double theta;
	
	/**Class constructor.
	 */
	public RotatableLine(){
		super();
	}
	
	/**Constructs a RotatableLine with a length and angle centered around a point
	 * @param centerX x-coordinate of the midpoint
	 * @param centerY y-coordinate of the midpoint
	 * @param length length of the line segment
	 */
	public RotatableLine(int centerX, int centerY, int length){
		this.radius = length/2;
		this.theta = 0;
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	/**Constructs a RotatableLine defined by two points
	 * @param X1 x-coordinate of the first endpoint
	 * @param Y1 y-coordinate of the first endpoint
	 * @param X2 x-coordinate of the latter endpoint
	 * @param Y2 y-coordinate of the latter endpoint
	 */
	public RotatableLine(int x1, int y1, int x2, int y2){
		this(	(x1+x2)/2,
				(y1+y2)/2,
				(int) Math.sqrt( Math.pow((x1+x2),2) + Math.pow((y1+y2),2) )
		);
	}
	
	/**Constructs a RotatableLine which forms the diagonal defined by the dimensions of a rectangle
	 * @param dimension the dimensions
	 */
	public RotatableLine(Dimension dimension){
		this(0, 0 , (int) dimension.getWidth(), (int) dimension.getHeight());
	}
	
	/**Returns the angle of the line segment
	 * @return angle of the line segment with respect to the x-axis in radians
	 */
	public double getAngle(){
		return theta;
	}
	
	/**Returns the length of the line segment
	 * @return length of the line segment
	 */
	public int getLength(){
		return radius;
	}
	
	/**Sets the length of the line segment
	 * @param length length of the line segment
	 */
	public void setLength(int length){
		this.radius = length;
	}
	
	/**Rotates the line segment in a counterclockwise manner
	 * @param radians angle of rotation in radians 
	 */
	public void rotate(double radians){
		this.theta+=radians;
	}
	
	/**Returns the x coordinate of one endpoint in direction theta
	 * @return the x coordinate of one endpoint in direction theta
	 */
	public int getX1(){
		return this.getCenterX() + (int) (radius*Math.cos(theta));
	}
	
	/**Returns the y coordinate of one endpoint in direction theta
	 * @return  the y coordinate of one endpoint in direction theta
	 */
	public int getY1(){
		return this.getCenterY() + (int) (radius*Math.sin(theta));
	}
	
	/**Returns the x coordinate of one endpoint opposite direction theta
	 * @return the x coordinate of one endpoint opposite direction -theta
	 */
	public int getX2(){
		return this.getCenterX() + (int) (-radius*Math.cos(theta));
	}
	
	/**Returns the y coordinate of one endpoint opposite direction theta
	 * @return  the y coordinate of one endpoint opposite direction theta
	 */
	public int getY2(){
		return this.getCenterY() + (int) (-radius*Math.sin(theta));
	}
	
	/**Returns a string representation of the object.
	 * @return string representation of the object
	 */
	public String toString(){
		return "theta = "+theta+"\n"+
				"r<= "+radius+"\n"+
				"y="+Math.tan(theta);
	}
	
	// Overridden methods implemented by the Shape interface.
	
	/**Tests if a specified Point2D is inside the boundary of the Shape.
	 * @return false always
	 */
	public boolean contains(Point2D p) {
		return false;
	}

	/**Tests if the interior of the Shape entirely contains the specified Rectangle2D.
	 * @return false always
	 */
	public boolean contains(Rectangle2D r) {
		return false;
	}

	/**Tests if the specified coordinates are inside the boundary of the Shape.
	 * @return false always
	 */
	public boolean contains(double x, double y) {
		return false;
	}

	/**Tests if the interior of the Shape entirely contains the specified rectangular area.
	 * @return false always
	 */
	public boolean contains(double x, double y, double w, double h) {
		return false;
	}

	/**Returns a Rectangle that completely encloses the Shape.
	 * @return a Rectangle that completely encloses the Shape.
	 */
	public Rectangle getBounds() {
		return this.getBounds2D().getBounds();
	}

	/**Returns a Rectangle2D that completely encloses the Shape.
	 * @return a Rectangle2D that completely encloses the Shape.
	 */
	public Rectangle2D getBounds2D() {
		int height, width;
		
		int x1 = this.getX1();
		int y1 = this.getY1();
		int x2 = this.getX2();
		int y2 = this.getY2();
		
		if(x1 < x2 || y1 < y2){
			width = x2 - x1;
			height = y2 - y1;
			return new Rectangle(x1, y1 ,width, height);
		}else if(x1 < x2 || y2 < y1){
			width = x2 - x1;
			height = y1 - y2;
			return new Rectangle(x1, y2 ,width, height);
		}else if(x2 < x1 || y1 < y2){
			width = x2 - x1;
			height = y2 - y1;
			return new Rectangle(x2, y1 ,width, height);
		}else{
			width = x1 - x2;
			height = y1 - y2;
			return new Rectangle(x2, y2 ,width, height);
		}
	}

	/**Returns an iterator object that iterates along the Shape boundary and provides access to the geometry of the Shape outline.
	 * @return a new PathIterator object, which independently traverses the geometry of the Shape
	 */
	public PathIterator getPathIterator(final AffineTransform at) {
		final RotatableLine line = this; 
		return new PathIterator(){
			
			private int segment;
			
			public int currentSegment(float[] coords){
				int type;
				if(segment == 0){
					coords[0] = line.getX1();
					coords[1] = line.getY1();
					type = PathIterator.SEG_MOVETO;
				}else{
					coords[0] = line.getX2();
					coords[1] = line.getY2();
					type = PathIterator.SEG_LINETO;
				}
				return type;
			}

			public int currentSegment(double[] coords){
				int type;
				if(segment == 0){
					coords[0] = line.getX1();
					coords[1] = line.getY1();
					type = PathIterator.SEG_MOVETO;
				}else{
					coords[0] = line.getX2();
					coords[1] = line.getY2();
					type = PathIterator.SEG_LINETO;
				}
				return type;
			}

			public int getWindingRule(){
				return PathIterator.WIND_NON_ZERO;
			}

			public boolean isDone(){
				return segment > 1;
			}

			public void next(){
				segment++;
			}
		};
	}
	
	/**Returns an iterator object that iterates along the Shape boundary and provides access to a flattened view of the Shape outline geometry.
	 * @return a new PathIterator object, which independently traverses the geometry of the Shape
	 */
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return getPathIterator(at);
	}

	/**Tests if the interior of the Shape intersects the interior of a specified rectangular area.
	 * @return  true if the interior of the Shape and the interior of the specified Rectangle2D intersect
	 */
	public boolean intersects(Rectangle2D r) {
		return r.intersectsLine(this.getX1(), this.getY1(), this.getX2(), this.getY2());
	}

	/**Tests if the interior of the Shape intersects the interior of a specified Rectangle2D.
	 * @return true if the interior of the Shape and the interior of the specified Rectangle2D intersect
	 */
	public boolean intersects(double x, double y, double w, double h) {
		return intersects(new Rectangle.Double(x, y, w, h));
	}
}
