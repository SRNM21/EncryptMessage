import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

public class Constructor extends JFrame
{
	private static final int DISPLAY_WIDTH = 900;
    private static final int DISPLAY_HEIGHT = 700;
    private static final String TITLE = "Message Tool";
    private static final ImageIcon ICON = new ImageIcon("Cryptograph/images/ICON.png");
    private static JLabel[] KEYS_INDICATOR = new JLabel[94];
    static boolean ENC_MODE;

    private void showResult(boolean encMode, String message, String key) 
    {
        String m = encMode ? "Encrypt Message?" : "Decrypt Message?";
        int a = JOptionPane.showConfirmDialog(this, m, TITLE, JOptionPane.YES_NO_OPTION);

        if (a == JOptionPane.YES_OPTION)
        {
            this.dispose();

            SwingUtilities.invokeLater(new Runnable() 
            {
                public void run()
                {
                    new Cryptograph(ENC_MODE, message, key);
                } 
            });
        }
    }

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

    private void construct(String message, String key)
    {
        Validate validate = new Validate();

        MessageValidation validMessage = validate.isValidMessage(message);
        KeyValidation validKey = validate.isValidKey(key);

        final String NM = "Message is Empty";
        final String IU = "Message contains unkown characters";
        final String IC = "Key must contain every english letters and special characters";

        switch(validMessage)
        {
            case NullMessage    -> JOptionPane.showMessageDialog(this, NM ,TITLE, JOptionPane.WARNING_MESSAGE);
            case InvalidUnicode -> JOptionPane.showMessageDialog(this, IU, TITLE, JOptionPane.WARNING_MESSAGE); 
            case ValidMessage   -> 
            {
                switch(validKey)
                {
                    case InsufficientCipherKey  -> JOptionPane.showMessageDialog(this, IC ,TITLE, JOptionPane.WARNING_MESSAGE);     
                    case InvalidUnicode         -> JOptionPane.showMessageDialog(this, IU, TITLE, JOptionPane.WARNING_MESSAGE);     
                    case ValidKey               -> showResult(ENC_MODE, message, key);
                }
            }
        }
    }

    Constructor(boolean enc)
    {
        super(TITLE);
        this.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        this.setResizable(false);    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setIconImage(ICON.getImage()); 
		this.getContentPane().setLayout(null);
        ENC_MODE = enc;

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        JLabel title = new JLabel(enc ? "Encryption" : "Decryption", SwingConstants.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        title.setForeground(new Color(63, 63, 63));
        title.setBounds((int) ((DISPLAY_WIDTH - 300) / 2), 0, 300, 60);

        JLabel primaryLabel = new JLabel(enc ? "Message" : "Encrypted Message");
        primaryLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        primaryLabel.setForeground(new Color(63, 63, 63));
        primaryLabel.setBounds(20, 50, 250, 50);
        
        JLabel primaryTextCount = new JLabel("Text: 0/1000");
        primaryTextCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        primaryTextCount.setForeground(new Color(63, 63, 63));
        primaryTextCount.setBounds(DISPLAY_WIDTH - 140 , 50, 110, 50);
        
        JTextArea primaryTextArea = new JTextArea(5, 10); 
        primaryTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        primaryTextArea.setForeground(new Color(63, 63, 63));
        primaryTextArea.setLineWrap(true);
        primaryTextArea.setWrapStyleWord(true);
        primaryTextArea.setDocument(new LimitedDocument(1000));
        primaryTextArea.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void changedUpdate(DocumentEvent e) 
            { 
                update(primaryTextArea, primaryTextCount, 1000); 
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) 
            { 
                update(primaryTextArea, primaryTextCount, 1000); 
            }

            @Override
            public void insertUpdate(DocumentEvent e) 
            { 
                update(primaryTextArea, primaryTextCount, 1000); 
            }
        });
        
        Action deleteAction1 = primaryTextArea.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
        primaryTextArea.getActionMap()
            .put(DefaultEditorKit.deletePrevCharAction, new DeleteActionWrapper(primaryTextArea, deleteAction1));
        primaryTextArea.addKeyListener(new KeyAdapter() 
        {
            public void keyTyped(KeyEvent e) 
            {
                char c = e.getKeyChar();
                if (c < 32 || c > 126) e.consume();    	
            }
        }); 

        JScrollPane primaryScrollTextArea = new JScrollPane(primaryTextArea);
        primaryScrollTextArea.setBounds(20, 100, DISPLAY_WIDTH - 50, (DISPLAY_HEIGHT / 3) - 50);
        
        JLabel keyLabel = new JLabel("Key");
        keyLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        keyLabel.setForeground(new Color(63, 63, 63));
        keyLabel.setBounds(20, 290, 250, 50);
       
        JLabel keyTextCount = new JLabel("Text: 0/200");
        keyTextCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        keyTextCount.setForeground(new Color(63, 63, 63));
        keyTextCount.setBounds((DISPLAY_WIDTH / 2) - 120, 290, 100, 50);
       
