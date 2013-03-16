/**JPanel on which shapes are drawn annd rotated.
 * @author Andrew Borghesani
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class Paint extends JPanel implements MouseListener, MouseMotionListener, ActionListener{

	private ArrayList<RotatableShape> shapes = new ArrayList<RotatableShape>();
	private RotatableShape current;
	private JComboBox shapesMenu = new JComboBox(new String[]{"Add Shape",
			"Line", "Rectangle", "Regular Polygon", "Custom"});

	private JComboBox currentMenu = new JComboBox(new String[]{"Select Shape"});
	private String[] names = {"OutOfNames","NotALine","Alice","Bob","Carol","Dave","Eve",
			"Joe","Mallory","Oscar","Peggy","Trent","Victor","Walter",
			"foo","bar","spam","ham","eggs"};

	private Lights l;
	private Color c = Color.black;

	private boolean centered = true;
	private boolean drag = false;
	private int mouseIntX;
	private int mouseIntY;
	private int mouseFinX;
	private int mouseFinY;

	private boolean custom = false;
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Integer> customRadii = new ArrayList<Integer>(); 
	private ArrayList<Double> customAngles = new ArrayList<Double>();

	/**JPanel paint method
	 * @param g the Graphics context in which to paint
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(centered){
			current.setCenterX(this.getWidth()/2);
			current.setCenterY(this.getHeight()/2);
		}
		g2.setColor(l.getColor());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		if(l.isOn()){
			Color cc = l.getColor();
			c=new Color(255-cc.getRed(),
					255-cc.getGreen(),
					255-cc.getBlue());
		}else{c=Color.gray;}

		g2.setColor(c);
		g2.setStroke(new BasicStroke(3));
		for(RotatableShape shape: shapes){
			g2.draw(shape);
		}
		// current shape drawing
		g2.setColor(Color.BLACK);
		g2.draw(current);
		g2.fillOval(current.getCenterX(), current.getCenterY(), 2, 2);
		//custom shape drawing
		if(this.custom){
			for(int i=1; i<this.points.size(); i++){
				g2.drawLine((int)this.points.get(i-1).getX(), (int)this.points.get(i-1).getY(),
						(int)this.points.get(i).getX(), (int)this.points.get(i).getY());
			}
		}
	}

	/**Class Constructor. Constructs Jpanel Paint
	 * @param b ArrayList of RotatableShapes to be drawn
	 * @param l Lights to use for background and foreground colors
	 */
	public Paint(ArrayList<RotatableShape> b,Lights l) {
		shapes = b;
		current = b.get(0);
		this.currentMenu.addItem("Line");
		this.l = l;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		shapesMenu.addActionListener(this);
		currentMenu.addActionListener(this);
		add(shapesMenu);
		add(currentMenu);
	}
	/**Adds another RotatableShape to Paint and sets it to be current
	 * @param shape RotatableShape to be added
	 */
	public void add(RotatableShape shape){
		this.current = shape;
		this.shapes.add(current);
		this.currentMenu.addItem(""+names[currentMenu.getItemCount()%names.length]);
		this.repaint();
	}
	/**Sets the current shape to the indexed shape
	 * @param index index at which the shape is
	 */
	public void setCurrent(int index){
		this.current = this.shapes.get(index);
		this.repaint();
	}
	/**Sets whether the current shape should be locked to center
	 * @param b boolean true or false
	 */
	public void setCentering(boolean b){
		this.centered = b;
	}
	/**Actions for the JComboBox Menu
	 * @param e an ActionEvent
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==this.shapesMenu){
			if(this.custom == false){
				JComboBox cb = (JComboBox)e.getSource();
				String shape = (String)cb.getSelectedItem();
				if(shape.equals("Custom")){
					this.custom = true;
				}else if(!shape.equals("Add Shape")){
					this.clearCustom();
					ShapeChooser chooser = new ShapeChooser(shape, this);
					cb.setSelectedIndex(0);
				}
			}
		}
		if(e.getSource()==this.currentMenu){
			if(currentMenu.getSelectedIndex()!=0){
				this.setCurrent(currentMenu.getSelectedIndex()-1);
			}
		}
		this.repaint();
	}
	/**Locks shape onto mouse cursor when pressed
	 * @param e a MouseEvent
	 */
	public void mousePressed(MouseEvent e){
		this.drag = true;
		this.centered = false;
		this.mouseIntX = e.getX();
		this.mouseIntY = e.getY();
	}

	/**Moves shape with mouse cursor
	 * @param e a MouseEvent
	 */
	public void mouseDragged(MouseEvent e){
		if(this.drag){
			this.mouseFinX =  e.getX();
			this.mouseFinY =  e.getY();
			int difX = this.mouseFinX - this.mouseIntX;
			int difY = this.mouseFinY - this.mouseIntY;
			this.current.setCenterX(this.current.getCenterX()+difX);
			this.current.setCenterY(this.current.getCenterY()+difY);
			repaint();
			this.mouseIntX = e.getX();
			this.mouseIntY = e.getY();
		}
	}
	/**Releases shape from mouse cursor when pressed
	 * @param e a MouseEvent
	 */
	public void mouseReleased(MouseEvent e){
		if(this.drag){
			this.drag = false;
		}
	}
	/**Creates a new Custom Polygon if custom drawing is enabled
	 * @param e a MouseEvent
	 */
	public void mouseClicked(MouseEvent e){
		if(this.custom){
			//close and end shape
			if(this.points.size()!=0 &&
					(this.points.get(0).getX() + 50 > e.getX() && this.points.get(0).getX() - 50 < e.getX()) &&
					(this.points.get(0).getY() + 50 > e.getY() && this.points.get(0).getY() - 50 < e.getY())){					
				this.shapesMenu.setSelectedIndex(0);
				this.current = new RotatableFreePolygon(this.getWidth()/2, this.getHeight()/2,
						this.intConv(this.customRadii), this.doubleConv(this.customAngles));
				this.shapes.add(current);
				this.clearCustom();
				//continue adding shape points
			}else{
				this.points.add(e.getPoint());
				this.customRadii.add(convRad(e.getX(), e.getY()));
				this.customAngles.add(convTheta(e.getX(), e.getY()));
			}
		}
		this.repaint();
	}

	// converts point to radius
	private int convRad(int x, int y){
		int w = this.getWidth()/2;
		int h = this.getHeight()/2;
		return (int) Math.sqrt( Math.pow((x-w),2) + Math.pow((y-h),2) );
	}

	// converts point to angle
	private double convTheta(int x, int y){
		int w = this.getWidth()/2;
		int h = this.getHeight()/2;
		double theta = Math.atan((double)((y-h))/(x-w));
		if(x>w && y>h){
			return theta;
		}else if(x<w && y>h){
			return Math.PI+theta;
		}else if(x<w && y<h){
			return Math.PI+theta;
		}else{
			return theta;
		}
	}

	// converts arraylist to array
	private int[] intConv(ArrayList<Integer> a){
		int n = a.size();
		int [] arr = new int[n];
		for(int i=0; i<n; i++){
			arr[i] = a.get(i);
		}
		return arr;
	}

	// converts arraylist to array
	private double[] doubleConv(ArrayList<Double> a){
		int n = a.size();
		double [] arr = new double[n];
		for(int i=0; i<n; i++){
			arr[i] = a.get(i);
		}
		return arr;
	}

	// resets all custom shape options for the next custom shape
	private void clearCustom(){
		this.custom = false;
		this.customRadii.clear();
		this.customAngles.clear();
	}

	//unused overriden methods
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}
}
