/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.io.Serializable;

/**
 *
 * @author brhof_000
 */
public class Move implements Serializable{
    int from,to;
    String type;
    Move(int from,int to,String type){
        this.type=type;
        this.from=from;
        this.to=to;
    }
}
