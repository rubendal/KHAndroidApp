package com.rubendal.kuroganehammercom.smash4.calculator;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.*;
import com.rubendal.kuroganehammercom.interfaces.NavigationFragment;

public class CalculatorFragment extends NavigationFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public Data data;
    public APIList apiList;
    public KBResponse kbResponse = new KBResponse();

    public CalculatorFragment() {

    }

    @Override
    public void updateData() {
        //Do nothing, syncing with KH doesn't affect the calculator
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.data = (Data)getArguments().getSerializable("data");
            this.apiList = (APIList)getArguments().getSerializable("apiList");
            this.kbResponse = (KBResponse)getArguments().getSerializable("kbResponse");
        }
    }

    @Override
    public String getTitle() {
        return "Sm4sh Calculator";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static CalculatorFragment newInstance(Data data, APIList apiList, KBResponse kbResponse){
       CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putSerializable("apiList", apiList);
        args.putSerializable("kbResponse", kbResponse);
        fragment.setArguments(args);
        fragment.data = data;
        fragment.apiList = apiList;
        fragment.kbResponse = kbResponse;
        return fragment;
    }


    public void displayResults(KBResponse response){
        this.kbResponse = response;
        ((PagerAdapter)viewPager.getAdapter()).updateResultFragment(response);
        viewPager.setCurrentItem(1);
        tabLayout.setScrollPosition(1,0,true);
    }


    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        public void updateResultFragment(KBResponse response){
            ((ResultFragment)getItem(1)).setResponse(response);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Data";
                case 1:
                    return "Results";
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            if(object instanceof ResultFragment){
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return DataFragment.newInstance(data,apiList);
                case 1:
                    return ResultFragment.newInstance(kbResponse);
            }
            return null;
        }
    }
}
