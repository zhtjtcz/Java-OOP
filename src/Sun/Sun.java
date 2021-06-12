package Sun;

import Control.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sun extends JLabel implements Runnable {
    private static final long serialVersionUID = 1L;
    private Image image, offScreenImage;

    private int x, y;
    private boolean isCollected;
    private int nowPic;
    private static int totPic = 21;
    private IController controller;

    private double alpha = 100;

    private boolean drop;
    // 掉落还是随机生成

    private int ymax;
    // 随机生成

    private int ymin;
    private int startX, startY, endX, endY;
    // 自然掉落

    public Sun(IController controller) {
        this.x = (int) (Math.random() * 660) + 30;
        this.ymin = this.y = 40;
        this.ymax = (int) (Math.random() * 400) + 130;
        this.controller = controller;
        this.drop = false;
        setVisible(true);
        controller.getLayeredPane().add(this, new Integer(500));
    }

    public Sun(IController controller, int r, int c) {
        this(controller);
        this.drop = true;
        this.ymin = r * 100 + 55;
        startX = c * 80 + 34 + (int) (Math.random() * 2);
        startY = r * 100 + 80;
        endX = c * 80 + 30 + (int) (Math.random() * 10);
        endY = r * 100 + 100;
        this.x = startX;
        this.y = startY;
    }

    public void picChange() {
        nowPic = (nowPic + 1) % totPic;
    }

    public void shrink() {
        for (int i = 1; i <= 50; i++) {
            if (i % 8 == 0) picChange();
            alpha -= 2;
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        setVisible(false);
    }

    @Override
    public void run() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isCollected) isCollected = true;
            }
        });

        // Dropping
        if (drop) {
            int vx = 1, vy = 2;
            boolean flag1 = false, flag2 = false, flag3 = false, flag4 = false;
            int dx = (endX >= startX) ? 1 : -1, dy = -1;
            for (int i = 1; (this.x <= endX && dx == 1) || (this.x >= endX && dx == -1) || this.y <= endY; i++) {
                if (i % 5 == 0) this.picChange();
                this.setBounds(x, y, 78, 78);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                x += dx * vx;
                y += dy * vy;
                if (!flag1) {
                    if (y <= (ymin + startY) / 2) {
                        flag1 = true;
                        vy = 1;
                    }
                } else if (!flag2) {
                    if (y <= ymin) {
                        vy = -1;
                        flag2 = true;
                    }
                } else if (!flag3) {
                    if ((x >= (endX * 3 + startX) / 4 || y >= (ymin + endY) / 2)) {
                        vy = -2;
                        flag3 = true;
                    }
                } else if (!flag4) {
                    if ((x >= (endX * 7 + startX) / 8 || y >= (startY + endY) / 2)) {
                        vy = -3;
                        flag4 = true;
                    }
                }
                this.repaint();
                // 掉落，用线段近似二次函数
            }
        } else {
            for (int i = 1; this.y < ymax && !isCollected; i++) {
                if (i % 50 == 0) this.picChange();

                this.setBounds(x, y, 78, 78);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (i % 10 == 0) this.y++;
                this.repaint();
            }
            // 随机生成，直线
        }


        for (int i = 1; i <= 5000 && !isCollected; i++) {
            if (i % 50 == 0) this.picChange();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.repaint();
        }
        // On the ground

        Point tempPoint = new Point(x, y);
        if (isCollected) {
            MusicPlayer play = new MusicPlayer("clicksun.mp3");
            new Thread(play).start();
            controller.addSunCount(25);
            controller.checkCards();
            int dir = x < 25 ? -1 : 1;
            for (int i = 1; (dir == 1 && x > 25 || x <= 25 && dir == -1) && y > 25; i++) {
                if (i % 10 == 0) {
                    this.picChange();
                }
                double ty = (x - 25) * (tempPoint.getY() - 25) / (tempPoint.getX() - 25) + 25;
                if ((ty <= y && dir == 1) || (ty >= y && dir == -1)) y -= 6;
                else {
                    x -= 6;
                    if ((int) ((100 * x - 2400) / (tempPoint.getX() - 25)) != (int) ((100 * x - 2500)
                            / (tempPoint.getX() - 25)))
                        alpha -= (Math.random() >= 0.45) ? 1 : 0;
                }

                this.setBounds(x, y, 78, 78);
                this.repaint();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } else this.shrink();
        // Disappear

        setVisible(false);
        Thread.currentThread().interrupt();
    }
    
	/** 
	 * @param g 图层
	 */
	// Collect it

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("img\\Sun\\" + nowPic + ".png");

        image = img.getImage();
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.SrcOver.derive((float) alpha / 100));
        g2.drawImage(image, 0, 0, img.getIconWidth() * 6 / 7, img.getIconWidth() * 6 / 7, this);
    }

    
	/** 
	 * @param g 图层
	 */
	public void update(Graphics g) {
        if (offScreenImage == null)
            offScreenImage = this.createImage(800, 600);
        Graphics gImage = offScreenImage.getGraphics();
        paint(gImage);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}