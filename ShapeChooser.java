/**Dialog menu for choosing shapes
 * 
 * @author Andrew Chen
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ShapeChooser extends JDialog implements PropertyChangeListener{
	
	private Paint p;
	private RotatableShape shape;
	private String s;
	private JTextField text1 = new JTextField(10);
	private JTextField text2 = new JTextField(10);
	private JTextField text3 = new JTextField(10);
	
	/**Class constructor.
	 * @param choice String representation of a shape
	 */
	public ShapeChooser(String choice, Paint p){
		s = choice;
		this.p = p;
		if(choice.equals("Line")){
			JOptionPane pane = new JOptionPane(new Object[]{"Enter length", text1},
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    new String[]{"OK"});
			setContentPane(pane);
			pane.addPropertyChangeListener(this);
		}
		if(choice.equals("Rectangle")){
			JOptionPane pane = new JOptionPane(new Object[]{"Enter radius and acute angle (rad)",
															"... what? it's how my constructor works...",
															text1, text3},
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    new String[]{"OK"});
			setContentPane(pane);
			pane.addPropertyChangeListener(this);
		}
		if(choice.equals("Regular Polygon")){
			JOptionPane pane = new JOptionPane(new Object[]{"Enter length and sides", text1, text2},
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION,
                    null,
                    new String[]{"OK"});
			setContentPane(pane);
			pane.addPropertyChangeListener(this);
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setLocation(100,100);
	}
	
	/**Adds a shape to the Paint on "OK" 
	 * @param e PropertyChangeEvent
	 */
	public void propertyChange(PropertyChangeEvent e){
		if(this.isVisible()){
			if(s.equals("Line")){
				int n;
				try{
					n = Integer.parseInt(text1.getText());
					if(n>5000 || n<1){
						n = 400;
					}
				}
				catch(Exception ex){
					n = 400;
				}
				shape = new RotatableLine(0, 0, n);
			}
			if(s.equals("Rectangle")){
				int n;
				try{
					n = Integer.parseInt(text1.getText());
					if(n>5000 || n<1){
						n = 200;
					}
				}
				catch(Exception ex){
					n = 200;
				}
				double m;
				try{
					m = Double.parseDouble(text3.getText());
					if(m>4 || m<0){
						m = Math.PI/2;
					}
				}
				catch(Exception ex){
					m = Math.PI/2;
				}
				shape = new RotatableRect(0, 0, n, m);
			}
			if(s.equals("Regular Polygon")){
				int n;
				try{
					n = Integer.parseInt(text1.getText());
					if(n>5000 || n<1){
						n = 200;
					}
				}
				catch(Exception ex){
					n = 200;
				}
				int m;
				try{
					m = Integer.parseInt(text2.getText());
					if(m>20){
						m = 20;
					}
					if(m<3){
						m = 3;
					}
				}
				catch(Exception ex){
					m = 3;
				}
				shape = new RotatableRegularPolygon(new int[]{0, 0}, m, n);
			}
			p.add(shape);
			p.setCentering(true);
			this.setVisible(false);
		}
	}
}
