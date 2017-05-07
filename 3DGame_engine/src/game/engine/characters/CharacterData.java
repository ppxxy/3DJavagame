/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.characters;

import java.io.Serializable;
import java.util.ArrayList;

/**
* <h1>CharacterData</h1>
* Handles data related to the players character
* <p>

* @author  Tomi Lehto
* @version 1.0
*/
public class CharacterData implements Serializable {
	private String name;
    private ArrayList<Item> inventory;
    private int bagsize;
    private int level = 0;
    private int exp = 0;

    public CharacterData(String nimi){
        this.inventory = new ArrayList<Item>();
        this.name = nimi;
    }

    /**
	 * Adds a new item to the inventory list if it is not full
	 * or a similar item doesn't already exist on the list. In that
	 * case increase the amount of the item in the Item class.
	 *
	 * @param tavara Item object that is added to the inventory list.
	 * @return Returns false if the inventory list is full, otherwise returns true
	 * @deprecated Inventory system isn't in use.
	 */
	@Deprecated
    public boolean lootItem(Item tavara){
        if(containsItem(tavara) == null && inventory.size() < this.bagsize){
            inventory.add(tavara);
            return true;
        }else if(inventory.size() == this.bagsize){
            return false;
        } else {
            containsItem(tavara).setMaara(tavara.getMaara()+1);
            return true;
        }
    }

	/**
	 * Checks if the inventory already contains a certain item.
	 *
	 * @param tavara Item object that is added to the inventory list.
	 * @return returns Item if it is already on the list, otherwise returns null.
	 * @deprecated Inventory system isn't in use.
	 */
    public Item containsItem(Item tavara){
        for (Item item : inventory) {
            if(item.getNimi().equals(tavara.getNimi())){
                return item;
            }
        }

        return null;
    }

    /**
	 * Removes an Item from the inventory list.
	 *
	 * @param tavara Item object that is removed from the inventory list.
	 * @deprecated Inventory system isn't in use.
	 */
    public void removeItem(Item tavara){
        inventory.remove(tavara);
    }

    /**
     *
     * @param bagsize bagsize to set (int)
     * @deprecated Inventory system isn't in use.
     */
    public void setBagsize(int i){
        this.bagsize = i;
    }

    /**
	 *
	 * @return name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @return current level of the player
	 * @deprecated Experience/level system isn't in use.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 *
	 * @return current experience of the player
	 * @deprecated Experience/level system isn't in use.
	 */
	public int getExp() {
		return exp;
	}

}
