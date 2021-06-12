package Control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer implements Runnable {
	public String name;
	public Player player;
	
	public MusicPlayer(String name){
		this.name = "music\\" + name;
	}
	
	
	/** 
	 * @throws Exception 异常
	 */
	public void Play() throws Exception{
		File mp3 = new File(this.name);
		FileInputStream fis = new FileInputStream(mp3);
		BufferedInputStream stream = new BufferedInputStream(fis);
		player = new Player(stream);
		new Thread(()->{
			try {
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	@Override
	public void run(){
			try{
				Play();
				while (this.name.equals("music\\BackGroundMusic.mp3")) {
					if (player.isComplete())
						Play();
					if (Thread.currentThread().isInterrupted())
						player.close();
					Thread.sleep(800);
				}
			}	catch (Exception e){
				System.out.println("Music play error!");
			}
		Thread.currentThread().interrupt();
	}
}
