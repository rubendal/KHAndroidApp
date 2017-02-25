package com.rubendal.kuroganehammercom.classes;

import java.io.Serializable;


public enum MoveType implements Serializable {
    Aerial(0),
    Ground(1),
    Special(2),
    Throw(3),
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
            default:
                return Aerial;
        }
    }
}
