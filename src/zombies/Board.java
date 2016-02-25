package zombies;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 8, 2016
 */


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {
    
    
    final static int FPS_MAX = 60;

    public static int clamp(int val, int min, int max) {
        if(val < min)
            return min;
        else if(val > max)
            return max;
        else
            return val;
    }
    
    SoundHandler audio;
    
    private Timer updateTimer;
    JFrame window;
    
    HUD hud;
    
    Player player;
    
    boolean running;
    
    Logic logic;
    BufferedImage bg;
    
    int frames = 0;
    int updates = 0;
    float time = 0;
    
    public Board(JFrame window) {
        super();
        this.window = window;
    }
    
    public void start()
    {
        init();
    }

    private void init()
    {
        audio = new SoundHandler();
        audio.add(getResourceDirectory() + "\\" + "organ.wav", "bg");
        audio.add(getResourceDirectory() + "\\" + "zombiehit.wav", "zhit");
        audio.add(getResourceDirectory() + "\\" + "gunshot.wav", "gunshot");
        audio.add(getResourceDirectory() + "\\" + "grunt.wav", "grunt");
        audio.add(getResourceDirectory() + "\\" + "bullethit.wav", "bhit");
        audio.add(getResourceDirectory() + "\\" + "zombiedeath.wav", "zdeath");
        audio.add(getResourceDirectory() + "\\" + "emptygun.wav", "dryfire");
        audio.add(getResourceDirectory() + "\\" + "reload.wav", "reload");
        audio.add(getResourceDirectory() + "\\" + "coin.wav", "coin");
        audio.add(getResourceDirectory() + "\\" + "machinegun.wav", "mg");
        
        bg = getMedia(null, "land_dirt12.png");
    
        player = new Player(50, 50, this, this);
        player.currWeapon.setReloadTime(audio);
        
        hud = new HUD(0, player, 5);
        
        hud.setHealthIcon(getMedia(null, "heart.gif"));
        logic = new Logic(this,32_000,5_000,8,10,100);
        
        audio.getFloatControl("bg", FloatControl.Type.MASTER_GAIN).setValue(-17);
        
        audio.loop("bg", true);
        audio.start("bg");
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        logic.zombieManager.anotherOne();
        logic.checkCollisions();
        logic.newPowerUp();
        
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        
        readPowerUpQueue(g);    
        player.draw(g);
        logic.drawZombies(g);
        readBulletQueue(g); 
        hud.draw(this, logic, g);
        updates++;
    }       
    
    private void readPowerUpQueue(Graphics g)
    {
        if(logic.powerupQueue.isEmpty())
            return;
        
        for(int i = 0; i < logic.powerupQueue.size(); i++)
        {
            if(logic.powerupQueue.get(i).isDead)
            {
                logic.powerupQueue.remove(i);
                continue;
            }
            logic.powerupQueue.get(i).draw(g);
        }
    }   
    
    private void readBulletQueue(Graphics g)
    {
        if(logic.bullets.isEmpty() && player.currWeapon.currAmmoCount > 0
                && !player.currWeapon.reloading)
        {
            player.currCharacter.setCurrent("stand");
            return;
        } else if (player.currWeapon.currAmmoCount <= 0 || 
                player.currWeapon.reloading) {
            player.currCharacter.setCurrent("reload");
        }
        
        for(int i = 0; i < logic.bullets.size(); i++)
        {
            if(logic.bullets.get(i).isDone) {
                logic.bullets.remove(i);
                continue;
            }
            
            logic.bullets.get(i).draw(g);
        }
    }
    
    public void startUpdate() {
        updateTimer = new Timer(1000 / FPS_MAX, (ActionEvent e) -> {
            frames++;
            time += 1000 / FPS_MAX;
            
            if(time > 1000){
                System.out.println("FPS: "+frames+", UPS: "+updates + ", Zombies Spawned: "+logic.zombieManager.spawnedZombies);
                time = 0;
                frames = 0;
                updates = 0;
            }
            
            if(hud.health <= 0)
                System.exit(0);
            
            logic.readQueue();
            repaint();
        });
        
        updateTimer.setRepeats(true);
        updateTimer.start();
    }
    
    BufferedImage getMedia(String subFolder, String nameWithExtension)
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
    
   /* Class for drawing a background made up of "tiles". 
    Too taxing to draw every frame, so I had to opt out of using this
    and instead use one large static image for the background. 
    
    My idea was to have the terrain change depending on the gamestate,
    and having tiles would allow greater flexibility in realizing this 
    idea. Alas, it was too much and the game became far too laggy to be played
    
    This problem is mostly caused by the fact that the game runs in 
    fullscreen. This class may be able to be used in full effect with
    no downsides on a smaller resolution, but this is untested
    
    public class TiledBG {
       
        public void paint(Graphics g, int x, int y, 
                int width, int height, double scaling,
                BufferedImage tile, ImageObserver observer)
        {
            int currentX = 0;
            int currentY = 0;

            tile = scaledBuffered(tile, scaling);
            double screenHeight = width;

            double screenWidth = height;

            for(int i = 0; i < screenWidth; i++)
            {
                for(int j = 0; j < screenHeight; j++)
                {
                    g.drawImage(tile,currentX,currentY,observer);               
                    currentY = currentY+tile.getHeight();
                }
                currentX = currentX+tile.getWidth();
                currentY = 0;       
            }
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
    } */
    
    String getResourceDirectory()
    {
        URI uri = null;
        try {
            uri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
        
        return new File(uri).getParentFile().getParent() + "\\Resources";
    }
    
    public BufferedImage scaledBuffered(BufferedImage arg, double scale){
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
