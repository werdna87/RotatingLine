/**
 * 
 */

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class GUI extends JFrame{

	static final int Speed_Min = 1;
	static final int Speed_Max = 1000;
	static final int Speed_Init = 100;
	double rotateSpeed = .01;
	int delay;
	JButton run;
	ArrayList<RotatableShape> shapes = new ArrayList<RotatableShape>();
	Paint p;
	Lights l = new Lights(50);
	Beats b = new Beats("bin\\Party.wav");
	
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			stepButtonActionPerformed();
		}
	};
	JPanel rotateSlider = new JPanel();
	String[] beats = {"bin\\Party.wav","bin\\Style.wav"
			,"bin\\Dota.wav", "bin\\RickRoll.wav"};
	int songIndex=0;
	Timer timer = new Timer(10, taskPerformer);

	//constructs a new jframe
	public GUI(RotatableShape g){
		setLayout(new BorderLayout());
        
		setTitle("Rotating Line");
		shapes.add(g);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		this.stepButton();
		this.playButton();
//		rotateSlider.add(speedSlider());
		setVisible(true);
		p = new Paint(shapes,l);
		add(p);
		p.setVisible(true);
		p.repaint();
	}
	public void addGridBag(){
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setLayout(new BorderLayout());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;/*
        add("Run", gridBag, c);
        makebutton("Button2", gridBag, c);
        makebutton("Button3", gridBag, c);*/

	}
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			int speed = (int)source.getValue();
			rotateSpeed= speed/100;
		}
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
	public void speedSlider(){
		JLabel sliderLabel = new JLabel("Rotate Speed");
		JSlider Speed = new JSlider(JSlider.HORIZONTAL,Speed_Min, Speed_Max, Speed_Init);
		sliderLabel.setAlignmentX(LEFT_ALIGNMENT);
		Speed.setMajorTickSpacing(100);
		Speed.setMinorTickSpacing(10);
		Speed.setPaintTicks(true);
		Speed.setPaintLabels(true);
		Speed.setBorder(
				BorderFactory.createEmptyBorder(0,0,10,0));
		Font font = new Font("Serif", Font.ITALIC, 15);
		Speed.setFont(font);
		add(sliderLabel);
		add(Speed);
		Speed.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	}
	//tells the step button what to do
	private void stepButtonActionPerformed(){
		for(Rotatable shape : shapes){
			shape.rotate(rotateSpeed);
		}
		p.repaint();
	}
	//gets the next filelocation and plays the file in the string array
	public void getNextBeat(String[] s){
		if(songIndex<s.length){
			b.setFileLocation(s[songIndex++]);
		}else{
			songIndex=0;
			b.setFileLocation(s[songIndex++]);
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
