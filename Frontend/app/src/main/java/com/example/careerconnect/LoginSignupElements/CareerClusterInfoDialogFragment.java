package com.example.careerconnect.LoginSignupElements;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import com.example.careerconnect.R;

/**
 * Dialog fragment for displaying Career Cluster selection information
 */
public class CareerClusterInfoDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.career_cluster_info_layout,
                                    container, false);
    }
}
