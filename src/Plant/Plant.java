package Plant;

import Control.*;
import Sun.*;
import Bullet.*;

import javax.swing.*;
import java.awt.*;

public class Plant extends JLabel implements Runnable {
    private static final long serialVersionUID = 1L;

    private String name;
    protected ImageIcon img;

    protected int hp = 0;
    private int price = 0;
    private int CD;
    private int Cnow = 0;
    // 累计时间

    private int pic = 0;
    private int SumPic = 0;
    // Plant picture

    protected int sleepTime;
    private int cardCD;
    private int state;
    private boolean canChange = false;

    private Controller controller;
    private int row, column;

    public Plant() {
        this.img = null;
        this.name = null;
        this.price = 0;
        this.hp = 0;
        this.SumPic = 0;
    }

    public Plant(String name, int price, int attack, int hp, int SumPic, boolean canChange) {
        this.canChange = canChange;
        this.img = new ImageIcon("img\\" + this.name + "\\" + this.name + "_" + "0.png");
        this.name = name;
        this.price = price;
        this.hp = hp;
        this.SumPic = SumPic;
        this.state = 0;
    }

    public int getR() {
        return row;
    }

    public int getC() {
        return column;
    }

    public int getPic() {
        return pic;
    }

    public void setCnow(int Cnow) {
        this.Cnow = Cnow;
    }

    public int getCnow() {
        return Cnow;
    }

    public int getCD() {
        return CD;
    }

    public int getPrice() {
        return price;
    }

    public int getCardCD() {
        return cardCD;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setPos(int row, int column) {
        this.row = row;
        this.column = column;
    }
    // Getter and setter

    public void picChange() {
        pic = (pic + 1) % SumPic;
        img = new ImageIcon("img\\" + this.name + "\\" + this.name + "_" + pic + ".png");
        if (this.getName() == "WallNut")
            img = new ImageIcon("img\\" + this.name + "\\" + this.name + "_cracked" + state + "\\" + this.name
                    + "_cracked" + state + "_" + pic + ".png");
    }

    public void attacked(int x) {
        this.hp -= x;
        if (this.getName() == "WallNut") {
            if (this.hp < 1333) {
                sleepTime = 96;
                this.SumPic = 15;
                state = 2;
            } else if (this.hp < 2666) {
                this.SumPic = 11;
                sleepTime = 131;
                if (this.pic >= SumPic)
                    this.pic = SumPic - 1;
                state = 1;
            } else {
                sleepTime = 90;
                this.SumPic = 16;
                state = 0;
            }
        }
    }

    public void die() {
        hp = -1;
        controller.plantDeath(row, column);
        this.setVisible(false);
        Thread.currentThread().interrupt();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // The shadow under the plants

        ImageIcon shadow = new ImageIcon("img\\shadow.png");
        g2.drawImage(shadow.getImage(), -11, 45, shadow.getIconWidth(), shadow.getIconHeight(), null);
        g.drawImage(img.getImage(), 0, 0, img.getIconWidth(), img.getIconHeight(), null);
        if (getName() == "SunFlower") {
            if (this.getCnow() > 261) {
                ImageIcon tempImg = new ImageIcon("img\\GoldenSunflower\\Frame" + getPic() + ".png");
                g.drawImage(tempImg.getImage(), 0, 0, tempImg.getIconWidth(), tempImg.getIconHeight(), null);
            }
        }
    }

    void accumulate() {
        if (this.canChange == false) return;
        if (getName() == "SunFlower") {
            if (Cnow <= 261)
                this.picChange();
        } else {
            this.picChange();
        }
        this.Cnow = this.Cnow + 1;
    }
    // Accelorator the CD time

    public Plant getPlant(String name) {
        Plant newPlant = null;
        switch (name) {
            case "SunFlower":
                newPlant = new Plant().SunFlower();
                break;
            case "PeaShooter":
                newPlant = new Plant().PeaShooter();
                break;
            case "Repeater":
                newPlant = new Plant().Repeater();
                break;
            case "CherryBomb":
                newPlant = new Plant().CherryBomb();
                break;
            case "WallNut":
                newPlant = new Plant().WallNut();
                break;
        }
        return newPlant;
    }

    @Override
    public void run() {
        boolean hasAttacked = false;// repeater
        while (hp > 0) {
            this.accumulate();
            if (getName() == "SunFlower") {
                if (this.getCnow() >= this.getCD()) {
                    this.setCnow(0);
                    new Thread(new Sun(getController(), getR(), getC())).start();
                }
            } else if (getName() == "PeaShooter") {
                if (this.getCnow() >= this.getCD()) {
                    if (controller.haveZombie(row)) {
                        this.setCnow(0);
                        new Thread(new Bullet(getController(), getR(), getC())).start();
                    } else
                        this.setCnow(CD);
                }
            } else if (getName() == "Repeater") {
                if (this.getCnow() >= this.getCD()) {
                    hasAttacked = true;
                    if (controller.haveZombie(row)) {
                        this.setCnow(0);
                        new Thread(new Bullet(getController(), getR(), getC())).start();
                    } else
                        this.setCnow(CD);
                } else if (this.getCnow() == this.getCD() / 4 && hasAttacked) {
                    new Thread(new Bullet(getController(), getR(), getC())).start();
                }
            } else if (getName() == "CherryBomb") {
                if (this.pic == this.SumPic - 1) {
                    this.setBounds(5 + column * 80, 40 + row * 100, 300, 300);
                    controller.boom(row, column);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            this.repaint();
            for (int i = 0; hp > 0 && i < 10; i++) {
                try {
                    Thread.sleep(sleepTime / 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        die();
    }

    // All plant is here~
    public Plant SunFlower() {
        Plant tempPlant = new Plant("SunFlower", 50, 0, 300, 17, true);
        tempPlant.CD = 24000 / 90;
        tempPlant.cardCD = 3500;// 7500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    public Plant PeaShooter() {
        Plant tempPlant = new Plant("PeaShooter", 100, 0, 300, 13, true);
        tempPlant.CD = 1000 / 90;
        tempPlant.cardCD = 3000;// 7500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    public Plant Repeater() {
        Plant tempPlant = new Plant("Repeater", 200, 0, 300, 15, true);
        tempPlant.CD = 1000 / 90;
        tempPlant.cardCD = 3500;// 7500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    public Plant CherryBomb() {
        Plant tempPlant = new Plant("CherryBomb", 150, 0, 1000000, 8, true);
        tempPlant.CD = 1000000;
        tempPlant.cardCD = 10000;// 12500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    public Plant WallNut() {
        Plant tempPlant = new Plant("WallNut", 50, 0, 4000, 16, true);
        tempPlant.CD = 1000000;
        tempPlant.cardCD = 8000;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }
}