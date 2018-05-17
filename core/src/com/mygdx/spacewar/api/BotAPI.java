/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar.api;

import com.badlogic.gdx.utils.Array;
import com.mygdx.spacewar.ObjectSprite;

/**
 * Интерфейс бота
 * @author Katie
 */
public interface BotAPI {
    
    /**
     * Запустить бота
     * @param api апи, предоставляемое системой игры
     */
    public void run(SpaceWarAPI api);
    
    /**
     * Обновить поведение управлением бота
     * @param delta прошедшее время
     */
    public void update(float delta, Array<ObjectSprite> objs);
    
}
