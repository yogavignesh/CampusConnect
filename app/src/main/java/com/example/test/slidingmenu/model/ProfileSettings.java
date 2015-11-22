package com.example.test.slidingmenu.model;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.test.campusconnect.R;

public class ProfileSettings extends android.support.v4.app.Fragment {

    public ProfileSettings(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        // RadioButoon yes tutor
        RadioButton rIsTutor =(RadioButton) rootView.findViewById(R.id.rUpYes);
        RadioButton rIsNotTutor =(RadioButton)rootView.findViewById(R.id.rUpNo);
        rIsTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //       Toast.makeText(TouchpointmockupsActivity.this, "test", Toast.LENGTH_SHORT).show();
                LinearLayout IsTutor = (LinearLayout) rootView.findViewById(R.id.isUpTutor);
                IsTutor.setVisibility(View.VISIBLE);


            }
        });
        rIsNotTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                //       Toast.makeText(TouchpointmockupsActivity.this, "test", Toast.LENGTH_SHORT).show();
                LinearLayout IsTutor = (LinearLayout) rootView.findViewById(R.id.isUpTutor);
                IsTutor.setVisibility(View.GONE);
                clearCheckbox(rootView);
            }
        });
        return rootView;
    }
    public void clearCheckbox(View v){

       CheckBox cbAerospace =  (CheckBox) v.findViewById(R.id.cbUAerospace);
        CheckBox cbBiotechnology = (CheckBox) v.findViewById(R.id.cbUBiotechnology);
        CheckBox cbBusiness = (CheckBox) v.findViewById(R.id.cbUBusiness);
        CheckBox cbComputerScience = (CheckBox) v.findViewById(R.id.cbUComputerScience);
        CheckBox cbElectrical = (CheckBox) v.findViewById(R.id.cbUElectrical);
        CheckBox cbIndustrialEngineering = (CheckBox) v.findViewById(R.id.cbUIndustrial);
        CheckBox cbMechanical = (CheckBox) v.findViewById(R.id.cbUMechanical);
        CheckBox cbMathematics = (CheckBox) v.findViewById(R.id.cbUMaths);
        CheckBox cbNursing = (CheckBox) v.findViewById(R.id.cbUNursing);
        CheckBox cbPsychology = (CheckBox) v.findViewById(R.id.cbUPsychology);
        CheckBox cbPhysics = (CheckBox) v.findViewById(R.id.cbUPhysics);

        cbAerospace.setChecked(false);
        cbBiotechnology.setChecked(false);
        cbBusiness.setChecked(false);
        cbComputerScience.setChecked(false);
        cbElectrical.setChecked(false);
        cbIndustrialEngineering.setChecked(false);
        cbMechanical.setChecked(false);
        cbMathematics.setChecked(false);
        cbNursing.setChecked(false);
        cbPsychology.setChecked(false);
        cbPhysics.setChecked(false);


    }
}
