/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.utils.Array;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMMISSILE;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMMISSILEFAST;

/**
 * Строитель снарядов врагов
 * @author Katie
 */
public class EnemyMissileBuilder {
    private ObjectImage enemiesMissileSprite;  /// Спрайт снаряда врага
    
    public EnemyMissileBuilder(){     
        enemiesMissileSprite = new ObjectImage("fire/redpng.png", ENMMISSILE);
    }
    
    public Array<Missile> generateEnemyMissiles(ObjectImage.ObjectType missileType){
        if(missileType==ENMMISSILE)
            return geneateDefaultMissiles();
        if(missileType==ENMMISSILEFAST)
            return generateFastMissiles();
        return null;
    }
    
    private Array<Missile> geneateDefaultMissiles(){
        ObjectSprite enemiesMissileView = new ObjectSprite(enemiesMissileSprite, 22, 11, ++(GameSystem.curId));        
        // Снаряд легкого врага летит по прямой траектории
        StraightTrajectory enemiesTrajectory = new StraightTrajectory((float) 250.0, true);
        // Создаем вражеский снаряд
        Missile enemiesMissile = new Missile(1, (float) 250.0, enemiesTrajectory, enemiesMissileView);
        
        Array<Missile> ms = new Array();
        ms.add(enemiesMissile);
        return ms;        
    }
    
    private Array<Missile> generateFastMissiles(){
        ObjectSprite enemiesMissileView = new ObjectSprite(enemiesMissileSprite, 22, 11, ++(GameSystem.curId));
        Array<Missile> mssls = new Array<Missile>();
        // Снаряд данного врага стреляет по дуге, поэтому у него дуговая траектория
        ArcTrajectory enemiesTrajectory = new ArcTrajectory((float) 330.0, true, true);
        // Создаем снаряд врага
        Missile enemiesMissile = new Missile(2, (float) 330.0, enemiesTrajectory, enemiesMissileView); 
        mssls.add(enemiesMissile);
        
        enemiesMissileView = new ObjectSprite(enemiesMissileSprite, 22, 11, ++(GameSystem.curId));
        // Снаряд данного врага стреляет по дуге, поэтому у него дуговая траектория
        StraightTrajectory enemiesSTrajectory = new StraightTrajectory((float) 330.0, true);
        // Создаем снаряд врага
        enemiesMissile = new Missile(2, (float) 330.0, enemiesSTrajectory, enemiesMissileView); 
        mssls.add(enemiesMissile);
        
        enemiesMissileView = new ObjectSprite(enemiesMissileSprite, 22, 11, ++(GameSystem.curId));
        // Снаряд данного врага стреляет по дуге, поэтому у него дуговая траектория
        enemiesTrajectory = new ArcTrajectory((float) 330.0, true, false);
        // Создаем снаряд врага
        enemiesMissile = new Missile(2, (float) 330.0, enemiesTrajectory, enemiesMissileView); 
        mssls.add(enemiesMissile);
        return mssls;
    }
}
