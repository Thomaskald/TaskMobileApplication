package com.example.taskly.list;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskly.R;

public class MyDataViewHolder extends RecyclerView.ViewHolder {

    TextView data_shortName_textView;
    TextView data_taskId_textView;
    TextView data_description_textView;
    TextView data_startTime_textView;
    TextView data_duration_textView;
    TextView data_location_textView;
    View view;

    public MyDataViewHolder(@NonNull View itemView) {
        super(itemView);
        data_shortName_textView = itemView.findViewById(R.id.data_shortName);
        data_taskId_textView = itemView.findViewById(R.id.data_taskId);
        data_description_textView = itemView.findViewById(R.id.data_description);
        data_startTime_textView = itemView.findViewById(R.id.data_startTime);
        data_duration_textView = itemView.findViewById(R.id.data_duration);
        data_location_textView = itemView.findViewById(R.id.data_location);
        view = itemView;
    }

}