        Action doNothing = new AbstractAction() { public void actionPerformed(ActionEvent e) {} };

        JTextArea keyTextArea = new JTextArea(5, 10); 
        keyTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        keyTextArea.setForeground(new Color(63, 63, 63));
        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);
        keyTextArea.setDocument(new LimitedDocument(200));
        keyTextArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
        keyTextArea.getActionMap().put("doNbothing", doNothing);
        keyTextArea.getDocument().addDocumentListener(new DocumentListener() 
        {
            @Override
            public void changedUpdate(DocumentEvent e) 
            { 
                update(keyTextArea, keyTextCount, 200); 
            }

            @Override
            public void removeUpdate(DocumentEvent e) 
            { 
                update(keyTextArea, keyTextCount, 200);
                
                for (char c = '!'; c <= '~'; c++)
                    if (!checkKey(c))
                        KEYS_INDICATOR[c - '!'].setForeground(new Color(63, 63, 63));
            }

            @Override
            public void insertUpdate(DocumentEvent e) 
            { 
                update(keyTextArea, keyTextCount, 200);
                
                for (char c = '!'; c <= '~'; c++)
                    if (checkKey(c))
                        KEYS_INDICATOR[c - '!'].setForeground(Color.GREEN);  
            }

            public boolean checkKey(char key) 
            {
                for (char c : keyTextArea.getText().toCharArray()) 
                    if (c == key) return true;
                return false;
            }
        });

        Action deleteAction2 = keyTextArea.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
        keyTextArea.getActionMap()
            .put(DefaultEditorKit.deletePrevCharAction, new DeleteActionWrapper(keyTextArea, deleteAction2));
        keyTextArea.addKeyListener(new KeyAdapter() 
        {
            public void keyTyped(KeyEvent e) 
            {
                char c = e.getKeyChar();
                if (c < 32 || c > 126) e.consume();    	
            }
        }); 

        JScrollPane keyScrollTextArea = new JScrollPane(keyTextArea);
        keyScrollTextArea.setBounds(20, 340, (DISPLAY_WIDTH / 2) - 50, (DISPLAY_HEIGHT / 3) - 20);

        for (int i = 0, lx = 450, ly = 300; i < 94; i++, lx += 32)
        {
            if (lx >= 850) 
            {
                ly += 32;
                lx = 450;
            }

            KEYS_INDICATOR[i] = new JLabel(String.valueOf((char) (i + 33)));
            KEYS_INDICATOR[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
            KEYS_INDICATOR[i].setForeground(new Color(63, 63, 63));
            KEYS_INDICATOR[i].setBounds(lx, ly, 30, 30);
            this.add(KEYS_INDICATOR[i]);
        }

        final int BTN_POS = (int) ((DISPLAY_WIDTH - 100) / 2);
        JButton HometBtn = new JButton("Home ", new ImageIcon("Cryptograph/images/Home.png"));
		HometBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		HometBtn.setBackground(new Color(246, 246, 246));
		HometBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        HometBtn.setVerticalTextPosition(AbstractButton.CENTER);
        HometBtn.setHorizontalTextPosition(AbstractButton.LEADING);  
        HometBtn.setFocusPainted(false);
		HometBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HometBtn.setBounds(BTN_POS - 60, 590, 100, 40);
        HometBtn.addActionListener(e -> goToHome());

        String resultBtnText = enc ? "Encrypt " : "Decrypt ";
        ImageIcon resultBtnIcon = enc ? 
            new ImageIcon("Cryptograph/images/EncryptLogoS.png") : 
            new ImageIcon("Cryptograph/images/DecryptLogoS.png");

        JButton getResultBtn = new JButton(resultBtnText, resultBtnIcon);
		getResultBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		getResultBtn.setBackground(new Color(246, 246, 246));
		getResultBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        getResultBtn.setVerticalTextPosition(AbstractButton.CENTER);
        getResultBtn.setHorizontalTextPosition(AbstractButton.LEADING);  
        getResultBtn.setFocusPainted(false);
		getResultBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getResultBtn.setBounds(BTN_POS + 60, 590, 100, 40);
        getResultBtn.addActionListener(e -> construct(primaryTextArea.getText(), keyTextArea.getText()));

        this.add(title);
        this.add(primaryLabel);
        this.add(primaryScrollTextArea);
        this.add(primaryTextCount);
        this.add(keyLabel);
        this.add(keyScrollTextArea);
        this.add(keyTextCount);
        this.add(HometBtn);
        this.add(getResultBtn);

        this.setVisible(true);
    }

    public void update(JTextArea textarea, JLabel label, int limit) 
    {
        label.setText("Text: " + textarea.getText().length() + "/" + limit);
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
        if (textArea.getText().length() > 0)  action.actionPerformed(e);
    }
}