/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: KnollAudioPlayer
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package gamePackage;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class KnollAudioPlayer {

	private File audioFile;
	private AudioInputStream audioStream;
	private Clip clip;
	
	
	/**
	 * Creates a new audioFile and sets up the audioStream. This lets us extract a audio clip which can be played by @method play()
	 * @throws Exception
	 */
	public KnollAudioPlayer() throws Exception{
		audioFile = new File(URLDecoder.decode(MainFrame.class.getResource("title.wav").getPath()));
		audioStream = AudioSystem.getAudioInputStream(audioFile);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		System.out.println(System.currentTimeMillis()+": KnollAudioPlayer created.");
	}
	/**
	 * Loop over the clip which was created in the constructor
	 */
	public void play()  {
		clip.loop(clip.LOOP_CONTINUOUSLY);
		System.out.println(System.currentTimeMillis() + ": AUDIO IS PLAYING");
	}

}
