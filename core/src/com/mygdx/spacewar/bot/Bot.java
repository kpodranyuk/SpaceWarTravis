/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar.bot;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.spacewar.ObjectSprite;
import com.mygdx.spacewar.api.BotAPI;
import com.mygdx.spacewar.api.SpaceWarAPI;

/**
 *
 * @author Katie
 */
public class Bot implements BotAPI{
    
    private SpaceWarAPI api;

    @Override
    public void run(SpaceWarAPI api) {
        // Связываем бота и апи
        this.api = api;
        this.api.setBot(this);
    }

    @Override
    public void update(float delta, Array<ObjectSprite> objs) {

        Rectangle player = api.getPlayerRectangle();
        float speed = api.getPlayerSpeed();
        // получаем ближайший объект
        ObjectSprite object = null;
        if (objs.size > 0) {
            object = objs.get(0);
            for (ObjectSprite o : objs) {
                if (o.rect.x < object.rect.x) {
                    object = o;
                }
            }
            float deltaY = this.correlatePlayersPosition(player, object.rect, speed, delta);
            // доходим до уровня объекта
            player.y += deltaY;
            api.shoot();      
            
        }
    }
    
    public float correlatePlayersPosition(Rectangle player, Rectangle object, float speed, float dt){
        float playerCenter = player.y + player.height/2; 
        float objectCenter = object.y + object.height/2; 
        if (Math.abs(objectCenter - playerCenter) < 2)
            return 0;
        if (playerCenter > objectCenter) {
           return -1*speed * dt;
        }
        else if (playerCenter < objectCenter) {
            return speed * dt;
        }
        return 0;
    }
}
