/**GUI JFrame for RotatingLine Project
 * @author Andrew Borghesani
 */

//ANDREW, i got the slider to show up, but i have no idea how to get it to do anything XD can you help?

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JFrame implements ChangeListener{

	private double rotateSpeed = .01;
	private JButton run;
	private ArrayList<RotatableShape> shapes = new ArrayList<RotatableShape>();
	private Paint p;
	private Lights l = new Lights(50);
	private Beats b = new Beats("bin\\Party.wav");
	
	private ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			stepButtonActionPerformed();
		}
	};
	private String[] beats = {"bin\\Party.wav","bin\\Style.wav"
			,"bin\\Dota.wav", "bin\\RickRoll.wav"};
	private int songIndex=0;
	private Timer timer = new Timer(10, taskPerformer);

	/**Constructs a new jframe
	 * @param g an intitial RotatableShape
	 */
	public GUI(RotatableShape g){
		setLayout(new BorderLayout());
        
		setTitle("Rotating Line");
		shapes.add(g);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.stepButton();
		this.playButton();
		speedSlider();
		setVisible(true);
		p = new Paint(shapes,l);
		add(p);
		p.setVisible(true);
		p.repaint();
		setSize(800,600);
	}

	/**tells the slider what to do
	 * @param e a Slider event
	 */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (source.getValueIsAdjusting()) {
			int speed = (int)source.getValue();
			rotateSpeed= speed/500.0;
		}
	}
	/**creates a button that steps to the next generation
	 */
	private void stepButton(){
		JButton stepButton = new JButton();
		stepButton.setText("Step");
		stepButton.setSize(new Dimension(20,20));
		stepButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stepButtonActionPerformed();
			}
		});
		getContentPane().add(stepButton,BorderLayout.NORTH);
	}
	/**creates a button that continuously steps to the next generation
	 */
	private void playButton(){
		run = new JButton();
		run.setText("Run");
		run.setSize(new Dimension(20,20));
		run.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playButtonActionPerformed();
			}
		});
		getContentPane().add(run,BorderLayout.SOUTH);
	}
	/**creates a button that continuously steps to the next line location
	 */
	private void  speedSlider(){
		JLabel sliderLabel = new JLabel("Rotate Speed");
		JSlider Speed = new JSlider(JSlider.VERTICAL,-100, 100, 10);
		sliderLabel.setAlignmentX(CENTER_ALIGNMENT);
        Speed.addChangeListener(this);
		Speed.setMajorTickSpacing(10);
		Speed.setMinorTickSpacing(1);
		Speed.setPaintTicks(true);
		Speed.setPaintLabels(true);
		Speed.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(0,10,0,10)));
		Font font = new Font("Serif", Font.ITALIC, 15);
		Speed.setFont(font);
		getContentPane().add(Speed,BorderLayout.EAST);
	}
	/**tells the step button what to do
	 */
	private void stepButtonActionPerformed(){
		for(Rotatable shape : shapes){
			shape.rotate(rotateSpeed);
		}
		p.repaint();
	}
	/**gets the next filelocation and plays the file in the string array
	 * @param s a String array containing the file location of songs
	 */
	private void getNextBeat(String[] s){
		if(songIndex<s.length){
			b.setFileLocation(s[songIndex++]);
		}else{
			songIndex=0;
			b.setFileLocation(s[songIndex++]);
		}
	}
	/**tells the play button what to do
	 */
	private void playButtonActionPerformed(){
		if(timer.isRunning()){
			timer.stop();
			l.off();
			b.stop();
			run.setText("Run");
			p.repaint();
		}
		else{
			timer.start();
			l.on();
			getNextBeat(beats);
			b.start();
			run.setText("Stop");
		}
	}
	// test for commit
	public static void main(String[] args){
		RotatableShape line = new RotatableLine(0,0, 800, 600);
		((RotatableLine)line).setLength(99999);
		GUI test = new GUI(line);
	}
}
