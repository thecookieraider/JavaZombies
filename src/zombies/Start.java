package zombies;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 8, 2016
 */

public class Start {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            
            JFrame gameWindow = new JFrame();
            Board testBoard = new Board(gameWindow);
          
            gameWindow.add(testBoard);
            
            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setUndecorated(true);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameWindow.setVisible(true);
            
            testBoard.start();
            testBoard.startUpdate();
            
            gameWindow.setResizable(false);
        });
       
            
        
       
    }
}
