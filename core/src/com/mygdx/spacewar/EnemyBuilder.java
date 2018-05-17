/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.spacewar.ObjectImage.ObjectType;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMMISSILE;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMMISSILEFAST;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMSHIP;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMSHIPFAST;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMSHIPHEALTHY;

/**
 * Строитель врагов
 * @author Katie
 */
public class EnemyBuilder {
    private Array<ObjectImage> enemiesSprites;          /// Спрайты врагов
    private EnemyMissileBuilder missileBuilder;         /// Строитель снарядов
    private int shipsFromWall;                          /// Количество выпущенных после стены смерти кораблей
    
    public EnemyBuilder (){
        missileBuilder = new EnemyMissileBuilder();
        enemiesSprites = new Array();
        shipsFromWall = -1;
        
        ObjectImage enemiesImg = null;        
        enemiesImg = new ObjectImage("enemies/enemy1.png", ENMSHIP);
        enemiesSprites.add(enemiesImg);
        enemiesImg = new ObjectImage("enemies/enemy2.png", ENMSHIPHEALTHY);
        enemiesSprites.add(enemiesImg);
        enemiesImg = new ObjectImage("enemies/enemy3.png", ENMSHIPFAST);
        enemiesSprites.add(enemiesImg);
    }
    
    /**
     *
     * @param gameLevel
     * @return
     */
    public Array<EnemyShip> generateEnemies(int gameLevel){
        // Определяем тип создаваемого игрока, основываясь на сложности игры
        int enemyIndex = MathUtils.random(1, gameLevel);  
        if(gameLevel==4&&shipsFromWall!=-1){
            shipsFromWall++;
        }
        // Если первый тип, то создаем легкого врага
        if(enemyIndex==1){
            return generateEasyEnemies(1);
        }
        // Иначе если второй тип, то создаем врага с большим хп
        if(enemyIndex==2){
            return generateHealthyEnemies(1, gameLevel);//generateHealthyEnemy();
        }
        // Иначе если третий тип, то создаем врага, стреляющего по дуге
        if(enemyIndex==3){
            return generateFastEnemies(1);//generateFastEnemy();
        }            
        if(enemyIndex==4){
            if(shipsFromWall==-1 || shipsFromWall>10){
                shipsFromWall = 0;
                return generateWallOfDeath();
            }
            else {
                return generateHealthyEnemies(1, gameLevel);
            }
        }
        return null;
    }
    
    /**
     * Создать легких врагов
     * @param count количество врагов для генерации
     * @return Враги
     */
    private Array<EnemyShip> generateEasyEnemies(int count){
        if (count<=0)
            return null;
        Array<EnemyShip> enms = new Array();
        while(enms.size<count){
            // Создаем отображение врага
            ObjectSprite enemiesView = null;
            // Получаем изображение врага
            ObjectImage enemiesImg = getImageOfEnemyObject(ENMSHIP);        
            // Создаем спрайт врага
            enemiesView = new ObjectSprite(enemiesImg, 29, 38, ++(GameSystem.curId));

            // Повторяем то же самое с снарядом врага
            Array<Missile> enemyMissiles = missileBuilder.generateEnemyMissiles(ENMMISSILE);
            // Создаем вражеский корабль
            Array<Weapon> wps = new Array<Weapon>();
            for (Missile m: enemyMissiles){
                wps.add(new Weapon(m));
            }       
            StraightTrajectory enemiesTrajectory = new StraightTrajectory((float) 200.0, true);
            EnemyShip enemy = new EnemyShip(1, (float) 200.0, enemiesView, wps, enemiesTrajectory);
            enms.add(enemy);
        }
        // Возвращаем созданных врагов
        return enms;
    }
    
    /**
     * Создать врагов с большим здоровьем
     * @param count количество врагов для генерации
     * @return Враги
     */
    private Array<EnemyShip> generateHealthyEnemies(int count, int gameLevel){
        if (count<=0)
            return null;
        Array<EnemyShip> enms = new Array();
        while(enms.size<count){
            // Создаем отображение врага
            ObjectSprite enemiesView = null;
            // Запрашиваем имеется ли в памяти изображение врага
            ObjectImage enemiesImg = getImageOfEnemyObject(ENMSHIPHEALTHY);
            // Создаем спрайт врага
            enemiesView = new ObjectSprite(enemiesImg, 40, 52, ++(GameSystem.curId));
            // Создаем врага с учетом того, что у данного типа нет снарядов и большое хп
            Array<Weapon> wps = new Array<Weapon>();
            wps.add(new Weapon(null));
            EnemyShip enemy = null;
            if(gameLevel>3){
                enemy = new EnemyShip(6, (float) 170.0, enemiesView, wps, new CosTrajectory((float) 170.0, true));
            }
            else{
                enemy = new EnemyShip(6, (float) 170.0, enemiesView, wps, new StraightTrajectory((float) 170.0, true));
            }            
            enms.add(enemy);
        }
        // Возвращаем созданного врага
        return enms;
    }
    
    /**
     * Создать врагов стреляющих по дуге
     * @param count количество врагов для генерации
     * @return Враги
     */
    private Array<EnemyShip> generateFastEnemies(int count){
        if (count<=0)
            return null;
        Array<EnemyShip> enms = new Array();
        while(enms.size<count){
            // Создаем отображение врага
            ObjectSprite enemiesView = null;
            // Запрашиваем в памяти изображение врага
            ObjectImage enemiesImg = getImageOfEnemyObject(ENMSHIPFAST);
            // Создаем спрайт врага
            enemiesView = new ObjectSprite(enemiesImg, 19, 31, ++(GameSystem.curId));      
            Array<Missile> enemyMissiles = this.missileBuilder.generateEnemyMissiles(ENMMISSILEFAST);
            Array<Weapon> wps = new Array<Weapon>();
            for(Missile em: enemyMissiles){
                wps.add(new Weapon(em));
            }        
            // Создаем врага
            StraightTrajectory enemiesTrajectory = new StraightTrajectory((float) 300.0, true);
            EnemyShip enemy = new EnemyShip(2, (float) 300.0, enemiesView, wps, enemiesTrajectory);
            enms.add(enemy);
        }
        // Возвращаем созданного врага
        return enms;
    }
    
    private Array<EnemyShip> generateWallOfDeath(){
        Array<EnemyShip> enms = new Array();
        enms.addAll(generateEasyEnemies(2));
        enms.addAll(generateFastEnemies(1));
        enms.addAll(generateHealthyEnemies(1,1));
        enms.addAll(generateFastEnemies(1));
        enms.addAll(generateEasyEnemies(2));        
        return enms;
    }
    
    private ObjectImage getImageOfEnemyObject(ObjectType type){
        for(ObjectImage img: this.enemiesSprites){
            if(img.getObjType()==type)
                return img;
        }
        return null;
    }
}
