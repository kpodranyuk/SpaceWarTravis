/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.utils.Array;
import static com.mygdx.spacewar.Bonus.BonusType.WEAPONBOOST;
import com.mygdx.spacewar.ObjectImage.ObjectType;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMMISSILE;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMSHIP;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMSHIPFAST;
import static com.mygdx.spacewar.ObjectImage.ObjectType.ENMSHIPHEALTHY;
import static com.mygdx.spacewar.ObjectImage.ObjectType.GAMEBONUS;
import static com.mygdx.spacewar.ObjectImage.ObjectType.USRMISSILE;
import static com.mygdx.spacewar.ObjectImage.ObjectType.USRSHIP;

/**
 * Игровая система, управляющая процессом игры
 * @author Katie
 */
public class GameSystem {
    private PlayerShip player;                          /// Игрок
    private Array<EnemyShip> enemies;                   /// Враги
    private Array<Missile> playersMissiles;             /// Снаряды игрока (выпущенные)
    private Array<Missile> enemiesMissiles;             /// Снаряды врагов (выпущенные)
    private Array<Bonus> bonuses;                       /// Игровые бонусы (выпущенные)
    private EnemyBuilder enmbuilder;                    /// Строитель врагов
    private BonusBuilder bnsbuilder;                    /// Строитель бонусов
    
    public static int curId = -1;                       /// Текущий идентификатор
    private int maxBonusShoots = 6;                     /// Максимальная временная длина бонуса
    private int shootsSinceBonus;                       /// Выстрелов с последнего бонуса
    private int currentWeaponBonusId;                   /// Id текущего активного бонуса
    private int enemiesToEndGame;                       /// Количество врагов для завершения игры
    
    /**
     * Уровень игры, отвечающий за разнообразие создаваемых врагов
     * EASY - мелкие враги, стреляющие прямо
     * MEDIUM - EASY + крупные враги с большим хп (не стреляют)
     * HARD и ULTRAHARD - MEDIUM + быстрые мелкие враги, стреляющие по дуге
     */
    public enum Level { EASY, MEDIUM, HARD, ULTRAHARD }
    private Level currentLevel;                         /// Текущий уровень игры
    
    /**
     * Конструктор
     */
    public GameSystem(){
        // Инициализируем поля
        enmbuilder = new EnemyBuilder();
        bnsbuilder = new BonusBuilder();
        enemies = new Array();
        playersMissiles = new Array();
        enemiesMissiles = new Array();
        currentLevel = Level.EASY;
        
        bonuses = new Array();
        shootsSinceBonus=0;
        currentWeaponBonusId=0;
        enemiesToEndGame = 250;
        
        // Создаем игрока
        createPlayer();
        
    }
    
    /**
     * Контролировать численность идентификатора игры
     */
    private void controlIdCounter(){
        // Если достигнут предел, обнуляем счетчик
        if (this.curId>1500){
            this.curId = -1;
        }
    }
    
    /**
     * Создать игрока
     */
    private void createPlayer(){
        // Создаем изображение и спрайт корабля игрока
        ObjectImage playersImg = new ObjectImage("ship.png", USRSHIP);
        ObjectSprite playersView = new ObjectSprite(playersImg, 34, 37, ++(GameSystem.curId));
        
        // Создаем изображение и спрайт снаряда игрока
        ObjectImage playersMissileImg = new ObjectImage("fire/greenpng.png", USRMISSILE);
        ObjectSprite playersMissileView = new ObjectSprite(playersMissileImg, 22, 11, ++(GameSystem.curId));

        // Снаряд игрока летит по прямой траектории
        StraightTrajectory playersTrajectory = new StraightTrajectory((float) 250.0, false);
        // Создаем снаряд игрока
        Missile playersMissile = new Missile(1, (float) 250.0, playersTrajectory, playersMissileView);
        Array<Weapon> wps = new Array<Weapon>();
        wps.add(new Weapon(playersMissile));
        // Создаем объект игрока
        player = new PlayerShip(3, (float) 250.0, playersView, wps);
    }
    
