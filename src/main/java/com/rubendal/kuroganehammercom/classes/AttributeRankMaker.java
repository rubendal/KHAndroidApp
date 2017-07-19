package com.rubendal.kuroganehammercom.classes;

import java.util.Comparator;
import java.util.LinkedList;

public class AttributeRankMaker {

    private static Character getCharacter(LinkedList<Character> characters, int id){
        for(Character character : characters){
            if(character.id == id){
                return character;
            }
        }
        return null;
    }

    public static LinkedList<AttributeRank> buildRankList(LinkedList<Attribute> list){
        LinkedList<AttributeRank> ranks = new LinkedList<>();

        /*list.sort(new Comparator<Attribute>() {
            @Override
            public int compare(Attribute attribute, Attribute t1) {
                if(attribute.attributes.get(0).value) //is a string...
                return 0;
            }
        });
        */

        return ranks;
    }

}
