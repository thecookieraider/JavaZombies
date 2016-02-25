package zombies;
/**
 * Resource manager for audio files
 * 
 * @author Zachary Zoltek
 * @version 1.0
 * @since 1/29/2016
 */

import java.awt.event.ActionEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.FloatControl;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import javax.swing.Timer;

public class SoundHandler
{
  
   private HashMap<String,Clip> audioFiles = new HashMap();
   
   private HashMap<String,Long> pauseTimes = new HashMap();
  
   private AudioInputStream ais;
  
   public final static String[] SNDEXTS = {
                "wav",
		"aiff",
		"au",
		"snd",
		"aifc"
    };
   
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        
        if(i > 0 && i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        
        return ext;
    }
    
    public boolean isRunning(String name)
    {
        return audioFiles.get(name).isRunning();
    }
   
    public void removeAll()
    {
            audioFiles.clear();
            pauseTimes.clear();
    }
    
    public FloatControl getFloatControl(String clipName, FloatControl.Type type)
    {
        return (FloatControl)audioFiles.get(clipName).getControl(type);
    }
    
    public long getLength(String name)
    {
        return audioFiles.get(name).getMicrosecondLength();
    }
    
    public void pauseFor(int milliseconds, String name)
    {	
        pause(name);
		
        Timer t = new Timer(milliseconds, (ActionEvent e) -> {
           unpause(name); 
        });
        
        t.start();
    }
	
    public void stopFor(int milliseconds, String name)
    {
        stop(name);
        Timer t = new Timer(milliseconds, (ActionEvent e) -> {
               start(name);
        });
        
        t.start();
    }

    public void pauseAll()
    {
       Set<Map.Entry<String,Clip>> iterable = audioFiles.entrySet();
       
       Iterator<Map.Entry<String,Clip>> iterator = iterable.iterator();
       
       while(iterator.hasNext())
       {
           this.pause(iterator.next().getKey());
       }
    }
  
   public void stopAll()
   {
       Set<Map.Entry<String,Clip>> iterable = audioFiles.entrySet();
       
       Iterator<Map.Entry<String,Clip>> iterator = iterable.iterator();
       
       while(iterator.hasNext())
       {
           iterator.next().getValue().stop();
       }
   }
   
   public void loop(String name, boolean arg)
   {
       if(arg)
       {
           audioFiles.get(name).loop(Clip.LOOP_CONTINUOUSLY);
       }
       else if(!arg)
       {
           audioFiles.get(name).loop(0);
       }
   }
  
   public String[] getNames()
   {
       int counter = 0;
       
       String[] names = new String[audioFiles.size()];
       
       Set<Map.Entry<String,Clip>> iterable = audioFiles.entrySet();
       
       Iterator<Map.Entry<String,Clip>> iterator = iterable.iterator();
       
       while(iterator.hasNext())
       {
           names[counter] = iterator.next().getKey();
           counter++;
       }
       
       return names;
   }
  
   public void add(String fileName, String name)
   {
        if(audioFiles.containsKey(name))
        {
            if(isRunning(name))
            {
                stop(name);
            }
        }
	   
       try{	 
           ais = AudioSystem.getAudioInputStream(
           new File(fileName).getAbsoluteFile());
        }
        catch(UnsupportedAudioFileException | IOException er)
        {
        }
       
        try{
           Clip clip = AudioSystem.getClip();
           clip.open(ais);
           audioFiles.put(name, clip);
           pauseTimes.put(name, new Long(0));
        }
        catch(LineUnavailableException | IOException exc)
        {

        }
   }
  
   public void pause(String name)
   {
       if(audioFiles.containsKey(name) && pauseTimes.containsKey(name))
       {
            Clip songToPause = audioFiles.get(name);
            if(!songToPause.isRunning())
                return;
            else{
                pauseTimes.put(name, songToPause.getMicrosecondPosition());
                songToPause.stop();
            }
       }
       else
       {
           return;
       }
   }
  
   public void unpause(String name)
   {
        if(audioFiles.containsKey(name) && pauseTimes.containsKey(name))
        {
                Clip songToStart = audioFiles.get(name);
                if(!songToStart.isRunning())
                {
                    if(pauseTimes.get(name) < songToStart.getMicrosecondLength())
                    {
                        songToStart.setMicrosecondPosition(pauseTimes.get(name));
                    } else {
                        songToStart.setMicrosecondPosition(0);
                    }
                    
                    songToStart.start();
                }
                else{
                    return;
                }
                
        } else {
            
            return;
           
        }
    }  
	
    public void remove(String name)
    {
      if(audioFiles.containsKey(name) && pauseTimes.containsKey(name)) {
            if(isRunning(name))
                stop(name);
			
            audioFiles.remove(name);
            pauseTimes.remove(name);
        } else {
            return;
        }
    }
   
    public void restart(String name)
    {
        if(audioFiles.containsKey(name) && pauseTimes.containsKey(name)) {
            audioFiles.get(name).setMicrosecondPosition(0);
            audioFiles.get(name).start();
            pauseTimes.put(name, new Long(0));
        } else {
            return;
        }
    }
   
    public void start(String name)
    {
        if(audioFiles.containsKey(name) && pauseTimes.containsKey(name)) {
            if(audioFiles.get(name).isRunning()) {
                return;
            }
            
            audioFiles.get(name).setMicrosecondPosition(0);
            audioFiles.get(name).start();
            pauseTimes.put(name, new Long(0));
            
        } else {
            return;
        }
    }
    
    public void delayedStart(String name, int milliseconds)
    {
       if(audioFiles.containsKey(name) && pauseTimes.containsKey(name)) {
            if(audioFiles.get(name).isRunning()) {
                return;
            }
            
            Timer t = new Timer(milliseconds, (ActionEvent e) -> {
                audioFiles.get(name).setMicrosecondPosition(0);
                audioFiles.get(name).start();
                pauseTimes.put(name, new Long(0));
            });
            
            t.start();
            
        } else {
        }
    }
    
    public void stop(String name)
    {
        if(audioFiles.containsKey(name) && pauseTimes.containsKey(name)) {
            if(audioFiles.get(name).isRunning()) {
                audioFiles.get(name).stop();
                pauseTimes.put(name, new Long(0));
            } else {
            }
            
        } else {
        }
    }
}
