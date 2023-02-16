/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.io.Serializable;

/**
 *
 * @author hofmannb
 */
public class numToSuit implements Serializable{

    /**
     *
     * @param n
     * @return
     */
    static public Suit numToSuit(int n){
            if(n==0) return Suit.CLUBS;
            if(n==1) return Suit.DIAMONDS;
            if(n==2) return Suit.HEARTS;
            return Suit.SPADES;
        }
}
