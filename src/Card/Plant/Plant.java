package Card.Plant;

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
    // Card.Plant picture

    protected int sleepTime;
    private int cardCD;
    private int state;
    private boolean canChange = false;

    private IController controller;
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

    
	/** 
	 * @return int 行数
	 */
	public int getR() {
        return row;
    }

    
	/** 
	 * @return int 列数
	 */
	public int getC() {
        return column;
    }

    
	/** 
	 * @return int 获取图片
	 */
	public int getPic() {
        return pic;
    }

    
	/** 
	 * @param Cnow 设置累计的冷却时间
	 */
	public void setCnow(int Cnow) {
        this.Cnow = Cnow;
    }

    
	/** 
	 * @return int 获取当前冷却时间
	 */
	public int getCnow() {
        return Cnow;
    }

    
	/** 
	 * @return int CD间隔
	 */
	public int getCD() {
        return CD;
    }

    
	/** 
	 * @return int 价格
	 */
	public int getPrice() {
        return price;
    }

    
	/** 
	 * @return int 卡片CD
	 */
	public int getCardCD() {
        return cardCD;
    }

    
	/** 
	 * @param getController(
	 * @return String 植物名称
	 */
	public String getName() { return this.name; }

    
	/** 
	 * @return IController 我是僵尸模式的控制器
	 */
	public IController getController() {
        return controller;
    }

    
	/** 
	 * @param controller 当前模式的控制器
	 */
	public void setController(IController controller) {
        this.controller = controller;
    }

    
	/** 
	 * @param row 行
	 * @param column 列
	 */
	public void setPos(int row, int column) {
        this.row = row;
        this.column = column;
    }
    // Getter and setter

    public void picChange() {
        if (this.name.equals("Potato")){
            if (this.controller instanceof PuzzleController)    this.state = 1;
            if (getCnow() >= getCD() && this.state == 0){
                this.state = 1;
                pic = 0;
            }
            if (this.state == 0){
                img = new ImageIcon("img\\" + this.name + "\\" + "sleep.png");
            }   else {
                pic = (pic + 1) % SumPic;
                img = new ImageIcon("img\\" + this.name + "\\" + this.name + "_" + pic + ".png");
            }
            return;
        }
        pic = (pic + 1) % SumPic;
        img = new ImageIcon("img\\" + this.name + "\\" + this.name + "_" + pic + ".png");
        if (this.getName().equals("WallNut"))
            img = new ImageIcon("img\\" + this.name + "\\" + this.name + "_cracked" + state + "\\" + this.name
                    + "_cracked" + state + "_" + pic + ".png");
    }

    
	/** 
	 * @param x 受到的伤害值
	 */
	public void attacked(int x) {
        this.hp -= x;
        if (this.getName().equals("WallNut")) {
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
        if (name.equals("SunFlower") && controller instanceof PuzzleController)
            for (int i = 0;i < 10; ++i)
                new Thread(new Sun(getController(), getR(), getC())).start();
    }

    
	/** 
	 * @param g 图层
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // The shadow under the plants
        
        ImageIcon shadow = new ImageIcon("img\\shadow.png");
        if (!this.name.equals("Potato"))
            g2.drawImage(shadow.getImage(), -11, 45, shadow.getIconWidth(), shadow.getIconHeight(), null);
        g.drawImage(img.getImage(), 0, 0, img.getIconWidth(), img.getIconHeight(), null);
        if (getName().equals("SunFlower")) {
            if (this.getCnow() > 261) {
                ImageIcon tempImg = new ImageIcon("img\\GoldenSunflower\\Frame" + getPic() + ".png");
                g.drawImage(tempImg.getImage(), 0, 0, tempImg.getIconWidth(), tempImg.getIconHeight(), null);
            }
        }
    }

    void accumulate() {
        if (!this.canChange) return;
        if (getName().equals("SunFlower")) {
            if (controller instanceof PuzzleController) {
                if (Cnow <= 240)
                    this.picChange();
                else
                    Cnow = 0;
            } else {
                if (Cnow <= 261)
                    this.picChange();
            }
        } else {
            this.picChange();
        }
        this.Cnow = this.Cnow + 1;
    }
    
	/** 
	 * @param name 植物名称
	 * @return Plant 植物
	 */
	// Accelorator the CD time

    public Plant getPlant(String name) {
        return switch (name) {
            case "SunFlower" -> new Plant().SunFlower();
            case "PeaShooter" -> new Plant().PeaShooter();
            case "Repeater" -> new Plant().Repeater();
            case "CherryBomb" -> new Plant().CherryBomb();
            case "WallNut" -> new Plant().WallNut();
            case "Potato" -> new Plant().Potato();
            default -> null;
        };
    }

    @Override
    public void run() {
        boolean hasAttacked = false;// repeater
        while (hp > 0) {
            this.accumulate();
            // System.out.println(getName());
            if (getName().equals("SunFlower")) {
                if (this.getCnow() >= this.getCD() && !(controller instanceof PuzzleController)) {
                    this.setCnow(0);
                    new Thread(new Sun(getController(), getR(), getC())).start();
                }
            } else if (getName().equals("PeaShooter")) {
                if (this.getCnow() >= this.getCD()) {
                    if (controller.haveZombie(row)) {
                        this.setCnow(0);
                        new Thread(new Bullet(getController(), getR(), getC())).start();
                    } else
                        this.setCnow(CD);
                }
            } else if (getName().equals("Repeater")) {
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
            } else if (getName().equals("CherryBomb")) {
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
            } else if (getName().equals("Potato")){
                if (this.state == 1){
                    if (controller.haveaZombie(row, column)){
                        controller.Potatoboom(row, column);
                        break;
                    }
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

    
	/** 
	 * @return Plant  向日葵
	 */
	// All plant is here~
    public Plant SunFlower() {
        Plant tempPlant = new Plant("SunFlower", 50, 0, 300, 17, true);
        tempPlant.CD = 24000 / 90;
        tempPlant.cardCD = 3500;// 7500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    
	/** 
	 * @return Plant 豌豆射手
	 */
	public Plant PeaShooter() {
        Plant tempPlant = new Plant("PeaShooter", 100, 0, 300, 13, true);
        tempPlant.CD = 1000 / 90;
        tempPlant.cardCD = 3000;// 7500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    
	/** 
	 * @return Plant 双发射手
	 */
	public Plant Repeater() {
        Plant tempPlant = new Plant("Repeater", 200, 0, 300, 15, true);
        tempPlant.CD = 1000 / 90;
        tempPlant.cardCD = 3500;// 7500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    
	/** 
	 * @return Plant 樱桃炸弹
	 */
	public Plant CherryBomb() {
        Plant tempPlant = new Plant("CherryBomb", 150, 0, 1000000, 8, true);
        tempPlant.CD = 1000000;
        tempPlant.cardCD = 10000;// 12500;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }

    
	/** 
	 * @return Plant 坚果墙
	 */
	public Plant WallNut() {
        Plant tempPlant = new Plant("WallNut", 50, 0, 4000, 16, true);
        tempPlant.CD = 1000000;
        tempPlant.cardCD = 8000;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }
    
    
	/** 
	 * @return Plant 土豆雷
	 */
	public Plant Potato() {
        Plant tempPlant = new Plant("Potato", 25, 0, 300, 9, true);
        tempPlant.CD = 10000 / 90;
        tempPlant.cardCD = 8000;
        tempPlant.sleepTime = 90;
        return tempPlant;
    }
}