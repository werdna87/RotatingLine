/**Defines some beats.
 * 
 * @author Andrew Chen
 * 
 * Works consulted: Java Tutorial - Sound
 */

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


public class Beats{
	
	private String fileLocation;
	private AudioInputStream audio;
	private Clip clip;
	
	/**Class Constructor
	 * @param fileLocation url location of the file
	 */
	
	public Beats(String fileLocation){
		JFrame j = new JFrame();
		j.setVisible(true);
		j.setVisible(false);
		this.setFileLocation(fileLocation);
	}
	
	/**Sets the location of the file
	 * @param fileLocation location of the file
	 */
	public void setFileLocation(String fileLocation){
		this.fileLocation = fileLocation;
		try{
			this.audio = AudioSystem.getAudioInputStream(new File(fileLocation));
			this.clip = AudioSystem.getClip();
			this.clip.open(audio);
			
		}catch(UnsupportedAudioFileException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**Starts music in a loop.
	 */
	public void start(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**Stops music.
	 */
	public void stop(){
		clip.stop();
	}
}
