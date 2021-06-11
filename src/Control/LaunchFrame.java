package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LaunchFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public JLayeredPane layeredPane = new JLayeredPane();

    private GameBoard gameboard;
    private PuzzleBoard puzzleboard;

    public void reset(int x){
        if (gameboard != null){
            gameboard.reset();
             System.out.println("!!");
        }
        if (puzzleboard != null){
            puzzleboard.reset();
            System.out.println("!!!");
        }
        this.setContentPane(layeredPane);
        layeredPane.setVisible(true);
        layeredPane.repaint();
        /*
        if (x == 0)
            new LoseBoard();
        else
            new WinBoard();
        */
//        System.exit(0);
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

        ImageIcon startImg = new ImageIcon("img\\SelectorScreen_StartAdventure_Button1.png");
        ImageIcon startImg2 = new ImageIcon("img\\SelectorScreen_StartAdventure_Highlight.png");
        JLabel startLabel = new JLabel(startImg);
        gameboard = new GameBoard(LaunchFrame.this, layeredPane);
        startLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startLabel.setIcon(startImg2);
            }

            public void mouseExited(MouseEvent e) {
                startLabel.setIcon(startImg);
            }

            public void mouseClicked(MouseEvent e) {
                layeredPane.setVisible(false);
                gameboard.startGame();
            }
        });
        startLabel.setBounds(410, 60, startImg.getIconWidth(), startImg2.getIconHeight());
        layeredPane.add(startLabel, 0);
        // Add the start game button

        ImageIcon miniImg = new ImageIcon("img\\SelectorScreen_Survival_button.png");
        ImageIcon miniImg2 = new ImageIcon("img\\SelectorScreen_Survival_highlight.png");
        JLabel miniLabel = new JLabel(miniImg);
        miniLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                miniLabel.setIcon(miniImg2);
            }

            public void mouseExited(MouseEvent e) {
                miniLabel.setIcon(miniImg);
            }

            public void mouseClicked(MouseEvent e) {
                layeredPane.setVisible(false);
            }
        });
        miniLabel.setBounds(415, 170, miniImg.getIconWidth(), miniImg2.getIconHeight());
        layeredPane.add(miniLabel, 0);
        // Add the mini game button

        ImageIcon smartImg = new ImageIcon("img\\SelectorScreen_Challenges_button.png");
        ImageIcon smartImg2 = new ImageIcon("img\\SelectorScreen_Challenges_highlight.png");
        JLabel smartLabel = new JLabel(smartImg);
        puzzleboard = new PuzzleBoard(LaunchFrame.this, layeredPane);
        smartLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                smartLabel.setIcon(smartImg2);
            }

            public void mouseExited(MouseEvent e) {
                smartLabel.setIcon(smartImg);
            }

            public void mouseClicked(MouseEvent e) {
                layeredPane.setVisible(false);
                puzzleboard.startGame();
            }
        });
        smartLabel.setBounds(420, 255, smartImg.getIconWidth(), smartImg2.getIconHeight());
        layeredPane.add(smartLabel, 0);
        // Add the smart game button

        ImageIcon surviveImg = new ImageIcon("img\\SelectorScreen_Vasebreaker_button.png");
        ImageIcon surviveImg2 = new ImageIcon("img\\SelectorScreen_vasebreaker_highlight.png");
        JLabel surviveLabel = new JLabel(surviveImg);
        surviveLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                surviveLabel.setIcon(surviveImg2);
            }

            public void mouseExited(MouseEvent e) {
                surviveLabel.setIcon(surviveImg);
            }

            public void mouseClicked(MouseEvent e) {
                layeredPane.setVisible(false);
            }
        });
        surviveLabel.setBounds(420, 325, surviveImg.getIconWidth(), surviveImg2.getIconHeight());
        layeredPane.add(surviveLabel, 0);
        // Add the survive game button


        ImageIcon almanacImg = new ImageIcon("img\\SelectorScreen_Almanac.png");
        ImageIcon almanacImg2 = new ImageIcon("img\\SelectorScreen_AlmanacHighlight.png");
        JLabel almanacLabel = new JLabel(almanacImg);
        almanacLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                almanacLabel.setIcon(almanacImg2);
            }

            public void mouseExited(MouseEvent e) {
                almanacLabel.setIcon(almanacImg);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        almanacLabel.setBounds(340, 425, almanacImg.getIconWidth(), almanacImg2.getIconHeight());
        layeredPane.add(almanacLabel, 0);
        // Add the almanac book

        ImageIcon gardenImg = new ImageIcon("img\\SelectorScreen_ZenGarden.png");
        ImageIcon gardenImg2 = new ImageIcon("img\\SelectorScreen_ZenGardenHighlight.png");
        JLabel gardenLabel = new JLabel(gardenImg);
        gardenLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                gardenLabel.setIcon(gardenImg2);
            }

            public void mouseExited(MouseEvent e) {
                gardenLabel.setIcon(gardenImg);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        gardenLabel.setBounds(175, 400, gardenImg.getIconWidth(), gardenImg2.getIconHeight());
        layeredPane.add(gardenLabel, 0);
        // Add the garden

        ImageIcon achivementImg = new ImageIcon("img\\Achievements_pedestal.png");
        ImageIcon achivementImg2 = new ImageIcon("img\\Achievements_pedestal_press.png");
        JLabel achivementLabel = new JLabel(achivementImg);
        achivementLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                achivementLabel.setIcon(achivementImg2);
            }

            public void mouseExited(MouseEvent e) {
                achivementLabel.setIcon(achivementImg);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        achivementLabel.setBounds(15, 400, achivementImg.getIconWidth(), achivementImg2.getIconHeight());
        layeredPane.add(achivementLabel, 0);
        // Add the achivement

        ImageIcon shopImg = new ImageIcon("img\\SelectorScreen_Store.png");
        ImageIcon shopImg2 = new ImageIcon("img\\SelectorScreen_StoreHighlight.png");
        JLabel shopLabel = new JLabel(shopImg);
        shopLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                shopLabel.setIcon(shopImg2);
            }

            public void mouseExited(MouseEvent e) {
                shopLabel.setIcon(shopImg);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        shopLabel.setBounds(430, 480, shopImg.getIconWidth(), shopImg2.getIconHeight());
        layeredPane.add(shopLabel, 0);
        // Add the shop

        ImageIcon board1Img = new ImageIcon("img\\SelectorScreen_WoodSign1.png");
        JLabel board1Label = new JLabel(board1Img);
        board1Label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        board1Label.setBounds(30, 0, board1Img.getIconWidth(), board1Img.getIconHeight());
        layeredPane.add(board1Label, 0);
        // Add the board1

        ImageIcon board2Img = new ImageIcon("img\\SelectorScreen_WoodSign2.png");
        ImageIcon board2Img2 = new ImageIcon("img\\SelectorScreen_WoodSign2_press.png");
        JLabel board2Label = new JLabel(board2Img);
        board2Label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                board2Label.setIcon(board2Img2);
            }

            public void mouseExited(MouseEvent e) {
                board2Label.setIcon(board2Img);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        board2Label.setBounds(30, 140, board2Img.getIconWidth(), board2Img2.getIconHeight());
        layeredPane.add(board2Label, 0);
        // Add the board2

        ImageIcon board3Img = new ImageIcon("img\\SelectorScreen_WoodSign3.png");
        ImageIcon board3Img2 = new ImageIcon("img\\SelectorScreen_WoodSign3_press.png");
        JLabel board3Label = new JLabel(board3Img);
        board3Label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                board3Label.setIcon(board3Img2);
            }

            public void mouseExited(MouseEvent e) {
                board3Label.setIcon(board3Img);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        board3Label.setBounds(30, 190, board3Img.getIconWidth(), board3Img2.getIconHeight());
        layeredPane.add(board3Label, 0);
        // Add the board3

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