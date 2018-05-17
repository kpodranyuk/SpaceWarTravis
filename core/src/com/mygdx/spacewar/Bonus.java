/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import static com.mygdx.spacewar.Bonus.BonusType.HEALTHKIT;
import static com.mygdx.spacewar.Bonus.BonusType.WEAPONBOOST;

/**
 * Класс бонуса игры
 * @author Katie
 */
public abstract class Bonus {
    /*
    * Поддерживаемые типы бонусов: аптечка, ускорение оружия
    */
    public enum BonusType{ HEALTHKIT, WEAPONBOOST}
    private BonusType type;                         /// Тип бонуса
    private ObjectSprite sprite;                    /// Спрайт объекта
    private StraightTrajectory traj;                /// Траектория объекта

    /**
     * Конструктор
     * @param type Тип бонуса
     * @param sprite Отображение бонуса
     */
    public Bonus(BonusType type, ObjectSprite sprite, StraightTrajectory traj){
        if(type == null || traj == null || sprite == null || type == HEALTHKIT || type == WEAPONBOOST ) {
            this.type = type;
            this.sprite = sprite;
            this.traj = traj;
        }
        else
            throw new Error ("Can't create bonus");
    }

    /**
     * Получить тип бонуса
     * @return Тип бонуса
     */
    public BonusType getType(){
        return this.type;
    }
    
    /**
     * Получить отображение бонуса
     * @return Отображение бонуса
     */
    public ObjectSprite getView(){
        return this.sprite;
    }
    
    public StraightTrajectory getTrajectory() {
        return this.traj;
    }
    
    /**
     * Активироваться
     * @param shipToTakeBonus Корабль, к которому применяется бонус
     */
    public abstract void activate(Ship shipToTakeBonus);
}
