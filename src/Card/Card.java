package Card;

import Control.IController;
import Card.Plant.Plant;
import Control.PuzzleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class Card extends JLabel implements MouseListener, Runnable {
    protected static final long serialVersionUID = 1L;
    protected boolean inCooling;
    protected boolean isChoosed;
    protected int cd;
    protected int totTime;
    // CD and cooling

    protected String cardName;
    protected int price;
    protected int cardWidth;
    protected int cardHeight;

    protected ImageIcon card;
    protected ImageIcon cardLight;
    protected ImageIcon cardDark;
    protected ImageIcon cardCooling;

    protected ImageIcon preImg;
    protected ImageIcon blurImg;
    protected Rectangle rectangle;
    protected IController controller;

    protected Map<String, Plant> plantMap;

    protected int index;

    
	/** 
	 * @return int 卡片ID
	 */
	public int getIndex() {
        return index;
    }

    
	/** 
	 * @param index ID
	 */
	public void setIndex(int index) {
        this.index = index;
    }

    
	/** 
	 * @param x x坐标
	 * @param y y坐标
	 * @param w 宽度
	 * @param h 高度
	 */
	public void setRectangle(int x, int y, int w, int h) {
        this.rectangle = new Rectangle(x, y, w, h);
    }

    
	/** 
	 * @return Rectangle 长方形卡片
	 */
	public Rectangle getRectangle() {
        return rectangle;
    }

    
	/** 
	 * @param b 是否在冷却状态
	 */
	public void setInCooling(boolean b) {
        this.inCooling = b;
    }

    
	/** 
	 * @return boolean 是否在冷却
	 */
	public boolean getInCooling() {
        return inCooling;
    }

    
	/** 
	 * @return int 价格
	 */
	public int getPrice() {
        return price;
    }

    
	/** 
	 * @return String 卡片名称
	 */
	public String getCardName() {
        return cardName;
    }

    
	/** 
	 * @return int 卡片宽度
	 */
	public int getCardWidth() {
        return cardWidth;
    }

    
	/** 
	 * @return int 卡片高度
	 */
	public int getCardHeight() {
        return cardHeight;
    }

    
	/** 
	 * @param choosed 是否被选择
	 */
	public void setChoosed(boolean choosed) {
        this.isChoosed = choosed;
    }

    
	/** 
	 * @param e 点击事件
	 */
	@Override
    public void mouseClicked(MouseEvent e) {
        selected();
    }

    
	/** 
	 * @param e 进入事件
	 */
	@Override
    public void mouseEntered(MouseEvent e) {
    }

    
	/** 
	 * @param e 退出事件
	 */
	@Override
    public void mouseExited(MouseEvent e) {
    }

    
	/** 
	 * @param e 按下事件
	 */
	@Override
    public void mousePressed(MouseEvent e) {
    }

    
	/** 
	 * @param e 释放事件
	 */
	@Override
    public void mouseReleased(MouseEvent e) {
    }

    public Card(String name, IController controller) {
        this.controller = controller;
        this.plantMap = controller.getPlantMap();
        this.cardName = name;
        this.cardLight = new ImageIcon("Img\\Cards\\" + cardName + "0.png");
        this.cardDark = new ImageIcon("Img\\Cards\\" + cardName + "1.png");
        this.cardCooling = new ImageIcon("Img\\Cards\\" + cardName + "2.png");
        this.preImg = new ImageIcon("img\\" + cardName + "\\" + cardName + "_0.png ");
        this.blurImg = new ImageIcon("img\\Blurs\\" + cardName + ".png ");
        this.card = cardDark;
        this.inCooling = true;
        this.addMouseListener(this);
        this.cardHeight = cardLight.getIconHeight();
        this.cardWidth = cardLight.getIconWidth();
        if (this.cardName.contains("Zombie")) {
            this.price = ((PuzzleController) controller).getZombieMap().get(cardName).getPrice();
            this.cd = ((PuzzleController) controller).getZombieMap().get(cardName).getCardCD();
        } else {
            this.price = plantMap.get(cardName).getPrice();
            this.cd = plantMap.get(cardName).getCardCD();
        }
    }

    
	/** 
	 * @param SunCount 需要阳光数量
	 * @return boolean 是否可以购买
	 */
	public boolean sunCountEnough(int SunCount) {
        return SunCount >= plantMap.get(this.cardName).getPrice();
    }

    
	/** 
	 * @param SunCount 需要阳光数量
	 */
	public void check(int SunCount) {
        if (sunCountEnough(SunCount) && !inCooling) {
            card = cardLight;
            this.repaint();
        } else {
            card = cardDark;
            this.repaint();
        }
    }
    
	/** 
	 * @param g 图层
	 */
	// 能否选择这个卡，能：light，否：dark

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = card.getIconHeight(), w = card.getIconWidth();
        g.drawImage(card.getImage(), 0, 0, w, h, this);
        if (inCooling) {
            g.drawImage(cardCooling.getImage(), 0, 0, w, h - h * totTime / cd, 0, 0, w, h - h * totTime / cd, this);
        }
    }

    public void selected() {
        if (!inCooling && controller.getIntSunCount() >= this.getPrice()) {
            System.out.println("Selected");
            controller.getTopPanel().setVisible(true);
            controller.setPreImg(this.preImg);
            controller.setBlurImg(this.blurImg);
            controller.setCard(this);
            controller.setSelectedIndex(index);
            controller.setNowPlant(new Plant().getPlant(this.getCardName()));
            inCooling = true;
            isChoosed = true;
            check(0);
        }
    }
    // Select a card

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