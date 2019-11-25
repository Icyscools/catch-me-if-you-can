package com.sheepy.catchme.sounds;


import java.io.*;
import java.net.URISyntaxException;
import sun.audio.*;

public class Sound {
    private InputStream File_main;
    private AudioStream Sound_main;
    public void play(){
        try {
            File_main = new FileInputStream(new File(getClass().getResource("bgm.wav").toURI()));
            Sound_main = new AudioStream(File_main);
            AudioPlayer.player.start(Sound_main);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
        	ex.printStackTrace();
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        new Sound().play();
    }
}
