package Card.Zombie;

import Control.*;
import Card.Plant.*;

import javax.swing.*;
import java.awt.*;

public class Zombie extends JLabel implements Runnable {
    private static final long serialVersionUID = 1L;

    private static int MOVE = 1;
    private static int ATTACK = 2;
    private static int LOSTHEAD = 3;
    private static int LOSTHEADATTACK = 4;
    private static int DIE = 5;
    private static int BOOM = 6;

    private String name;
    private int hp = 0, hp2 = 0;
    // Card
    private int cardCD;
    private int price;

    public int getCardCD() {
        return cardCD;
    }

    public int getPrice() {
        return price;
    }

    // hp: 总生命值
    private int state = 1;

    private int x, y;
    private int row;

    private ImageIcon img;
    private int[] sumPic = new int[3];
    private int moveSum, attackSum, lostheadattackSum, lostheadSum, dieSum, boomSum;
    private int nowSumPic;
    private int nowPic = 0;
    private int headPic = 0;
    private static int BOOMINJURY = 1800;

    private int headPos;
    private int type;

    private IController controller;

    public Zombie() { }

    public Zombie(IController controller) {
        this.controller = controller;
    }

    public Zombie(IController controller, String name, int hp, int hp2, int row, int v) {
        setVisible(true);
        this.nowPic = 0;
        this.controller = controller;
        this.name = name;
        this.hp = hp + hp2;
        this.hp2 = hp2;
        this.y = row * 100 + 28;
        this.x = 800;
        this.row = row;
        this.sumPic[0] = 22;
        this.sumPic[1] = 31;
        this.sumPic[2] = 18;
        this.setState(MOVE);
        this.headPos = (int) (Math.random() * 60 + 30);
    }

    public int getXPos() {
        return x;
    }

    public int getColumn() {
        return (x + 60) / 80;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.x = col;
    }

    public void setRow(int row) {
        this.y = row * 100 + 28;
        this.row = row;
    }

    public void setState(int state) {
        this.state = state;
        nowPic = 0;
        if (state == MOVE) this.nowSumPic = moveSum;
        else if (state == ATTACK)
            this.nowSumPic = attackSum;
        else if (state == LOSTHEAD)
            this.nowSumPic = lostheadSum;
        else if (state == LOSTHEADATTACK)
            this.nowSumPic = lostheadattackSum;
        else if (state == DIE)
            this.nowSumPic = dieSum;
        else if (state == BOOM)
            this.nowSumPic = boomSum;
    }

    @Override
    public void paintComponent(Graphics g) {
        ImageIcon Img;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        img = new ImageIcon("img\\shadow.png");
        g.drawImage(img.getImage(), 70, 115, img.getIconWidth(), img.getIconHeight(), this);

        if (state == MOVE) {
            Img = new ImageIcon("img\\" + name + "\\Zombie" + (type + 1) + "\\Frame" + nowPic + ".png");
            g2.drawImage(Img.getImage(), 0, 0, Img.getIconWidth(), Img.getIconHeight(), this);
        } else if (state == ATTACK) {
            Img = new ImageIcon("img\\" + name + "\\ZombieAttack\\Frame" + nowPic + ".png");
            if (this.name.equals("hzyZombie") && this.type == 1)
                Img = new ImageIcon("img\\" + name + "\\ZombieAttack2\\Frame" + nowPic + ".png");
            if (this.name.equals("BucketZombie") && this.type == 1)
                Img = new ImageIcon("img\\" + name + "\\ZombieAttack2\\Frame" + nowPic + ".png");

            g2.drawImage(Img.getImage(), 0, 0, Img.getIconWidth(), Img.getIconHeight(), this);
        } else if (state == LOSTHEAD || state == LOSTHEADATTACK) {
            if (state == LOSTHEAD)
                Img = new ImageIcon("img\\" + name + "\\ZombieLostHead\\Frame" + nowPic + ".png");
            else
                Img = new ImageIcon("img\\" + name + "\\ZombieLostHeadAttack\\Frame" + nowPic + ".png");
            g2.drawImage(Img.getImage(), 0, 0, Img.getIconWidth(), Img.getIconHeight(), this);
            // 头动画只持续前10帧
            if (headPic < 10) {
                Img = new ImageIcon("img\\" + name + "\\ZombieHead\\Frame" + headPic + ".png");
                g2.drawImage(Img.getImage(), headPos, 0, Img.getIconWidth() * 5 / 6, Img.getIconHeight() * 5 / 6, this);
            }
        } else if (state == DIE) {
            Img = new ImageIcon("img\\" + name + "\\ZombieDie\\Frame" + nowPic + ".png");
            g2.drawImage(Img.getImage(), 0, 0, Img.getIconWidth(), Img.getIconHeight(), this);
        } else if (state == BOOM) {
            Img = new ImageIcon("img\\" + name + "\\ZombieBoom\\Frame" + nowPic + ".png");
            g2.drawImage(Img.getImage(), 0, 0, Img.getIconWidth(), Img.getIconHeight(), this);
        }
    }
    // Paint the Zonbie
    // Depend on the situations


