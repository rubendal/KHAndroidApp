package com.rubendal.kuroganehammercom.smash4.classes;

import java.io.Serializable;
import java.util.LinkedList;

public class CharacterData implements Serializable {

    public Character character;
    public LinkedList<Movement> movement;
    public LinkedList<Move> moveList;
    public LinkedList<Move> evasion;
    public MoveSort moveSort;

    public CharacterData(Character character, LinkedList<Movement> movement, LinkedList<Move> moves, LinkedList<Move> evasion, MoveSort moveSort){
        this.character = character;
        this.movement = movement;
        this.moveList = moves;
        this.evasion = evasion;
        this.moveSort = moveSort;
    }
}
