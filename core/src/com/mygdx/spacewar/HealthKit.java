/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

/**
 * Бонус здоровья
 * @author Katie
 */
public class HealthKit extends Bonus{
    
    private final int healthBonus;  /// Количество здоровья, прибавляемое кораблю

    public HealthKit(BonusType type, ObjectSprite sprite, StraightTrajectory traj) {
        super(type, sprite, traj);
        this.healthBonus = 1;
    }

    @Override
    public void activate(Ship shipToTakeBonus) {
        shipToTakeBonus.takePill(healthBonus);
    }
    
}
