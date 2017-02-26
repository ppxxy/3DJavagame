/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.characters;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tomi
 */
public class CharacterData implements Serializable {
    private ArrayList<Item> inventory;
    private int bagsize;
    
    public CharacterData(){
        this.inventory = new ArrayList<Item>();
    }
    
    //Lisää uusi Item listalle, jos inventory ei ole täynnä, eikä siellä ole jo vastaavaa itemiä.
    //Jos Item on jo listalla niin nostetaan määrää Item-luokassa.
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
    
    //palauttaa listalta löytyvän item olion, jos parametrina annetun itemin nimi vastaa sitä 
    //tai null jos itemiä ei löydy listalta
    public Item containsItem(Item tavara){
        for (Item item : inventory) {
            if(item.getNimi().equals(tavara.getNimi())){
                return item;
            }
        }
        
        return null;
    }
    
    public void removeItem(Item tavara){
        inventory.remove(tavara);
    }
    
    private void setBagsize(int i){
        this.bagsize = i;
    }
}
