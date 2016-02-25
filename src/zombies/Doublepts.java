package zombies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 14, 2016
 */

public class Doublepts extends Powerup {
    Logic logic;
    Board board;
    
    public Doublepts(Board board, Logic logic)
    {
        this.board = board;
        this.logic = logic;
        
        name = "dblpts";
        
        lifeTime = 20_000;
        
        image = board.scaledBuffered(board.getMedia(null, "doublepoints.png"), .5);
        
        collisionBody = new Rectangle(x,y,image.getWidth(),image.getHeight());
        
        spawn();
    }
    
    @Override
    public void effect()
    {
       logic.pointsPerKill *= 2;
       logic.pointsPerHit *= 2;
       logic.currPowerups.add(this);
       Timer timer = new Timer(10_000, (ActionEvent e) ->
       {
          logic.currPowerups.remove(this);
          logic.pointsPerKill /= 2;
          logic.pointsPerHit /= 2;
       });
       
       timer.setRepeats(false);
       timer.start();
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.drawImage(image, x, y, null);
    }
    
    @Override
    public void spawn()
    {
        Random rand = new Random(System.nanoTime());
        x = rand.nextInt(board.window.getWidth() - (board.window.getWidth() / 3));
        y = rand.nextInt(board.window.getHeight() - (board.window.getHeight() / 3)) + 100;
        collisionBody.setLocation(x,y);
        
        Timer timer = new Timer(lifeTime, (ActionEvent e) -> {           
           isDead = true;
        });
        
        timer.setRepeats(false);
        timer.start();
    }
}
