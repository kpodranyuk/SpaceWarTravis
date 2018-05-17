/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.utils.Array;

/**
 * Корабль
 * @author Katie
 */
public abstract class Ship {
    protected final int maxHealth;      /// Максимальное здоровье корабля
    protected int currentHealth;        /// Здоровье корабля
    private float speed;                /// Скорость корабля
    private ObjectSprite view;          /// Отображение корабля
    private Array<Weapon> weapons;              /// Оружие корабля
    private int weaponsCount;           /// Количество активных оружий у корабля текущее
    private final int defaultWeaponsCount;    /// Количество активных оружий у корабля по умолчанию
    
    /**
     * Конструктор корабля
     * @param health Здоровье корабля
     * @param speed Скорость корабля
     * @param view Отображение корабля
     * @param weapon Оружие корабля
     */
    public Ship(int health, float speed, ObjectSprite view, Array<Weapon> weapons){
        if(health<=0 || speed <=0.0 || view == null || weapons == null)
            throw new Error("Can't create ship");
        this.currentHealth = health;
        this.maxHealth = health;
        this.speed = speed;
        this.view = view;
        this.weapons = weapons;
        this.weaponsCount = 1;
        this.defaultWeaponsCount = 1;
    }
    
    /**
     * Получить отображение корабля
     * @return Отображение корабля
     */
    public ObjectSprite getView(){
        // Заглушка
        return this.view;
    }
    
    /**
     * Получить снаряд корабля
     * @return Снаряд корабля
     */
    public Missile getMissile(int weaponIndex){
        return this.weapons.get(weaponIndex).getMissile();
    }
    
    /**
     * Получить здоровье корабля
     * @return Здоровье корабля
     */
    public int getCurrentHealth(){
        return this.currentHealth;
    }
    
    /**
     * Получить размер максимально возможного здоровья у корабля
     * @return
     */
    public int getMaxHealth(){
        return this.maxHealth;
    }
    
    /**
     * Получить скорость корабля
     * @return Скорость корабля
     */
    public float getSpeed(){
        return this.speed;
    }
    
    /**
     * Получить повреждения
     * @param damage урон, нанесенный при повреждении
     */
    public void takeDamage(int damage){
        if (damage<0)
            throw new Error("Ship can't take negative damage");
        this.currentHealth-=damage;
    }
    
    /**
     * Получить здоровье в виде таблетки
     * @param healthCount Количество здоровья, на которое увеличится текущее
     */
    public void takePill(int healthCount) {
        if (healthCount<0)
            throw new Error("Ship can't take negative health");
        this.currentHealth+=healthCount;
    }
    
    public int getActiveWeaponsCount(){
        return this.weapons.size;
    }
    
    /**
     * Установить количество активных орудий корабля
     * @param weaponsCount Количество активных орудий корабля для установки
     */
    public void addActiveWeapon(Weapon wp){
        if (weaponsCount<0)
            throw new Error("Ship can't take negative weapons сount");
        this.weapons.add(wp);
        this.weaponsCount++;
    }
    
    public void deleteLastWeapons(int count){
        while(count>0){
            this.weapons.removeIndex(this.weapons.size - 1);
            count--;
        }
    }
    
    public int getDefaultWeaponsCount(){
        return this.defaultWeaponsCount;
    }
    
    /**
     * Совершить выстрел
     * @return Снаряд, которым выстрелили
     */
    public Array<Missile> shoot(){
        Array<Missile> ms = new Array<Missile>();
        for(Weapon wp: this.weapons){
            ms.add(wp.getMissile());
        }
        return ms;
    }
}
