package Shovel;

import Control.*;

import javax.swing.*;
import java.awt.*;

public class Shovel extends JLabel implements Runnable {
    private static final long serialVersionUID = 1L;

    IController controller;
    ImageIcon shovelImg = new ImageIcon("Img\\Shovel.png");

    public Shovel(IController controller) {
        this.controller = controller;
    }

    
	/** 
	 * @return ImageIcon 铲子图片
	 */
	public ImageIcon getImg() {
        return shovelImg;
    }

    
	/** 
	 * @param g 图层
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(shovelImg.getImage(), 0, 0, shovelImg.getIconWidth(), shovelImg.getIconHeight(), this);
    }

    public void selected() {
        if (controller.isRunning()) {
            if (controller.getCard() == null) {
                this.setVisible(false);
                controller.setShovel(true);
            } else {
                controller.cancelSelectingCard();
            }
        }
    }

    @Override
    public void run() {
        while (!controller.isRunning()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (controller.isRunning()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}