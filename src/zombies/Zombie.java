package zombies;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 8, 2016
 */

public class Zombie {
    private ImageManager Zombie;
    
    ImageManager currCharacter;
    
    protected int x;
    protected int y;
    int hits;
    int speed;
    
    protected Timer timer;
    ImageObserver observer;
    
    Rectangle collisionBody;
    
    boolean isDone;
    
    public Zombie(int x, int y, int speed, int hits)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.hits = hits;
        
        setupZombie();
        currCharacter = Zombie;
        
        collisionBody = new Rectangle(x,y,currCharacter.getCurrent(true).getWidth(),
        currCharacter.getCurrent(true).getHeight());
        
        isDone = false;
        
        start();
    }
    private void setupZombie()
    {
        Zombie = new ImageManager();
        
        Zombie.add(getMedia("Characters", "zoimbie1_standleft.png"), "stand", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_hold.png"), "hold", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_reload.png"), "reload", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_silencer.png"), "silencer", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_gun.png"), "gun", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_machine.png"), "machine", 1);
        
        Zombie.setCurrent("stand");
    }
    
    public void draw(Graphics g) {
        g.drawImage(currCharacter.getCurrent(true), x, y, observer);      
    }
    
    public void setCharacter(ImageManager character) {
        currCharacter = character;
    }
    
    public void move(int dx, int dy)
    {
        x += dx;
        y += dy;
    }
    
    public void move(double dx, double dy)
    {
        x += dx;
        y += dy;
    }
   
    public void setPosition(int x, int y, int xMax, int xMin, int yMax, int yMin)
    {
        if((x < yMin || x > xMax) || (y > yMax || y < yMin))
            return;
        
        this.x = x;
        this.y = y;
    }
    
    protected void start()
    {
        timer = new Timer(1000 / Board.FPS_MAX, (ActionEvent e) -> {
            if(x <= 0)
            {
                isDone = true;
                timer.stop();
            }
            x -= speed;
            collisionBody.setLocation(x,y);
            
        });
        
        timer.setRepeats(true);
        timer.start();
    }
    
    public Point getPosition()
    {
        return new Point(x,y);
    }
 
    protected BufferedImage getMedia(String subFolder, String nameWithExtension)
    {
        try {
            if(subFolder != null)
                return(ImageIO.read(new File(getResourceDirectory()
                        + "\\" + subFolder + "\\" + nameWithExtension)));
            else
                return(ImageIO.read(new File(getResourceDirectory()
                        + "\\" + nameWithExtension)));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected String getResourceDirectory()
    {
        URI uri = null;
        try {
            uri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
        
        return new File(uri).getParentFile().getParent() + "\\Resources";
    }
}
