package com.example.alasti;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyTimePicker extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    public interface OnInputSelected{
        void sendInput(int mHours,int mMinutes,int[] mChecked,int tone);
    }
    public OnInputSelected mOnInputSelected;

    //widgets
    private TimePicker timePicker;
    private TextView mActionSign, mActionCancel;
    private CheckBox Sun,Sat,Mon,Tue,Wed,Thurs,Fri;
    private Spinner spinner;
    private int[] checked = new int[7];
    private int hours,minutes,pos,count=0;
    private String[] ringTones = { "Rap", "Classic","Crazy","Iphone","Trumpet"};

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_time_picker, container, false);

        mActionSign = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);
        spinner = view.findViewById(R.id.toneSpinner);

        ArrayAdapter ad = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, ringTones);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pos = 0;
            }
        });

        timePicker = view.findViewById(R.id.timePicker);

        Sun = view.findViewById(R.id.checkSun);
        Sat = view.findViewById(R.id.checkSat);
        Mon = view.findViewById(R.id.checkMon);
        Tue = view.findViewById(R.id.checkTue);
        Wed = view.findViewById(R.id.checkWed);
        Thurs = view.findViewById(R.id.checkThurs);
        Fri = view.findViewById(R.id.checkFri);

        for(int i=0;i<7;i++){
            checked[i] = 1;
        }

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Sun.isChecked()){
                    checked[0]=0;
                }

                if(!Sat.isChecked()){
                    checked[6]=0;
                }

                if(!Mon.isChecked()){
                    checked[1]=0;
                }

                if(!Tue.isChecked()){
                    checked[2]=0;
                }

                if(!Wed.isChecked()){
                    checked[3]=0;
                }

                if(!Thurs.isChecked()){
                    checked[4]=0;
                }

                if(!Fri.isChecked()){
                    checked[5]=0;
                }

                hours = timePicker.getCurrentHour();
                minutes = timePicker.getCurrentMinute();

                for (int i=0;i<7;i++){
                    if(checked[i]==0){
                        count+=1;
                    }
                }

                if(count==7){
                    Toast.makeText(getContext(),"Choose a day !",Toast.LENGTH_SHORT).show();
                    count=0;
                }
                else {
                    mOnInputSelected.sendInput(hours,minutes,checked,pos);
                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

}
