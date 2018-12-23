package com.rubendal.kuroganehammercom.smash4.classes;


import java.io.Serializable;
import java.util.Comparator;

public enum MoveSort implements Serializable {
    NONE,
    HITBOX_ACTIVE,
    FAF,
    DAMAGE,
    ANGLE,
    BKB,
    KBG;

    public static Comparator<Move> HitboxActiveComparator(){
        return new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                int i1 = -1, i2 = -1;
                try {
                    i1 = Integer.parseInt(o1.hitboxActive.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:", "").split("-")[0].split(",")[0].trim());
                    i2 = Integer.parseInt(o2.hitboxActive.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:", "").split("-")[0].split(",")[0].trim());
                } catch (Exception e) {
                    if (i1 == -1) {
                        try {
                            i2 = Integer.parseInt(o2.hitboxActive.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:", "").split("-")[0].split(",")[0].trim());
                            return -1;
                        } catch (Exception e2) {

                        }
                        return 1;
                    }
                }

                return Integer.valueOf(i1).compareTo(i2);
            }
        };
    }

    public static Comparator<Move> FAFComparator(){
        return new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                int i1 = -1, i2 = -1;
                try{
                    i1 = Integer.parseInt(o1.FAF.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:","").split("-")[0].split(",")[0].trim());
                    i2 = Integer.parseInt(o2.FAF.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:","").split("-")[0].split(",")[0].trim());
                }catch(Exception e){
                    if(i2 == -1){
                        try{
                            i2 = Integer.parseInt(o2.FAF.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:","").split("-")[0].split(",")[0].trim());
                            return -1;
                        }catch(Exception e2){

                        }
                        return 1;
                    }
                }

                return Integer.valueOf(i1).compareTo(i2);
            }
        };
    }

    public static Comparator<Move> DamageComparator(){
        return new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                float i1 = -1, i2 = -1;
                try{
                    i1 = Float.parseFloat(o1.baseDamage.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:","").split("/")[0].split("-")[0].split(",")[0].trim());
                    i2 = Float.parseFloat(o2.baseDamage.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:","").split("/")[0].split("-")[0].split(",")[0].trim());
                }catch(Exception e){
                    if(i2 == -1){
                        try{
                            i2 = Float.parseFloat(o2.baseDamage.replaceAll("[a-zA-Z]|\\?|\\(.+\\)|:","").split("/")[0].split("-")[0].split(",")[0].trim());
                            return 1;
                        }catch(Exception e2){

                        }
                        return -1;
                    }
                }

                return Float.valueOf(i1).compareTo(i2) * -1;
            }
        };
    }

    public static Comparator<Move> AngleComparator(){
        return new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                int i1 = -1, i2 = -1;
                try{
                    i1 = Integer.parseInt(o1.angle.split("-")[0].split("/")[0].split(",")[0].split("\\(")[0].trim());
                    i2 = Integer.parseInt(o2.angle.split("-")[0].split("/")[0].split(",")[0].split("\\(")[0].trim());
                }catch(Exception e){
                    if(i2 == -1){
                        try{
                            i2 = Integer.parseInt(o2.angle.split("-")[0].split("/")[0].split(",")[0].split("\\(")[0].trim());
                            return 1;
                        }catch(Exception e2){

                        }
                        return -1;
                    }
                }

                return Integer.valueOf(i1).compareTo(i2);
            }
        };
    }

    public static Comparator<Move> BKBComparator(){
        return new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                int i1 = -1, i2 = -1;
                try{
                    i1 = Integer.parseInt(o1.bkb.replaceAll("W:","").replaceAll("B:","").split("/")[0].split("-")[0].split(",")[0].split("\\(")[0].trim());
                    i2 = Integer.parseInt(o2.bkb.replaceAll("W:","").replaceAll("B:","").split("/")[0].split("-")[0].split(",")[0].split("\\(")[0].trim());
                }catch(Exception e){
                    if(i2 == -1){
                        try{
                            i2 = Integer.parseInt(o2.bkb.replaceAll("W:","").replaceAll("B:","").split("/")[0].split("-")[0].split(",")[0].split("\\(")[0].trim());
                            return 1;
                        }catch(Exception e2){

                        }
                        return -1;
                    }
                }

                return Integer.valueOf(i1).compareTo(i2) * -1;
            }
        };
    }

    public static Comparator<Move> KBGComparator(){
        return new Comparator<Move>() {
            @Override
            public int compare(Move o1, Move o2) {
                int i1 = -1, i2 = -1;
                try{
                    i1 = Integer.parseInt(o1.kbg.split("/")[0].split("-")[0].split(",")[0].split("\\(")[0].trim());
                    i2 = Integer.parseInt(o2.kbg.split("/")[0].split("-")[0].split(",")[0].split("\\(")[0].trim());
                }catch(Exception e){
                    if(i2 == -1){
                        try{
                            i2 = Integer.parseInt(o2.kbg.split("/")[0].split("-")[0].split(",")[0].split("\\(")[0].trim());
                            return 1;
                        }catch(Exception e2){

                        }
                        return -1;
                    }
                }

                return Integer.valueOf(i1).compareTo(i2) * -1;
            }
        };
    }
}
