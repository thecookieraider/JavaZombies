    package zombies;


import java.util.ArrayList;
import java.util.Random;

/**
 * @author Zachary Zoltek
 * @version 1.0
 * @since Feb 8, 2016
 */

public class Zombies {
    
    ArrayList<Zombie> zombies;
    Random rand;
    
    int maxSpeed;
    int maxZombie;
    
    int secondsTillAdd;
    int currTime;
    
    int decrement;
    int hitPerZombie;
    int maxHits;
    
    boolean incHits;
    
    int wave;
    int spawnedZombies = 0;
    
    Board board;
    
    public Zombies(Board board, int maxSpeed,
            int max, int secondsTillAdd, int decrement, int startHealth,
            int maxHealth)
    {
        zombies = new ArrayList<>();
        rand = new Random();
        maxZombie = max;
        currTime = 0;
        this.maxSpeed = maxSpeed;
        this.secondsTillAdd = secondsTillAdd;
        this.board = board;
        this.decrement = decrement;
        
        hitPerZombie = startHealth;
        maxHits = maxHealth;
        incHits = false;
    }
    
    private void addZombie()
    {
        int yPos = rand.nextInt(board.getHeight() - 100);
        int xPos = board.getWidth();
        int speed = rand.nextInt(maxSpeed)+1;
        zombies.add(new SmartZombie(xPos,yPos,speed,rand.nextInt(hitPerZombie)+1,
        board.player));
    }
    
    public synchronized void anotherOne()
    {
        if(zombies.size() >= maxZombie)
        {}
        else if (currTime >= secondsTillAdd) {
            secondsTillAdd -= decrement;
            currTime = 0;
            
            if(maxHits > 0){
                if(hitPerZombie < maxHits){
                    hitPerZombie += (incHits) ? 1 : 0;
                }
            }

            incHits = !incHits;
            ++spawnedZombies;
            addZombie();
        } else {
            currTime += 1000 / Board.FPS_MAX;
        }
    }
}
