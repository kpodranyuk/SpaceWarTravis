/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.com.mygdx.spacewar.bot;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.spacewar.bot.Bot;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Katie
 */
public class BotTest {
    
    private final float playerSpeed = (float) 250.0;
    private final float deltaTime = (float) 0.001;
    
    private final int PLAYERWIDTH = 34;
    private final int PLAYERHEIGHT = 37;
    
    private final int ENEMYWIDTH = 29;
    private final int ENEMYHEIGHT = 38;
    
    public BotTest() {
    }

    @Test
    public void testCorrelatePlayersPositionOneLevelWithObject () {
        System.out.println("correlatePlayersPosition player and object on one level");
        Rectangle player = new Rectangle(10, 200, PLAYERWIDTH, PLAYERHEIGHT);
        Rectangle enemy = new Rectangle(750, 200, ENEMYWIDTH, ENEMYHEIGHT);
        
        Bot bot = new Bot();
        float expResult = 0;
        float result = bot.correlatePlayersPosition(player, enemy, playerSpeed, deltaTime);
        assertEquals(expResult, result, 0);
    }
    
    @Test
    public void testCorrelatePlayersPositionPlayerAboveObject () {
        System.out.println("correlatePlayersPosition player is above object");
        Rectangle player = new Rectangle(10, 220, PLAYERWIDTH, PLAYERHEIGHT);
        Rectangle enemy = new Rectangle(750, 200, ENEMYWIDTH, ENEMYHEIGHT);
        
        Bot bot = new Bot();
        float expResult = (float) -0.25;
        float result = bot.correlatePlayersPosition(player, enemy, playerSpeed, deltaTime);
        assertEquals(expResult, result, 0.001);
    }
    
    @Test
    public void testCorrelatePlayersPositionPlayerUnderObject () {
        System.out.println("correlatePlayersPosition player is under object");
        Rectangle player = new Rectangle(10, 200, PLAYERWIDTH, PLAYERHEIGHT);
        Rectangle enemy = new Rectangle(750, 220, ENEMYWIDTH, ENEMYHEIGHT);
        
        Bot bot = new Bot();
        float expResult = (float) 0.25;
        float result = bot.correlatePlayersPosition(player, enemy, playerSpeed, deltaTime);
        assertEquals(expResult, result, 0.001);
    }
}
