import java.awt.Toolkit;

import java.awt.*;
import javax.swing.*;

public class Tool extends JFrame
{
    private static final int DISPLAY_WIDTH = 900;
    private static final int DISPLAY_HEIGHT = 700;
    
    Tool(boolean enc)
    {
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        setResizable(false);    
        setTitle("Message Tool");
		getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setIconImage(Toolkit.getDefaultToolkit().getImage("Cryptograph/images/ICON.png")); 

        Panel panel = new Panel();
		panel.setBackground(new Color(217, 217, 217));
		panel.setBounds(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		panel.setLayout(null);
		getContentPane().add(panel);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        
        final int CENTERED = (int) ((DISPLAY_WIDTH - 300) / 2);
        final String titleText = enc ? "Encryption" : "Decryption";
        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 10, 300, 100);

        panel.add(title);
        setVisible(true);
    }
}
