package com.rubendal.kuroganehammercom.classes;

import java.io.Serializable;
import java.util.LinkedList;

public class AttributeRankWrapper implements Serializable {
    public LinkedList<String> valueTypes = new LinkedList<>();
    public LinkedList<AttributeRank> attributes = new LinkedList<>();

    public AttributeRankWrapper(LinkedList<AttributeRank> attributes, LinkedList<String> valueTypes){
        this.valueTypes = valueTypes;
        this.attributes = attributes;
    }
}
