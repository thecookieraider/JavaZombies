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

public class Instakill extends Powerup 
{
    Board board;
    Logic logic;
    int prevDmg;
    
    public Instakill(Board board, Logic logic)
    {
        this.logic = logic;
        this.board = board;
        
        name = "instakill";
        
        lifeTime = 5_000;
        
        image = board.scaledBuffered(board.getMedia(null, "instakill.png"), .2);
        
        collisionBody = new Rectangle(x,y,image.getWidth(),image.getHeight());     
        
        spawn();
    }
    
    @Override
    public void effect()
    {
        for(int i = 0; i < logic.zombieManager.zombies.size(); i++){
            logic.zombieManager.zombies.remove(i);
        }
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
