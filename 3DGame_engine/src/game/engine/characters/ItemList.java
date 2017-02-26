/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.characters;

import java.util.HashMap;

/**
 *
 * @author Tomi
 */
public class ItemList {
    private HashMap<String, String> itemList;
    
    public ItemList(){
        this.itemList = new HashMap<String, String>();
    }
    
    public String getPicture(String key){
        return itemList.get(key);
    }
    
    public void addItems(){
        itemList.put("key", "value");
    }
}
