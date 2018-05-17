/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Отображение игрового объекта
 * @author Katie
 */
public class ObjectSprite {
    private ObjectImage view;   /// Изображение спрайта
    public Rectangle rect;     /// Прямоугольник отображения
    private int id;
    
    /**
     * Конструктор спрайта объекта
     * @param view Отображение объекта
     * @param id Идентификатор объекта
     */
    public ObjectSprite(ObjectImage view, int width, int height, int id){
        this(view, width, height);
        this.setId(id);
    }
    
    /**
     * Конструктор спрайта объекта
     * @param view Отображение объекта
     */
    public ObjectSprite(ObjectImage view, int width, int height){
        if (view == null || width <= 0 || height <= 0)
            throw new Error("Can't create sprite of object");
        this.view = view;
        this.id = -1;
        this.rect = new Rectangle();
        this.rect.width = width;
        this.rect.height = height;
        
        this.rect.x = 0;
        this.rect.y = 0;
    }
    
    /**
     * Конструктор копирования
     * @param other Другое отображение
     * @param newId Id для создаваемого объекта
     */
    public ObjectSprite(ObjectSprite other, int newId){
        if (other == null)
            throw new Error("Can't create copy of null ObjectSprite");
        if (newId < 0)
            throw new Error("Can't create object with negative id");
        this.view = other.getImage();
        this.id=newId;
        this.rect = new Rectangle();
        this.rect.width = other.rect.width;
        this.rect.height = other.rect.height;
        
        this.rect.x = 0;
        this.rect.y = 0;
    }
    
    /**
     * Получить отображение объекта
     * @return Отображение объекта
     */
    public ObjectImage getImage(){
        return this.view;
    }
    
    /**
     * Получить текстуру объекта
     * @return Текустура объекта
     */
    public Texture getTexture(){
        return this.view.getTexture();
    }

    /**
     * Получить тип объекта
     * @return Тип объекта
     */
    public ObjectImage.ObjectType getObjType(){
        return this.view.getObjType();
    }
    
    /**
     * Получить Id объекта
     * @return Id объекта
     */
    public int getId(){
        return this.id;
    }
    
    public void setId(int newId){
        if(newId>-1){
            this.id = newId;
        }
        else
            throw new Error("Can't set ObjectSprite id");
    }
}
