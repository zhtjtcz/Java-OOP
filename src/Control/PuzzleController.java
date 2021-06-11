package Control;

import Card.*;
import Card.Plant.*;
import Card.Zombie.*;
import Shovel.*;
// Import all element

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PuzzleController implements IController {
    public boolean isRunning = false;
    private int selectedIndex = -1;
    private boolean selectingShovel = false;
    // Basic settings


    @Override
    public boolean isRunning() {
        return isRunning;
    }

    private LaunchFrame frame;
    private static int cardHeight = 90, cardWidth = 45;
    private static int tileHeight = 100, tileWidth = 80;
    // Appearance

    private Plant[][] plants = new Plant[5][9];
    public Plant nowPlant = null;
    public Zombie nowZombie = null;
    private Map<String, Plant> plantMap = new HashMap<>();
    private Map<String, Zombie> zombieMap = new HashMap<>();
    // Plants

    private JLabel sunCount = new JLabel();
    // Sun

    private static int numRow = 5, numCol = 9;    // Row and Col number
    private static int cardNum = 1;                // Card sum
    private Card[] Cards = new Card[10];
    private Card card = null;                    // Card on selected
    // Cards

    private Shovel shovel;
    // Shovel

    public List<ArrayList<Zombie>> Zombies = new LinkedList<>();
    // Zombies

    private Point mouseP;
    private JLayeredPane layeredPane;
    private TopPanel topPanel = new TopPanel();
    private ImageIcon preImg;
    private ImageIcon blurImg;
    // Mouse

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public class myMouseListener extends MouseAdapter implements MouseMotionListener {
        private TopPanel topPanel;

        myMouseListener(TopPanel topPanel) {
            this.topPanel = topPanel;
        }

        public void mouseClicked(MouseEvent e) {
            Point p = getP();
            if (p == null) return;

            if (shovel.getBounds().contains(p)) return;

            if (e.getButton() == MouseEvent.BUTTON3) {// right click, cancel choosing card
                if (card != null) {
                    cancelSelectingCard();
                } else if (selectingShovel) {
                    selectingShovel = false;
                    setShovel(false);
                    topPanel.setVisible(false);
                }
                return;
            }

            for (int i = 0; i < cardNum; i++)
                if ((Cards[i].getRectangle()).contains(p)) return;

            if (e.getButton() == MouseEvent.BUTTON1) {// left click
                int x = p.x, y = p.y;
                int grassR = (y - cardHeight) / tileHeight, grassC = (x - cardWidth) / tileWidth;
                // Card.Plant or displant
                if (isOnGrass(grassR, grassC)) {
                    if (nowPlant != null && plants[grassR][grassC] == null && !selectingShovel) {
                        // Card.Plant
                        plant(grassR, grassC, nowPlant);
                        reduceSun(nowPlant.getPrice());
                        cancelSelectingCard();
                    } else if (nowZombie != null && plants[grassR][grassC] == null && !selectingShovel) {
                        //getLayeredPane().add(nowZombie, Integer.valueOf(400));
                        //tempZombie = new Zombie().BucketZombie(this, grassR);
                        getLayeredPane().add(nowZombie, Integer.valueOf(400));
                        nowZombie.setRow(grassR);
                        nowZombie.setCol(50 + (grassC - 1) * 80);
                        addZombie(nowZombie, grassR);
                        new Thread(nowZombie).start();

                        reduceSun(nowZombie.getPrice());

                        nowZombie = null;
                        cancelSelectingCard();
                    } else if (nowPlant == null && plants[grassR][grassC] != null && selectingShovel) {
                        // Remove
                        plants[grassR][grassC].die();
                        setShovel(false);
                    }
                }
            }
        }

        public void mouseMoved(MouseEvent e) {
            setP(e.getPoint());
            topPanel.repaint();
        }
    }

    public PuzzleController(LaunchFrame launchframe) {
        frame = launchframe;

        setCardMap();
        for (int i = 0; i < 5; i++) Zombies.add(new ArrayList<>());

        cardNum = 0;

        this.setSunCount(500);
        this.topPanel.addMouseMotionListener(new myMouseListener(this.topPanel));
        this.topPanel.addMouseListener(new myMouseListener(this.topPanel));
        this.topPanel.setVisible(false);
    }

    /* Sun */
    public void setSunCount(JLabel cnt) {
        this.sunCount = cnt;
    }

    public void setSunCount(int cnt) {
        this.sunCount.setText("" + cnt);
    }

    public void addSunCount(int cnt) {
        setSunCount(cnt + getIntSunCount());
    }

    public JLabel getSunCount() {
        return this.sunCount;
    }

    public int getIntSunCount() {
        return Integer.parseInt(this.sunCount.getText());
    }

    public void reduceSun(int x) {
        int sun = getIntSunCount();
        sun -= x;
        setSunCount(sun);
    }
    //-----------------------

    /* Card.Plant */
    public Plant[][] getPlants() {
        return this.plants;
    }

    public Map<String, Plant> getPlantMap() {
        return plantMap;
    }

    public Map<String, Zombie> getZombieMap() {return zombieMap;}

    public void plantDeath(int row, int column) {
        plants[row][column] = null;
    }

    private boolean isOnGrass(int r, int c) {
        return r >= 0 && r < numRow && c >= 0 && c < numCol;
    }

    public void plant(int r, int c, Plant plant) {
        plant.setController(this);
        plant.setVisible(true);
        plant.setBounds(45 + c * 80, 90 + r * 100, 300, 300);
        plant.setPos(r, c);
        layeredPane.add(plant, Integer.valueOf(50));
        plants[r][c] = plant;
        new Thread(plant).start();
    }
    // Put a plant on (r,c)
    //-----------------------


    /* Card */
    public void setCard(Card card) {
        this.card = card;
    }

    public void setSelectedIndex(int x) {
        this.selectedIndex = x;
    }

    public Card getCard() {
        return card;
    }

    public void cancelSelectingCard() {
        card.setChoosed(false);
        card = null;
        selectedIndex = -1;
        nowPlant = null;
        topPanel.setVisible(false);
    }

    public void setNowPlant(Plant nowPlant) {
        this.nowPlant = nowPlant;
    }

    public void setNowZombie(Zombie zombie) {this.nowZombie = zombie;}

    public void checkCards() {
        for (int i = 0; i < cardNum; i++) Cards[i].check(getIntSunCount());
    }

    public void setCardMap() {
        plantMap.put("SunFlower", new Plant().SunFlower());
        plantMap.put("PeaShooter", new Plant().PeaShooter());
        plantMap.put("Repeater", new Plant().Repeater());
        plantMap.put("CherryBomb", new Plant().CherryBomb());
        plantMap.put("WallNut", new Plant().WallNut());
        zombieMap.put("BucketZombie", new Zombie().BucketZombie(this, 1));
    }

    public void addCard(Card card) {
        Cards[cardNum] = card;
        card.setIndex(cardNum);
        new Thread(card).start();
        cardNum++;
    }
    //-----------------------

    /* Card.Zombie */
    public void addZombie(Zombie zombie, int row) {
        Zombies.get(row).add(zombie);
        System.out.println("Add Zombie  " + Zombies.get(row).size() + " " + zombie.getXPos());
    }

    public void deleteZombie(Zombie zombie, int row) {
        for (Zombie tempZombie : Zombies.get(row))
            if (tempZombie == zombie) {
                Zombies.get(row).remove(tempZombie);
                return;
            }
        assert (false);
    }

    public Zombie getAttackedZombie(int row, int x) {
        for (Zombie tempZombie : Zombies.get(row))
            if (tempZombie.getXPos() >= x - 2 && tempZombie.getXPos() <= x + 2) {
                return tempZombie;
            }
        return null;
    }

    public void boom(int row, int column) {
        if (row > 0)
            for (Zombie tempZombie : Zombies.get(row - 1)) {
                int c = tempZombie.getColumn();
                if (c <= column + 1 && c >= column - 1) {
                    tempZombie.boom();
                }
            }

        for (Zombie tempZombie : Zombies.get(row)) {
            int c = tempZombie.getColumn();
            if (c <= column + 1 && c >= column - 1) tempZombie.boom();
        }

        if (row + 1 < 5)
            for (Zombie tempZombie : Zombies.get(row + 1)) {
                int c = tempZombie.getColumn();
                if (c <= column + 1 && c >= column - 1) {
                    tempZombie.boom();
                }
            }
    }

    public boolean haveZombie(int row) {
        for (Zombie zombie : Zombies.get(row))
            if (zombie.getXPos() < 720) return true;
        return false;
    }
    //-----------------------

    /* Game Control */
    public void setLayeredPane(JLayeredPane gameboardView) {
        this.layeredPane = gameboardView;
    }

    public void setRunning() {
        this.isRunning = true;
        for (int i = 0; i < cardNum; i++) Cards[i].setInCooling(false);

        checkCards();
    }

    public void endGame() {
        System.out.println("You Lost.");
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++)
                if (plants[i][j] != null)
                    plants[i][j].die();

            for (Zombie zombie : Zombies.get(i)) zombie.endThread();
        }
        this.isRunning = false;
        frame.reset();
    }
    //-----------------------

    /* Shovel */
    public void setShovel(Shovel shovel) {
        this.shovel = shovel;
    }

    public void setShovel(boolean b) {
        this.selectingShovel = b;
        if (b) {
            ImageIcon pre;
            pre = new ImageIcon("img\\Shovel.png");
            setBlurImg(pre);
            setPreImg(pre);
            topPanel.setVisible(true);
        } else {
            topPanel.setVisible(false);
            shovel.setVisible(true);
        }
    }
    //-----------------------

    /* Mouse */
    public Point getP() {
        return topPanel.getMousePosition();
    }

    public void setP(Point p) {
        this.mouseP = p;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public void setPreImg(ImageIcon preImg) {
        this.preImg = preImg;
    }

    public void setBlurImg(ImageIcon blurImg) {
        this.blurImg = blurImg;
    }

    public class TopPanel extends JPanel {
        Graphics2D g2;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g2 = (Graphics2D) g;
            Point mouseP = getP();
            if (mouseP != null) {
                int c = (mouseP.x - 40) / 80;
                int r = (mouseP.y - 90) / 100;
                if (isOnGrass(r, c) && plants[r][c] == null) {
                    g2.drawImage(blurImg.getImage(), 40 + c * 80, 90 + r * 100, this);
                }
                g2.drawImage(preImg.getImage(), mouseP.x - 35, mouseP.y - 40, preImg.getIconWidth(),
                        preImg.getIconWidth(), this);
            }
        }

        public void update(Graphics g) {
            Image offScreenImage = this.createImage(800, 600);
            Graphics gImage = offScreenImage.getGraphics();
            paint(gImage);
            if (mouseP != null)
                g.drawImage(offScreenImage, mouseP.x - 35, mouseP.y - 40, null);
        }
    }
    //-----------------------
}