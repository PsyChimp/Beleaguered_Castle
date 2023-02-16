/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MainPane extends JPanel implements Serializable{
    //how to do random
    //"R" determines ranks
    //"S" determines suits
    
    void rePos(){
            int p=0;
        for (int i=0; i<piles.size();i+=1){
            for (int c=0; c<piles.get(i).size();c+=1){
                if(i<=3){
                    Card tmp=(Card) piles.get(i).get(c);
                    tmp.x=(int) ((getWidth()*0.6-92/2)+(c*(96/2)));
                    tmp.y=i*(128+getHeight()/12);
                    
                }
                if(i>3){
                    Card tmp=(Card) piles.get(i).get(c);
                    tmp.x=(int) ((getWidth()*0.4-92/2)-(c*(96/2)));
                    tmp.y=p*(128+getHeight()/12);
                    
                }
            }
        if(i>3){
            p+=1;
        }
        for(int a=0; a<talons.size();a+=1){
            for (int b=0; b<talons.get(a).size();b+=1){
                Card tmp=(Card)talons.get(a).get(b);
                tmp.x=(int) (getWidth()*0.5-92/2);
                tmp.y=a*(128+getHeight()/12);
                
            }
        }    
        }
    repaint();
    }
    void RecPos(){
        for(int x=0; x<piles.size();x+=1){
            if (piles.get(x).isEmpty()){
            
            }
            else{
                Card tmp=(Card)piles.get(x).get(0);    
                recPos.get(x).clear();
                recPos.get(x).add(tmp.x);
                recPos.get(x).add(tmp.y);
            }
            
        }
    }
    void Undo(){
        if(undos.size()>0){
            
            

            
            Move tmp=undos.getLast();
            if("piles".equals(tmp.type)){
                int from=tmp.from;
                int to=tmp.to;
                
            
                Card card=(Card) piles.get(to).getLast();
                piles.get(to).remove(card);
                piles.get(from).add(card);
                card=null;
                
                
                
            }
            if("talons".equals(tmp.type)){
                int from=tmp.from;
                int to=tmp.to;
                
                Card card=(Card) talons.get(to).pop();
                piles.get(from).add(card);
                card=null;
                
                
            }
            rePos();
            repaint();
            redos.add(tmp);
            undos.removeLast();
            
            
        }      
    }
    void Redo(){
        if(redos.size()>0){
            Move tmp=redos.getLast();
            if ("piles".equals(tmp.type)){
                int from=tmp.from;
                int to=tmp.to;
                
                Card card=(Card) piles.get(from).getLast();
                piles.get(from).remove(card);
                piles.get(to).add(card);
                card=null;
            }
            if("talons".equals(tmp.type)){
                int from=tmp.from;
                int to=tmp.to;
                Card card=(Card) piles.get(from).getLast();
                piles.get(from).remove(card);
                talons.get(to).push(card);
                card=null;
            }
            
            rePos();
            repaint();
            undos.add(tmp);
            redos.removeLast();
        }
    }
    
    
    Deck deck=new Deck();
    LinkedList <ArrayList> recPos=new LinkedList();
    
    LinkedList <LinkedList> piles = new LinkedList();
    LinkedList <Stack> talons = new LinkedList();
    
    LinkedList <Move> undos = new LinkedList();
    LinkedList <Move> redos = new LinkedList();
    
    Random R=new Random();
    Random S=new Random();
        
    int c=0;
    
    Card selectedCard;
    int selectedPile;
    
    
    
    numToSuit N=new numToSuit();
    ArrayList <Card> remove=new ArrayList();
    int index=0;
    int offsetx=0;
    int offsety=0;
    
    
    public MainPane(){
        
        deck.shuffle();
        
        for(int i=0; i<8; i+=1){
            piles.add(new LinkedList());
        }
        for(int i=0; i<4; i+=1){
            talons.add(new Stack());
        }
        //remove aces
        for(int i=0; i<deck.deckList.size();i+=1){
            
            
            if(deck.deckList.get(i).rank==1){
                remove.add(deck.deckList.get(i));
                
            }
            
        }
        for(int i=0; i<remove.size();i+=1){
            deck.deckList.remove(remove.get(i));
            talons.get(i).push(remove.get(i));
        }
        
        
        
        
        for(int x=0; x<piles.size();x+=1){
            for(int y=0; y<6;y+=1){
                piles.get(x).add(deck.deal());
                
            }
            
            
        }
        for(int i=0; i<piles.size();i+=1){
            recPos.add(new ArrayList());
        }
        deck.deckList.clear();
        
        
        
        
        /*int p=0;
        for (int i=0; i<piles.size();i+=1){
            for (int c=0; c<piles.get(i).size();c+=1){
                if(i<=3){
                    Card tmp=(Card) piles.get(i).get(c);
                    tmp.x=640+(c*(96/2));
                    tmp.y=i*128;
                    
                }
                if(i>3){
                    Card tmp=(Card) piles.get(i).get(c);
                    tmp.x=340-(c*(96/2));
                    tmp.y=p*128;
                    
                }
            }
        if(i>3){
            p+=1;
        }
            
        }*/
        
        
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                /*int mx,my;
                mx=me.getX();
                my=me.getY();
                for(int i=0; i<cards.size(); i+=1){
                    Card tmp=cards.get(i);
                    int tmpx=tmp.x;
                    int tmpy=tmp.y;
                    if ((mx>=tmpx)&&(mx<=tmpx+96)){
                        if ((my>=tmpy)&&(my<=tmpx+128)){
                            
                            tmp.randomize();
                            cards.remove(i);
                            cards.add(tmp);
                            repaint();
                        }
                    }
                    
                    
                    
                }*/
            }

            @Override
            public void mousePressed(MouseEvent me) {
                int mx=me.getX();
                int my=me.getY();
                for(int i=0; i<piles.size(); i+=1){
                    for(int x=0; x<piles.get(i).size();x+=1){
                        
                        if(x==piles.get(i).size()-1){
                            
                            Card tmp=(Card)piles.get(i).get(x);
                            int tmpx=tmp.x;
                            int tmpy=tmp.y;
                            if (((mx>=tmpx)&&(mx<=tmpx+96))&&((my>=tmpy)&&(my<=tmpy+128))){


                                selectedCard=tmp;
                                selectedPile=i;
                                piles.get(i).removeLast();
                                
                                offsetx=mx-selectedCard.x;
                                offsety=my-selectedCard.y;
                                repaint();
                            }
                        
                        }
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent me) {
                int mx=me.getX();
                int my=me.getY();
                if(selectedCard!=null){
                    for(int w=0; w<piles.size(); w+=1){
                        for(int x=0; x<piles.get(w).size();x+=1){
                            
                            Card tmp=(Card) piles.get(w).get(x);
                            int tmpx=tmp.x;
                            int tmpy=tmp.y;
                                
                            if (((mx>=tmpx)&&(mx<=tmpx+96))&&((my>=tmpy)&&(my<=tmpy+128))){
                                Card tmp2=(Card) piles.get(w).get(piles.get(w).size()-1);
                                if((selectedCard.rank)==(tmp2.rank-1)){
                                    piles.get(w).add(selectedCard);
                                    undos.add(new Move(selectedPile,w,"piles"));
                                    if(redos.size()>0){
                                        redos.clear();
                                    }
                                    selectedCard=null;
                                    selectedPile=0;
                                    break;
                                }
                                
                            }
                            
                                
                            
                            
                            
                        }
                    
                        if ((piles.get(w).isEmpty())&&(selectedCard!=null)){
                            
                            
                            if((mx>getWidth()*0.4-43)&&(mx<getWidth()*0.4+96)){
                                
                                if((my>(128+getHeight()/12)*(w-4))&&(my<(128+getHeight()/12)*(w+1-4))){
                                    
                                    
                                    piles.get(w).add(selectedCard);
                                    undos.add(new Move(selectedPile,w,"piles"));
                                    if(redos.size()>0){
                                        redos.clear();
                                    }
                                    selectedCard=null;
                                    selectedPile=0;
                                }
                                
                                
                            }
                            if((mx>getWidth()*0.6-43)&&(mx<getWidth()*0.6+96)){
                                
                                if((my>(128+getHeight()/12)*(w))&&(my<(128+getHeight()/12)*(w+1))){
                                    piles.get(w).add(selectedCard);
                                    undos.add(new Move(selectedPile,w,"piles"));
                                    if(redos.size()>0){
                                        redos.clear();
                                    }
                                    selectedCard=null;
                                    selectedPile=0;
                                }
                            }
                            
                        }
                    }
                    for(int a=0;a<talons.size();a+=1){
                        for(int b=0;b<talons.get(a).size();b+=1){
                            Card tmp=(Card)talons.get(a).get(b);
                            int tmpx=tmp.x;
                            int tmpy=tmp.y;
                            if (((mx>=tmpx)&&(mx<=tmpx+96))&&((my>=tmpy)&&(my<=tmpy+128))){
                                Card tmp2=(Card) talons.get(a).get(talons.get(a).size()-1);
                                if(((selectedCard.suit)==(tmp2.suit))&&(selectedCard.rank)==(tmp2.rank+1)){
                                    talons.get(a).push(selectedCard);
                                    undos.add(new Move(selectedPile,a,"talons"));
                                    selectedCard=null;
                                    selectedPile=0;
                                    break;
                                }
                                
                            }
                        }
                    }
                    if(selectedCard!=null){
                        
                        piles.get(selectedPile).add(selectedCard);
                        selectedCard=null;
                        selectedPile=0;
                    }
                    
                    rePos();
                    repaint();
                    
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        
        this.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent me) {
                
                if (selectedCard!=null){
                    
                
                    int mx=me.getX();
                    int my=me.getY();
                    selectedCard.x=mx-offsetx;
                    selectedCard.y=my-offsety;
                    /*
                    deck.deckList.get(index).x=mx-offsetx;
                    deck.deckList.get(index).y=my-offsety;
                    */
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                
            }
            
        });
        
    }
    public void paintComponent(Graphics g){
        
        
        super.paintComponent(g);
        rePos();
        RecPos();
        //how to add colored shapes to the screen
        g.setColor(new Color(0,150,0));
        //g.fillRect(0, 0, this.getWidth(), this.getHeight());
        //g.setColor(Color.GREEN);
        //g.fillRect(0, 0, 40, 40);
        
        //everytime you rezise the window, this code is re-executed
        
        for(int i=0;i<recPos.size();i+=1){
            int tmpx;
            int tmpy;
            tmpx=(int) recPos.get(i).get(0);
            tmpy=(int) recPos.get(i).get(1);
            g.fillRect(tmpx, tmpy, 96, 128);
        }
        for(int x=0;x<piles.size();x+=1){
                for(int y=0;y<piles.get(x).size();y+=1){
                    Card temp=(Card) piles.get(x).get(y);
                    temp.draw(g);
                }
                
            }
        for(int a=0;a<talons.size();a+=1){
            for(int b=0;b<talons.get(a).size();b+=1){
                Card temp=(Card) talons.get(a).get(b);
                temp.draw(g);
            }
        }   
        if (selectedCard!=null){
            selectedCard.draw(g);
        }
            
            
        
        //Card x=new Card(r1,Suit.CLUBS,0,0);
        //Card y=new Card(r2,Suit.DIAMONDS,96,128);
        //x.draw(g);
        //y.draw(g);
    }
}
