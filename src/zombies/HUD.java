package zombies;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 11, 2016
 */

public class HUD {
    
    private int yPos;
    
    Player player;
    
    int health;
    BufferedImage healthIcon;
    
    public HUD(int y, Player player, int startingHealth)
    {
        health = startingHealth;
        yPos = y;
        this.player = player;
    }
    
    public void setPos(int newPos)
    {
        yPos = newPos;
    }
    
    public void setHealthIcon(BufferedImage img)
    {    
            healthIcon = img;
    }

    protected void draw(Board board, Logic logic, Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setFont(new Font("Times New Roman", Font.BOLD, 19));
        
        if(health > 10) {
            g.drawImage(healthIcon, 10, yPos, board);
            g2.drawString(String.format("+%d", health-1), 40, 15);      
        } else {
            for(int i = 0; i < health; i++) {
                g.drawImage(healthIcon, i * 30 + 10, yPos, null);
            }
        }
       
        g2.drawString(String.format("Ammo: %d/%d", board.player.currWeapon.currAmmoCount,
                board.player.currWeapon.maxAmmoCount), 
                board.getWidth() / 5, 15); 
        
        g2.drawString(String.format("Clips Remaining: %d", board.player.currWeapon.reserveClips),
                board.getWidth() / 5 * 2, 15);
        
        g2.drawString(String.format("Score: %d", board.logic.points), 
                board.getWidth() / 5 * 3, 15);
        
        for(int i = 0; i < logic.currPowerups.size(); i++){
            g.drawImage(logic.currPowerups.get(i).image, ((board.getWidth() / 5) * 4)+(i*50), 10, board);
        }
        
    }
}
