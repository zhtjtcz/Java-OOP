package Bullet;

import Control.*;
import Card.Zombie.*;

import javax.swing.*;
import java.awt.*;

public class Bullet extends JLabel implements Runnable {
    private static final long serialVersionUID = 1L;
    private IController controller;
    private ImageIcon img;
    private int x, y, row;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img.getImage(), 0, 0, img.getIconWidth(), img.getIconHeight(), this);
    }

    public Bullet() { }

    public Bullet(IController controller, int row, int column) {
        setVisible(true);
        img = new ImageIcon("img\\Bullets\\PeaShooter.png");
        this.controller = controller;
        this.row = row;
        this.x = 40 + column * 80 + 40;
        this.y = 90 + row * 100;
        this.setBounds(x, y, img.getIconWidth(), img.getIconHeight());
        controller.getLayeredPane().add(this, Integer.valueOf(500));
    }

    public Bullet Pea(Controller controller, int row, int column) {
        Bullet tempBullet = new Bullet(controller, row, column);
        tempBullet.img = new ImageIcon("img\\Bullets\\PeaShooter.png");
        return tempBullet;
    }

    public void bulletAttack(Zombie zombie) {
        zombie.reduceHP(20);
    }

    @Override
    public void run() {
        while (true) {
            Zombie tempZombie = controller.getAttackedZombie(row, x - 60);
            if (tempZombie != null) {
                this.bulletAttack(tempZombie);
                break;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x += 2;
            this.setBounds(x, y, img.getIconWidth(), img.getIconHeight());
            this.repaint();
            if (x > 810) break;
        }
        // Bullet Boom~

        img = new ImageIcon("img\\Bullets\\PeaShooterHit.png");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
        Thread.currentThread().interrupt();
    }
}