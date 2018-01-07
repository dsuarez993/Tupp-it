package com.app.tuppit.ui.user.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.tuppit.R;

/**
 * Created by David on 17/9/16.
 */
public class UserProfileFragmentTab extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v;
        Bundle bundle = getArguments();

        switch(this.getTag()){

            case "user":
                v = inflater.inflate(R.layout.userprofile_fragment_user, container, false);

                TextView description = (TextView) v.findViewById(R.id.desciption_content);
                description.setText(bundle.getString("description"));

                TextView preferences = (TextView) v.findViewById(R.id.preferences_content);

                String [] preferencesData = bundle.getStringArray("preferences");
                String preferencesFinal ="";
                for (int i=0; i<preferencesData.length; i++){
                    if(i!=0)
                        preferencesFinal+=" | ";

                    preferencesFinal+=preferencesData[i];
                }

                preferences.setText(preferencesFinal);
                ImageView kitchen = (ImageView) v.findViewById(R.id.kitchen_image);
                kitchen.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.kitchen));

                break;

            case "chef":
                v = inflater.inflate(R.layout.userprofile_fragment_chef, container, false);
                TextView prueba = (TextView)v.findViewById(R.id.text);
                prueba.setText(bundle.getString("prueba"));
                break;

            case "customer":
                v = inflater.inflate(R.layout.userprofile_fragment_customer, container, false);
                TextView prueba1 = (TextView)v.findViewById(R.id.text);
                prueba1.setText(bundle.getString("prueba"));
                break;

            case "rank":
                v = inflater.inflate(R.layout.userprofile_fragment_rank, container, false);
                TextView prueba2 = (TextView)v.findViewById(R.id.text);
                prueba2.setText(bundle.getString("prueba"));
                break;

            default:
                v = inflater.inflate(R.layout.userprofile_fragment_user, container, false);
        }

        return v;

    }
}
