
package ca.chesm.it.smartcity;
//        Name: Hieu Chu N01371619
//        Course: CENG322-RND
//        Purpose: Control Snow level on street
//        Last updated: Sep 27 2021
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SnowLevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SnowLevelFragment extends Fragment {

    TextView snow_alert;
    Button snow_alertBtn;
    View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SnowLevelFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SnowLevelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SnowLevelFragment newInstance(String param1, String param2) {
        SnowLevelFragment fragment = new SnowLevelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_snow_level, container, false);

        snow_alert = (TextView) v.findViewById(R.id.Snow_Alert);
        snow_alertBtn = (Button) v.findViewById(R.id.Snow_AlertBtn);
        snow_alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlinkEffect();
            }
        });

        return v;
    }

    @SuppressLint("WrongConstant")
    public void BlinkEffect(){
        ObjectAnimator anim = ObjectAnimator.ofInt(snow_alert, "backgroundColor", Color.WHITE, Color.RED, Color.WHITE);
        anim.setDuration(800);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }
}