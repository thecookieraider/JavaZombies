package zombies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 10, 2016
 */

public class ImageManager {
    
    private int totalImages = 0;
    private double currImageScale;
    private BufferedImage currImage = null;
    private HashMap<String, BufferedImage> images;
    private HashMap<String, Double> scales;
    
    public ImageManager(BufferedImage image, String name, double scale)
    {
        if(scale <= 0)
            scale = 1;
        
        images = new HashMap<>();
        scales = new HashMap<>();
        images.put(name, image);
        scales.put(name, scale);
        
        currImage = image;
        currImageScale = scale;
        
        totalImages = images.size();    
    }
    
    public ImageManager()
    {
        images = new HashMap<>();
        scales = new HashMap<>();
    }
    
    public void add(BufferedImage image, String name, double scale)
    {
        if(scale <= 0)
            scale = 1;
        
        if(!images.containsKey(name))
        {
            images.put(name, image);
            scales.put(name, scale);
            totalImages = images.size();   
        }
    }
    
    public void remove(String name)
    {
        images.remove(name);
        scales.remove(name);
       
        totalImages = images.size();   
    }
    
    public BufferedImage getScaled(String name, boolean scaled)
    {
        if(scaled)
            return scaledBuffered(images.get(name), scales.get(name));
        else
            return images.get(name);
    }
    
    public void setScale(String name, double newScale)
    {
        scales.put(name, newScale);
    }
    
    public void setCurrent(String name)
    {
        currImage = images.get(name);
        currImageScale = scales.get(name);
    }
    
    public BufferedImage getCurrent(boolean scaled)
    {
        if(scaled) {
            return scaledBuffered(currImage, currImageScale);
        }
        
        return currImage;
    }
    
    public int getTotalImages()
    {
        return totalImages;
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
