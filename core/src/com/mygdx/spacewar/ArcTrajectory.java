/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.math.Rectangle;

/**
 * Дуговая траектория
 * @author Katie
 */
public class ArcTrajectory extends Trajectory {

    private boolean upDirected;
    /**
     * Конструктор дуговой траектории
     * @param speed Скорость движения
     * @param leftDirected Является ли траектория направленной влево
     */
    public ArcTrajectory(float speed, boolean leftDirected, boolean upDirected) {
        super(speed, leftDirected);
        this.upDirected = upDirected;
    }

    /**
     * Вычислить новую позицию согласно траектории
     * @param curPos Текущая позиция
     * @return Новая позиция
     */
    @Override
    public Rectangle calculatePosition(Rectangle curPos, boolean toLeft, float deltaTime) {
        float deltaX = curPos.x;
        if(toLeft)
            curPos.x -= this.getSpeed() * deltaTime;
        else
            curPos.x += this.getSpeed() * deltaTime;
        deltaX=Math.abs(800-curPos.x);
        if(this.upDirected)
            curPos.y += (float) (deltaX/6) * deltaTime;//this.getSpeed() * deltaTime;
        else
            curPos.y -= (float) (deltaX/6) * deltaTime;//this.getSpeed() * deltaTime;
        return curPos;
    }
    
}
