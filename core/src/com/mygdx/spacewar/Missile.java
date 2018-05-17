/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

/**
 * Снаряд
 * @author Katie
 */
public class Missile {
    private int damage;                 /// Урон
    private float speed;                /// Скорость
    private Trajectory trajectory;      /// Траектория
    private ObjectSprite missileView;   /// Отображение
    
    /**
     * Конструктор снаряда
     * @param defaultDamage Урон снаряда
     * @param speed Скорость снаряда
     * @param curTrajectory Траектория снаряда
     * @param view Отображение снаряда
     */
    public Missile(int defaultDamage, float speed, Trajectory curTrajectory, ObjectSprite view){
        if(defaultDamage<0 || speed <=0.0 || curTrajectory==null ||  view== null)
            throw new Error("Can't create missile");
        this.damage = defaultDamage;
        this.speed = speed;
        this.trajectory = curTrajectory;
        this.missileView = view;
    }
    
    /**
     * Конструктор копирования 
     * @param other Другой снаряд
     * @param view Новый вид для этого снаряда
     */
    public Missile(Missile other, ObjectSprite view){
        if(other == null || view == null)
            throw new Error("Can't create copy of null missile");
        this.damage = other.getDamage();
        this.speed = other.getSpeed();
        this.trajectory = other.getTrajectory();
        this.missileView = view;
    }
    
    /**
     * Получить отображение снаряда
     * @return Отображение снаряда
     */
    public ObjectSprite getView(){
        return this.missileView;
    }
    
    /**
     * Получить отображение снаряда
     * @return Отображение снаряда
     */
    public void setView(ObjectSprite v){
        this.missileView = v;
    }
    
    /**
     * Получить информацию об уроне снаряда
     * @return Урон снаряда
     */
    public int getDamage(){
        return damage;
    }
    
    /**
     * Получить скорость снаряда
     * @return Скорость снаряда
     */
    public float getSpeed(){
        return this.speed;
    }
    
    /**
     * Получить траекторию снаряда
     * @return Траектория снаряда
     */
    public Trajectory getTrajectory(){
        return this.trajectory;
    }
}
