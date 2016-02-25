package zombies;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 22, 2016
 */

public class SmartZombie extends Zombie {
    private final Player player;
    private float velx;
    private float vely;
    public SmartZombie(int x, int y, int speed, int hits, Player player) {
        super(x, y, speed, hits);
        this.player = player;
    }
    
    @Override
    protected void start()
    {
        timer = new Timer(1000 / Board.FPS_MAX, (ActionEvent e) -> {
            if(x <= 0)
            {
                isDone = true;
                timer.stop();
            }
            
            float diffX = x - (float)player.getPosition().getX() - 8;
            float diffY = y - (float)player.getPosition().getY() - 8;
            float distance = (float) Math.sqrt( Math.pow(x-player.getPosition().getX(), 2) + Math.pow(y-player.getPosition().getY(),2));
        
            velx = (float)((-1.0/distance) * diffX);
            vely = (float)((-1.0/distance) * diffY);
            
            x += velx * speed;
            y += vely * speed;
            collisionBody.setLocation(x,y);
            
        });
        
        timer.setRepeats(true);
        timer.start();
    }

}
