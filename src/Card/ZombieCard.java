package Card;

import Control.IController;
import Card.Plant.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class ZombieCard extends Card implements MouseListener, Runnable {


    public ZombieCard(String name, IController controller) {
        super(name, controller);
        this.cardLight = new ImageIcon("Img\\Cards\\" + cardName + "0.png");
        this.cardDark = new ImageIcon("Img\\Cards\\" + cardName + "1.png");
        this.cardCooling = new ImageIcon("Img\\Cards\\" + cardName + "2.png");
        this.preImg = new ImageIcon("img\\" + cardName + "\\Zombie1\\Frame0.png ");
        this.blurImg = new ImageIcon("img\\" + cardName + "\\Zombie1\\Frame0.png ");
        this.card = cardDark;
        this.inCooling = true;
        this.addMouseListener(this);
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
            while (controller.isRunning() && (!inCooling || (inCooling && isChoosed))) {
                check(controller.getIntSunCount());
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            check(controller.getIntSunCount());
            while (controller.isRunning()) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totTime += 3;
                if (totTime >= cd) break;

                check(controller.getIntSunCount());
            }
            totTime = 0;
            inCooling = false;
        }
    }
}