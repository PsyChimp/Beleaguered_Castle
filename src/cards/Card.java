/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author hofmannb
 */
public class Card implements Serializable{
    int HEIGHT=128;
    int WIDTH=96;
    int rank; //Ace=1, ... J=11, Q=12, K=13
    Suit suit;
    int x,y; //location on the screen where the card is
    static BufferedImage img=null;
    
    Card(int r, Suit s,int xpos,int ypos){
        this.rank=r;
        this.suit=s;
        this.x=xpos;
        this.y=ypos;
        if(img==null){
            try {
                img=ImageIO.read(new FileInputStream("allcards.png"));
            } catch (IOException ex) {
                System.out.println("Could not find allcards.png");
                System.exit(1);
            }
        }
    }
    int suitToNumber(Suit s){
        if( s  == Suit.CLUBS) return 0;
        if( s == Suit.DIAMONDS ) return 1;
        if( s == Suit.HEARTS ) return 2;
        return 3;       //must be spades 
    }
    void randomize(){
        Random r = new Random();
        int i=r.nextInt(14);
        int l=r.nextInt(4);
        if (i==0){
            i+=1;
        }

        Suit newSuit=numToSuit.numToSuit(l);
        this.rank=i;
        this.suit=newSuit;
    }
    
    void draw(Graphics g){
        int tmpx = (rank-1)*WIDTH;
        int tmpy = suitToNumber(suit) * HEIGHT;
        g.drawImage(img, 
                x, y, x+WIDTH, y+HEIGHT,        //destination rectangle
                tmpx, tmpy, tmpx+WIDTH, tmpy+HEIGHT,   //source rectangle
                null );
    }
    
}
