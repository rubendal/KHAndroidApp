package com.rubendal.kuroganehammercom.classes;

import android.content.Context;

import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class AttributeRankMaker {

    private static LinkedList<Character> characters = null;

    private static String[] ASC_ATTRIBUTES_SORT = new String[]{
            "AerialJump",
            "AirAcceleration",
            "AirDeceleration",
            "AirFriction",
            "FallSpeed",
            "FullHop",
            "Gravity",
            "LedgeHop",
            "ShortHop",
            "Traction",
            "WalkSpeed",
            "AirSpeed"

    };

    private static boolean usesAsc(String name){
        for(String s : ASC_ATTRIBUTES_SORT){
            if(s.equals(name))
                return true;
        }
        return false;
    }

    private static void Initialize(Context context){
        if(characters != null)
            return;

        characters = new LinkedList<>();
        try {
            String json = Storage.read("data","characters.json",context);
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                characters.add(Character.fromJson(context, jsonArray.getJSONObject(i)));
            }
            Collections.sort(characters, Character.getComparator());
        } catch (Exception e) {
            characters = null;
        }
    }

    private static Character getCharacter(int id){
        if(characters==null)
            return null;

        for(Character character : characters){
            if(character.id == id){
                return character;
            }
        }
        return null;
    }

    public static LinkedList<AttributeRank> buildRankList(Context context, LinkedList<Attribute> list){

        Initialize(context);

        LinkedList<AttributeRank> ranks = new LinkedList<>();

        try {

            if(usesAsc(list.get(0).name)){

                Collections.sort(list, new Comparator<Attribute>() {
                    @Override
                    public int compare(Attribute attribute, Attribute t1) {
                        boolean containsEndFrame = attribute.attributes.get(0).value.contains("-");
                        if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(0).value.split("-")[0]))
                            return 1;
                        if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(0).value.split("-")[0]))
                            return -1;

                        if(containsEndFrame){
                            if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[1]) < Float.parseFloat(t1.attributes.get(0).value.split("-")[1]))
                                return -1;
                            if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[1]) > Float.parseFloat(t1.attributes.get(0).value.split("-")[1]))
                                return 1;
                        }

                        if(attribute.attributes.size()>=2){
                            if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                return 1;
                            if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                return -1;
                        }

                        return 0;
                    }
                });

            }else {

                Collections.sort(list, new Comparator<Attribute>() {
                    @Override
                    public int compare(Attribute attribute, Attribute t1) {
                        boolean containsEndFrame = attribute.attributes.get(0).value.contains("-");
                        if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(0).value.split("-")[0]))
                            return -1;
                        if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(0).value.split("-")[0]))
                            return 1;

                        if(containsEndFrame){
                            if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[1]) < Float.parseFloat(t1.attributes.get(0).value.split("-")[1]))
                                return 1;
                            if (Float.parseFloat(attribute.attributes.get(0).value.split("-")[1]) > Float.parseFloat(t1.attributes.get(0).value.split("-")[1]))
                                return -1;
                        }

                        if(attribute.attributes.size()>=2){
                            if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                return -1;
                            if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                return 1;
                        }

                        return 0;
                    }
                });
            }

            int i=1;
            for(Attribute a : list){
                ranks.add(new AttributeRank(a.name, a, getCharacter(a.ownerId),i));
                i++;
            }

        }catch(Exception e){
            ranks = new LinkedList<>();

            int i=1;
            for(Attribute a : list){
                ranks.add(new AttributeRank(a.name, a, getCharacter(a.ownerId),i));
                i++;
            }
            System.out.println(e.getMessage());
        }

        return ranks;
    }

}
