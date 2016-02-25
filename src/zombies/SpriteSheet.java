package zombies;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 9, 2016
 */

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private int animateTime;
    private int currIndex;
    private int loopAmt;
    
    private final int totalFrames;
    private double scale;
    private String name;
    
    private boolean paused;
    
    private final BufferedImage[] sheet;
    
    public SpriteSheet(BufferedImage[] imgs, String name,
            int time)
    {
        sheet = imgs;
        animateTime = time;
        paused = false;
        totalFrames = sheet.length;
        this.name = name;
        currIndex = 0;
        
        loopAmt = 0;
    }
    
    public SpriteSheet(BufferedImage[] imgs, String name,
            int time, int scale)
    {
        sheet = imgs;
        animateTime = time;
        paused = false;
        totalFrames = sheet.length;
        this.name = name;
        currIndex = 0;
        this.scale = scale;
        
        loopAmt = 0;
    }
    
    public boolean isPaused()
    {
        return paused;
    }
    
    public void setTime(int milliseconds)
    {
        if(milliseconds <= 0) {
            System.err.print("Cannot have a animate time <= 0");
            throw new RuntimeException();
        }
        animateTime = milliseconds;
    }
    
    public BufferedImage nextFrame()
    {
        if(!paused)
        {
            currIndex++;
            
            if(currIndex > sheet.length - 1) {
                currIndex = 0;
                loopAmt++;
            }
            
            return sheet[currIndex];
            
        }
        
        return null;
            
    }
    
    public int getLoops()
    {
        return loopAmt;
    }
    
    public void resetLoops()
    {
        loopAmt = 0;
    }
    
    public void restart()
    {
        loopAmt = 0;
        currIndex = 0;
    }
    
    public void pause()
    {
        paused = true;
    }
    
    public void unpause()
    {
        paused = false;
    }
    
    public int getCurrFrame()
    {
        return currIndex;
    }
    
    public BufferedImage getCurrImg()
    {
        return sheet[currIndex];
    }
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getTotalFrames()
    {
        return totalFrames;
    }
    
    public int getAnimateTime()
    {
        return animateTime;
    }
    
    public BufferedImage getFrame(int frame)
    {
        return sheet[frame];
    }
    
    public void setScale(double scale)
    {
        this.scale = scale;
    }
    
    public double getScale()
    {
        return scale;
    }
}
