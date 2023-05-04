import javax.swing.SwingUtilities;

public class Frame 
{
    public static void main(String[] args) throws Exception 
    {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}