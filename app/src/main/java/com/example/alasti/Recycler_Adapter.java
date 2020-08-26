package com.example.alasti;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import static java.security.AccessController.getContext;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.Frag_Viewholder> {

    ArrayList<AlarmDetails> Frag1List;
    Context mContext;
    Activity mActivity;

    public class Frag_Viewholder extends RecyclerView.ViewHolder {

        public TextView textView1, sun, mon, tue, wed, thur, fri, sat;
        public RelativeLayout expandableLayout;
        public CardView cardView;
        public ImageView imageView;
        public Switch aSwitch;

        public Frag_Viewholder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.content_text1);

            sun = itemView.findViewById(R.id.sunday);
            mon = itemView.findViewById(R.id.monday);
            tue = itemView.findViewById(R.id.tuesday);
            wed = itemView.findViewById(R.id.wednesday);
            thur = itemView.findViewById(R.id.thursday);
            fri = itemView.findViewById(R.id.friday);
            sat = itemView.findViewById(R.id.saturday);

            aSwitch = itemView.findViewById(R.id.alarmOnOff);

            expandableLayout = itemView.findViewById(R.id.fragment1_content);
            cardView = itemView.findViewById(R.id.alarm_card);
            imageView = itemView.findViewById(R.id.frag_image);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmDetails item = Frag1List.get(getAdapterPosition());
                    item.setExpanded(!item.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(mActivity, AlertReceiver.class);
                    for (int i = 0; i < 7; i++) {
                        int[] requests = Frag1List.get(getAdapterPosition()).getRequestCodes();
                        if (requests[i] != 0) {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requests[i], intent, 0);
                            assert alarmManager != null;
                            alarmManager.cancel(pendingIntent);
                        }
                    }
                    Frag1List.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

        }
    }

    public Recycler_Adapter(ArrayList<AlarmDetails> List, Context context, Activity activity) {
        Frag1List = List;
        mContext = context;
        mActivity = activity;
    }

    @NonNull
    @Override
    public Frag_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_cardview, parent, false);
        Frag_Viewholder fvh = new Frag_Viewholder(v);
        return fvh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final Frag_Viewholder holder, final int position) {

        AlarmDetails Item = Frag1List.get(position);

        holder.textView1.setText(Item.getAlarmTime());

        boolean isExpanded = Frag1List.get(position).getExpanded();

        int[] checking = Item.getRequestCodes();

        if (checking[0] != 0) {
            holder.sun.setTextColor(Color.CYAN);
        }
        if (checking[1] != 0) {
            holder.mon.setTextColor(Color.CYAN);
        }
        if (checking[2] != 0) {
            holder.tue.setTextColor(Color.CYAN);
        }
        if (checking[3] != 0) {
            holder.wed.setTextColor(Color.CYAN);
        }
        if (checking[4] != 0) {
            holder.thur.setTextColor(Color.CYAN);
        }
        if (checking[5] != 0) {
            holder.fri.setTextColor(Color.CYAN);
        }
        if (checking[6] != 0) {
            holder.sat.setTextColor(Color.CYAN);
        }

        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.aSwitch.setVisibility(isExpanded ? View.GONE : View.VISIBLE);

        int count = 0;
        int isSwitched;

        isSwitched = Frag1List.get(position).getSwitched();

        if (isSwitched == 0 && count == 0){
            holder.aSwitch.setChecked(false);
            count = 1;
        }

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Calendar now = Calendar.getInstance();
                    now.set(Calendar.SECOND, 0);
                    now.set(Calendar.MILLISECOND, 0);

                    for (int i = 0; i < 7; i++) {
                        int[] requests = Frag1List.get(position).getRequestCodes();
                        if (requests[i] != 0) {

                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.DAY_OF_WEEK,i+1);
                            c.set(Calendar.HOUR_OF_DAY, Frag1List.get(position).getHours());
                            c.set(Calendar.MINUTE,Frag1List.get(position).getMinutes());
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);

                            if(c.before(now)) {
                                c.add(Calendar.DATE, 7);
                            }

                            AlarmManager alarmManager = (AlarmManager) mActivity.getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(mActivity, AlertReceiver.class);
                            intent.putExtra("Value",Frag1List.get(position).getTone());

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requests[i], intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                        }
                    }
                    Frag1List.get(position).setSwitched(1);
                    Toast toast = Toast.makeText(mContext, "Alarm ON", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(mContext, "Alarm OFF", Toast.LENGTH_SHORT);
                    toast.show();
                    AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(mActivity, AlertReceiver.class);
                    for (int i = 0; i < 7; i++) {
                        int[] requests = Frag1List.get(position).getRequestCodes();
                        if (requests[i] != 0) {
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requests[i], intent, 0);
                            assert alarmManager != null;
                            alarmManager.cancel(pendingIntent);
                        }
                    }
                    Frag1List.get(position).setSwitched(0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Frag1List.size();
    }

}
