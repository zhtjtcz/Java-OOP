package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LaunchFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public JLayeredPane layeredPane = new JLayeredPane();

    private GameBoard gameboard;

    public void reset() {
        gameboard.reset();
        System.exit(0);
    }

    public LaunchFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Plants VS Zombies");
        this.setSize(810, 625);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setContentPane(layeredPane);
        this.setIconImage(new ImageIcon("img\\Icon.png").getImage());

        ImageIcon background = new ImageIcon("img\\Surface.png");
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        panel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        layeredPane.add(panel, 10);
        // Setting the icon and background img

        ImageIcon startImg = new ImageIcon("img\\SelectorScreen_Adventure_highlight.png");
        ImageIcon startImg2 = new ImageIcon("img\\SelectorScreen_StartAdventure_Highlight.png");
        JLabel startLabel = new JLabel(startImg);
        startLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startLabel.setIcon(startImg2);
            }

            public void mouseExited(MouseEvent e) {
                startLabel.setIcon(startImg);
            }

            public void mouseClicked(MouseEvent e) {
                layeredPane.setVisible(false);
                gameboard = new GameBoard(LaunchFrame.this);
            }
        });
        startLabel.setBounds(410, 70, startImg.getIconWidth(), startImg2.getIconHeight());
        layeredPane.add(startLabel, 0);
        // Add the start game button

        ImageIcon quitImg = new ImageIcon("img\\SelectorScreen_Quit1.png");
        ImageIcon quitImg2 = new ImageIcon("img\\SelectorScreen_Quit2.png");
        JLabel quitLabel = new JLabel(quitImg);
        quitLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                quitLabel.setIcon(quitImg2);
            }

            public void mouseExited(MouseEvent e) {
                quitLabel.setIcon(quitImg);
            }

            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        quitLabel.setBounds(725, 510, quitImg.getIconWidth(), quitImg2.getIconHeight());
        layeredPane.add(quitLabel, 0);
        // Add the quit button

        ImageIcon helpPic = new ImageIcon("img\\Help.png");
        JLabel helpView = new JLabel(helpPic) {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(helpPic.getImage(), 0, 0, 810, 625, this);
            }
        };
        helpView.setVisible(false);
        layeredPane.add(helpView, Integer.valueOf(114514));
        // Add the help button
        // TODO change the picture to a text?

        ImageIcon helpImg = new ImageIcon("img\\SelectorScreen_Help1.png");
        ImageIcon helpImg2 = new ImageIcon("img\\SelectorScreen_Help2.png");
        JLabel helpLabel = new JLabel(helpImg);

        JLabel helpReturnButton = new JLabel();
        helpReturnButton.setBounds(325, 543, 155, 40);
        helpReturnButton.setVisible(false);
        helpReturnButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                helpView.setVisible(false);
                helpReturnButton.setVisible(false);
                startLabel.setVisible(true);
                quitLabel.setVisible(true);
                helpLabel.setVisible(true);
            }
        });
        layeredPane.add(helpReturnButton, new Integer(998244353));

        helpLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                helpLabel.setIcon(helpImg2);
            }

            public void mouseExited(MouseEvent e) {
                helpLabel.setIcon(helpImg);
            }

            public void mouseClicked(MouseEvent e) {
                helpView.setVisible(true);
                helpReturnButton.setVisible(true);
                startLabel.setVisible(false);
                quitLabel.setVisible(false);
                helpLabel.setVisible(false);
                helpView.setBounds(0, 0, 810, 625);
                // Set all buttons unvisible
            }
        });
        helpLabel.setBounds(655, 520, helpImg.getIconWidth(), helpImg2.getIconHeight());
        layeredPane.add(helpLabel, 0);

        ImageIcon optionImg = new ImageIcon("img\\SelectorScreen_Options1.png");
        ImageIcon optionImg2 = new ImageIcon("img\\SelectorScreen_Options2.png");
        JLabel optionLabel = new JLabel(optionImg);
            optionLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                optionLabel.setIcon(optionImg2);
            }

            public void mouseExited(MouseEvent e) {
                optionLabel.setIcon(optionImg);
            }

            public void mouseClicked(MouseEvent e) {
                System.exit(0);
                // TODO add a new page
            }
        });
        optionLabel.setBounds(572, 486, optionImg.getIconWidth(), optionImg2.getIconHeight());
        layeredPane.add(optionLabel, 0);
        // Add the option button
    }
}