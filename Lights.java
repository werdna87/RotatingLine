/**Defines some animated colors.
 * 
 * @author Andrew Chen
 * 
 * Nota bene: Colors as defined by java.awt.Color are immutable.
 */

import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lights implements ActionListener{

	private Color color = Color.WHITE;
	private Color defaultColor = Color.WHITE;
	private boolean change = false;
	private Timer timer =  new Timer(50, this);
	private int period;
	private int count;
	
	private static final int FULLCOLOR = 255;
	
	/**Constructs a Light that changes colors within a period
	 * @param period period of color change in milliseconds 
	 */
	public Lights(int period){
		this.setPeriod(period);
		this.timer.start();
	}
	
	/**Returns the current color
	 * @return the current color
	 */
	public Color getColor(){
		return this.color;
	}

	/**Sets the period of color change
	 * @param period period of color change in milliseconds
	 */
	public void setPeriod(int period){
		this.period = (int) (Math.ceil((double)period/15)*5);
	}
	
	/**Sets the default color when the light is off
	 * @param color the default color
	 */
	public void setDefaultColor(Color color){
		this.defaultColor = color;
	}

	/**Checks if the light is on
	 * @return true if the light is on
	 */
	public boolean isOn(){
		return this.change;
	}
	
	/**Turns on the light
	 */
	public void on(){
		this.change = true;
	}

	/**Turns off the light
	 */
	public void off(){
		this.change = false;
		this.color = this.defaultColor;
	}

	/**Continuously changes the color to a new color every 5 milliseconds
	 */
	public void actionPerformed(ActionEvent e) {
		if(change){
			if(this.count>=3*period){
				this.count=0;
			}
			this.count+=5;
			
			double percent2 = (double)count % period / period;
			double percent1 = 1-percent2;
			
			//First interval of change
			if(0 <= count && count < period){
				this.color = new Color(
						(int) (percent1 * Lights.FULLCOLOR),
						(int) (percent2 * Lights.FULLCOLOR),
						0
				);
			//Second interval of change
			}else if(period <= count && count < 2*period){
				this.color = new Color(
						0,
						(int) (percent1 * Lights.FULLCOLOR),
						(int) (percent2 * Lights.FULLCOLOR)
				);
			//Third interval of change
			}else if(period <= 2*count && count < 3*period){
				this.color = new Color(
						(int) (percent2 * Lights.FULLCOLOR),
						0,
						(int) (percent1 * Lights.FULLCOLOR)
				);
			}
		}
	}
}