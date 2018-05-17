/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import static com.mygdx.spacewar.Bonus.BonusType.HEALTHKIT;
import static com.mygdx.spacewar.Bonus.BonusType.WEAPONBOOST;
import static com.mygdx.spacewar.ObjectImage.ObjectType.GAMEBONUS;

/**
 *
 * @author Katie
 */
public class BonusBuilder {
    private ObjectImage kitImage;
    private ObjectImage wbImage;
    
    public BonusBuilder(){
        kitImage = new ObjectImage("firstaid_kit.png", GAMEBONUS); //new ObjectSprite(healthKitImage, 15, 15, ++(GameSystem.curId));
        wbImage =  new ObjectImage("super_missile.png", GAMEBONUS);// new ObjectSprite(weaponBoostImage, 15, 15, ++(GameSystem.curId));
    }
    
    public Bonus generateHealthKit(){
        // Создать бонус здоровья
        ObjectSprite kitSprite = new ObjectSprite(kitImage, 15, 15, ++(GameSystem.curId));
        StraightTrajectory traj = new StraightTrajectory((float) 300.0, true);
        HealthKit kit = new HealthKit(HEALTHKIT, kitSprite, traj);
        return ((Bonus)kit);
    }
    
    public Bonus generateWeaponBoost(){
        // Создать бонус оружия
        ObjectSprite wbSprite = new ObjectSprite(wbImage, 15, 15, ++(GameSystem.curId));
        StraightTrajectory traj = new StraightTrajectory((float) 300.0, true);
        WeaponBoost wb = new WeaponBoost(WEAPONBOOST, wbSprite, traj);
        return ((Bonus)wb);
    }
}
