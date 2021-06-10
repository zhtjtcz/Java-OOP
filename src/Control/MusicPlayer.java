package Control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer implements Runnable {
	public String name;
	
	public MusicPlayer(String name){
		this.name = "music\\" + name;
	}
	
	public void Play() throws Exception{
		File mp3 = new File(this.name);
		FileInputStream fis = new FileInputStream(mp3);
		BufferedInputStream stream = new BufferedInputStream(fis);
		Player player = new Player(stream);
		player.play();
	}
	
	@Override
	public void run(){
			try{
				if (this.name.equals("music\\BackGroundMusic.mp3")){
					while (true)	Play();
				}
				else
					Play();
			}	catch (Exception e){
				System.out.println("Music play error!");
			}
		Thread.currentThread().interrupt();
	}
}
