package model;

import model.exception.CouldNotFindAudioPathException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This class deals with playing audio in the program
 * This class was modeled after the code in this video: https://www.youtube.com/watch?v=3q4f6I5zi2w
 */
public class HandleAudio {

    // EFFECTS: creates an instance of HandleAudio
    public HandleAudio() {
    }

    // REQUIRES: filepath should be a locally-saved location
    // EFFECTS: Takes is the location of audio to played and plays it
    //          throws CouldNotFindAudioPathException if audio cannot be played
    public void playAudio(String filepath) throws CouldNotFindAudioPathException {
        InputStream music;
        try {
            music = new FileInputStream(new File(filepath));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            throw new CouldNotFindAudioPathException();
        }

    }
}

//package ui;
//
//        import sun.audio.AudioPlayer;
//        import sun.audio.AudioStream;
//
//        import javax.swing.*;
//        import java.io.File;
//        import java.io.FileInputStream;
//        import java.io.InputStream;
//
///**
// * This class deals with playing audio in the program
// * This class was modeled after the code in this video: https://www.youtube.com/watch?v=3q4f6I5zi2w
// */
//public class HandleAudio {
//
//    // EFFECTS: creates an instance of HandleAudio
//    public HandleAudio() {
//    }
//
//    // REQUIRES: filepath should be a locally-saved location
//    // EFFECTS: Takes is the location of audio to played and plays it
//    public void playAudio(String filepath) {
//        InputStream music;
//        try {
//            music = new FileInputStream(new File(filepath));
//            AudioStream audio = new AudioStream(music);
//            AudioPlayer.player.start(audio);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "There was an error Playing Audio");
//        }
//
//    }
//}
