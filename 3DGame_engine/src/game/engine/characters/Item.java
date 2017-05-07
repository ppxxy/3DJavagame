/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.characters;

/**
* <h1>Item</h1>
* Item objects that can be found in game
* <p>

* @author  Tomi Lehto
* @version 1.0
* @deprecated Item/Inventory system isn't in use
*/

public class Item {
    private String nimi;
    private int maara;
    private String kuvaus;


    public Item(String nimi){
        this.nimi = nimi;
    }

    /**
     * @return the nimi
     * @deprecated Item/Inventory system isn't in use
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * @param nimi the nimi to set
     * @deprecated Item/Inventory system isn't in use
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * @return the maara
     * @deprecated Item/Inventory system isn't in use
     */
    public int getMaara() {
        return maara;
    }

    /**
     * @param maara the maara to set
     * @deprecated Item/Inventory system isn't in use
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }

    /**
     * @return the kuvaus
     * @deprecated Item/Inventory system isn't in use
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * @param kuvaus the kuvaus to set
     * @deprecated Item/Inventory system isn't in use
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }


}
