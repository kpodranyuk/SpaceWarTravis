/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Katie
 */
public class ObjectImage {
    private Texture texture;    /// Текстура     
    /**
     * Возможные типы объекта
     */
    public enum ObjectType { USRMISSILE, ENMMISSILE, ENMMISSILEFAST, USRSHIP, ENMSHIP, ENMSHIPFAST, ENMSHIPHEALTHY, GAMEBONUS }
    private ObjectType type;    /// Тип объекта
    
    /**
     * Конструктор отображения объекта
     * @param img Путь к файлу с изображением объекта
     * @param width Ширина объекта на игровом поле
     * @param height Высота объекта на игровом поле
     * @param type Тип объекта
     */
    public ObjectImage(String img, ObjectType type){
        if (img == null || img.equals("") || type == null)
            throw new Error("Can't create view of object");
        this.texture = new Texture(img);        
        this.type = type;
    }
    
    public ObjectImage(ObjectImage other){
        if (other == null)
            throw new Error("Can't create view of object");
        this.texture = other.getTexture();    
        this.type = other.getObjType();
    }
    
    /**
     * Получить текстуру объекта
     * @return Текустура объекта
     */
    public Texture getTexture(){
        return this.texture;
    }

    /**
     * Получить тип объекта
     * @return Тип объекта
     */
    public ObjectType getObjType(){
        return this.type;
    }
}