    /**
     * Создать врагов
     * @param enemiesKilled Количество убитых игроком кораблей
     * @return Отображение врагов
     */
    public Array<ObjectSprite> generateEnemies(int enemiesKilled){
        // Контроллируем идентификатор
        controlIdCounter();
        // В зависимости от убитых врагов вычисляем текущий уровень игры
        controlLevel(enemiesKilled);
        int lvl = levelToInt();
        // Получаем созданных билдеров врагов
        Array<EnemyShip> createdEnemies = enmbuilder.generateEnemies(lvl);
        Array<ObjectSprite> createdEnemiesSprites = new Array();
        for(EnemyShip enm: createdEnemies){
            createdEnemiesSprites.add(enm.getView());
        }
        // Добавляем их в массив
        this.enemies.addAll(createdEnemies);
        // Возвращаем массив их отображений
        return createdEnemiesSprites;
    }    
    
    /**
     * Сделать выстрел
     * @param type Тип объекта, инициирующего выстрел
     * @param objectId Id объекта, инициирующего выстрел
     * @return Отображение снарядов
     */
    public Array<ObjectSprite> makeShoot(ObjectType type, int objectId){
        // Контроллируем идентификатор
        controlIdCounter();
        Array<Missile> ms = new Array<Missile>();
        Array<ObjectSprite> msView = new Array<ObjectSprite>();
        if (type == USRSHIP){
            if(this.player.getActiveWeaponsCount()>this.player.getDefaultWeaponsCount())
                shootsSinceBonus++;
            controlBonusTime(currentWeaponBonusId,shootsSinceBonus);
            ms = this.player.shoot();
            Missile bufM;
            for(Missile m: ms){
                ObjectSprite newMissileView = new ObjectSprite (m.getView(), ++(GameSystem.curId));
                bufM = new Missile(m, newMissileView);
                this.playersMissiles.add(bufM);
                msView.add(newMissileView);
            }            
            return msView;
        }
        if (type == ENMSHIP || type == ENMSHIPFAST){
            Ship currentEnemy = getActiveEnemy(type, objectId);
            ms = currentEnemy.shoot();
            Missile bufM;
            for(Missile m: ms){                
                ObjectSprite newMissileView = new ObjectSprite (m.getView(), ++(GameSystem.curId));
                bufM = new Missile(m, newMissileView);
                this.enemiesMissiles.add(bufM);
                msView.add(newMissileView);
            }
            return new Array<ObjectSprite>(msView);
        }            
        return null;
    }
    
    /**
     * Получить активный снаряд
     * @param type Тип снаряда
     * @param missileId Идентификатор снаряда
     * @return Снаряд
     */
    public Missile getActiveMissile(ObjectType type, int missileId){
        // Проверяем идентификатор на корректность
        if (missileId<0)
            return null;
        // Если идентификатор корректный..
        // Если запрашивают пользовательский снаряд..
        if (type == USRMISSILE){
            // Для каждого активного пользовательского снаряда..
            for (Missile miss : this.playersMissiles){
                // Если идентификатор текущего снаряда совпадает с требуемым, возвращаем текущий снаряд
                if (miss.getView().getId() == missileId)
                    return miss;
            }
        }
        // Если запрашивают вражеский снаряд..
        if (type == ENMMISSILE){
            // Для каждого активного вражеского снаряда..
            for (Missile miss : this.enemiesMissiles){
                // Если идентификатор текущего снаряда совпадает с требуемым, возвращаем текущий снаряд
                if (miss.getView().getId() == missileId)
                    return miss;
            }
        }
        // Если не был найден ни один снаряд, возвращаем null
        return null;
    }
    
    /**
     * Получить активного врага
     * @param type Тип врага
     * @param enemyId Идентификатор врага
     * @return Враг
     */
    public EnemyShip getActiveEnemy(ObjectType type, int enemyId){
        // Проверяем на корректность идентификатор
        if (enemyId<0)
            return null;
        // Если идентификатор корректный..
        // Если требуемый тип является типом вражеских кораблей..
        if (type == ENMSHIP || type == ENMSHIPHEALTHY || type == ENMSHIPFAST){
            // Для каждого активного врага в массиве врагов..
            for (Ship enemy: this.enemies){
                // Если идентификатор текущего врага совпадает с требуемым, возвращаем текущего врага
                if (enemy.getView().getId() == enemyId)
                    return ((EnemyShip)enemy);
            }
        }
        // Возвращаем null, так как враг так и не нашелся
        return null;
    }
    
