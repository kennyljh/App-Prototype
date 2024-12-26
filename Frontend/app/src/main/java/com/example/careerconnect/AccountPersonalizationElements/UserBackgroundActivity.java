package com.example.careerconnect.AccountPersonalizationElements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careerconnect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBackgroundActivity extends AppCompatActivity {

    private final List<String> months = new ArrayList<>();
    private MonthAdapter monthAdapter;

    private List<String> days = new ArrayList<>();
    private List<String> years = new ArrayList<>();

    private String selectedMonth = "";
    private String selectedDay = "";
    private String selectedYear = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_background);

        setupDateSelections();
        RecyclerView monthsRecyclerView = findViewById(R.id.months_recyclerView);
        monthsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        monthAdapter = new MonthAdapter(months, getApplicationContext());
        monthsRecyclerView.setAdapter(monthAdapter);
    }

    private class DateViewHolder extends RecyclerView.ViewHolder{

        LinearLayout dateLayout;
        TextView dateTxtView;

        public DateViewHolder(View itemView){

            super(itemView);
            dateLayout = itemView.findViewById(R.id.date_layout);
            dateTxtView = itemView.findViewById(R.id.date_textView);
        }
    }

    private class MonthAdapter extends RecyclerView.Adapter<DateViewHolder>{

        private List<String> months;
        private Context context;
        private int lastSelectedPosition = -1;

        private MonthAdapter(List<String> months, Context context){

            this.months = months;
            this.context = context;
        }

        @NonNull
        @Override
        public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_layout, parent, false);
            return new DateViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DateViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String month = months.get(position);

            holder.dateLayout.setOnClickListener(v -> {

                if (!selectedMonth.isEmpty()){

                    selectedMonth = month;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.dateLayout,
                            ContextCompat.getColorStateList(context,  R.color.medium_blue));
                }
                else {
                    selectedMonth = month;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.dateLayout,
                            ContextCompat.getColorStateList(context,  R.color.medium_blue));
                }
                Log.d("SELECTED MONTH", selectedMonth);
            });

            if (selectedMonth.equals(month)){

                ViewCompat.setBackgroundTintList(holder.dateLayout,
                        ContextCompat.getColorStateList(context,  R.color.medium_blue));
            }
            else {

                ViewCompat.setBackgroundTintList(holder.dateLayout,
                        ContextCompat.getColorStateList(context,  R.color.white));
            }

            holder.dateTxtView.setText(month);
        }

        @Override
        public int getItemCount() {
            return months.size();
        }
    }

    private void setupDateSelections(){

        for (int i = 1; i <= 12; i++){
            months.add(String.valueOf(i));
        }

        for (int i = 1; i <= 31; i++){
            days.add(String.valueOf(i));
        }

        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        for (int i = (Integer.parseInt(currentYear) - 150); i <= Integer.parseInt(currentYear); i++){
            years.add(String.valueOf(i));
        }
    }

    private boolean isValidDate(String dateToCheck){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);

        try {

            Date date = simpleDateFormat.parse(dateToCheck);
            return true;
        } catch (ParseException e){
            return false;
        }
    }






















}
