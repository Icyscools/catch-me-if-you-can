package com.sheepy.catchme.sounds;



import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	Clip clip;
	AudioInputStream audioInputStream;

	public Sound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// create AudioInputStream object
		audioInputStream = AudioSystem.getAudioInputStream(new File("src/com/sheepy/catchme/sounds/bgm.wav"));

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);

		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void play() {
		// start the clip
		clip.start();
	}
	
	public void stop() {
		clip.stop();
	}
	
}
