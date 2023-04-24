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
        JLabel title = new JLabel(enc ? "Encryption" : "Decyption", SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 0, 300, 60);

        JLabel primaryLabel = new JLabel(enc ? "Message" : "Encrypted Message");
        primaryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        primaryLabel.setForeground(new Color(63, 63, 63));
        primaryLabel.setBounds(20, 80, 250, 60);

        JTextArea primaryTextArea = new JTextArea(10, 10);  
        primaryTextArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        primaryTextArea.setForeground(new Color(63, 63, 63));
        primaryTextArea.setLineWrap(true);
        primaryTextArea.setWrapStyleWord(true);

        JScrollPane scrollTextArea = new JScrollPane(primaryTextArea);
        scrollTextArea.setBounds(20, 130, (DISPLAY_WIDTH / 2) - 20, 200);

        // test extend
        JButton test = new JButton("extend");
        test.addActionListener(e -> showResult());
        test.setBounds(CENTERED, 100, 200, 200);

        panel.add(title);
        panel.add(primaryLabel);
        panel.add(scrollTextArea);
        setVisible(true);
    }
}
