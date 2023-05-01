import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainMenu extends JFrame
{
    private static final int DISPLAY_WIDTH = 450;
    private static final int DISPLAY_HEIGHT = 400;
    private static final String TITLE = "Message Tool";
    private static final ImageIcon ICON = new ImageIcon("Cryptograph/images/ICON.png");

    private void goToConstructor(boolean enc)
    {
        this.dispose();

        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run()
            {
                new Constructor(enc);
            } 
        });
    }

    MainMenu()
    {
        super(TITLE);
        this.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        this.setResizable(false);    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setIconImage(ICON.getImage()); 
		this.getContentPane().setLayout(null);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        final int CENTERED = (int) ((DISPLAY_WIDTH - 300) / 2);

        JLabel title = new JLabel("Cryptograph", SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 10, 300, 100);

        JButton encryptBtn = new JButton("Encrypt  ", new ImageIcon("Cryptograph/images/EncryptLogoL.png"));
		encryptBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		encryptBtn.setBackground(new Color(246, 246, 246));
		encryptBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptBtn.setVerticalTextPosition(AbstractButton.CENTER);
        encryptBtn.setHorizontalTextPosition(AbstractButton.LEADING);  
        encryptBtn.setFocusPainted(false);
		encryptBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		encryptBtn.setBounds(CENTERED, 130, 300, 80);
        encryptBtn.addActionListener(e -> goToConstructor(true));
        
        JButton decryptBtn = new JButton("Decrypt  ", new ImageIcon("Cryptograph/images/DecryptLogoL.png"));
		decryptBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		decryptBtn.setBackground(new Color(246, 246, 246));
		decryptBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        decryptBtn.setVerticalTextPosition(AbstractButton.CENTER);
        decryptBtn.setHorizontalTextPosition(AbstractButton.LEADING); 
        decryptBtn.setFocusPainted(false);
		decryptBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		decryptBtn.setBounds(CENTERED, 230, 300, 80);
        decryptBtn.addActionListener(e -> goToConstructor(false));

        this.add(title); 
        this.add(encryptBtn); 
        this.add(decryptBtn); 
        this.setVisible(true);
    }
}
