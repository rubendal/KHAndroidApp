package com.rubendal.kuroganehammercom.classes;

import java.io.Serializable;
import java.util.LinkedList;

public class CharacterData implements Serializable {

    public Character character;
    public LinkedList<Movement> movement;
    public LinkedList<Move> moveList;
    public LinkedList<Move> evasion;

    public CharacterData(Character character, LinkedList<Movement> movement, LinkedList<Move> moves, LinkedList<Move> evasion){
        this.character = character;
        this.movement = movement;
        this.moveList = moves;
        this.evasion = evasion;
    }
}
