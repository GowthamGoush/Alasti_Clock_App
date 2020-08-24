package com.example.alasti;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BlankFragment3() {
        // Required empty public constructor
    }

    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
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

    private Animation animation;
    private boolean running;
    private long pauseOffset;
    private Chronometer chronometer;
    private ImageButton startPause, stopLap;
    private Handler handler;
    private long  tBuff,tMilli,tStart,tUpdate = 0L;
    int sec,min,milliSec;
    private ListView listView;
    private List<String> splitList;
    private ArrayAdapter adapter;
    private int count =1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank3, container, false);

        startPause = view.findViewById(R.id.startWatch);
        stopLap = view.findViewById(R.id.stopWatch);
        chronometer = view.findViewById(R.id.counter);
        listView = view.findViewById(R.id.splitList);

        splitList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,splitList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        listView.setAdapter(adapter);

        handler = new Handler();

        stopLap.setAlpha(0f);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.anchor_rotation);

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!running) {

                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();

                    startPause.animate().translationX(70).setDuration(300).start();
                    stopLap.animate().alpha(1).translationX(-70).setDuration(300).start();

                    startPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    stopLap.setImageDrawable(getResources().getDrawable(R.drawable.ic_lap));

                    running = true;
                }
                else if (running) {

                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                    startPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));
                    stopLap.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));

                }
            }
        });

        stopLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running) {

                    splitList.clear();
                    adapter.notifyDataSetChanged();

                    chronometer.animate().translationY(0).setDuration(500).start();

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    pauseOffset = 0;

                    startPause.animate().translationX(0).setDuration(300).start();
                    stopLap.animate().alpha(0).translationX(0).setDuration(300).start();

                    count = 1;
                }
                else if(running){
                    chronometer.animate().translationY(-150).setDuration(500).start();

                    splitList.add(count+"                "+chronometer.getText().toString());
                    adapter.notifyDataSetChanged();

                    count += 1;
                }
            }
        });

        return view;
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
                tMilli = SystemClock.uptimeMillis()-tStart;
                tUpdate = tBuff + tMilli;
                sec = (int) (tUpdate/1000);
                min = sec / 60;
                sec = sec % 60;
                milliSec = (int) (tUpdate%100);
                chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",milliSec));
                handler.postDelayed(this,100);
        }
    };

    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase() - 179;
        int elapsed = (int) elapsedMillis/100;
        Toast toast = Toast.makeText(getContext(),"Elapsed Milliseconds : "+elapsedMillis,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,300);
        toast.show();
    }
}