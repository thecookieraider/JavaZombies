package zombies;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 14, 2016
 */

public class Pistol extends Weapon {
    

    int currTime;
    
    public Pistol(BufferedImage bulletImage, String shootStance)
    {
        super("pistol",false,15,15,2);
        
        this.bulletImage = bulletImage;
        this.shootStance = shootStance;
    }
    
    @Override
    public void fire(Board board)
    {
        if(currAmmoCount > 0)
        { 
            board.logic.bullets.add(new Bullet((int)board.player.getPosition().getX() + 40,
                    (int)board.player.getPosition().getY() + 20,
                    board.window.getWidth(), bulletImage));

            board.player.currCharacter.setCurrent(shootStance);
            

            --currAmmoCount;       
        }
    }
    
    @Override
    public void reload(Board board)
    {   
        reloading = true;
        
        Timer reloadTimer = new Timer(reloadTime, (ActionEvent e) -> {
           reserveClips -= (reserveClips >= 0) ? 1 : 0;
           currAmmoCount = maxAmmoCount; 
           reloading = false;
        });
        
        reloadTimer.setRepeats(false);
        reloadTimer.start();
    }
    
    @Override
    public void setReloadTime(SoundHandler audio)
    {
        reloadTime = (int)(audio.getLength("reload") * .001);
    }

}
