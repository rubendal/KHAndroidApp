package com.rubendal.kuroganehammercom.smash4.classes;

import java.io.Serializable;


public enum MoveType implements Serializable {
    Aerial(0),
    Ground(1),
    Special(2),
    Throw(3),
    Evasion(4),
    Any(-1);

    public int value;

    MoveType(int value){
        this.value = value;
    }

    public static MoveType fromValue(int value){
        switch(value){
            case 1:
                return Ground;
            case 2:
                return Special;
            case 3:
                return Throw;
            case 4:
                return Evasion;
            case -1:
                return Any;
            default:
                return Aerial;
        }
    }

    public static MoveType fromValue(String value){
        switch(value){
            case "ground":
                return Ground;
            case "special":
                return Special;
            case "throw":
                return Throw;
            case "evasion":
                return Evasion;
            default:
                return Aerial;
        }
    }
}
