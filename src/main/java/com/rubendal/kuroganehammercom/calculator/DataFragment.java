package com.rubendal.kuroganehammercom.calculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.calculator.adapter.StringSpinnerAdapter;
import com.rubendal.kuroganehammercom.calculator.asynctask.KBRequestAsyncTask;
import com.rubendal.kuroganehammercom.calculator.asynctask.ModifiersAsyncTask;
import com.rubendal.kuroganehammercom.calculator.asynctask.MovesAsyncTask;
import com.rubendal.kuroganehammercom.calculator.classes.*;

public class DataFragment extends Fragment {

    public Data data;
    public APIList apiList;

    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        reloadView(view);

        return view;
    }

    public static DataFragment newInstance(Data data, APIList apiList){
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putSerializable("apiList", apiList);
        fragment.setArguments(args);
        fragment.data = data;
        fragment.apiList = apiList;
        return fragment;
    }

    public void reloadView(final View view){
        if(view!=null) {
            Spinner attacker = (Spinner)view.findViewById(R.id.attackerSpinner);
            Spinner target = (Spinner)view.findViewById(R.id.targetSpinner);
            Spinner effects = (Spinner)view.findViewById(R.id.effectSpinner);

            StringSpinnerAdapter characterAdapter = new StringSpinnerAdapter(getContext(), apiList.characters);
            StringSpinnerAdapter characterAdapter2 = new StringSpinnerAdapter(getContext(), apiList.characters);
            StringSpinnerAdapter effectsAdapter = new StringSpinnerAdapter(getContext(), apiList.effects);

            attacker.setAdapter(characterAdapter);
            target.setAdapter(characterAdapter2);
            effects.setAdapter(effectsAdapter);

            updateAttacks(view);

            attacker.setSelection(apiList.characters.indexOf(data.attacker.character));
            target.setSelection(apiList.characters.indexOf(data.target.character));

            final DataFragment ref = this;
            attacker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String character = (String)adapterView.getItemAtPosition(i);
                    ModifiersAsyncTask m = new ModifiersAsyncTask(ref,character,true);
                    m.execute();
                    MovesAsyncTask moves = new MovesAsyncTask(ref, character);
                    moves.execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            target.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String character = (String)adapterView.getItemAtPosition(i);
                    ModifiersAsyncTask m = new ModifiersAsyncTask(ref,character,false);
                    m.execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            CheckBox smash_attack = (CheckBox)view.findViewById(R.id.smash_attack);
            smash_attack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    RelativeLayout chargeContainer = (RelativeLayout)view.findViewById(R.id.chargeContainer);
                    EditText frames = (EditText)view.findViewById(R.id.frames_charged);
                    frames.setText("0");
                    if(b){
                        chargeContainer.setVisibility(View.VISIBLE);
                    }else{
                        chargeContainer.setVisibility(View.GONE);
                    }
                }
            });


            Button calculate = (Button)view.findViewById(R.id.calculate);

            calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRequest();
                }
            });
        }
    }

    public void updateAttackerModifier(View view){
        if(view != null){
            RelativeLayout container = (RelativeLayout)view.findViewById(R.id.attackerModifierContainer);
            Spinner attackerModifier = (Spinner)view.findViewById(R.id.attackerModifierSpinner);
            attackerModifier.setAdapter(new StringSpinnerAdapter(getContext(), apiList.attacker_modifiers));
            if(apiList.attacker_modifiers.size()>0){
                container.setVisibility(View.VISIBLE);
            }else{
                container.setVisibility(View.GONE);
            }
        }
    }

    public void updateTargetModifier(View view){
        if(view != null){
            RelativeLayout container = (RelativeLayout)view.findViewById(R.id.targetModifierContainer);
            Spinner targetModifier = (Spinner)view.findViewById(R.id.targetModifierSpinner);
            targetModifier.setAdapter(new StringSpinnerAdapter(getContext(), apiList.target_modifiers));
            if(apiList.target_modifiers.size()>0){
                container.setVisibility(View.VISIBLE);
            }else{
                container.setVisibility(View.GONE);
            }
        }
    }

    public void updateAttacks(final View view){
        if(view != null){
            Spinner attack = (Spinner)view.findViewById(R.id.attackSpinner);
            StringSpinnerAdapter movesAdapter = new StringSpinnerAdapter(getContext(), apiList.moves);
            attack.setAdapter(movesAdapter);

            if(apiList.moves.size()>0) {
                data.attack.name = apiList.moves.get(0);
                attack.setSelection(0);
            }

            EditText hit_frame = (EditText)view.findViewById(R.id.hit_frame);
            EditText faf = (EditText)view.findViewById(R.id.faf);

            MoveData md = apiList.moveData.get(0);
            if(md.start_frame != null){
                hit_frame.setText(String.valueOf(md.start_frame));
            }else{
                hit_frame.setText("0");
            }

            if(md.faf != null){
                faf.setText(String.valueOf(md.faf));
            }else{
                faf.setText("0");
            }

            attack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                    EditText hit_frame = (EditText)view.findViewById(R.id.hit_frame);
                    EditText faf = (EditText)view.findViewById(R.id.faf);

                    MoveData md = apiList.moveData.get(i);
                    if(md.start_frame != null){
                        hit_frame.setText(String.valueOf(md.start_frame));
                    }else{
                        hit_frame.setText("0");
                    }

                    if(md.faf != null){
                        faf.setText(String.valueOf(md.faf));
                    }else{
                        faf.setText("0");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    public void sendRequest(){
        View view = getView();

        Spinner attacker = (Spinner)view.findViewById(R.id.attackerSpinner);
        Spinner target = (Spinner)view.findViewById(R.id.targetSpinner);
        Spinner attackerModifier = (Spinner)view.findViewById(R.id.attackerModifierSpinner);
        Spinner targetModifier = (Spinner)view.findViewById(R.id.targetModifierSpinner);
        Spinner attack = (Spinner)view.findViewById(R.id.attackSpinner);
        Spinner effectSpinner = (Spinner)view.findViewById(R.id.effectSpinner);



        EditText frames = (EditText)view.findViewById(R.id.frames_charged);
        EditText hitlag = (EditText)view.findViewById(R.id.hitlag);
        EditText attackerPercent = (EditText)view.findViewById(R.id.attackerPercent);
        EditText targetPercent = (EditText)view.findViewById(R.id.targetPercent);
        EditText hit_frame = (EditText)view.findViewById(R.id.hit_frame);
        EditText faf = (EditText)view.findViewById(R.id.faf);
        EditText di = (EditText)view.findViewById(R.id.di);

        CheckBox smash_attack = (CheckBox)view.findViewById(R.id.smash_attack);
        CheckBox aerial = (CheckBox)view.findViewById(R.id.aerial_opponent);
        CheckBox projectile = (CheckBox)view.findViewById(R.id.projectile);
        CheckBox set_weight = (CheckBox)view.findViewById(R.id.set_weight);
        CheckBox no_di = (CheckBox)view.findViewById(R.id.no_di);
        CheckBox powershield = (CheckBox)view.findViewById(R.id.powershield);
        CheckBox vs_mode = (CheckBox)view.findViewById(R.id.vs_mode);


        data.attacker.character = (String)attacker.getSelectedItem();
        data.attacker.percent = Float.parseFloat(attackerPercent.getText().toString());
        data.target.character = (String)target.getSelectedItem();
        data.target.percent = Float.parseFloat(targetPercent.getText().toString());

        if(attackerModifier.getAdapter()!=null) {
            if (attackerModifier.getAdapter().getCount() == 0) {
                data.attacker.modifier = null;
            } else {
                data.attacker.modifier = (String) attackerModifier.getSelectedItem();
            }
        }else{
            data.attacker.modifier = null;
        }

        if(targetModifier.getAdapter()!=null) {
            if (targetModifier.getAdapter().getCount() == 0) {
                data.target.modifier = null;
            } else {
                data.target.modifier = (String) targetModifier.getSelectedItem();
            }
        }else{
            data.target.modifier = null;
        }

        data.attack.name = (String)attack.getSelectedItem();
        data.attack.hitlag = Float.parseFloat(hitlag.getText().toString());
        data.attack.charged_frames = Integer.parseInt(frames.getText().toString());
        data.attack.is_smash_attack = smash_attack.isChecked();
        data.attack.aerial_opponent = aerial.isChecked();
        data.attack.projectile = projectile.isChecked();

        boolean effectSetWeight = false;

        if(effectSpinner.getAdapter() != null){
            data.attack.effect = (String)effectSpinner.getSelectedItem();
            if(data.attack.effect.equals("Paralyzer")){
                effectSetWeight = true;
            }
        }else{
            data.attack.effect = null;
        }

        data.attack.set_weight = set_weight.isChecked() || effectSetWeight;

        data.shield_advantage.hit_frame = Integer.parseInt(hit_frame.getText().toString());
        if(data.shield_advantage.hit_frame==0){
            data.shield_advantage.hit_frame = null;
            data.shield_advantage.faf = null;
        }
        data.shield_advantage.faf = Integer.parseInt(faf.getText().toString());
        if(data.shield_advantage.faf==0){
            data.shield_advantage.hit_frame = null;
            data.shield_advantage.faf = null;
        }
        data.modifiers.di = Integer.parseInt(di.getText().toString());
        data.modifiers.no_di = no_di.isChecked();
        data.shield_advantage.power_shield = powershield.isChecked();
        data.vs_mode = vs_mode.isChecked();

        RadioButton crouch_cancel = (RadioButton)view.findViewById(R.id.crouch_cancel);
        RadioButton interrupted = (RadioButton)view.findViewById(R.id.interrupted_smash);

        if(crouch_cancel.isChecked()){
            data.modifiers.crouch_cancel = true;
            data.modifiers.interrupted_smash_charge = false;
        }else if(interrupted.isChecked()){
            data.modifiers.crouch_cancel = false;
            data.modifiers.interrupted_smash_charge = true;
        }else{
            data.modifiers.crouch_cancel = false;
            data.modifiers.interrupted_smash_charge = false;
        }

        CalculatorFragment calculatorFragment = (CalculatorFragment)getParentFragment();

        KBRequestAsyncTask r = new KBRequestAsyncTask(calculatorFragment, data, "Sending request");
        r.execute();
    }

}
