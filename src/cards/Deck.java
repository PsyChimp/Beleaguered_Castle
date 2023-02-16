/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author hofmannb
 */
public class Deck implements Serializable{
    ArrayList <Card> deckList;
    
    Deck(){
        
        this.deckList=new ArrayList();
        
            for(int r=1;r<14;r+=1){
                for(int s=0;s<4;s+=1){
                    
                    Card card =new Card(r,numToSuit.numToSuit(s),0*96,0*128);
                    this.deckList.add(card);
                }
            
            }
        
        
        
    }
    void shuffle(){
        
        Collections.shuffle(deckList);
    }
    Card deal(){
        if(this.deckList.size()>0){
            Card card = (this.deckList.get(0));
            this.deckList.remove(card);
            return card;
        }
        else{
            throw new RuntimeException("This Deck is Empty");
        }
    }
    boolean empty(){
        return this.deckList.isEmpty();
    
    }
    
}
