package Zombie;

import javax.swing.*;

public class Zombie extends JLabel implements Runnable {
	private static int MOVE = 1;
	private static int ATTACK = 2;
	private static int LOSTHEAD = 3;
	private static int LOSTHEADATTACK = 4;
	private static int DIE = 5;
	private static int BOOM = 6;

	private String name;
	private int hp = 0, hp2 = 0;
	// hp: 总生命值
	private int state = 1;
	// hp2: 

	@Override
	public void run(){
		return;
	}
}