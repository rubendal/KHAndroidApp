package com.rubendal.kuroganehammercom.ultimate.classes;

import com.rubendal.kuroganehammercom.smash4.classes.Move;
import com.rubendal.kuroganehammercom.smash4.classes.MoveSort;
import com.rubendal.kuroganehammercom.smash4.classes.Movement;

import java.io.Serializable;
import java.util.LinkedList;

public class SSBUCharacterData implements Serializable {

    public SSBUCharacter character;
    public LinkedList<Movement> movement;
    public LinkedList<Move> moveList;
    public LinkedList<Move> evasion;
    public MoveSort moveSort;

    public SSBUCharacterData(SSBUCharacter character, LinkedList<Movement> movement, LinkedList<Move> moves, LinkedList<Move> evasion, MoveSort moveSort){
        this.character = character;
        this.movement = movement;
        this.moveList = moves;
        this.evasion = evasion;
        this.moveSort = moveSort;
    }
}