    public Zombie getZombie(String name) {
        return switch (name){
            case "NormalZombie" -> NormalZombie(controller, 1);
            case "hzyZombie" -> hzyZombie(controller, 1);
            case "BucketZombie" -> BucketZombie(controller, 1);
            default -> null;
        };
    }


    public Zombie NormalZombie(IController controller, int row) {
        Zombie tempZombie = new Zombie(controller, "NormalZombie", 200, 70, row, 4700 / 80);
        tempZombie.type = 0;
        tempZombie.moveSum = tempZombie.sumPic[type];
        tempZombie.attackSum = 21;
        tempZombie.lostheadSum = 17;
        tempZombie.lostheadattackSum = 11;
        tempZombie.dieSum = 17;
        tempZombie.boomSum = 17;
        tempZombie.cardCD = 1;
        tempZombie.price = 50;
//        controller.getLayeredPane().add(tempZombie, Integer.valueOf(400));
        return tempZombie;
    }

    public Zombie hzyZombie(IController controller, int row) {
        Zombie tempZombie = new Zombie(controller, "hzyZombie", 370, 180, row, 4700 / 80);
        tempZombie.type = 0;
        tempZombie.moveSum = tempZombie.sumPic[type];
        tempZombie.attackSum = 21;
        tempZombie.lostheadSum = 17;
        tempZombie.lostheadattackSum = 11;
        tempZombie.dieSum = 17;
        tempZombie.boomSum = 17;
        tempZombie.cardCD = 1;
        tempZombie.price = 75;
//        controller.getLayeredPane().add(tempZombie, Integer.valueOf(400));
        return tempZombie;
    }

    public Zombie BucketZombie(IController controller, int row) {
        Zombie tempZombie = new Zombie(controller, "BucketZombie", 1100, 180, row, 4700 / 80);
        tempZombie.type = 0;
        tempZombie.moveSum = tempZombie.sumPic[type];
        tempZombie.attackSum = 21;
        tempZombie.lostheadSum = 17;
        tempZombie.lostheadattackSum = 11;
        tempZombie.dieSum = 17;
        tempZombie.boomSum = 17;
        tempZombie.cardCD = 1;
        tempZombie.price = 150;
//        controller.getLayeredPane().add(tempZombie, Integer.valueOf(400));
        return tempZombie;
    }

    public void updateState() {
        if (hp <= 0) {
            if (this.state != DIE && this.state != BOOM) setState(DIE);
            // Die
        } else if (hp < hp2) {
            if (this.state == ATTACK) setState(LOSTHEADATTACK);
            else if (this.state != LOSTHEAD && this.state != LOSTHEADATTACK) {
                setState(LOSTHEAD);
                headPic = 0;
            }
        }
    }

    public Plant getPlant() {
        assert (findPlant());
        return controller.getPlants()[row][getColumn()];
    }

