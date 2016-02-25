package zombies;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 21, 2016
 */

public class KeyInput extends KeyAdapter {
    private final Board board;
    private final Logic logic;
    private final boolean[] pressed = new boolean[4];
    private boolean spacePressed = false;
    
    public KeyInput(Board board, Logic logic){
        this.board = board;
        this.logic = logic;
    }
    @Override
    public void keyPressed(KeyEvent e)
    {

       if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
           System.exit(0);
       }

       if(e.getKeyCode() ==  KeyEvent.VK_SPACE)
       {
           spacePressed = true;
           if(board.player.currWeapon.automatic)
           {
               if(board.player.currWeapon.currAmmoCount > 0 
                       && !board.player.currWeapon.reloading)
               {
                    board.player.currWeapon.fire(board);
                    board.audio.start("mg");
               } else if(board.player.currWeapon.currAmmoCount <= 0) {
                   board.audio.restart("dryfire");
               }
           } else {
               if(!logic.isShooting && board.player.currWeapon.currAmmoCount > 0
                       && !board.player.currWeapon.reloading)
               {
                    logic.isShooting = true;
                    board.audio.restart("gunshot");
                    board.player.currWeapon.fire(board);
               } else if (board.player.currWeapon.currAmmoCount <= 0) {
                    board.audio.restart("dryfire");
               }
           }

       } else if(e.getKeyCode() == KeyEvent.VK_R)    
       {
           if(!board.player.currWeapon.reloading)
           {
                if(board.player.currWeapon.reserveClips > 0)
                {
                     board.audio.restart("reload");
                     board.player.currWeapon.reload(board);
                } else if(board.player.currWeapon.reserveClips == 0 
                        && board.player.currWeapon.shootStance.equals("gun")) {
                    board.audio.restart("dryfire");
                } else if(board.player.currWeapon.reserveClips < 0) {
                    board.audio.restart("reload");
                    board.player.currWeapon.reload(board);
                } else if(board.player.currWeapon.reserveClips == 0
                        && !board.player
                                .currWeapon
                                .shootStance
                                .equals("gun"))
                {
                    board.player.currWeapon.automatic = false;
                    board.player.currWeapon.bulletImage = board.getMedia(null,"bullet.png");
                    board.player.currWeapon.shootStance = "gun";
                    board.player.currWeapon.currAmmoCount = 15;
                    board.player.currWeapon.maxAmmoCount = 15;
                    board.player.currWeapon.reserveClips = -1;
                }
           }             
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                pressed[0] = true;
                board.player.setVelY(-logic.playerSpeed);                       
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                pressed[1] = true;
                board.player.setVelX(logic.playerSpeed);                        
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                pressed[2] = true;
                board.player.setVelY(logic.playerSpeed);                      
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                pressed[3] = true;
                board.player.setVelX(-logic.playerSpeed);                        
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode()) {
               case KeyEvent.VK_W:
               case KeyEvent.VK_UP:
                   pressed[0] = false;                          
                   break;
               case KeyEvent.VK_D:
               case KeyEvent.VK_RIGHT:
                   pressed[1] = false;                         
                   break;
               case KeyEvent.VK_S:
               case KeyEvent.VK_DOWN:
                   pressed[2] = false;                           
                   break;
               case KeyEvent.VK_A:
               case KeyEvent.VK_LEFT:
                   pressed[3] = false;                          
                   break;
               case KeyEvent.VK_SPACE:
                   spacePressed = false;  
                   break;
               default:
                   break;
        }
        if(!pressed[0] && !pressed[2]) board.player.setVelY(0);
        if(!pressed[1] && !pressed[3]) board.player.setVelX(0);
        
        if(!spacePressed){
            board.audio.pause("mg");
            logic.isShooting = false;
        }
    }
}

