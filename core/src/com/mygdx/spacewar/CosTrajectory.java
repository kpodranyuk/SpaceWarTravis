/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Katie
 */
public class CosTrajectory extends Trajectory{

    private float startY;
    
    public CosTrajectory(float speed, boolean leftDirected) {
        super(speed, leftDirected);
    }

    @Override
    public Rectangle calculatePosition(Rectangle curPos, boolean toLeft, float deltaTime) {
        if(800 - curPos.x<0.01)
            startY = curPos.y;
        float deltaX = curPos.x;
        if(toLeft)
            curPos.x -= this.getSpeed() * deltaTime;
        else
            curPos.x += this.getSpeed() * deltaTime;
        deltaX=Math.abs(800-curPos.x);
        curPos.y = (float) Math.cos(Math.toRadians(deltaX))*75 + startY;//this.getSpeed() * deltaTime;
        if(curPos.y<0)
            curPos.y = 0;
        else if (curPos.y>380)
            curPos.y=380;
        return curPos;
    }
    
}
