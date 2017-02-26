/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.characters;

/**
 *
 * @author Tomi
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
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * @param nimi the nimi to set
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * @return the maara
     */
    public int getMaara() {
        return maara;
    }

    /**
     * @param maara the maara to set
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }

    /**
     * @return the kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * @param kuvaus the kuvaus to set
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }
    
    
}
