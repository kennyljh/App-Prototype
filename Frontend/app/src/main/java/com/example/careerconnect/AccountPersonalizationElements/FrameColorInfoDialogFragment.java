package com.example.careerconnect.AccountPersonalizationElements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.careerconnect.R;

public class FrameColorInfoDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.picture_frame_info_layout,
                container, false);
    }
}