    public boolean findPlant() {
        if (getColumn() >= 9 || getColumn() < 0) return false;
        return controller.getPlants()[row][getColumn()] != null;
    }

    public void reduceHP(int x) {
        this.hp -= x;
        // MusicPlayer play = new MusicPlayer("splat.mp3");
        // new Thread(play).start();
        updateState();
    }

    public void boom() {
        if (this.hp > BOOMINJURY) reduceHP(BOOMINJURY);
        else {
            this.hp = 0;
            setState(BOOM);
        }
    }

    public void endThread() {
        Thread.currentThread().interrupt();
        hp = -1; setState(DIE);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            setState(MOVE);
            while (hp > hp2 && !Thread.currentThread().isInterrupted()) {

                if (this.name.equals("hzyZombie") && this.hp<200)       this.type = 1;
                if (this.name.equals("BucketZombie") && this.hp<200)    this.type = 1;

                // MOVE
                while (this.state == MOVE && !Thread.currentThread().isInterrupted()) {
                    if (this.name.equals("hzyZombie") && this.hp<200)       this.type = 1;
                    if (this.name.equals("BucketZombie") && this.hp<200)    this.type = 1;

                    if (findPlant()) {
                        setState(ATTACK);
                        break;
                    }
                    // sleep 60ms, x--

                    for (int j = 0; j < 2; j++) {
                        for (int i = 0; i < 10 && this.state == MOVE; i++)
                            try {
                                Thread.sleep(6);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        this.x -= 1;
                        System.out.println(x);
                        if (x < -70){
                            this.state = DIE;
                            controller.endGame();
                            setVisible(false);
                            Thread.currentThread().interrupt();
                        }
                        this.setBounds(x, y, 400, 300);
                        this.repaint();
                    }
                    // 120ms

                    nowPic = (nowPic + 1) % nowSumPic;
                    if (x < -70) controller.endGame();
                }

                // ATTACK
                while (this.state == ATTACK && !Thread.currentThread().isInterrupted()) {
                    if (this.name.equals("hzyZombie") && this.hp<200)       this.type = 1;
                    if (this.name.equals("BucketZombie") && this.hp<200)    this.type = 1;

                    for (int i = 0; i < 24 && this.state == ATTACK && findPlant(); i++) {
                        if (getPlant() != null)
                            getPlant().attacked(1);
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    nowPic = (nowPic + 1) % nowSumPic;
                    this.repaint();
                    if (!findPlant() && this.state == ATTACK) setState(MOVE);
                    // 不掉胳膊
                    updateState();
                }
            }


            while (hp > 0 && !Thread.currentThread().isInterrupted()) {
                // 临界状态
                while (this.state == LOSTHEAD || this.state == LOSTHEADATTACK) {
                    // sleep 60ms change
                    for (int j = 0; j < 2 && hp > 0; j++) {
                        for (int i = 0; i < 10 && hp > 0; i++) {
                            try {
                                Thread.sleep(6);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (this.state == LOSTHEAD) {
                            this.x--;
                            this.setBounds(x, y, 400, 300);
                        }
                        headPic++;
                        this.repaint();
                    }
                    // 120ms

                    nowPic = (nowPic + 1) % nowSumPic;
                    reduceHP(3);
                    if (x < -70) controller.endGame();
                }
            }
            controller.deleteZombie(this, row);

            if (this.state == DIE && !Thread.currentThread().isInterrupted()) {
                MusicPlayer play = new MusicPlayer("Zombie_Fail.mp3");
                new Thread(play).start();
                for (nowPic = 0; nowPic < nowSumPic; nowPic++) {
                    // sleep 120ms change
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.repaint();
                }
            }
            // Die~~~

            if (this.state == BOOM && !Thread.currentThread().isInterrupted()) {
                for (nowPic = 0; nowPic < nowSumPic; nowPic++) {
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.repaint();
                }
            }
            // Boom~~~

            setVisible(false);
            Thread.currentThread().interrupt();

        }
    }
}