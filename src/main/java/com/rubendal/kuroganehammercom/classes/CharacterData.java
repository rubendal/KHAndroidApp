package com.rubendal.kuroganehammercom.classes;

import java.io.Serializable;
import java.util.LinkedList;

public class CharacterData implements Serializable {

    public Character character;
    public LinkedList<Movement> movement;
    public LinkedList<Move> moveList;
    public LinkedList<AttributeList> attributes;

    public CharacterData(Character character, LinkedList<Movement> movement, LinkedList<Move> moves, LinkedList<AttributeList> attributes){
        this.character = character;
        this.movement = movement;
        this.moveList = moves;
        this.attributes = attributes;
    }
}
