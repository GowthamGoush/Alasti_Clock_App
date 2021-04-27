package com.example.alasti.Fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alasti.R;

import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class TimerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final long START_TIME_IN_MILLIS = 600000;

    private String mParam1;
    private String mParam2;

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private boolean running,resume;
    private CountDownTimer countDownTimer;
    private TextView countText;
    private ImageButton startPause,reset,mButtonSet;
    private Animation animation;
    private ImageView imageView;
    private EditText mEditTextInput;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private ObjectAnimator anim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_blank2, container, false);

        countText = view.findViewById(R.id.timerCount);
        startPause = view.findViewById(R.id.startBtn);
        reset = view.findViewById(R.id.resetBtn);
        imageView = view.findViewById(R.id.anchor);
        mEditTextInput = view.findViewById(R.id.edit_text_input);
        mButtonSet = view.findViewById(R.id.button_set);

        startPause.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.anchor_rotation);

        anim = ObjectAnimator.ofFloat(imageView, "rotation", 0, 360);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.setInterpolator(new LinearInterpolator());

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                mEditTextInput.setText("");
                startPause.setVisibility(View.VISIBLE);
            }
        });

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    startTimer();
                    mEditTextInput.setVisibility(View.GONE);
                    mButtonSet.setVisibility(View.GONE);
                    startPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    if(reset.getVisibility() == View.VISIBLE){
                        reset.setVisibility(View.GONE);
                    }
                }
                else if (running) {
                    pauseTimer();
                    reset.setVisibility(View.VISIBLE);
                    reset.animate().translationY(130).setDuration(300).start();
                    resume = true;
                    startPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                reset.setVisibility(View.GONE);
                mEditTextInput.setVisibility(View.VISIBLE);
                mButtonSet.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void setTime(long milliseconds) {
        anim.setDuration(milliseconds);
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                running = false;
                resume = false;
                startPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_start));
                reset.setVisibility(View.GONE);
                mEditTextInput.setVisibility(View.VISIBLE);
                mButtonSet.setVisibility(View.VISIBLE);
                startPause.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Time is Up !!", Toast.LENGTH_SHORT).show();
            }
        }.start();
        if(!resume){
            anim.start();
        }
        else {
            anim.resume();
            resume = false;
        }
        running = true;
    }
    private void pauseTimer() {
        countDownTimer.cancel();
        anim.pause();
        running = false;
    }
    private void resetTimer() {
        anim.end();
        resume = false;
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        countText.setText(timeLeftFormatted);
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}