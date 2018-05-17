/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.utils.Array;

/**
 * Пользовательский корабль
 * @author Katie
 */
public class PlayerShip extends Ship {

    private int points;
    /**
     * Конструктор пользовательского корабля
     * @param health Здоровье корабля
     * @param speed Скорость корабля
     * @param view Отображение корабля
     * @param weapon Оружие корабля
     */
    public PlayerShip(int health, float speed, ObjectSprite view, Array<Weapon> weapons) {
        super(health, speed, view, weapons);
        points = 0;
    }
    
    public int getPoints(){
        return this.points;
    }
    
    public void increasePoints(int earnedPoints) {
        this.points+=earnedPoints;
    }
}
