/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.utils.Array;

/**
 * Вражеский корабль
 * @author Katie
 */
public class EnemyShip extends Ship{
    
    private Trajectory trajectory;      /// Траектория
    
    /**
     * Конструктор вражеского корабля
     * @param health Здоровье корабля
     * @param speed Скорость корабля
     * @param view Отображение корабля
     * @param weapons Оружие корабля
     */
    public EnemyShip(int health, float speed, ObjectSprite view, Array<Weapon> weapons, Trajectory trajectory) {
        super(health, speed, view, weapons);
        if(trajectory == null){
            throw new Error("Can't create enemy without trajectory");
        }
        this.trajectory = trajectory;
    }
    
    
    public Trajectory getTrajectory(){
        return this.trajectory;
    }
    
}
