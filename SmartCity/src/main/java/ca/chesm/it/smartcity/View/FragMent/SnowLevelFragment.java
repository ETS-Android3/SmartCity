
package ca.chesm.it.smartcity.View.FragMent;
//        Name: Hieu Chu N01371619
//        Course: CENG322-RND
//        Purpose: Control Snow level on street
//        Last updated: Sep 27 2021
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ca.chesm.it.smartcity.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SnowLevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SnowLevelFragment extends Fragment {
    View v;

    TextView snow_alert;
    Button snow_alertBtn;

    Spinner snow_location;
    TextView snow_level;

    CircleImageView snow_weather_image;

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

        //Blinking effect for Alert button
        snow_alert = (TextView) v.findViewById(R.id.Snow_Alert);
        snow_alertBtn = (Button) v.findViewById(R.id.Snow_AlertBtn);
        snow_alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlinkEffect();
            }
        });

        //Change weather image based on weather
        snow_weather_image = v.findViewById(R.id.snowLevelImage);

        //Spinner location
        snow_location = v.findViewById(R.id.Snow_currentlocation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.snow_locationarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snow_location.setAdapter(adapter);

        timer.schedule(snowlvTask, 10, 5000);

        //Image View change due to the change in snow level
        snow_weather_image= v.findViewById(R.id.snowLevelImage);
        String snow_result = snowlvCheck();
        switch (snow_result)
        {
            case "low":
                snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                break;
            case "medium":
                snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.snowmedium));
                break;
            case "high":
                snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.snowheavy));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + snow_result);
        }


        //Returns
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



    //Snow level checking level
    public String snowlvCheck() {
        try {
            int value = 1;
            if (value >= 0 && value < 8) {
                return "low";
            } else if (value >= 8 && value < 16) {
                return "medium";
            } else if (value > 16 && value <= 25) {
                return "high";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Not in range";
    }


    class randNoGenerator extends TimerTask {
        public void run() {
            //Do something here please I am stupid
                int min = 0;
                int max = 25;
                int radNum = (int)(Math.random() * (double)(max - min + 1) + (double)min);

            //Snow level simulator
            snow_level = v.findViewById(R.id.snow_level);
            snow_level.setText(radNum + " " + getString(R.string.unitCM));

        }
    }
    //Task timer
    randNoGenerator snowlvTask = new randNoGenerator();
    Timer timer = new Timer();

}