package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import java.io.Serializable;
import java.util.LinkedList;

public class APIList implements Serializable {

    public LinkedList<String> characters, attacker_modifiers, target_modifiers, moves, stages, effects;
    public LinkedList<MoveData> moveData;

    public APIList(){
        characters = new LinkedList<>();
        attacker_modifiers = new LinkedList<>();
        target_modifiers = new LinkedList<>();
        moves = new LinkedList<>();
        stages = new LinkedList<>();
        effects = new LinkedList<>();

        moveData = new LinkedList<>();
    }
}
