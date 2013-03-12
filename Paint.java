import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Paint extends JPanel{
	RotatableShape b;
	Lights l;
	Color c = Color.black;
	Image offScreen = createImage(this.getWidth(), this.getHeight());
	JFrame frame = new JFrame();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		b.setCenterX(this.getWidth()/2);
		b.setCenterY(this.getHeight()/2);
		g2.setColor(l.getColor());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(l.isOn()){
			Color cc = l.getColor();
			c=new Color(255-cc.getRed(),
					255-cc.getGreen(),
					255-cc.getBlue());
		}
		else{c=Color.black;}
		
		g2.setColor(c);
		g2.setStroke(new BasicStroke(1));
		g2.draw(b);
	}
	
	public Paint(RotatableShape b,Lights l){
		this.b = b;
		this.l = l;
	}
}
