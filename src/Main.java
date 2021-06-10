import Control.*;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;

public class Main {

    /**
     * @param args hello world!
     */
    public static void main(String[] args) throws JavaLayerException, FileNotFoundException {
        /*
        MusicPlayer play = new MusicPlayer("BackGroundMusic.mp3");
        new Thread(play).start();
        */
        new LaunchFrame();
    }
}