/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.math.Rectangle;

/**
 * Траектория
 * @author Katie
 */
public abstract class Trajectory {
    private float speed;                /// Скорость
    private boolean hasLeftDirection;   /// Является ли левонаправленной
    
    /**
     * Конструктор траектории
     * @param speed Скорость 
     * @param leftDirected Является ли левонаправленной
     */
    public Trajectory(float speed, boolean leftDirected){
        if (speed<=0.0)
            throw new Error ("Speed can't be nagative");
        this.speed = speed;
        this.hasLeftDirection = leftDirected;
        
    }

    /**
     * Вычислить новую позицию
     * @param curPos Текущая позиция
     * @return Новая позиция
     */
    public abstract Rectangle calculatePosition(Rectangle curPos, boolean toLeft, float deltaTime);
    
    /**
     * Получить скорость
     * @return Скорость
     */
    public float getSpeed(){
        return this.speed;
    }
    
    /**
     * Узнать, является ли левонаправленной
     * @return true, если левонаправленная, иначе - false
     */
    public boolean isLeftDirected(){
        return this.hasLeftDirection;
    }
}
