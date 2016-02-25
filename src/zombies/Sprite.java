package zombies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.Timer;
/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 9, 2016
 */

public class Sprite {
    
    private HashMap<String,SpriteSheet> spriteSheets;
    private SpriteSheet currSheet = null;
    private BufferedImage currImg = null;
    private Timer animationTimer = null;
    
    public Sprite(SpriteSheet[] sheets)
    {
        spriteSheets = new HashMap<>();
        
        for(SpriteSheet sheet : sheets)
        {
            spriteSheets.put(sheet.getName(), sheet);
        }
        
        currSheet = sheets[0];
        currImg = sheets[0].getCurrImg();   
    }
    
    public Sprite(SpriteSheet sheet, int scale)
    {
        spriteSheets = new HashMap<>();
        
        spriteSheets.put(sheet.getName(), sheet).setScale(scale);
        
        currSheet = sheet;
        currImg = sheet.getCurrImg();
    }
    
    public Sprite(SpriteSheet sheet)
    {
        spriteSheets = new HashMap<>();
        
        spriteSheets.put(sheet.getName(), sheet);
        
        currSheet = sheet;
        currImg = sheet.getCurrImg();
    }
    
    public void nextFrame()
    {
        if(currSheet == null || currSheet.isPaused())
            return;
        
        currImg = currSheet.nextFrame();
        double scale = currSheet.getScale();
        
        if(scale > 0)
        {
            BufferedImage toReturn = scaledBuffered(currImg, scale);
            
            currImg = toReturn;
        }
    }
    
    public void play(int repetitionAmt)
    {
        
        animationTimer = new Timer(currSheet.getAnimateTime()
                / currSheet.getTotalFrames(), (ActionEvent e) -> {
                    
                    if(repetitionAmt > 0)
                    {
                        if(currSheet.getLoops() == repetitionAmt)
                        {
                            nextFrame();
                            animationTimer.stop();
                        }
                    }
                    
                    nextFrame();
        });
        
        animationTimer.setCoalesce(true);
        
        if(repetitionAmt <= 0)
            animationTimer.setRepeats(true);
 
        animationTimer.start();
    }
    
    public void pause()
    {
        currSheet.pause();
        animationTimer.stop();
    }
    
    public void pauseFor(int milliseconds, int repeatAmt) throws InterruptedException
    {
        currSheet.pause();
        new Thread(() -> {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            unpause(repeatAmt);
        }).start();
    }
    
    public void delayedStart(int milliseconds, int repeatAmt)
    {
        new Thread(() -> {
            try {
                Thread.sleep(milliseconds);
                play(repeatAmt);
            } catch (InterruptedException e) {
             Thread.currentThread().interrupt();}
        }).start();
    }
    
    public void unpause(int repeatAmt)
    {
        currSheet.unpause();
        play(repeatAmt);
    }
    
    public void setCurrent(String sheetName)
    {
        if(animationTimer != null)
            if(animationTimer.isRunning())
                animationTimer.stop();
        
        if(!spriteSheets.containsKey(sheetName))
            return;
        
        currSheet = spriteSheets.get(sheetName);
        currImg = spriteSheets.get(sheetName).getCurrImg();
    }
    
    public BufferedImage getCurrImg(boolean scaled)
    {
        double scale = currSheet.getScale();
        
        if(scaled && scale > 0)
        {
            if((currSheet.getCurrImg().getWidth() * scale == currImg.getWidth())
                    && currSheet.getCurrImg().getHeight() * scale == currImg.getHeight())
            {
                return currImg;
            }
            
            Image toConvert = currImg.getScaledInstance(
                    (int)(currImg.getWidth() * scale), 
                    (int)(currImg.getHeight() * scale), Image.SCALE_DEFAULT);
            
            BufferedImage toReturn = new BufferedImage(toConvert.getWidth(null),
            toConvert.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            
            Graphics g = toReturn.getGraphics();
            g.drawImage(toConvert, 0, 0, null);
            g.dispose();
            
            return toReturn;
        }
        
        return currImg;
    }
    
    public SpriteSheet getCurrSheet()
    {
        return currSheet;
    }
    
    public void setScale(int scale)
    {
        currSheet.setScale(scale);
    }
    
    public void addSheet(SpriteSheet sheet)
    {
        spriteSheets.put(sheet.getName(), sheet);
    }
    
    public void removeSheet(String name)
    {
        if(currSheet == spriteSheets.get(name))
            pause();
        
        spriteSheets.remove(name);
    }
    
    public BufferedImage getFrame(int frame)
    {
        return currSheet.getFrame(frame);
    }
    
    public BufferedImage getFrame(String name, int frame)
    {
        return spriteSheets.get(name).getFrame(frame);
    }
    
    public SpriteSheet getSheet(String name)
    {
        return spriteSheets.get(name);
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
