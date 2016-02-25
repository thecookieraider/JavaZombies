package zombies;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 8, 2016
 */

public class Player {
    
    private ImageManager manBlue;
    private ImageManager manOld;
    private ImageManager manBrown;
    private ImageManager Soldier;
    private ImageManager Zombie;
    private ImageManager Survivor;
    private ImageManager Woman;
    private ImageManager Robot;
    private ImageManager Hitman;
    
    private final ArrayList<ImageManager> characters;
    
    Weapon currWeapon;
    
    ImageManager currCharacter;
    
    private int x;
    private int y;
    private int velx;
    private int vely;
    
    ImageObserver observer;
    Board board;
    Rectangle collisionBody;
    
    public Player(int x, int y, Board board,ImageObserver observer)
    {
        this.board = board;
        characters = new ArrayList<>();
        currWeapon = new Pistol(getMedia(null,"bullet.png"), "gun");
        
        this.x = x;
        this.y = y;
        
        this.observer = observer;
        
        setupBlue();
        characters.add(manBlue);
        setupWoman();
        characters.add(Woman);
        setupHitman();
        characters.add(Hitman);
        setupRobot();
        characters.add(Robot);
        setupZombie();
        characters.add(Zombie);
        setupSoldier();
        characters.add(Soldier);
        setupmanBrown();
        characters.add(manBrown);
        setupmanOld();
        characters.add(manOld);
        setupSurvivor();   
        characters.add(Survivor);
        
        Random rand = new Random();
        
        currCharacter = characters.get(rand.nextInt(characters.size()));
        
        collisionBody = new Rectangle(x,y,currCharacter.getCurrent(true).getWidth(),
        currCharacter.getCurrent(true).getHeight());
    }
    
    private void setupBlue()
    {
        manBlue = new ImageManager();
        
        manBlue.add(getMedia("Characters", "manBlue_stand.png"), "stand", 1);
        manBlue.add(getMedia("Characters", "manBlue_hold.png"), "hold", 1);
        manBlue.add(getMedia("Characters", "manBlue_reload.png"), "reload", 1);
        manBlue.add(getMedia("Characters", "manBlue_silencer.png"), "silencer", 1);
        manBlue.add(getMedia("Characters", "manBlue_gun.png"), "gun", 1);
        manBlue.add(getMedia("Characters", "manBlue_machine.png"), "machine", 1);
        
        manBlue.setCurrent("stand");
    }
    
    private void setupHitman()
    {
        Hitman = new ImageManager();
        
        Hitman.add(getMedia("Characters", "hitman1_stand.png"), "stand", 1);
        Hitman.add(getMedia("Characters", "hitman1_hold.png"), "hold", 1);
        Hitman.add(getMedia("Characters", "hitman1_reload.png"), "reload", 1);
        Hitman.add(getMedia("Characters", "hitman1_silencer.png"), "silencer", 1);
        Hitman.add(getMedia("Characters", "hitman1_gun.png"), "gun", 1);
        Hitman.add(getMedia("Characters", "hitman1_machine.png"), "machine", 1);
        
        Hitman.setCurrent("stand");
    }
    
    private void setupRobot()
    {
        Robot = new ImageManager();
        
        Robot.add(getMedia("Characters", "robot1_stand.png"), "stand", 1);
        Robot.add(getMedia("Characters", "robot1_hold.png"), "hold", 1);
        Robot.add(getMedia("Characters", "robot1_reload.png"), "reload", 1);
        Robot.add(getMedia("Characters", "robot1_silencer.png"), "silencer", 1);
        Robot.add(getMedia("Characters", "robot1_gun.png"), "gun", 1);
        Robot.add(getMedia("Characters", "robot1_machine.png"), "machine", 1);
        
        Robot.setCurrent("stand");
    }
    
    private void setupWoman()
    {
        Woman = new ImageManager();
        
        Woman.add(getMedia("Characters", "womanGreen_stand.png"), "stand", 1);
        Woman.add(getMedia("Characters", "womanGreen_hold.png"), "hold", 1);
        Woman.add(getMedia("Characters", "womanGreen_reload.png"), "reload", 1);
        Woman.add(getMedia("Characters", "womanGreen_silencer.png"), "silencer", 1);
        Woman.add(getMedia("Characters", "womanGreen_gun.png"), "gun", 1);
        Woman.add(getMedia("Characters", "womanGreen_machine.png"), "machine", 1);
        
        Woman.setCurrent("stand");
    }
    
