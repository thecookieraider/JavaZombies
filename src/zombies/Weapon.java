package zombies;

import java.awt.image.BufferedImage;


/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 14, 2016
 */

public abstract class Weapon {
    String name;
    boolean automatic;
    
    int reserveClips;
    int maxAmmoCount;
    int currAmmoCount;
    
    int damage;
    
    int reloadTime;
    
    boolean reloading;
    
    BufferedImage gunImg;
    BufferedImage bulletImage;
    
    String shootStance;
    
    public Weapon(String name, boolean automatic, int maxammo,
            int currAmmo, int damage, int reloadTime, int reserveClips)
    {
        this.name = name;
        this.automatic = automatic;
        this.maxAmmoCount = maxammo;
        this.currAmmoCount = currAmmo;
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.reserveClips = reserveClips;
    }
    
    public Weapon(String name, boolean automatic, int maxammo,
            int currAmmo, int damage)
    {
        this.name = name;
        this.automatic = automatic;
        this.maxAmmoCount = maxammo;
        this.currAmmoCount = currAmmo;
        this.damage = damage;
        this.reloadTime = 0;
        this.reserveClips = -1;
    }
    
    abstract public void fire(Board board);
    abstract public void reload(Board board);
    abstract public void setReloadTime(SoundHandler audio);
}
