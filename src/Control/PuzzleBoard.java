package Control;

import Zombie.*;
import Shovel.*;
import Card.*;
import Sun.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class PuzzleBoard extends JLayeredPane {
    private static final long serialVersionUID = 1L;

    private JFrame GameFrame;
    private ImageIcon panel, cardboard, shovelbank;
    private JPanel Panel;
    private JPanel Cardboard;
    private JPanel ShovelBank;

    private Shovel shovel;

    IController controller;

    private int x = -215;
    private boolean flag = false;
    private int direction = 1;
    private JLabel SunLabel;

    Thread sunThread, zombieThread;

    public void reset() {
        sunThread.interrupt();
        zombieThread.interrupt();
    }

    class PaintThread implements Runnable {
        JFrame frame;

        PaintThread(LaunchFrame launchFrame) {
            this.frame = launchFrame;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // cardboard淡入
            for (int y = -40; y <= 5; y++) {
                Cardboard.setBounds(20, y, cardboard.getIconWidth(), cardboard.getIconHeight());
                ShovelBank.setBounds(465, y, shovelbank.getIconWidth(), shovelbank.getIconHeight());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Cardboard.repaint();
            }

            shovel.setBounds(458, -5, shovel.getImg().getIconWidth(), shovel.getImg().getIconHeight());

            // 倒计时
            JLabel label;
            for (int i = 1; i <= 3; i++)
                try {
                    label = new JLabel(new ImageIcon("img\\PrepareGrowPlants" + i + ".png"));
                    PuzzleBoard.this.add(label, 1);
                    label.setBounds(250, 200, 300, 200);
                    Thread.sleep(700);
                    label.setVisible(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            controller.setRunning();

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.currentThread().interrupt();
        }
    }

    PuzzleBoard(LaunchFrame launchframe) {
        this.controller = new PuzzleController(launchframe);
        this.GameFrame = launchframe;
        this.GameFrame.setContentPane(PuzzleBoard.this);
        this.setVisible(true);

        // bg
        panel = new ImageIcon("img\\background_dark.jpg");
        Panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(panel.getImage(), x, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        Panel.setVisible(true);
        Panel.setBounds(0, 0, panel.getIconWidth(), panel.getIconHeight());
        PuzzleBoard.this.add(Panel, -1);

        // cardboard
        cardboard = new ImageIcon("img\\SeedBank.png");
        Cardboard = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(cardboard.getImage(), 0, 0, cardboard.getIconWidth(), cardboard.getIconHeight(), this);
            }
        };
        Cardboard.setVisible(true);
        Cardboard.setLayout(null);
        PuzzleBoard.this.add(Cardboard, 0);

        // sun
        SunLabel = new JLabel("" + controller.getSunCount().getText(), JLabel.CENTER);
        SunLabel.setBounds(20, 60, 35, 20);
        Cardboard.add(SunLabel);

        controller.setLayeredPane(this);
        controller.setSunCount(SunLabel);

        // shovel bank
        shovelbank = new ImageIcon("img\\ShovelBank.png");
        ShovelBank = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(shovelbank.getImage(), 0, 0, shovelbank.getIconWidth(), shovelbank.getIconHeight(), this);
            }
        };
        ShovelBank.setVisible(true);
        PuzzleBoard.this.add(ShovelBank, 0);

        // shovel
        shovel = new Shovel(controller);
        shovel.setVisible(true);
        controller.setShovel(shovel);
        PuzzleBoard.this.add(shovel, Integer.valueOf(2));
        shovel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                shovel.selected();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });


        // cards
        Card card1 = new Card("SunFlower", controller);
        card1.setRectangle(83, 7, card1.getCardWidth(), card1.getCardHeight());
        card1.setBounds(83, 7, card1.getCardWidth(), card1.getCardHeight());
        controller.addCard(card1);
        Cardboard.add(card1);

        Card card2 = new Card("PeaShooter", controller);
        card2.setRectangle(142, 7, card2.getCardWidth(), card2.getCardHeight());
        card2.setBounds(142, 7, card2.getCardWidth(), card2.getCardHeight());
        controller.addCard(card2);
        Cardboard.add(card2);

        Card card3 = new Card("Repeater", controller);
        card3.setRectangle(201, 7, card3.getCardWidth(), card3.getCardHeight());
        card3.setBounds(201, 7, card3.getCardWidth(), card3.getCardHeight());
        controller.addCard(card3);
        Cardboard.add(card3);

        Card card4 = new Card("CherryBomb", controller);
        card4.setRectangle(260, 7, card4.getCardWidth(), card4.getCardHeight());
        card4.setBounds(260, 7, card4.getCardWidth(), card4.getCardHeight());
        controller.addCard(card4);
        Cardboard.add(card4);

        Card card5 = new Card("WallNut", controller);
        card5.setRectangle(319, 7, card5.getCardWidth(), card5.getCardHeight());
        card5.setBounds(319, 7, card5.getCardWidth(), card5.getCardHeight());
        controller.addCard(card5);
        Cardboard.add(card5);

        // animation
        Thread Animation = new Thread(new PaintThread(launchframe));
        Animation.start();

        // produce zombie TODO: Remove it from Puzzle Mode
        zombieThread = new Thread(new ZombieProducer(controller));
        zombieThread.start();

        // mouse img
        JPanel topPanel = controller.getTopPanel();
        topPanel.setVisible(false);
        topPanel.setOpaque(false);
        topPanel.setBounds(0, 0, panel.getIconWidth(), panel.getIconHeight());
        PuzzleBoard.this.add(topPanel, Integer.valueOf(998244353));
    }
}