    private void setupSurvivor()
    {
        Survivor = new ImageManager();
        
        Survivor.add(getMedia("Characters", "survivor1_stand.png"), "stand", 1);
        Survivor.add(getMedia("Characters", "survivor1_hold.png"), "hold", 1);
        Survivor.add(getMedia("Characters", "survivor1_reload.png"), "reload", 1);
        Survivor.add(getMedia("Characters", "survivor1_silencer.png"), "silencer", 1);
        Survivor.add(getMedia("Characters", "survivor1_gun.png"), "gun", 1);
        Survivor.add(getMedia("Characters", "survivor1_machine.png"), "machine", 1);
        
        Survivor.setCurrent("stand");
    }
    
    private void setupZombie()
    {
        Zombie = new ImageManager();
        
        Zombie.add(getMedia("Characters", "zoimbie1_stand.png"), "stand", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_hold.png"), "hold", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_reload.png"), "reload", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_silencer.png"), "silencer", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_gun.png"), "gun", 1);
        Zombie.add(getMedia("Characters", "zoimbie1_machine.png"), "machine", 1);
        
        Zombie.setCurrent("stand");
    }
    
    private void setupSoldier()
    {
        Soldier = new ImageManager();
        
        Soldier.add(getMedia("Characters", "soldier1_stand.png"), "stand", 1);
        Soldier.add(getMedia("Characters", "soldier1_hold.png"), "hold", 1);
        Soldier.add(getMedia("Characters", "soldier1_reload.png"), "reload", 1);
        Soldier.add(getMedia("Characters", "soldier1_silencer.png"), "silencer", 1);
        Soldier.add(getMedia("Characters", "soldier1_gun.png"), "gun", 1);
        Soldier.add(getMedia("Characters", "soldier1_machine.png"), "machine", 1);
        
        Soldier.setCurrent("stand");
    }
    
    private void setupmanOld()
    {
        manOld = new ImageManager();
        
        manOld.add(getMedia("Characters", "manOld_stand.png"), "stand", 1);
        manOld.add(getMedia("Characters", "manOld_hold.png"), "hold", 1);
        manOld.add(getMedia("Characters", "manOld_reload.png"), "reload", 1);
        manOld.add(getMedia("Characters", "manOld_silencer.png"), "silencer", 1);
        manOld.add(getMedia("Characters", "manOld_gun.png"), "gun", 1);
        manOld.add(getMedia("Characters", "manOld_machine.png"), "machine", 1);
        
        manOld.setCurrent("stand");
    }
    
    private void setupmanBrown()
    {
        manBrown = new ImageManager();
        
        manBrown.add(getMedia("Characters", "manBrown_stand.png"), "stand", 1);
        manBrown.add(getMedia("Characters", "manBrown_hold.png"), "hold", 1);
        manBrown.add(getMedia("Characters", "manBrown_reload.png"), "reload", 1);
        manBrown.add(getMedia("Characters", "manBrown_silencer.png"), "silencer", 1);
        manBrown.add(getMedia("Characters", "manBrown_gun.png"), "gun", 1);
        manBrown.add(getMedia("Characters", "manBrown_machine.png"), "machine", 1);
        
        manBrown.setCurrent("stand");
    }
    
    public void draw(Graphics g) {
        
        x += velx;
        y += vely;
        
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle extra = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();

        int toLessenH = scrn.height - extra.height;
        int toLessenW = scrn.width - extra.width;

        int realWidth = board.getWidth() - toLessenW;
        int realHeight = board.getHeight() - toLessenH;
        
        x = Board.clamp(x, 0, realWidth);
        y = Board.clamp(y, 0, realHeight);
        g.drawImage(currCharacter.getCurrent(true), x, y, observer);
        collisionBody.setLocation(x,y);
    }
    
    public void setCharacter(ImageManager character) {
        currCharacter = character;
        collisionBody = new Rectangle(x,y,currCharacter.getCurrent(true).getWidth(),
        currCharacter.getCurrent(true).getHeight());
    }
    
    public void setPosition(int x, int y, int xMax, int xMin, int yMax, int yMin)
    {
        if((x < yMin || x > xMax) || (y > yMax || y < yMin))
            return;
        
        this.x = x;
        this.y = y;
        collisionBody.setLocation(x,y);
    }
    
    public void setVelX(int x)
    {
        velx = x;
    }
    
    public void setVelY(int y)
    {
        vely = y;
    }
    
    public Point getPosition()
    {
        return new Point(x,y);
    }
 
    private BufferedImage getMedia(String subFolder, String nameWithExtension)
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
    
    private String getResourceDirectory()
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

