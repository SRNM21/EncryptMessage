
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Tool extends JFrame
{
    private static final int DISPLAY_WIDTH = 900;
    private static final int DISPLAY_HEIGHT = 700;
    private static Panel panel = new Panel();
    private static final int MAX_PRIMARY_TEXT = 500;
    private static final int MAX_KEY_TEXT = 100;

    private void showResult() 
    {
        final int NEW_DISPLAY_WIDTH = DISPLAY_WIDTH + 300;

        setSize(NEW_DISPLAY_WIDTH, DISPLAY_HEIGHT);
        panel.setSize(NEW_DISPLAY_WIDTH, DISPLAY_HEIGHT); 

        Dimension newDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((newDimension.getWidth() - getWidth()) / 2);
        int y = (int) ((newDimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
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
        JLabel title = new JLabel(enc ? "Encryption" : "Decryption", SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 0, 300, 60);

        JLabel primaryLabel = new JLabel(enc ? "Message" : "Encrypted Message");
        primaryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        primaryLabel.setForeground(new Color(63, 63, 63));
        primaryLabel.setBounds(20, 60, 250, 60);

        JLabel primaryTextCount = new JLabel("Text: 0/500");
        primaryTextCount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        primaryTextCount.setForeground(new Color(63, 63, 63));
        primaryTextCount.setBounds((DISPLAY_WIDTH / 2) - 100, 60, 100, 50);

        JTextArea primaryTextArea = new JTextArea(5, 10);  
        primaryTextArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        primaryTextArea.setForeground(new Color(63, 63, 63));
        primaryTextArea.setLineWrap(true);
        primaryTextArea.setWrapStyleWord(true);
        primaryTextArea.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                if (primaryTextArea.getText().length() >= MAX_PRIMARY_TEXT) e.consume();;
            }

            @Override
            public void keyPressed(KeyEvent e) {} 

            @Override
            public void keyReleased(KeyEvent e) 
            {     
                primaryTextCount.setText("Text: " + primaryTextArea.getText().length() + "/500");    
            }
        });
        
        JScrollPane primaryScrollTextArea = new JScrollPane(primaryTextArea);
        primaryScrollTextArea.setBounds(20, 110, (DISPLAY_WIDTH / 2) - 40, 450);

        JLabel keyLabel = new JLabel("Key");
        keyLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        keyLabel.setForeground(new Color(63, 63, 63));
        keyLabel.setBounds(DISPLAY_WIDTH / 2 , 60, 250, 60);

        JLabel keyTextCount = new JLabel("Text: 0/500");
        keyTextCount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        keyTextCount.setForeground(new Color(63, 63, 63));
        keyTextCount.setBounds(DISPLAY_WIDTH - 120, 60, 100, 50);

        JTextArea keyTextArea = new JTextArea(5, 10);  
        keyTextArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        keyTextArea.setForeground(new Color(63, 63, 63));
        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);
        keyTextArea.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                if (keyTextArea.getText().length() >= MAX_KEY_TEXT) e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) 
            {     
                keyTextArea.setText("Text: " + keyTextArea.getText().length() + "/500");    
            }
        });

        JScrollPane KeyScrollTextArea = new JScrollPane(keyTextArea);
        KeyScrollTextArea.setBounds(DISPLAY_WIDTH / 2 , 110, (DISPLAY_WIDTH / 2) - 40, 200);
         
        /*
         * Message must be lowercase english letters and space (' ')
         * Message must not contain any special characters
         * 
         * Key must be more than 26 lowercase english letters and/or space (' ')
         * Key must not contain any special characters
         * Key must contain every letter in english alphabet('a' to 'z') at least once
         */

        // test extend
        JButton test = new JButton("extend");
        test.addActionListener(e -> showResult());
        test.setBounds(CENTERED, 100, 200, 200);

        panel.add(title);
        panel.add(primaryLabel);
        panel.add(primaryScrollTextArea);
        panel.add(primaryTextCount);
        panel.add(keyLabel);
        panel.add(KeyScrollTextArea);
        panel.add(keyTextCount);
        setVisible(true);
    }
}
