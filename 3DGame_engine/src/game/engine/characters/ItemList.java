/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.characters;

import java.util.HashMap;

/**
* <h1>ItemList</h1>
* List of all the items in game
* <p>

* @author  Tomi Lehto
* @version 1.0
* @deprecated Item/Inventory system isn't in use
*/

public class ItemList {
    private HashMap<String, String> itemList;

    public ItemList(){
        this.itemList = new HashMap<String, String>();
    }

    /**
     * @return the path to where a picture of the selected item is located
     * @deprecated Item/Inventory system isn't in use
     */
    public String getPicture(String key){
        return itemList.get(key);
    }

    /**
     * Adds a new item to the game
     * @deprecated Item/Inventory system isn't in use
     */
    public void addItems(){
        itemList.put("key", "value");
    }
}
