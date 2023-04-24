import java.awt.Toolkit;

import java.awt.*;
import javax.swing.*;

public class Tool extends JFrame
{
    private static final int DISPLAY_WIDTH = 900;
    private static final int DISPLAY_HEIGHT = 700;
    private static Panel panel = new Panel();

    private void showResult() 
    {
        setSize(DISPLAY_WIDTH + 300, DISPLAY_HEIGHT);
        panel.setSize(DISPLAY_WIDTH + 300, DISPLAY_HEIGHT); 

        Dimension newDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (int) ((newDimension.getWidth() - getWidth()) / 2);
        int newY = (int) ((newDimension.getHeight() - getHeight()) / 2);
        setLocation(newX, newY);
    }

    Tool(boolean enc)
    {
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        setResizable(false);    
        setTitle("Message Tool");
		getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setIconImage(Toolkit.getDefaultToolkit().getImage("Cryptograph/images/ICON.png")); 

		panel.setBackground(new Color(217, 217, 217));
		panel.setBounds(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		panel.setLayout(null);
		getContentPane().add(panel);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        
        final int CENTERED = (int) ((DISPLAY_WIDTH - 300) / 2);
        final String mode = enc ? "Encryption" : "Decryption";
        JLabel title = new JLabel(mode, SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 0, 300, 60);

        JButton test = new JButton("extend");
        test.addActionListener(e -> showResult());
        test.setBounds(CENTERED, 100, 200, 200);

        panel.add(title);
        panel.add(test);
        setVisible(true);
    }
}
