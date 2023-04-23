import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame
{
    private static final int DISPLAY_WIDTH = 500;
    private static final int DISPLAY_HEIGHT = 500;
    
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

    public MainMenu()
    {
        setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        setResizable(false);
        setTitle("Message Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
}
