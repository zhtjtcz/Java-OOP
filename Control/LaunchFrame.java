package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LaunchFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public JLayeredPane layeredPane = new JLayeredPane();

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
    }

}