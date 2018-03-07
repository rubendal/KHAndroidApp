package com.rubendal.kuroganehammercom.smash4.classes;

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
            "AirFriction",
            "FallSpeed",
            "FullHop",
            "Gravity",
            "LedgeHop",
            "ShortHop",
            "Traction",
            "WalkSpeed",
            "AirSpeed",
            "RunSpeed",
            "ShieldSize",
            "Weight"

    };

    private static String[] FAF_SORT = new String[]{
            "AirDodge",
            "Rolls",
            "Spotdodge"

    };

    private static String[] THIRD_COLUMN_SORT = new String[]{
            "AirAcceleration"

    };

    private static boolean usesAsc(String name){
        for(String s : ASC_ATTRIBUTES_SORT){
            if(s.equals(name))
                return true;
        }
        return false;
    }

    private static boolean usesFAFDesc(String name){
        for(String s : FAF_SORT){
            if(s.equals(name))
                return true;
        }
        return false;
    }

    private static boolean usesThirdColumn(String name){
        for(String s : THIRD_COLUMN_SORT){
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

            final boolean usesFAF = usesFAFDesc(list.get(0).name);
            final boolean thirdColumnSort = usesThirdColumn(list.get(0).name);

            if(thirdColumnSort){
                Collections.sort(list, new Comparator<Attribute>() {
                    @Override
                    public int compare(Attribute attribute, Attribute t1) {

                        String v1 = attribute.attributes.get(2).value.split(" ")[0].replace("?","");
                        String v2 = t1.attributes.get(2).value.split(" ")[0].replace("?","");

                        boolean containsEndFrame = v1.contains("-") && v2.contains("-");

                        try {
                            //Check if value is a number
                            Float.parseFloat(v1.split("-")[0]);
                        } catch (Exception e) {
                            //It's not a number
                            return -1;
                        }

                        try {
                            //Check if value is a number
                            Float.parseFloat(v2.split("-")[0]);
                        } catch (Exception e) {
                            //It's not a number
                            return 1;
                        }

                        if (Float.parseFloat(v1.split("-")[0]) < Float.parseFloat(v2.split("-")[0]))
                            return 1;
                        if (Float.parseFloat(v1.split("-")[0]) > Float.parseFloat(v2.split("-")[0]))
                            return -1;

                        if (containsEndFrame) {

                            try {
                                //Check if value is a number
                                Float.parseFloat(v1.split("-")[1]);
                            } catch (Exception e) {
                                //It's not a number
                                return -1;
                            }

                            try {
                                //Check if value is a number
                                Float.parseFloat(v2.split("-")[1]);
                            } catch (Exception e) {
                                //It's not a number
                                return 1;
                            }

                            if (Float.parseFloat(v1.split("-")[1]) < Float.parseFloat(v2.split("-")[1]))
                                return -1;
                            if (Float.parseFloat(v1.split("-")[1]) > Float.parseFloat(v2.split("-")[1]))
                                return 1;
                        }

                        return 0;
                    }
                });

            }else {

                if (usesAsc(list.get(0).name)) {

                    Collections.sort(list, new Comparator<Attribute>() {
                        @Override
                        public int compare(Attribute attribute, Attribute t1) {

                            String v1 = attribute.attributes.get(0).value.split(" ")[0];
                            String v2 = t1.attributes.get(0).value.split(" ")[0];

                            boolean containsEndFrame = v1.contains("-") && v2.contains("-");

                            try {
                                //Check if value is a number
                                Float.parseFloat(v1.split("-")[0]);
                            } catch (Exception e) {
                                //It's not a number
                                return -1;
                            }

                            try {
                                //Check if value is a number
                                Float.parseFloat(v2.split("-")[0]);
                            } catch (Exception e) {
                                //It's not a number
                                return 1;
                            }

                            if (!usesFAF) {
                                if (Float.parseFloat(v1.split("-")[0]) < Float.parseFloat(v2.split("-")[0]))
                                    return 1;
                                if (Float.parseFloat(v1.split("-")[0]) > Float.parseFloat(v2.split("-")[0]))
                                    return -1;

                                if (containsEndFrame) {

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(v1.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return -1;
                                    }

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(v2.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return 1;
                                    }

                                    if (Float.parseFloat(v1.split("-")[1]) < Float.parseFloat(v2.split("-")[1]))
                                        return -1;
                                    if (Float.parseFloat(v1.split("-")[1]) > Float.parseFloat(v2.split("-")[1]))
                                        return 1;
                                }


                            } else {
                                if (attribute.attributes.size() >= 2) {

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return -1;
                                    }

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(t1.attributes.get(1).value.split("-")[0]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return 1;
                                    }

                                    if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                        return 1;
                                    if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                        return -1;
                                }


                                if (Float.parseFloat(v1.split("-")[0]) < Float.parseFloat(v2.split("-")[0]))
                                    return 1;
                                if (Float.parseFloat(v1.split("-")[0]) > Float.parseFloat(v2.split("-")[0]))
                                    return -1;

                                if (containsEndFrame && (v1.split("-").length > 1 && v2.split("-").length > 1)) {
                                    if (Float.parseFloat(v1.split("-")[1]) < Float.parseFloat(v2.split("-")[1]))
                                        return -1;
                                    if (Float.parseFloat(v1.split("-")[1]) > Float.parseFloat(v2.split("-")[1]))
                                        return 1;
                                }
                            }

                            return 0;
                        }
                    });

                } else {

                    Collections.sort(list, new Comparator<Attribute>() {
                        @Override
                        public int compare(Attribute attribute, Attribute t1) {
                            String v1 = attribute.attributes.get(0).value.split(" ")[0];
                            String v2 = t1.attributes.get(0).value.split(" ")[0];

                            boolean containsEndFrame = v1.contains("-") && v2.contains("-");

                            try {
                                //Check if value is a number
                                Float.parseFloat(v1.split("-")[0]);
                            } catch (Exception e) {
                                //It's not a number
                                return 1;
                            }

                            try {
                                //Check if value is a number
                                Float.parseFloat(v2.split("-")[0]);
                            } catch (Exception e) {
                                //It's not a number
                                return -1;
                            }

                            if (!usesFAF) {
                                if (Float.parseFloat(v1.split("-")[0]) < Float.parseFloat(v2.split("-")[0]))
                                    return -1;
                                if (Float.parseFloat(v1.split("-")[0]) > Float.parseFloat(v2.split("-")[0]))
                                    return 1;

                                if (containsEndFrame && (v1.split("-").length > 1 && v2.split("-").length > 1)) {

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(v1.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return 1;
                                    }

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(v2.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return -1;
                                    }

                                    if (Float.parseFloat(v1.split("-")[1]) < Float.parseFloat(v2.split("-")[1]))
                                        return 1;
                                    if (Float.parseFloat(v1.split("-")[1]) > Float.parseFloat(v2.split("-")[1]))
                                        return -1;
                                }

                                if (attribute.attributes.size() >= 2) {

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(attribute.attributes.get(1).value.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return 1;
                                    }

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(t1.attributes.get(1).value.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return -1;
                                    }

                                    if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                        return -1;
                                    if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                        return 1;
                                }
                            } else {
                                if (attribute.attributes.size() >= 2) {

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return 1;
                                    }

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(t1.attributes.get(1).value.split("-")[0]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return -1;
                                    }

                                    if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) < Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                        return -1;
                                    if (Float.parseFloat(attribute.attributes.get(1).value.split("-")[0]) > Float.parseFloat(t1.attributes.get(1).value.split("-")[0]))
                                        return 1;
                                }

                                if (Float.parseFloat(v1.split("-")[0]) < Float.parseFloat(v2.split("-")[0]))
                                    return -1;
                                if (Float.parseFloat(v1.split("-")[0]) > Float.parseFloat(v2.split("-")[0]))
                                    return 1;

                                if (containsEndFrame && (v1.split("-").length > 1 && v2.split("-").length > 1)) {

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(v1.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return 1;
                                    }

                                    try {
                                        //Check if value is a number
                                        Float.parseFloat(v2.split("-")[1]);
                                    } catch (Exception e) {
                                        //It's not a number
                                        return -1;
                                    }

                                    if (Float.parseFloat(v1.split("-")[1]) < Float.parseFloat(v2.split("-")[1]))
                                        return 1;
                                    if (Float.parseFloat(v1.split("-")[1]) > Float.parseFloat(v2.split("-")[1]))
                                        return -1;
                                }


                            }

                            return 0;
                        }
                    });
                }
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
            e.printStackTrace();
        }

        return ranks;
    }

}
