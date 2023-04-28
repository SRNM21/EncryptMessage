import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame
{
    private static final int DISPLAY_WIDTH = 450;
    private static final int DISPLAY_HEIGHT = 400;

    public static void main(String[] args) throws Exception 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run()
            {
                new MainMenu();
            } 
        });
    }

    private void goToTool(boolean enc)
    {
        dispose();
        new Tool(enc);
    }

    MainMenu()
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

        JLabel title = new JLabel("Cryptograph", SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 10, 300, 100);

        JButton encryptBtn = new JButton("Encrypt  ", new ImageIcon("Cryptograph/images/EncryptLogo.png"));
		encryptBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		encryptBtn.setBackground(new Color(246, 246, 246));
		encryptBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptBtn.setVerticalTextPosition(AbstractButton.CENTER);
        encryptBtn.setHorizontalTextPosition(AbstractButton.LEADING);  
        encryptBtn.setFocusPainted(false);
		encryptBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		encryptBtn.setBounds(CENTERED, 130, 300, 80);
        encryptBtn.addActionListener(e -> goToTool(true));
        
        JButton decryptBtn = new JButton("Decrypt  ", new ImageIcon("Cryptograph/images/DecryptLogo.png"));
		decryptBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		decryptBtn.setBackground(new Color(246, 246, 246));
		decryptBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        decryptBtn.setVerticalTextPosition(AbstractButton.CENTER);
        decryptBtn.setHorizontalTextPosition(AbstractButton.LEADING); 
        decryptBtn.setFocusPainted(false);
		decryptBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		decryptBtn.setBounds(CENTERED, 230, 300, 80);
        decryptBtn.addActionListener(e -> goToTool(false));

        panel.add(title); 
        panel.add(encryptBtn); 
        panel.add(decryptBtn); 
        setVisible(true);
    }
}
