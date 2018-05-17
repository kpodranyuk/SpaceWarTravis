/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar.api;

import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Katie
 */
public interface SpaceWarAPI {
    
    /**
     * Установить связь с ботом
     * @param bot Бот
     */
    public void setBot(BotAPI bot);
    
    /**
     * Совершить выстрел из пользовательского корабля
     */
    public void shoot();
    
    public Rectangle getPlayerRectangle();
    
    public float getPlayerSpeed();
    
    public void controlPlayerPosition();
    
}
