package com.example.careerconnect.LoginSignupElements;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careerconnect.AccountPersonalizationElements.PersonalizeAccountDialogFragment;
import com.example.careerconnect.Global.ButterToast;
import com.example.careerconnect.R;
import com.example.careerconnect.Volley.VolleyJSONArrayRequests;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;
import com.example.careerconnect.Volley.VolleyStringRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows clients to select desired Career Clusters
 */
public class CareerClusterSelectionActivity extends AppCompatActivity {

    private final List<CareerCluster> careerClusterList = new ArrayList<>();
    private List<CareerCluster> selectedCareerClusters = new ArrayList<>();
    private CareerClustersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_cluster_selection);

        Button infoButton = findViewById(R.id.career_cluster_info_btn);
        Button confirmButton = findViewById(R.id.confirm_button);
        RecyclerView careerClusterRecyclerView = findViewById(R.id.career_cluster_recyclerView);

        getCareerClusters();
        careerClusterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CareerClustersAdapter(careerClusterList, getApplicationContext());
        careerClusterRecyclerView.setAdapter(adapter);

        infoButton.setOnClickListener(v -> {

            CareerClusterInfoDialogFragment dialog = new CareerClusterInfoDialogFragment();
            dialog.show(getSupportFragmentManager(), "Information");
        });

        confirmButton.setOnClickListener(v -> {

            sendCareerClusters();

            PersonalizeAccountDialogFragment dialogFragment = new PersonalizeAccountDialogFragment(getApplicationContext());

            //todo
            dialogFragment.setArguments(args);

            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
            finish();
        });
    }

    /**
     * Retrieve list of Career Clusters
     */
    private void getCareerClusters(){

        VolleyJSONArrayRequests.makeVolleyJSONArrayGETRequest(getApplicationContext(), LibraryURL.getCareerClustersGETRequest(), new VolleyJSONArrayRequests.VolleyJSONArrayCallback() {
            @Override
            public void onResult(boolean result) {
                if (!result){
                    ButterToast.show(getApplicationContext(), "Failed to retrieve career clusters", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onJSONArray(JSONArray jsonArray) {

                if (jsonArray != null){
                    JSONArrayToCareerClusters(jsonArray);
                }
            }
        });
    }

    /**
     * Converts a JSONArray to Career Clusters
     * @param jsonArray given JSONArray
     */
    private void JSONArrayToCareerClusters(JSONArray jsonArray){
        try {
            careerClusterList.clear();

            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                int iconResource = 0;

                switch (id){
                    case 1:
                        iconResource = R.drawable.baseline_energy_savings_leaf_24;
                        break;
                    case 2:
                        iconResource = R.drawable.baseline_architecture_24;
                        break;
                    case 3:
                        iconResource = R.drawable.baseline_format_paint_24;
                        break;
                    case 4:
                        iconResource = R.drawable.baseline_business_24;
                        break;
                    case 5:
                        iconResource = R.drawable.baseline_library_books_24;
                        break;
                    case 6:
                        iconResource = R.drawable.baseline_attach_money_24;
                        break;
                    case 7:
                        iconResource = R.drawable.baseline_assured_workload_24;
                        break;
                    case 8:
                        iconResource = R.drawable.baseline_health_and_safety_24;
                        break;
                    case 9:
                        iconResource = R.drawable.baseline_airplane_ticket_24;
                        break;
                    case 10:
                        iconResource = R.drawable.baseline_supervisor_account_24;
                        break;
                    case 11:
                        iconResource = R.drawable.baseline_perm_device_information_24;
                        break;
                    case 12:
                        iconResource = R.drawable.baseline_back_hand_24;
                        break;
                    case 13:
                        iconResource = R.drawable.baseline_hardware_24;
                        break;
                    case 14:
                        iconResource = R.drawable.baseline_auto_graph_24;
                        break;
                    case 15:
                        iconResource = R.drawable.baseline_science_24;
                        break;
                    case 16:
                        iconResource = R.drawable.baseline_emoji_transportation_24;
                        break;
                }
                CareerCluster careerCluster = new CareerCluster(id, name, iconResource);
                careerClusterList.add(careerCluster);
                adapter.notifyItemInserted(i);
            }
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Submit desired list of Career Clusters
     */
    private void sendCareerClusters(){

        if (selectedCareerClusters.isEmpty()){
            ButterToast.show(getApplicationContext(), "Select at least one Career Cluster", Toast.LENGTH_SHORT);
            return;
        }

        VolleyJSONObjectRequests.makeVolleyJSONObjectPOSTRequest(selectedClustersToJSONObject(), getApplicationContext(),LibraryURL.getCareerClustersPOSTRequest() + getIntent().getStringExtra("USERNAME"), new VolleyJSONObjectRequests.VolleyCallback() {
            @Override
            public void onResult(boolean result) {

                if (!result){
                    ButterToast.show(getApplicationContext(), "Failed to update selected career clusters", Toast.LENGTH_SHORT);
                }
                else {
                    ButterToast.show(getApplicationContext(), "Career cluster selection confirmed", Toast.LENGTH_SHORT);
                    //todo
                }
            }
        });
    }

    /**
     * Converts list of selected Career Clusters to JSONObject
     * @return JSONObject of selected Career Clusters
     */
    private JSONObject selectedClustersToJSONObject() {

        JSONArray jsonArray = new JSONArray();

        for (CareerCluster cluster : selectedCareerClusters) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", cluster.getId());
                jsonObject.put("name", cluster.getName());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        JSONObject selectedClusters = new JSONObject();
        try {
            selectedClusters.put("selectedCareerClusters", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return selectedClusters;
    }

    /**
     * Responsible for describing an item and its associated views within a RecyclerView
     */
    private class CareerClustersViewHolder extends RecyclerView.ViewHolder {

        LinearLayout clusterLayout;
        TextView clusterName;
        ImageView clusterIcon;

        public CareerClustersViewHolder(View itemView){

            super(itemView);
            clusterLayout = itemView.findViewById(R.id.career_cluster_layout);
            clusterName = itemView.findViewById(R.id.career_cluster_name_textView);
            clusterIcon = itemView.findViewById(R.id.career_cluster_icon_imageView);
        }
    }

    /**
     * Binds views data to the RecyclerView
     */
    private class CareerClustersAdapter extends RecyclerView.Adapter<CareerClustersViewHolder>{

        private List<CareerCluster> careerClusterList;
        private Context context;

        private CareerClustersAdapter(List<CareerCluster> careerClusterList, Context context){
            this.careerClusterList = careerClusterList;
            this.context = context;
        }

        @NonNull
        @Override
        public CareerClustersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.career_cluster_layout, parent, false);
            return new CareerClustersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CareerClustersViewHolder holder, int position) {

            CareerCluster careerCluster = careerClusterList.get(position);

            if (selectedCareerClusters.contains(careerCluster)) {

                ViewCompat.setBackgroundTintList(holder.clusterLayout,
                        ContextCompat.getColorStateList(context, R.color.medium_blue));
            } else {

                ViewCompat.setBackgroundTintList(holder.clusterLayout,
                        ContextCompat.getColorStateList(context, R.color.white));
            }

            holder.clusterLayout.setOnClickListener(v -> {

                if (!selectedCareerClusters.contains(careerCluster)){

                    if (selectedCareerClusters.size() >= 3){
                        ButterToast.show(getApplicationContext(), "Career clusters limit reached", Toast.LENGTH_SHORT);
                    }
                    else {
                        selectedCareerClusters.add(careerCluster);
                        ViewCompat.setBackgroundTintList(holder.clusterLayout,
                                ContextCompat.getColorStateList(context, R.color.medium_blue));
                    }
                }
                else {

                    selectedCareerClusters.remove(careerCluster);
                    ViewCompat.setBackgroundTintList(holder.clusterLayout,
                            ContextCompat.getColorStateList(context, R.color.white));
                }
            });

            holder.clusterIcon.setImageResource(careerCluster.getIconResource());
            holder.clusterIcon.setColorFilter(ContextCompat.getColor(context, R.color.cobalt_blue), PorterDuff.Mode.SRC_IN);
            holder.clusterName.setText(careerCluster.getName());
        }

        @Override
        public int getItemCount() {
            return careerClusterList.size();
        }
    }

    private Bundle getAccountInfoBundle(){

        Bundle args = new Bundle();

        VolleyStringRequests.makeVolleyStringGETRequest(getApplicationContext(), LibraryURL.getAccountTypeGETRequest() + getIntent().getStringExtra("USERNAME"), new VolleyStringRequests.VolleyStringCallback() {
            @Override
            public void onResult(boolean result) {

                if (!result){
                    ButterToast.show(getApplicationContext(),  "Failed to retrieve account type information", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onString(String string) {

                if (string != null) {
                    
                }
            }
        });

        return args;
    }
}
