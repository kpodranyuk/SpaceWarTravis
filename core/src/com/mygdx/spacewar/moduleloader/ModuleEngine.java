/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.spacewar.moduleloader;

import com.mygdx.spacewar.bot.Bot;
import com.mygdx.spacewar.api.SpaceWarAPI;

/**
 *
 * @author Katie
 */
public class ModuleEngine {
    
    private static Bot bot = null;
    
    public void loadBot(String modulePath, String moduleName,SpaceWarAPI api) {
        
        ModuleLoader loader = new ModuleLoader(modulePath, ClassLoader.getSystemClassLoader());
        
        if (modulePath.endsWith(".class")) {
            try {
                System.out.print("Executing loading module: ");
                System.out.println(moduleName);

                Class clazz = loader.loadClass("com.mygdx.spacewar.bot." + moduleName);
                bot = (Bot) clazz.newInstance();
                bot.run(api);
                System.out.println("Bot loaded");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
}
