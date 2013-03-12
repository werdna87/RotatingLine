/**Defines a rotatable polygon defined by a set of points at set angles from an origin
 * centered at a point specified by rectangular coordinates
 * 
 * All methods (except getPathIterator) inherited from the Shape interface DO NOT WORK! Actually. Don't use them.
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

public abstract class RotatablePolygon extends RotatableShape{
	
	protected int[] radii;
	protected double[] thetas;
	protected int numSides;
	
	public static final double FULL_CIRCLE = 2*Math.PI;
	
	/**Class constructor.
	 */
	public RotatablePolygon(){
	}
	
	/**Constructs a RotatableShape defined by two points of a diagnol
	 * @param X1 x-coordinate of the first endpoint
	 * @param Y1 y-coordinate of the first endpoint
	 * @param X2 x-coordinate of the latter endpoint
	 * @param Y2 y-coordinate of the latter endpoint
	 */
	public RotatablePolygon(int x1, int y1, int x2, int y2){}

	/**Constructs a RotatableShape which forms the diagonal defined by the dimensions of a rectangle
	 * @param dimension the dimensions
	 */
	public RotatablePolygon(Dimension dimension) {
		this(0, 0 , (int) dimension.getWidth(), (int) dimension.getHeight());
	}

	/**Gets the X-coordinate of the nth vertex 
	 * @param n the nth vertex bounded from 1 to the number of sides inclusive 
	 */
	public int getXn(int n){
		return this.getCenterX() + (int) (radii[n-1]*Math.cos(thetas[n-1]));
	}

	/**Gets the Y-coordinate of the nth vertex 
	 * @param n the nth vertex bounded from 1 to the number of sides inclusive 
	 */
	public int getYn(int n){
		return this.getCenterY() + (int) (radii[n-1]*Math.sin(thetas[n-1]));
	}

	/**Rotates the line segment in a counterclockwise manner
	 * @param radians angle of rotation in radians 
	 */
	public void rotate(double radians){
		for(int i = 0; i<this.numSides; i++){
			this.thetas[i]+=radians;
		}
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
	 * @return null always
	 */
	public Rectangle getBounds(){
		return null;
	}

	/**Returns a Rectangle2D that completely encloses the Shape.
	 * @return null always
	 */
	public Rectangle2D getBounds2D(){
		return null;
	}

	/**Returns an iterator object that iterates along the Shape boundary and provides access to the geometry of the Shape outline.
	 * @return a new PathIterator object, which independently traverses the geometry of the Shape
	 */
	public PathIterator getPathIterator(final AffineTransform at){
		final RotatablePolygon line = this; 
		return new PathIterator(){

			private int segment;

			public int currentSegment(float[] coords){
				int type;
				if(segment == 0){
					coords[0] = line.getXn(segment+1);
					coords[1] = line.getYn(segment+1);
					type = PathIterator.SEG_MOVETO;
				}else if(segment < numSides){
					coords[0] = line.getXn(segment+1);
					coords[1] = line.getYn(segment+1);
					type = PathIterator.SEG_LINETO;
				}else{
					type = SEG_CLOSE;
				}
				return type;
			}

			public int currentSegment(double[] coords){
				int type;
				if(segment == 0){
					coords[0] = line.getXn(segment+1);
					coords[1] = line.getYn(segment+1);
					type = PathIterator.SEG_MOVETO;
				}else if(segment < numSides){
					coords[0] = line.getXn(segment+1);
					coords[1] = line.getYn(segment+1);
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
				return segment > numSides;
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