package com.rubendal.kuroganehammercom.calculator.classes;

import com.google.gson.Gson;

import java.io.Serializable;

public class KBResponse implements Serializable {

    //Currently supporting these values, I will try to add the rest later

    public Float damage, launch_angle, kb, lsi, launch_speed, horizontal_launch_speed, vertical_launch_speed, gravity_boost, rage;
    public Integer attacker_hitlag, target_hitlag, paralysis_time, hitstun, hitstun_faf, airdodge_hitstun_cancel, aerial_hitstun_cancel, hit_advantage, shield_hitlag, shield_stun, shield_advantage;
    public boolean can_jab_lock, reeling, unblockable;

    public KBResponse(){
        damage = launch_angle = kb = lsi = launch_speed = horizontal_launch_speed = vertical_launch_speed = gravity_boost = 0f;
        rage = 1f;
        attacker_hitlag = target_hitlag = paralysis_time = hitstun_faf = hitstun = aerial_hitstun_cancel = airdodge_hitstun_cancel = hit_advantage = shield_advantage = shield_hitlag = shield_stun = 0;
        can_jab_lock = reeling = unblockable = false;
    }

    public static KBResponse fromJson(String s){
        return new Gson().fromJson(s, KBResponse.class);
    }

    public String getJson(){
        return new Gson().toJson(this);
    }
}
