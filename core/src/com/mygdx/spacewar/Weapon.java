/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

/**
 * Оружие
 * @author Katie
 */
public class Weapon {
    private Missile missile;    /// Снаряд, которым стреляет оружие
    
    /**
     * Конструктор оружия
     * @param missile Снаряд
     */
    public Weapon(Missile missile){
        this.missile = missile;
    }
    
    /**
     * Получить снаряд оружия
     * @return Снаряд
     */
    public Missile getMissile(){
        return missile;
    }
}
