import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class test extends JFrame {
    private JTextArea textArea;
    private char lastDeletedChar;

    public test() {
        setTitle("Text Area Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        lastDeletedChar = '\0';
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                // not interested in insertions
            }
            public void removeUpdate(DocumentEvent e) {
                try {
                    lastDeletedChar = e.getDocument().getText(e.getOffset(), e.getLength()).charAt(0);
                    System.out.println("Deleted: " + lastDeletedChar);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
            public void changedUpdate(DocumentEvent e) {
                // not interested in attribute changes
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        new test();
    }
}
