package zombies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 12, 2016
 */

public class Bullet extends JComponent {
    Rectangle collisionBody;
    private BufferedImage img;
    private Timer timer;
    static int bulletSpeed = 10;
    int currX;
    int currY;
    int max;
    boolean isDone;
    
    public Bullet(int x, int y, int max, BufferedImage img)
    {
        isDone = false;
        currX = x;
        currY = y;
        this.max = max;
        this.img = scaledBuffered(img, .7);
        collisionBody = new Rectangle(x, y, img.getWidth(), img.getHeight());
        start();
    }
    
    private void start()
    {
        timer = new Timer(1000 / Board.FPS_MAX, (ActionEvent e) -> {
            if(currX >= max) {
                isDone = true;
                img = null;
                timer.stop();
            }
            
            
            currX += bulletSpeed;
            collisionBody.setLocation(currX, currY);
        });
        
        timer.setRepeats(true);
        timer.start();
        
    }
    
    public void draw(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, currX, currY, this);
    }
    
    private BufferedImage scaledBuffered(BufferedImage arg, double scale)
    {
        Image toConvert = arg.getScaledInstance(
                    (int)(arg.getWidth() * scale), 
                    (int)(arg.getHeight() * scale), Image.SCALE_DEFAULT);
            
        BufferedImage toReturn = new BufferedImage(toConvert.getWidth(null),
        toConvert.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics g = toReturn.getGraphics();
        g.drawImage(toConvert, 0, 0, null);
        g.dispose();

        return toReturn;
    }
    
}
