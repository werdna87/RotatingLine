import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JFrame{
	/**
	 * 
	 */
	//wanted me to add it, no idea what it does, also i wanted to do another test
	private static final long serialVersionUID = 1L;
	
	JButton run;
	RotatableShape line;
	Paint p;
	Lights l = new Lights(1000);
	Beats b = new Beats("bin\\Party.wav");
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			stepButtonActionPerformed();
		}
	};
	String[] beats = {"bin\\Party.wav","bin\\Style.wav"
			,"bin\\Dota.wav", "bin\\RickRoll.wav"};
	int i=0;
	Timer timer = new Timer(10, taskPerformer);

	//constructs a new jframe
	public GUI(RotatableShape g){
		setTitle("Rotating Line");
		line = g;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		this.stepButton();
		this.playButton();
		setVisible(true);
		p = new Paint(line,l);
		this.add(p);
		p.setVisible(true);
		this.drawLine(line);
	}

	//creates a button that steps to the next generation
	public void stepButton(){
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
	//creates a button that 
	public void playButton(){
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
	//draws the board onto the jframe
	public void drawLine(RotatableShape original){
		p.repaint();
	}
	//tells the step button what to do
	private void stepButtonActionPerformed(){
		line.rotate(.01);
		drawLine(line);
	}
	public void getNextBeat(String[] s){
		if(i<s.length){
			b.setFileLocation(s[i++]);
		}else{
			i=0;
			b.setFileLocation(s[i++]);
		}
	}
	//tells the play button what to do
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
		GUI test = new GUI(line);
	}
}
