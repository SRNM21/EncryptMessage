import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Cryptograph extends JFrame
{
    private static final int DISPLAY_WIDTH = 600;
    private static final int DISPLAY_HEIGHT = 600;
    private static final String TITLE = "Message Tool";
    private static final ImageIcon ICON = new ImageIcon("Cryptograph/images/ICON.png");

    private void goToHome()
    {
        String m = "Are you sure?";
        int a = JOptionPane.showConfirmDialog(this, m, TITLE, JOptionPane.YES_NO_OPTION);

        if (a == JOptionPane.YES_OPTION)
        {
            this.dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        }
    }

    private String encrypt(String message, String key) 
    {   
        StringBuilder sb = new StringBuilder();
        char[] keyLet = new char[94];
        char charInd = '!';

        for (final char c : key.toCharArray())
            if (c != ' ' && keyLet[c - '!'] == 0) 
                keyLet[c - '!'] = charInd++;

        for (final char c : message.toCharArray()) 
        {
            if (c == ' ' || c == '\n') sb.append(c);
            else sb.append(keyLet[c - '!']); 
        }

        return sb.toString();
    }

    private String decrypt(String message, String key) 
    {   
        StringBuilder sb = new StringBuilder();
        char[] keyLet = new char[94];
        char charInd = '!';

        for (final char c : key.toCharArray())
            if (c != ' ' && keyLet[c - '!'] == 0) 
                keyLet[c - '!'] = charInd++;

        for (final char c : message.toCharArray()) 
        {
            String keyString = String.valueOf(keyLet);

            if (c == ' ' || c == '\n') sb.append(c);
            else sb.append((char) (keyString.indexOf(c) + '!'));
        }
        
        return sb.toString();
    }

    Cryptograph(boolean encMode, String message, String key)
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

        JLabel resultLabel = new JLabel(encMode ? "Encrypted Message" : "Decrypted Message");
        resultLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        resultLabel.setForeground(new Color(63, 63, 63));
        resultLabel.setBounds(20, 10, 250, 50);

        JTextArea resultText = new JTextArea(5, 10); 
        resultText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        resultText.setForeground(new Color(63, 63, 63));
        resultText.setLineWrap(true);
        resultText.setWrapStyleWord(true);
        resultText.setEditable(false);
        resultText.setText(encMode ? encrypt(message, key) : decrypt(message, key));

        JScrollPane scrollResultText = new JScrollPane(resultText);
        scrollResultText.setBounds(20, 70, DISPLAY_WIDTH - 50, DISPLAY_HEIGHT - 200);
        
        final int BTN_POS = (int) ((DISPLAY_WIDTH - 100) / 2);
        JButton HometBtn = new JButton("Home ", new ImageIcon("Cryptograph/images/Home.png"));
		HometBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		HometBtn.setBackground(new Color(246, 246, 246));
		HometBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        HometBtn.setVerticalTextPosition(AbstractButton.CENTER);
        HometBtn.setHorizontalTextPosition(AbstractButton.LEADING);  
        HometBtn.setFocusPainted(false);
		HometBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HometBtn.setBounds(BTN_POS - 60, 500, 100, 40);
        HometBtn.addActionListener(e -> goToHome());

        JButton copyBtn = new JButton("Copy ", new ImageIcon("Cryptograph/images/Copy.png"));
		copyBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		copyBtn.setBackground(new Color(246, 246, 246));
		copyBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        copyBtn.setVerticalTextPosition(AbstractButton.CENTER);
        copyBtn.setHorizontalTextPosition(AbstractButton.LEADING);  
        copyBtn.setFocusPainted(false);
		copyBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		copyBtn.setBounds(BTN_POS + 60, 500, 100, 40);
        copyBtn.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                StringSelection text = new StringSelection(resultText.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(text, null);

                JOptionPane.showMessageDialog(getContentPane(), "Copied to clipboard");
            }
        });

        this.add(resultLabel);
        this.add(scrollResultText); 
        this.add(HometBtn);
        this.add(copyBtn);
        this.setVisible(true);
    }
}

class Validate
{
    protected MessageValidation isValidMessage(String message)
    {
        if (message.isEmpty()) 
            return MessageValidation.NullMessage;

        for (char c : message.toCharArray()) 
            if ((c < 32 || c > 126) && c != '\n')
                return MessageValidation.InvalidUnicode;

        return MessageValidation.ValidMessage;
    }

    protected KeyValidation isValidKey(String key)
    {   
        if (key.length() < 26) 
            return KeyValidation.InsufficientCipherKey;

        for (char c : key.toCharArray()) 
            if (c < 32 || c > 126)
                return KeyValidation.InvalidUnicode;

        for (char c = '!'; c <= '~'; c++)
            if (!key.contains("" + c))
                return KeyValidation.InsufficientCipherKey;

        return KeyValidation.ValidKey;
    }
}

enum MessageValidation
{
    NullMessage,
    InvalidUnicode,
    ValidMessage,
}

enum KeyValidation
{
    InsufficientCipherKey,
    InvalidUnicode,
    ValidKey,
}