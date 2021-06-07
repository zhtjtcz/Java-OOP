package Plant;

import javax.swing.*;

public class Plant extends JLabel implements Runnable {
	private String name;

	protected int hp = 0;
	private int prive = 0;
	private int CD;
	private int Cnow = 0;
	//累计时间

	protected int sleepTime;
	private int cardCD;
	private int state;


	@Override
	public void run(){
		return;
	}
}