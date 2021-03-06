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

public class SlowZ extends Powerup {
    Logic logic;
    Board board;
    
    public SlowZ(Board board, Logic logic)
    {
        this.board = board;
        this.logic = logic;
        
        name = "slowzombies";
        
        lifeTime = 15_000;
        
        image = board.scaledBuffered(board.getMedia(null, "slowz.png"), .3);
        
        collisionBody = new Rectangle(x,y,image.getWidth(),image.getHeight());  
        
        spawn();
    }
    
    @Override
    public void effect()
    {
       Random rand = new Random(System.nanoTime());
       logic.currPowerups.add(this);
       for(int i = 0; i < logic.zombieManager.zombies.size(); i++)
       {
                logic.zombieManager.zombies.get(i).speed = 1;
       }
       
       Timer timer = new Timer(5_000, (ActionEvent e) -> {
          logic.currPowerups.remove(this);
          for(int i = 0; i < logic.zombieManager.zombies.size(); i++)
           {
               logic.zombieManager.zombies.get(i).speed = 
                       rand.nextInt(logic.zombieManager.maxSpeed)+1;
           } 
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
        x = rand.nextInt(board.getWidth() - (board.getWidth() / 3));
        y = rand.nextInt(board.getHeight() - (board.getHeight() / 3)) + 100;
        collisionBody.setLocation(x,y);
        
        Timer timer = new Timer(lifeTime, (ActionEvent e) -> {           
           isDead = true;
        });
        
        timer.setRepeats(false);
        timer.start();
    }
}
