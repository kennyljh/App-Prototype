package com.example.careerconnect.AccountPersonalizationElements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careerconnect.Global.ButterToast;
import com.example.careerconnect.R;
import com.example.careerconnect.SingletonRepository.IdentifyingDataRepository;
import com.example.careerconnect.SingletonRepository.UserProfile;
import com.example.careerconnect.Volley.VolleyJSONArrayRequests;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBackgroundActivity extends AppCompatActivity {

    private String USERNAME = IdentifyingDataRepository.getInstance().getUserProfile().getUsername();

    private final List<String> months = new ArrayList<>();
    private MonthAdapter monthAdapter;
    private String selectedMonth = "";

    private List<String> days = new ArrayList<>();
    private DayAdapter dayAdapter;
    private String selectedDay = "";

    private List<String> years = new ArrayList<>();
    private YearAdapter yearAdapter;
    private String selectedYear = "";

    private List<String> countriesList = new ArrayList<>();
    private CountryAdapter countryAdapter;
    private String selectedCountry = "";

    private TextView birthDateTxtView;
    private TextView selectedCountryTxtView;
    Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_background);

        birthDateTxtView = findViewById(R.id.birthDate_textView);
        selectedCountryTxtView = findViewById(R.id.selected_country_textView);
        genderSpinner = findViewById(R.id.gender_spinner);

        setupDateSelections();
        birthDateRecyclerViewSetup();
        genderSpinnerSetup();
        countryRecyclerViewSetup();

        // Confirming background details
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {

            String selectedBirthDate = birthDateTxtView.getText().toString();
            String selectedGender = genderSpinner.getSelectedItem().toString();
            String selectedCountry = selectedCountryTxtView.getText().toString();

            if (!checkBackgroundInfo(selectedBirthDate, selectedGender, selectedCountry)) return;

            // saving details to singleton repository
            UserProfile userProfile = IdentifyingDataRepository.getInstance().getUserProfile();
            userProfile.setBirthDate(selectedBirthDate);
            userProfile.setGender(selectedGender);
            userProfile.setCountry(selectedCountry);

            updateAccountBackgroundInfo();
        });
    }

    private boolean checkBackgroundInfo(String selectedBirthDate, String selectedGender,
                                        String selectedCountry){

        if (selectedBirthDate.isEmpty()){
            ButterToast.show(this, "Select birth date", Toast.LENGTH_SHORT);
            return false;
        }
        if (genderSpinner.getSelectedItem().toString().equals("Select Gender")){
            ButterToast.show(this, "Gender option not selected", Toast.LENGTH_SHORT);
            return false;
        }
        if (selectedCountry.isEmpty()){
            ButterToast.show(this, "Select country", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private void updateAccountBackgroundInfo(){

        VolleyJSONObjectRequests.makeVolleyJSONObjectGETRequest(this, LibraryURL.getUserProfileGETRequest() + USERNAME, new VolleyJSONObjectRequests.VolleyJSONObjectCallback() {
            @Override
            public void onResult(boolean result) {

                if (!result){
                    ButterToast.show(getApplicationContext(), "Server is acting slow. Please try again", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onJSONObject(JSONObject jsonObject) {

                if (jsonObject != null){

                    UserProfile userProfile = IdentifyingDataRepository.getInstance().getUserProfile();
                    try{
                        jsonObject.put("birthDate", userProfile.getBirthDate());
                        jsonObject.put("gender", userProfile.getGender());
                        jsonObject.put("country", userProfile.getCountry());
                    } catch (JSONException e){
                        throw new RuntimeException(e);
                    }

                    VolleyJSONObjectRequests.makeVolleyJSONObjectPUTRequest(
                            jsonObject,
                            getApplicationContext(),
                            LibraryURL.getUserProfilePUTRequest() + USERNAME,
                            new VolleyJSONObjectRequests.VolleyCallback() {
                                @Override
                                public void onResult(boolean result) {

                                    if (!result){
                                        ButterToast.show(getApplicationContext(), "Server is acting slow. Please try again", Toast.LENGTH_SHORT);
                                    }
                                    else {
                                        ButterToast.show(getApplicationContext(), "Background information updated", Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(UserBackgroundActivity.this, UserEducationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                    );
                }
            }
        });
    }

    private void birthDateRecyclerViewSetup(){

        // Months selection recycler view
        RecyclerView monthsRecyclerView = findViewById(R.id.months_recyclerView);
        monthsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        monthAdapter = new MonthAdapter(months, this);
        monthsRecyclerView.setAdapter(monthAdapter);

        // Days selection recycler view
        RecyclerView daysRecyclerView = findViewById(R.id.days_recyclerView);
        daysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dayAdapter = new DayAdapter(days, this);
        daysRecyclerView.setAdapter(dayAdapter);

        // Years selection recycler view
        RecyclerView yearsRecyclerView = findViewById(R.id.years_recyclerView);
        yearsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        yearAdapter = new YearAdapter(years, this);
        yearsRecyclerView.setAdapter(yearAdapter);

        // Birth Date confirmation
        Button confirmDateButton = findViewById(R.id.confirm_date_button);
        confirmDateButton.setOnClickListener(v -> {

            String selectedDate = selectedMonth + "/" + selectedDay + "/" + selectedYear;
            if (!isValidDate(selectedDate)){

                ButterToast.show(this, "Invalid date", Toast.LENGTH_SHORT);
            }
            else {

                birthDateTxtView.setText(selectedDate);
                ButterToast.show(this, "Birth date chosen", Toast.LENGTH_SHORT);
            }
        });
    }

    private void genderSpinnerSetup(){

        // Gender selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
    }

    private void countryRecyclerViewSetup(){

        // Retrieving countries
        RecyclerView countryRecyclerView = findViewById(R.id.country_list_recyclerView);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryAdapter = new CountryAdapter(countriesList, this);
        countryRecyclerView.setAdapter(countryAdapter);

        // starter search for countries
        getCountryByPrefix("u");

        Button findCountryButton = findViewById(R.id.find_country_button);
        EditText searchCountryEdtTxt = findViewById(R.id.search_country_edittext);
        findCountryButton.setOnClickListener(v -> {

            getCountryByPrefix(searchCountryEdtTxt.getText().toString());
        });
    }

    private class PlainTextViewHolder extends RecyclerView.ViewHolder{

        LinearLayout plainTextLayout;
        TextView plainTextTxtView;

        public PlainTextViewHolder(View itemView){

            super(itemView);
            plainTextLayout = itemView.findViewById(R.id.plain_text_layout);
            plainTextTxtView = itemView.findViewById(R.id.plain_text_textView);
        }
    }

    private class MonthAdapter extends RecyclerView.Adapter<PlainTextViewHolder>{

        private final List<String> months;
        private final Context context;
        private int lastSelectedPosition = -1;

        private MonthAdapter(List<String> months, Context context){

            this.months = months;
            this.context = context;
        }

        @NonNull
        @Override
        public PlainTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plain_text_layout, parent, false);
            return new PlainTextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlainTextViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String month = months.get(position);

            holder.plainTextLayout.setOnClickListener(v -> {

                if (!selectedMonth.isEmpty()){

                    selectedMonth = month;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
                else {

                    selectedMonth = month;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
            });

            if (selectedMonth.equals(month)){

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.medium_blue));
            }
            else {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.white));
            }
            holder.plainTextTxtView.setText(month);
        }

        @Override
        public int getItemCount() {
            return months.size();
        }
    }

    private class DayAdapter extends RecyclerView.Adapter<PlainTextViewHolder>{

        private final List<String> days;
        private final Context context;
        private int lastSelectedPosition = -1;

        private DayAdapter(List<String> days, Context context){

            this.days = days;
            this.context = context;
        }

        @NonNull
        @Override
        public PlainTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plain_text_layout, parent, false);
            return new PlainTextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlainTextViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String day = days.get(position);

            holder.plainTextLayout.setOnClickListener(v -> {

                if (!selectedDay.isEmpty()){

                    selectedDay = day;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context,  R.color.medium_blue));
                }
                else {

                    selectedDay = day;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context,  R.color.medium_blue));
                }
            });

            if (selectedDay.equals(day)){

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context,  R.color.medium_blue));
            }
            else {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context,  R.color.white));
            }
            holder.plainTextTxtView.setText(day);
        }

        @Override
        public int getItemCount() {
            return days.size();
        }
    }

    private class YearAdapter extends RecyclerView.Adapter<PlainTextViewHolder>{

        private final List<String> years;
        private final Context context;
        private int lastSelectedPosition = -1;

        private YearAdapter(List<String> years, Context context){

            this.years = years;
            this.context = context;
        }

        @NonNull
        @Override
        public PlainTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plain_text_layout, parent, false);
            return new PlainTextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlainTextViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String year = years.get(position);

            holder.plainTextLayout.setOnClickListener(v -> {

                if (!selectedYear.isEmpty()){

                    selectedYear = year;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context,  R.color.medium_blue));
                }
                else {

                    selectedYear = year;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context,  R.color.medium_blue));
                }
            });

            if (selectedYear.equals(year)){

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context,  R.color.medium_blue));
            }
            else {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context,  R.color.white));
            }
            holder.plainTextTxtView.setText(year);
        }

        @Override
        public int getItemCount() {
            return years.size();
        }
    }

    private class CountryAdapter extends RecyclerView.Adapter<PlainTextViewHolder>{

        private final List<String> countriesList;
        private final Context context;
        private int lastSelectedPosition = -1;

        private CountryAdapter(List<String> countriesList, Context context){

            this.countriesList = countriesList;
            this.context = context;
        }

        @NonNull
        @Override
        public PlainTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plain_text_layout, parent, false);
            return new PlainTextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlainTextViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String country = countriesList.get(position);

            holder.plainTextLayout.setOnClickListener(v -> {

                if (!selectedCountry.isEmpty()){

                    selectedCountry = country;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
                else {

                    selectedCountry = country;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
                selectedCountryTxtView.setText(selectedCountry);
            });

            if (selectedCountry.equals(country)){

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.medium_blue));
            }
            else {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.white));
            }
            holder.plainTextTxtView.setText(country);
        }

        @Override
        public int getItemCount() {
            return countriesList.size();
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
        for (int i = Integer.parseInt(currentYear); i >= (Integer.parseInt(currentYear) - 120); i--){
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

    private void getCountryByPrefix(String prefix){

        VolleyJSONArrayRequests.makeVolleyJSONArrayGETRequest(this, LibraryURL.getCountriesGETRequest() + prefix, new VolleyJSONArrayRequests.VolleyJSONArrayCallback() {
            @Override
            public void onResult(boolean result) {

                if (!result){
                    ButterToast.show(getApplicationContext(), "Server is acting slow. Search again to find countries", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onJSONArray(JSONArray jsonArray) {

                if (jsonArray != null){

                    countriesList.clear();
                    countryAdapter.notifyDataSetChanged();
                    for (int i = 0; i < jsonArray.length(); i++){

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            countriesList.add(jsonObject.getString("name"));
                            countryAdapter.notifyItemInserted(i);
                        } catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
