package zombies;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 11, 2016
 */

public class Event {
    
    private final String name;
    
    public Event(String name)
    {
        this.name = name;
    }
    
    public void run() 
    {
        
    }
    
    public String getName()
    {
        return name;
    }
}
