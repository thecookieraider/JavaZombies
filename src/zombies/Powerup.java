package zombies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 14, 2016
 */

public abstract class Powerup {
    String name;
    
    int x;
    int y;
    
    int lifeTime;
    boolean isDead;
    
    BufferedImage image;
    
    Rectangle collisionBody;
    
    public abstract void effect();
    public abstract void spawn();
    public abstract void draw(Graphics g);
}
