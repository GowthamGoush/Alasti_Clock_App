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



/*
    private void startAlarm(Calendar c,int[] checks,int tone) {

        int[] reqCode = new int[7];

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        intent.putExtra("Value",tone);

        Calendar calendar;
        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        for(int i=0;i<7;i++){                       // Neither For loop nor below if conditions seem to set alarm for set of days
            if(checks[i]==1){                           // Infact when alarm is set for more than a day , the first set day overlapped the next one

                calendar = c;
                calendar.set(Calendar.DAY_OF_WEEK,i+1);

                if(calendar.before(now)) {
                    calendar.add(Calendar.DATE, 7);
                }

                reqCode[i] = (int) System.currentTimeMillis();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext() ,reqCode[i], intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
            else {
                reqCode[i]=0;
            }
        }

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        if(checks[0]==1) {
            Calendar calendar1 = c;
            calendar1.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

            if(calendar1.before(now)) {
                calendar1.add(Calendar.DATE, 7);
            }
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[0] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[0], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[0]=0;
        }

        if(checks[1]==1) {
            Calendar calendar2 = c;
            calendar2.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

            if(calendar2.before(now)) {
                calendar2.add(Calendar.DATE, 7);
            }
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[1] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[1], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[1]=0;
        }

        if(checks[2]==1) {
            Calendar calendar3 = c;
            calendar3.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);

            if(calendar3.before(now)) {
                calendar3.add(Calendar.DATE, 7);
            }

            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[2] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[2], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[2]=0;
        }

        if(checks[3]==1) {
            Calendar calendar4 = c;
            calendar4.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);

            if(calendar4.before(now)) {
                calendar4.add(Calendar.DATE, 7);
            }

            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[3] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[3], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar4.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[3]=0;
        }

        if(checks[4]==1) {
            Calendar calendar5 = c;
            calendar5.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);

            if(calendar5.before(now)) {
                calendar5.add(Calendar.DATE, 7);
            }

            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[4] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[4], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar5.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[4]=0;
        }

        if(checks[5]==1) {
            Calendar calendar6 = c;
            calendar6.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);

            if(calendar6.before(now)) {
                calendar6.add(Calendar.DATE, 7);
            }

            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[5] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[5], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar6.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[5]=0;
        }

        if(checks[6]==1) {
            Calendar calendar7 = c;
            calendar7.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);

            if(calendar7.before(now)) {
                calendar7.add(Calendar.DATE, 7);
            }

            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            intent.putExtra("Value",tone);

            reqCode[6] = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), reqCode[6], intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar7.getTimeInMillis(), pendingIntent);
        }
        else {
            reqCode[6]=0;
        }


        Items.get(Items.size()-1).setRequestCodes(reqCode);
        Toast toast = Toast.makeText(getContext(),"Alarm has been set successfully !",Toast.LENGTH_SHORT);
        toast.show();
    }
*/


    @Override
    public void sendInput(int mHours, int mMinutes, int[] mChecked,int tone) {
        req++;

        //int[] checked = mChecked;

        //startAlarm(c,checked,tone);

        int[] reqCode = new int[7];

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        intent.putExtra("Value",tone);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        for(int i=0;i<7;i++){
            if(mChecked[i]==1){

                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,i+1);
                c.set(Calendar.HOUR_OF_DAY, mHours);
                c.set(Calendar.MINUTE, mMinutes);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);

                if(c.before(now)) {
                    c.add(Calendar.DATE, 7);
                }

                reqCode[i] = (int) System.currentTimeMillis();
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext() ,reqCode[i], intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            }
            else {
                reqCode[i]=0;
            }
        }

        if(mMinutes<10){
            Items.add(new AlarmDetails(mHours+":0"+mMinutes,reqCode,mHours,mMinutes,tone,3));
        }
        else {
            Items.add(new AlarmDetails(mHours+":"+mMinutes,reqCode,mHours,mMinutes,tone,3));
        }

        adapter.notifyDataSetChanged();
        Toast toast = Toast.makeText(getContext(),"Alarm has been set successfully !",Toast.LENGTH_SHORT);
        toast.show();
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