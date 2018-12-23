package com.rubendal.kuroganehammercom.smash4.asynctask.character;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.NavigationFragment;
import com.rubendal.kuroganehammercom.interfaces.Smash4MainFragment;
import com.rubendal.kuroganehammercom.smash4.adapter.CharacterListAdapter;
import com.rubendal.kuroganehammercom.smash4.fragments.CharacterFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.MainFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.MainListFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.smash4.adapter.CharacterAdapter;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;


public class CharacterAsyncTask extends AsyncTask<String, String, LinkedList<Character>> {

    private Smash4MainFragment context;
    private ProgressDialog dialog;
    private String title;
    private int x;

    public CharacterAsyncTask(Smash4MainFragment context, int x){
        this.context = context;
        this.title = null;
        this.dialog = null;
        this.x = x;
    }

    public CharacterAsyncTask(Smash4MainFragment context, int x, String title){
        this(context, x);
        this.title = title;
        this.dialog = new ProgressDialog(context.getActivity());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(dialog!=null) {
            dialog.setTitle(title);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    protected LinkedList<Character> doInBackground(String... params) {
        try {
            String json = Storage.read("data","characters.json",context.getActivity());
            LinkedList<Character> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Character.fromJson(context.getActivity(), jsonArray.getJSONObject(i)));
            }
            Collections.sort(list, Character.getComparator());
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<Character> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(s==null){
            Toast.makeText(context.getContext(), "An error ocurred while reading character data", Toast.LENGTH_LONG).show();
            return;
        }

        if(s != null) {
            if(context instanceof MainFragment) {
                CharacterAdapter adapter = new CharacterAdapter(context.getActivity(), s, x);
                final MainFragment img_context = (MainFragment)context;
                img_context.grid.setAdapter(adapter);
                if (img_context.selectedItem != -1) {
                    img_context.grid.smoothScrollToPosition(img_context.selectedItem);
                }
                img_context.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        img_context.selectedItem = position;
                        Character character = (Character) parent.getItemAtPosition(position);
                        ((MainActivity) context.getActivity()).loadFragment(CharacterFragment.newInstance(character));
                    }
                });
                img_context.grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Character character = (Character) adapterView.getItemAtPosition(i);
                        ImageView fav = (ImageView) view.findViewById(R.id.fav);
                        if (UserPref.checkCharacterFavorites(character.name)) {
                            UserPref.removeFavoriteCharacter(context.getActivity(), character.name);
                            fav.setVisibility(View.INVISIBLE);
                            character.favorite = false;
                        } else {
                            UserPref.addFavoriteCharacter(context.getActivity(), character.name);
                            fav.setVisibility(View.VISIBLE);
                            character.favorite = true;
                        }
                        return true;
                    }
                });
            }else if(context instanceof MainListFragment){
                final MainListFragment list_context = (MainListFragment)context;
                CharacterListAdapter adapter = new CharacterListAdapter(context.getActivity(), s);
                list_context.list.setAdapter(adapter);
                if (list_context.selectedItem != -1) {
                    list_context.list.smoothScrollToPosition(list_context.selectedItem);
                }
                list_context.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        list_context.selectedItem = position;
                        Character character = (Character) parent.getItemAtPosition(position);
                        ((MainActivity) context.getActivity()).loadFragment(CharacterFragment.newInstance(character));
                    }
                });
                list_context.list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Character character = (Character) adapterView.getItemAtPosition(i);
                        ImageView fav = (ImageView) view.findViewById(R.id.fav);
                        if (UserPref.checkCharacterFavorites(character.name)) {
                            UserPref.removeFavoriteCharacter(context.getActivity(), character.name);
                            fav.setVisibility(View.INVISIBLE);
                            character.favorite = false;
                        } else {
                            UserPref.addFavoriteCharacter(context.getActivity(), character.name);
                            fav.setVisibility(View.VISIBLE);
                            character.favorite = true;
                        }
                        return true;
                    }
                });
            }
        }
    }
}
