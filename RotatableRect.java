/**Defines a rotatable rectangle specified by two sides, an angle, and a center
 * centered at a point specified by rectangular coordinates
 * 
 * All boolean methods inherited from the Shape interface DO NOT WORK! Actually. Don't use them.
 * as of 2013-03-05.
 * 
 * @author Andrew Chen
 */

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class RotatableRect extends RotatableShape{
	
	private int radius;
	private double phi;
	private double theta;
	
	private static double RIGHT_ANGLE = Math.PI/2;
	private static double HALF_CIRCLE = Math.PI;
	
	/**Class constructor.
	 */
	public RotatableRect(){
	}
	
	/**Constructs a RotatableRect
	 * @param centerX x-coordinate of the midpoint
	 * @param centerY y-coordinate of the midpoint
	 * @param diagnol length of the diagnol
	 * @param angle angle between two vectors originating from the center to two adjacent vertices
	 */
	public RotatableRect(int centerX, int centerY, int diagnol, double angle){
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = diagnol/2;
		this.phi = angle;
	}
	
	/**Constructs a RotatableShape defined by two points of a diagnol
	 * @param X1 x-coordinate of the first endpoint
	 * @param Y1 y-coordinate of the first endpoint
	 * @param X2 x-coordinate of the latter endpoint
	 * @param Y2 y-coordinate of the latter endpoint
	 */
	public RotatableRect(int x1, int y1, int x2, int y2) {
		this(	(x1+x2)/2,
				(y1+y2)/2,
				(int) Math.min(x2, y2),
				RIGHT_ANGLE
		);
	}

	/**Constructs a RotatableShape which forms the diagonal defined by the dimensions of a rectangle
	 * @param dimension the dimensions
	 */
	public RotatableRect(Dimension dimension) {
		this(0, 0 , (int) dimension.getWidth(), (int) dimension.getHeight());
	}

	/**Returns the angle of the center to the median of the short side
	 * @return angle of the center to the median of the short side
	 */
	public double getAngle(){
		return this.theta;
	}
	
	/**Returns the angle between two vectors originating from the center to two adjacent vertices
	 * @return angle between two vectors originating from the center to two adjacent vertices
	 */
	public double getInnerAngle(){
		return this.theta;
	}
	
	/**Returns the length of the longest side
	 * @return length of the long side
	 */
	public int getLength(){
		return this.radius;
	}
	
	/**Returns the x coordinate of the first vertex
	 * @return the x coordinate of the first vertex
	 */
	public int getX1(){
		return this.getCenterX() + (int) (radius*Math.cos(theta));
	}
	
	/**Returns the y coordinate of the first vertex
	 * @return  the y coordinate of the first vertex
	 */
	public int getY1(){
		return this.getCenterY() + (int) (radius*Math.sin(theta));
	}
	
	/**Returns the x coordinate of the second vertex
	 * @return the x coordinate of the second vertex
	 */
	public int getX2(){
		return this.getCenterX() + (int) (radius*Math.cos(theta + phi));
	}
	
	/**Returns the y coordinate of the second vertex
	 * @return  the y coordinate of the second vertex
	 */
	public int getY2(){
		return this.getCenterY() + (int) (radius*Math.sin(theta + phi));
	}
	
	/**Returns the x coordinate of the third vertex
	 * @return the x coordinate of the third vertex
	 */
	public int getX3(){
		return this.getCenterX() + (int) (radius*Math.cos(theta + HALF_CIRCLE));
	}
	
	/**Returns the y coordinate of the third vertex
	 * @return  the y coordinate of the third vertex
	 */
	public int getY3(){
		return this.getCenterY() + (int) (radius*Math.sin(theta + HALF_CIRCLE));
	}
	
	/**Returns the x coordinate of the fourth vertex
	 * @return the x coordinate of the fourth vertex
	 */
	public int getX4(){
		return this.getCenterX() + (int) (radius*Math.cos(theta + HALF_CIRCLE + phi));
	}
	
	/**Returns the y coordinate of the fourth vertex
	 * @return  the y coordinate of the fourth vertex
	 */
	public int getY4(){
		return this.getCenterY() + (int) (radius*Math.sin(theta + HALF_CIRCLE + phi));
	}
	
	/**Sets the length of the diagnol
	 * @param length length of diagnol
	 */
	public void setLength(int length){
		this.radius = length;
	}
	
	/**Sets the inner angle of the 
	 * @param angle angle between two vectors originating from the center to two adjacent vertices
	 */
	public void setInnerAngle(double angle){
		this.phi = angle;
	}
	
	/**Rotates the line segment in a counterclockwise manner
	 * @param radians angle of rotation in radians 
	 */
	public void rotate(double radians){
		this.theta+=radians;
	}

	// Overridden methods implemented by the Shape interface.
	// All boolean methods DO NOT WORK

	/**Tests if a specified Point2D is inside the boundary of the Shape.
	 * @return false always
	 */
	public boolean contains(Point2D p){
		return false;
	}

	/**Tests if the interior of the Shape entirely contains the specified Rectangle2D.
	 * @return false always
	 */
	public boolean contains(Rectangle2D r){
		return false;
	}

	/**Tests if the specified coordinates are inside the boundary of the Shape.
	 * @return false always
	 */
	public boolean contains(double x, double y){
		return false;
	}

	/**Tests if the interior of the Shape entirely contains the specified rectangular area.
	 * @return false always
	 */
	public boolean contains(double x, double y, double w, double h){
		return false;
	}

	/**Returns a Rectangle that completely encloses the Shape.
	 * @return a Rectangle that completely encloses the Shape.
	 */
	public Rectangle getBounds(){
		return this.getBounds2D().getBounds();
	}

	/**Returns a Rectangle2D that completely encloses the Shape.
	 * @return a Rectangle2D that completely encloses the Shape.
	 */
	public Rectangle2D getBounds2D(){
		int x1 = Math.min(Math.min(this.getX1(),this.getX2()), Math.min(this.getX3(),this.getX4()));
		int y1 = Math.min(Math.min(this.getY1(),this.getY2()), Math.min(this.getY3(),this.getY4()));
		int x2 = Math.max(Math.max(this.getX1(),this.getX2()), Math.max(this.getX3(),this.getX4()));
		int y2 = Math.max(Math.max(this.getY1(),this.getY2()), Math.max(this.getY3(),this.getY4()));
		
		return new Rectangle(x1, y1, x2-x1, y2-y1);
	}

	/**Returns an iterator object that iterates along the Shape boundary and provides access to the geometry of the Shape outline.
	 * @return a new PathIterator object, which independently traverses the geometry of the Shape
	 */
	public PathIterator getPathIterator(final AffineTransform at){
		final RotatableRect line = this; 
		return new PathIterator(){

			private int segment;

			public int currentSegment(float[] coords){
				int type;
				if(segment == 0){
					coords[0] = line.getX1();
					coords[1] = line.getY1();
					type = PathIterator.SEG_MOVETO;
				}else if(segment == 1){
					coords[0] = line.getX2();
					coords[1] = line.getY2();
					type = PathIterator.SEG_LINETO;
				}else if(segment == 2){
					coords[0] = line.getX3();
					coords[1] = line.getY3();
					type = PathIterator.SEG_LINETO;
				}else if(segment == 3){
					coords[0] = line.getX4();
					coords[1] = line.getY4();
					type = PathIterator.SEG_LINETO;
				}else if(segment == 4){
					coords[0] = line.getX1();
					coords[1] = line.getY1();
					type = PathIterator.SEG_LINETO;
				}else{
					type = SEG_CLOSE;
				}
				return type;
			}

			public int currentSegment(double[] coords){
				int type;
				if(segment == 0){
					coords[0] = line.getX1();
					coords[1] = line.getY1();
					type = PathIterator.SEG_MOVETO;
				}else if(segment == 1){
					coords[0] = line.getX2();
					coords[1] = line.getY2();
					type = PathIterator.SEG_LINETO;
				}else if(segment == 2){
					coords[0] = line.getX3();
					coords[1] = line.getY3();
					type = PathIterator.SEG_LINETO;
				}else if(segment == 3){
					coords[0] = line.getX4();
					coords[1] = line.getY4();
					type = PathIterator.SEG_LINETO;
				}else if(segment == 4){
					coords[0] = line.getX1();
					coords[1] = line.getY1();
					type = PathIterator.SEG_LINETO;
				}else{
					type = SEG_CLOSE;
				}
				return type;
			}

			public int getWindingRule(){
				return PathIterator.WIND_NON_ZERO;
			}

			public boolean isDone(){
				return segment > 5;
			}

			public void next(){
				segment++;
			}
		};
	}

	/**Returns an iterator object that iterates along the Shape boundary and provides access to a flattened view of the Shape outline geometry.
	 * @return a new PathIterator object, which independently traverses the geometry of the Shape
	 */
	public PathIterator getPathIterator(AffineTransform at, double flatness){
		return getPathIterator(at);
	}

	/**Tests if the interior of the Shape intersects the interior of a specified rectangular area.
	 * @return false always
	 */
	public boolean intersects(Rectangle2D r){
		return false;
	}

	/**Tests if the interior of the Shape intersects the interior of a specified Rectangle2D.
	 * @return false always
	 */
	public boolean intersects(double x, double y, double w, double h){
		return false;
	}
}
