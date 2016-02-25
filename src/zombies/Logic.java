package zombies;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 8, 2016
 */

public class Logic {  
    ArrayList<Bullet> bullets;
    ArrayList<Event> eventQueue;
    ArrayList<Powerup> powerupQueue;
    ArrayList<Powerup> currPowerups;
    
    private final Board board;
    
    int powerupSpawnTime;
    int playerSpeed;
    
    int points;
    
    int pointsPerHit;
    int pointsPerKill;
    
    Zombies zombieManager;
    
    boolean isShooting;
    
    int timeTillNextPowerup;
    int minPowerupSpawnTime;
    
    public Logic(Board board, int powerupSpawnTime, int minPowerupSpawnTime,
            int playerSpeed, int pointsPerHit, int pointsPerKill)
    {   
        bullets = new ArrayList<>();
        eventQueue = new ArrayList<>();
        powerupQueue = new ArrayList<>();
        currPowerups = new ArrayList<>();
        
        points = 0;
        
        this.powerupSpawnTime = powerupSpawnTime;
        this.minPowerupSpawnTime = minPowerupSpawnTime;
        this.playerSpeed = playerSpeed;
        this.pointsPerHit = pointsPerHit;
        this.pointsPerKill = pointsPerKill;
        this.board = board;
        
        timeTillNextPowerup = 0;
          
        logicInit();
    }
    
    synchronized void drawZombies(Graphics g)
    {
        for(int i = 0; i < zombieManager.zombies.size(); i++)
        {
            if(zombieManager.zombies.get(i).isDone)
            {
                decreaseHealth();
                zombieManager.zombies.remove(i);
                continue;
            }
            zombieManager.zombies.get(i).draw(g);
        }
    }
    
    void decreaseHealth()
    {
        eventQueue.add(new Event("dechealth") {
            @Override
            public void run()
            {
                board.audio.start("zhit");
                --board.hud.health;
            }
        });
    }
    
    synchronized void checkCollisions()
    {     
        bullets.stream().forEach((bullet) -> {
            
            for (int j = 0; j < zombieManager.zombies.size(); j++) {
                
                if (bullet.collisionBody.intersects(zombieManager.zombies.get(j).collisionBody)) {
                    
                    zombieManager.zombies.get(j).hits -=
                            board.player.currWeapon.damage;
                    
                    board.audio.restart("bhit");
                    bullet.isDone = true;
                    if(zombieManager.zombies.get(j).hits <= 0)
                    {
                        points += pointsPerKill;
                        board.audio.restart("zdeath");
                        zombieManager.zombies.remove(j);
                    }
                    else
                    {
                        points += pointsPerHit;       
                        if(zombieManager.zombies.get(j).speed > 1)
                        {
                            zombieManager.zombies.get(j).speed--;
                        }
                    }
                }
            }
        });
        
        for(int i = 0; i < zombieManager.zombies.size(); i++)
        {
            if(zombieManager.zombies.get(i).collisionBody.intersects(
            board.player.collisionBody))
            {
                        board.audio.restart("grunt");
                        --board.hud.health;
                        
                        zombieManager.zombies.remove(i);
            }
        }
        
        for(int i = 0; i < powerupQueue.size(); i++)
        {
            if(board.player.collisionBody.intersects
                    (powerupQueue.get(i).collisionBody))
            {
                board.audio.restart("coin");
                powerupQueue.get(i).effect();
                powerupQueue.remove(i);
            }
        }
    }
    void newPowerUp()
    {
        timeTillNextPowerup += 1000 / Board.FPS_MAX;
        
        if(timeTillNextPowerup >= powerupSpawnTime)
        {
            powerupSpawnTime -= (powerupSpawnTime <= minPowerupSpawnTime) ? 0 : 1000;
            
            timeTillNextPowerup = 0;
            
            Random rand = new Random(System.nanoTime());

            int powerup = rand.nextInt(6);           
            
            switch(powerup) {
                case 0:
                    powerupQueue.add(new MachineGun(board,
                    board.getMedia(null, "weapon_machine.png")));
                    break;
                case 1:
                    powerupQueue.add(new Instakill(board, this));
                    break;
                case 2:
                    powerupQueue.add(new SlowZ(board, this));
                    break;
                case 3:
                    powerupQueue.add(new Doublepts(board,this));
                    break;
                case 4:
                    powerupQueue.add(new Doublespd(board, this));
                    break;
                case 5:
                    powerupQueue.add(new Health(board, this));
                    break;
            }  
        }
       
    }
        
    private void logicInit()
    {
        board.window.addKeyListener(new KeyInput(board,this));
        zombieManager = new Zombies(board, 5,
        10,6000,100,10,-1);     
    }
    
    void readQueue()
    {
        eventQueue.stream().forEach((task) -> {
            task.run();
        });
        
        eventQueue.clear();
    }
}
