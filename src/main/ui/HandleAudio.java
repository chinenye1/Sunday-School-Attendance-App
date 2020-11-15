package ui;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/** This class deals with playing audio in the program
 * This class was modeled after the code in this video: https://www.youtube.com/watch?v=3q4f6I5zi2w
 */
public class HandleAudio {
    public HandleAudio() {
    }

    public static void playAudio(String filepath) {
        InputStream music;
        try {
            music = new FileInputStream(new File(filepath));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"There was an error Playing Audio");
        }

    }
}
