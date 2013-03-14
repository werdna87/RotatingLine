/**
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class Paint extends JPanel implements MouseListener, MouseMotionListener, ActionListener{

	ArrayList<RotatableShape> shapes = new ArrayList<RotatableShape>();
	RotatableShape current;
	JComboBox shapesMenu = new JComboBox(new String[]{"Add Shape",
			"Line", "Rectangle", "Regular Polygon", "Custom"});

	Lights l;
	Color c = Color.black;

	boolean centered = true;
	boolean drag = false;
	int mouseIntX;
	int mouseIntY;
	int mouseFinX;
	int mouseFinY;

	boolean custom = false;
	ArrayList<Integer> customRadii = new ArrayList<Integer>(); 
	ArrayList<Double> customAngles = new ArrayList<Double>();
	int customX = -1;
	int customY = -1;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(centered){
			current.setCenterX(this.getWidth()/2);
			current.setCenterY(this.getHeight()/2);
		}
		g2.setColor(l.getColor());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.fillOval(this.getWidth()/2, this.getHeight()/2, 3, 3);

		if(l.isOn()){
			Color cc = l.getColor();
			c=new Color(255-cc.getRed(),
					255-cc.getGreen(),
					255-cc.getBlue());
		}else{c=Color.black;}

		g2.setColor(c);
		g2.fillOval(this.getWidth()/2, this.getHeight()/2, 3, 3);
		g2.setStroke(new BasicStroke(1));
		for(RotatableShape shape: shapes){
			g2.draw(shape);
		}
		if(custom){
			g2.fillOval (customX, customY, 3, 3);
		}
	}


	public Paint(ArrayList<RotatableShape> b,Lights l){
		shapes = b;
		current = b.get(0);
		this.l = l;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		shapesMenu.addActionListener(this);
		add(shapesMenu, BorderLayout.PAGE_START);
	}

	// adds a shape to the ArrayList shapes
	public void add(RotatableShape shape){
		current = shape;
		shapes.add(current);
		this.repaint();
	}
	// sets the boolean centered which locks the shape to the center
	public void setCentering(boolean b){
		centered = b;
	}
	//shape menu options
	public void actionPerformed(ActionEvent e){
		if(custom == false){
			JComboBox cb = (JComboBox)e.getSource();
			String shape = (String)cb.getSelectedItem();
			if(shape.equals("Custom")){
				this.custom = true;
			}else if(!shape.equals("Add Shape")){
				custom = false;
				customRadii.clear();
				customAngles.clear();
				customX = -1;
				customY = -1;
				ShapeChooser chooser = new ShapeChooser(shape, this);
				cb.setSelectedIndex(0);
			}
		}
	}

	//locks shape onto mouse cursor when pressed
	public void mousePressed(MouseEvent e){
		mouseIntX = e.getXOnScreen();
		mouseIntY = e.getYOnScreen();
		if(		current.getCenterX() > mouseIntX-200 &&
				current.getCenterX() < mouseIntX+200 &&
				current.getCenterY() > mouseIntY-200 &&
				current.getCenterY() < mouseIntY+200
				){
			drag = true;
			centered = false;
			mouseIntX = e.getXOnScreen();
			mouseIntY = e.getYOnScreen();
		}
	}

	//moves shape with mouse cursor
	public void mouseDragged(MouseEvent e){
		if(drag){
			mouseFinX =  e.getXOnScreen();
			mouseFinY =  e.getYOnScreen();
			int difX = mouseFinX - mouseIntX;
			int difY = mouseFinY - mouseIntY;
			current.setCenterX(current.getCenterX()+difX);
			current.setCenterY(current.getCenterY()+difY);
			repaint();
			mouseIntX = e.getXOnScreen();
			mouseIntY = e.getYOnScreen();
		}
	}

	//releases shape from mouse cursor when pressed
	public void mouseReleased(MouseEvent e){
		if(drag){
			drag = false;
		}
	}

	//unused overriden methods
	public void mouseClicked(MouseEvent e){
		if(custom){
			//first point
			if(customX == -1 && customY == -1){
				customX = e.getXOnScreen();
				customY = e.getYOnScreen();
				customRadii.add(convRad(customX, customY));
				customAngles.add(convTheta(customX, customY));
				repaint();
				//not first point
			}else{
				// close the shape
				if((customX + 50 > e.getXOnScreen() && customX - 50 < e.getXOnScreen()) &&
						(customY + 50 > e.getYOnScreen() && customY - 50 < e.getYOnScreen())){					
					custom = false;
					shapesMenu.setSelectedIndex(0);
					current = new RotatableFreePolygon(this.getWidth()/2, this.getHeight()/2,
							intConv(customRadii), doubleConv(customAngles));
					shapes.add(current);
					customRadii.clear();
					customAngles.clear();
					customX = -1;
					customY = -1;
					this.repaint();
					// continue shape
				}else{
					customRadii.add(convRad(e.getXOnScreen(), e.getYOnScreen()));
					customAngles.add(convTheta(e.getXOnScreen(), e.getYOnScreen()));
				}
			}
		}
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

	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}

}
