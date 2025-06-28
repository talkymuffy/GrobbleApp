package com.smcc.backend_process;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioSystem {
    public static void playSoundStart(){
     try {
        File audioFile = new File("audio.wav");
        AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(audioFile);

        Clip clip = javax.sound.sampled.AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

        // Wait for the clip to finish playing
        Thread.sleep(clip.getMicrosecondLength() / 1000);

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
        e.printStackTrace();
    }
   }
    public static void playPlop(){
        try {
            File audioFile = new File("plop.ogg.wav");
            AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(audioFile);

            Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Wait for the clip to finish playing
            Thread.sleep(clip.getMicrosecondLength() / 1000);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void playTrade(){
        try {
            File audioFile = new File("villager_trade.wav");
            AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(audioFile);

            Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Wait for the clip to finish playing
            Thread.sleep(clip.getMicrosecondLength() / 1000);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