    /**
     * Определить убит ли корабль
     * @param type Тип корабля
     * @param shipId Идентификатор корабля
     * @param missileType Тип снаряда
     * @param missileId Идентификатор снаряда
     * @return true, если корабль убит, false иначе
     */
    public boolean isKilledShip(ObjectType type, int shipId, ObjectType missileType, int missileId ){
        // Если корабль - пользовательский, а снаряд вражеский..
        if (type == USRSHIP && this.player.getView().getId() == shipId && missileType != USRMISSILE){
            if (missileType != USRMISSILE){
                // Запрашиваем урон снаряда
                Missile m = getActiveMissile(missileType, missileId);
                // Отправляем пользователю урон от снаряда
                this.player.takeDamage(m.getDamage());
                // Уничтожаем вражеский снаряд
                this.enemiesMissiles.removeValue(m, true);
                // Возвращаем значение того, что здоровье игрока меньше либо равно 0 (мертв ли игрок)
                return this.player.getCurrentHealth() <= 0;
            }
        }
        // Иначе если корабль вражеский, а снаряд пользовательский..
        else if (type == ENMSHIP || type == ENMSHIPHEALTHY || type == ENMSHIPFAST && missileType == USRMISSILE){
            // Получаем корабль 
            Ship s = getActiveEnemy(type, shipId);
            // Получаем снаряд
            Missile m = getActiveMissile(missileType, missileId);
            if (m == null)
                return false;
            // Отправляем врагу урон от снаряда
            s.takeDamage(m.getDamage());
            // Удаляем пользовательский снаряд
            this.playersMissiles.removeValue(m, true);
            // Если враг убит, возвращаем true и удаляем его из списка
            if (s.getCurrentHealth() <= 0){
                this.player.increasePoints(s.getMaxHealth());
                this.enemiesToEndGame-=1;
                enemies.removeValue((EnemyShip) s, true);
                return true;
            }
            // Иначе возвращаем false
            return false;
        }
        // Иначе возвращаем false
        return false;
    }
    
    /**
     * Столкновение снарядов
     * @param firstType Тип первого снаряда
     * @param firstId Идентификатор первого снаряда
     * @param secondType Тип второго снаряда
     * @param secondId Идентификатор первого снаряда
     */
    public void missilesCollision(ObjectType firstType, int firstId, ObjectType secondType, int secondId){
        ObjectType playerType = USRMISSILE;
        ObjectType enmType = ENMMISSILE;
        int playersMId = -1;
        int enemiesMId = -1;
        // Если первый снаряд пользовательский, а второй вражеский
        if (firstType == USRMISSILE && secondType == ENMMISSILE){
            playersMId = firstId;
            enemiesMId = secondId;            
        }
        // Иначе если первый снаряд вражеский, а второй пользовательский
        else if (firstType == ENMMISSILE && secondType == USRMISSILE){
            playersMId = secondId;
            enemiesMId = firstId;
        }
        // Удаляем первый снаряд из списка пользовательских снарядов
        this.playersMissiles.removeValue(this.getActiveMissile(playerType, playersMId), true);
        System.out.println("Freed user missile with id " + firstId);
        // Уменьшаем память массива пользовательских снарядов
        this.playersMissiles.shrink();
        // Удаляем второй снаряд из списка вражеских снарядов
        this.enemiesMissiles.removeValue(this.getActiveMissile(enmType, enemiesMId), true);
        System.out.println("Freed enemy missile with id " + secondId);
        // Уменьшаем память массива вражеских снарядов
        this.enemiesMissiles.shrink();
    }
    
    /**
     * Объект вылетел за пределы поля
     * @param type Тип объекта
     * @param id Идентификатор объекта
     */
    public void objectLeftField(ObjectType type, int id){
        // Если тип объекта - пользовательский снаряд
        if (type == USRMISSILE){
            System.out.println("Freed user missile with id " + id);
            // Удаляем объект из массива и ужимаем занимаемую им память
            this.playersMissiles.removeValue(this.getActiveMissile(type, id), true);
            this.playersMissiles.shrink();
        }
        // Если тип объекта - вражеский снаряд
        else if (type == ENMMISSILE){
            System.out.println("Freed enemy missile with id " + id);
            // Удаляем объект из массива и ужимаем занимаемую им память
            this.enemiesMissiles.removeValue(this.getActiveMissile(type, id), true);
            this.enemiesMissiles.shrink();
        }
        // Если тип объекта - вражеский корабль
        else if (type == ENMSHIP || type == ENMSHIPHEALTHY || type == ENMSHIPFAST) {
            System.out.println("Freed enemy ship missile with id " + id);
            // Удаляем объект из массива и ужимаем занимаемую им память
            this.enemies.removeValue((EnemyShip)this.getActiveEnemy(type, id), true);
            this.enemies.shrink();
        }
        // Если тип объекта - вражеский корабль
        else if (type == GAMEBONUS) {
            System.out.println("Freed game bonus with id " + id);
            // Удаляем объект из массива и ужимаем занимаемую им память
            this.bonuses.removeValue(this.getBonusWithId(id), true);
            this.bonuses.shrink();
        }
    }
    
