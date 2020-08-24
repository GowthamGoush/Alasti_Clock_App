package com.example.alasti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class BlankFragment extends Fragment implements MyTimePicker.OnInputSelected {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FloatingActionButton fab1;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private ArrayList<AlarmDetails> Items;
    private int req=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        fab1 = view.findViewById(R.id.fab1);

        loadData();

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        adapter = new Recycler_Adapter(Items,getContext(),getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimePicker dialog = new MyTimePicker();
                dialog.setTargetFragment(BlankFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");
            }
        });
        return view;
    }

    private void startAlarm(Calendar c,int[] checks) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);

        int[] reqCode = new int[7];

        for(int i=0;i<7;i++){
            if(checks[i]==1){

                c.set(Calendar.DAY_OF_WEEK,i+1);
                reqCode[i] = (int) System.currentTimeMillis();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext() ,reqCode[i], intent, 0);
                if (c.before(Calendar.getInstance())) {
                    c.add(Calendar.DATE, 7);
                }
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            }
            else {
                reqCode[i]=0;
            }
        }

        Items.get(Items.size()-1).setRequestCodes(reqCode);
        adapter.notifyDataSetChanged();
        Toast toast = Toast.makeText(getContext(),"Alarm has been set successfully !",Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    public void sendInput(int mHours, int mMinutes, int[] mChecked,int tone) {
        req++;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, mHours);
        c.set(Calendar.MINUTE, mMinutes);
        c.set(Calendar.SECOND, 0);

        if(mMinutes<10){
            Items.add(new AlarmDetails(mHours+":0"+mMinutes,mChecked));
        }
        else {
            Items.add(new AlarmDetails(mHours+":"+mMinutes,mChecked));
        }

        startAlarm(c,mChecked);
    }

    private void saveData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Items);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<AlarmDetails>>() {}.getType();
        Items = gson.fromJson(json, type);
        if (Items == null) {
            Items = new ArrayList<>();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveData();
    }
}