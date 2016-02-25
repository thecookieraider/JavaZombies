package zombies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.Timer;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 14, 2016
 */

public class MachineGun extends Powerup {
    
    Board board;
   
    public MachineGun(Board board, BufferedImage image)
    {
        this.board = board;
        this.image = image;
        
        name = "machinegun";
        
        lifeTime = 10_000;
        
        collisionBody = new Rectangle(x,y,image.getWidth(),image.getHeight());
        
        spawn();
    }
    
    @Override
    public void effect()
    {
        board.player.currWeapon.automatic = true;
        board.player.currWeapon.damage = board.logic.zombieManager.hitPerZombie / 2;
        board.player.currWeapon.maxAmmoCount = 30;
        board.player.currWeapon.currAmmoCount = board.player.currWeapon.maxAmmoCount;
        board.player.currWeapon.reserveClips = 3;
        board.player.currWeapon.shootStance = "machine";
        board.player.currWeapon.bulletImage = board.getMedia(null, "bulletred.png");
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.drawImage(image, x, y, board);
    }
    
    @Override
    public void spawn()
    {
        Random rand = new Random();
        x = rand.nextInt(board.getWidth() - (board.getWidth() / 3));
        y = rand.nextInt(board.getHeight() - (board.getHeight() / 3)) + 100;
        collisionBody.setLocation(x,y);
        
        Timer timer = new Timer(lifeTime, (ActionEvent e) -> {
           isDead = true; 
        });
        
        timer.setRepeats(false);
        timer.start();
    }
}
