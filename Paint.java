import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class Paint extends JPanel implements MouseListener, MouseMotionListener{
	
	RotatableShape shape;
	Lights l;
	Color c = Color.black;
	
	boolean centered = true;
	boolean drag = false;
	int mouseIntX;
	int mouseIntY;
	int mouseFinX;
	int mouseFinY;
	
	public void paintComponent(Graphics g) {
		if(centered){
			this.shape.setCenterX(this.getWidth()/2);
			this.shape.setCenterY(this.getHeight()/2);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(l.getColor());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		if(l.isOn()){
			Color cc = l.getColor();
			c=new Color(255-cc.getRed(),
					255-cc.getGreen(),
					255-cc.getBlue());
		}else{c=Color.black;}

		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.draw(shape);
	}
	
	public Paint(RotatableShape b,Lights l){
		this.shape = b;
		this.l = l;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	//locks shape onto mouse cursor when pressed
	public void mousePressed(MouseEvent e){
		mouseIntX = e.getXOnScreen();
		mouseIntY = e.getYOnScreen();
		if(		shape.getCenterX() > mouseIntX-200 &&
				shape.getCenterX() < mouseIntX+200 &&
				shape.getCenterY() > mouseIntY-200 &&
				shape.getCenterY() < mouseIntY+200
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
			shape.setCenterX(shape.getCenterX()+difX);
			shape.setCenterY(shape.getCenterY()+difY);
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
	public void mouseClicked(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}
}
