import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

public class Tool extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int DISPLAY_WIDTH = 900;
    private static final int DISPLAY_HEIGHT = 800;
    private static JLabel[] KEYS_INDICATOR = new JLabel[94];
    private static char[] KEYS = new char[94];
    private static Panel panel = new Panel();

    private void showResult() 
    {
        final int NEW_DISPLAY_WIDTH = DISPLAY_WIDTH + 300;

        this.setSize(NEW_DISPLAY_WIDTH, DISPLAY_HEIGHT);
        panel.setSize(NEW_DISPLAY_WIDTH, DISPLAY_HEIGHT); 

        Dimension newDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((newDimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((newDimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    Tool(boolean enc)
    {
        this.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        this.setResizable(false);    
        this.setTitle("Message Tool");
		this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Cryptograph/images/ICON.png")); 

		panel.setBackground(new Color(217, 217, 217));
		panel.setBounds(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		panel.setLayout(null);
		this.getContentPane().add(panel);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        final int CENTERED = (int) ((DISPLAY_WIDTH - 300) / 2);
        JLabel title = new JLabel(enc ? "Encryption" : "Decryption", SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds(CENTERED, 0, 300, 60);

        JLabel primaryLabel = new JLabel(enc ? "Message" : "Encrypted Message");
        primaryLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        primaryLabel.setForeground(new Color(63, 63, 63));
        primaryLabel.setBounds(20, 50, 250, 60);

        JLabel primaryTextCount = new JLabel("Text: 0/1000");
        primaryTextCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        primaryTextCount.setForeground(new Color(63, 63, 63));
        primaryTextCount.setBounds(DISPLAY_WIDTH - 130 , 50, 120, 50);

        
        JTextArea primaryTextArea = new JTextArea(5, 10);  
        primaryTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        primaryTextArea.setForeground(new Color(63, 63, 63));
        primaryTextArea.setLineWrap(true);
        primaryTextArea.setWrapStyleWord(true);
        primaryTextArea.setDocument(new LimitedDocument(1000));

        Action deleteAction1 = primaryTextArea.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
        primaryTextArea.getActionMap()
            .put(DefaultEditorKit.deletePrevCharAction, new DeleteActionWrapper(primaryTextArea, deleteAction1));
        primaryTextArea.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                if (e.getKeyCode() <= 32 && e.getKeyCode() >= 94) e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {} 

            @Override
            public void keyReleased(KeyEvent e) 
            {     
                primaryTextCount.setText("Text: " + primaryTextArea.getText().length() + "/1000");    
            }
        });

        JScrollPane primaryScrollTextArea = new JScrollPane(primaryTextArea);
        primaryScrollTextArea.setBounds(20, 100, DISPLAY_WIDTH - 50, (DISPLAY_HEIGHT / 3) - 50);

        JLabel keyLabel = new JLabel("Key");
        keyLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        keyLabel.setForeground(new Color(63, 63, 63));
        keyLabel.setBounds(20, 340, 250, 60);

        JLabel keyTextCount = new JLabel("Text: 0/94");
        keyTextCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        keyTextCount.setForeground(new Color(63, 63, 63));
        keyTextCount.setBounds((DISPLAY_WIDTH / 2) - 100, 340, 100, 50);
        
        Action doNothing = new AbstractAction() { public void actionPerformed(ActionEvent e) {} };

        JTextArea keyTextArea = new JTextArea(5, 10);  
        keyTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        keyTextArea.setForeground(new Color(63, 63, 63));
        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);
        keyTextArea.setDocument(new LimitedDocument(94));
        keyTextArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
        keyTextArea.getActionMap().put("doNothing", doNothing);

        Action deleteAction2 = keyTextArea.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
        keyTextArea.getActionMap().put(DefaultEditorKit.deletePrevCharAction, new DeleteActionWrapper(keyTextArea, deleteAction2));
        keyTextArea.addKeyListener(new KeyListener() 
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                final char c = e.getKeyChar();

                if (c == KeyEvent.VK_SPACE) e.consume();
                
                if (c >= 33 && c <= 126)
                {
                    int ci = c - '!';
                    char k = (char) ('!' + keyTextArea.getText().length());

                    if (KEYS[ci] == 0) KEYS[ci] = k++;
                }          
            }

            @Override
            public void keyPressed(KeyEvent e) {} 

            @Override
            public void keyReleased(KeyEvent e) 
            {     
                keyTextCount.setText("Text: " + keyTextArea.getText().length() + "/94");    
            }
        });

        JScrollPane keyScrollTextArea = new JScrollPane(keyTextArea);
        keyScrollTextArea.setBounds(20, 390, (DISPLAY_WIDTH / 2) - 50, (DISPLAY_HEIGHT / 3) - 50);

        /* 
         * Message must be lowercase english letters and space (' ')
         * Message must not contain any special characters
         * Enter key in a corresponding order
         * Key must be more than 26 lowercase english letters and/or space (' ')
         * Key must not contain any special characters
         * Key must contain every letter in english alphabet('a' to 'z') at least once
         */

        JLabel keySyntax = new JLabel("Enter key for character (..):");
        keySyntax.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        keySyntax.setForeground(new Color(63, 63, 63));
        keySyntax.setBounds(25, 590, 270, 60);

        for (int i = 0, lx = 450, ly = 390; i < 94; i++, lx += 30)
        {
            if (lx >= 850) 
            {
                ly += 30;
                lx = 450;
            }

            KEYS_INDICATOR[i] = new JLabel(String.valueOf((char) (i + 33)));
            KEYS_INDICATOR[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
            KEYS_INDICATOR[i].setForeground(new Color(63, 63, 63));
            KEYS_INDICATOR[i].setBounds(lx, ly, 30, 30);
            panel.add(KEYS_INDICATOR[i]);
        }

        // test extend
        JButton test = new JButton("extend");
        test.addActionListener(e -> showResult());
        test.setBounds(CENTERED, 100, 200, 200);

        panel.add(title);
        panel.add(primaryLabel);
        panel.add(primaryScrollTextArea);
        panel.add(primaryTextCount);
        panel.add(keyLabel);
        panel.add(keyScrollTextArea);
        panel.add(keyTextCount);
        panel.add(keySyntax);
        this.setVisible(true);
    }
}

class LimitedDocument extends PlainDocument 
{
	private int maxLength; 
    
    LimitedDocument(int maxLength) 
    {
        this.maxLength = maxLength;
    }

    public void insertString(int offs, String str, javax.swing.text.AttributeSet a) 
    throws BadLocationException 
    {
        int currentLength = this.getLength();

        if (currentLength >= maxLength) return;

        if (currentLength + str.length() > maxLength) 
            str = str.substring(0, maxLength - currentLength);
 
        super.insertString(offs, str, a);
    }
}

class DeleteActionWrapper extends AbstractAction
{
    private JTextArea textArea;
    private Action action;
    
    DeleteActionWrapper(JTextArea textArea, Action action)
    {
        this.textArea = textArea;
        this.action = action; 
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    { 
        if (textArea.getText().length() > 0) action.actionPerformed(e);
    }
}