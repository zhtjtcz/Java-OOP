package Shovel;

import Control.*;

import javax.swing.*;
import java.awt.*;

public class Shovel extends JLabel implements Runnable{
	private static final long serialVersionUID = 1L;

    Controller controller;
    ImageIcon shovelImg = new ImageIcon("Img\\Shovel.png");

    public Shovel(Controller controller){
        this.controller = controller;
    }

    public ImageIcon getImg(){
        return shovelImg;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(shovelImg.getImage(), 0, 0, shovelImg.getIconWidth(), shovelImg.getIconHeight(), this);
    }

    public void selected(){
        if (controller.isRunning) {
            if (controller.getCard() == null) {
                this.setVisible(false);
                controller.setShovel(true);
            } else {
                controller.cancelSelectingCard();
            }
        }
    }

    @Override
    public void run(){
        while (!controller.isRunning) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (controller.isRunning) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}