    /**
     * Получить корабль игрока
     * @return Корабль игрока
     */
    public PlayerShip getPlayer(){
        return this.player;
    }
    
    /**
     * Контролировать уровень игры
     * @param enemiesKilled Количество убитых врагов
     */
    private void controlLevel(int enemiesKilled){
        // Если 10<убитых врагов<=40, то игра на среднем уровне
        if (enemiesKilled>5 && enemiesKilled<=25) {
            currentLevel=Level.MEDIUM;
        }
        // Если 40<убитых врагов<=80, то игра на сложном уровне
        else if (enemiesKilled>25 && enemiesKilled<=40) {
            currentLevel=Level.HARD;
        }
        // Если убитых врагов>80, то игра на суперсложном уровне
        else if (enemiesKilled>40) {
            currentLevel=Level.ULTRAHARD;
        }
    }
    
    /**
     * Перевести текущий уровень игры в числовое представление
     * @return Корабль игрока
     */
    private int levelToInt(){
        // Если текущий уровень средний, вернуть 2
        if (currentLevel==Level.MEDIUM) {
            return 2;
        }
        // Иначе если текущий уровень сложнй или ультрасложный, вернуть 3
        else if (currentLevel==Level.HARD) {
            return 3;
        }
        else if(currentLevel==Level.ULTRAHARD){
            return 4;
        }
        // Вернуть 1
        return 1;
    }
    
    /**
     * Создать бонус здоровья
     * @return null, если бонус не создан, иначе - спрайт бонуса
     */
    public ObjectSprite createHealthBouns(){
        // Будем считать, что бонус здоровья выдается только при уровне игры не легче среднего
        // Также игрок должен нуждаться в бонусе здоровья
        if(levelToInt()>1 && this.player.getCurrentHealth()<this.player.getMaxHealth()){
            Bonus kit = bnsbuilder.generateHealthKit();
            bonuses.add(kit);
            return kit.getView();
        }
        return null;
    }
    
    public ObjectSprite createWeaponBouns() {
        // Будем считать, что бонус оружия выдается только при сложном уровне игры
        if(levelToInt()>2){
            Bonus boost = bnsbuilder.generateWeaponBoost();
            bonuses.add(boost);
            return boost.getView();
        }
        return null;
    }
    
    public Bonus getBonusWithId(int id){
        // Проверяем на корректность идентификатор
        if (id<0)
            return null;
        // Если идентификатор корректный..
        // Для каждого активного бонуса в массиве бонусов..
        for (Bonus bonus: this.bonuses){
            // Если идентификатор текущего врага совпадает с требуемым, возвращаем текущего врага
            if (bonus.getView().getId() == id)
                return bonus;
        }
        // Возвращаем null, так как враг так и не нашелся
        return null;
    }
    
    public void caughtBonus(int bonusId) {
        Bonus bonus = getBonusWithId(bonusId);
        if (bonus!=null){
            bonus.activate(player);
            if (bonus.getType() == WEAPONBOOST){
                shootsSinceBonus = 0;
                currentWeaponBonusId = bonusId;
            }
        }
    }
    
    private void controlBonusTime(int bonusId, int shootsSinceBonusOn){
        Bonus bonus = getBonusWithId(bonusId);
        if (bonus!=null && bonus.getType() == WEAPONBOOST && shootsSinceBonusOn>maxBonusShoots){
            ((WeaponBoost)bonus).deactivate(player);
            shootsSinceBonus=0;
            currentWeaponBonusId=-1;
        }
    }
    
    public int howMuchEnemiesToWin(){
        return this.enemiesToEndGame;
    }
    
    public boolean isGameFinished(){
        return this.enemiesToEndGame<=0;
    }
}
