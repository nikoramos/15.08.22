package com.example.checksensoravailability;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor; //step 4 : import library
import android.hardware.SensorManager; //step 4 : import library
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.checksensoravailability.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private TextView tv_heartRate;
    private TextView tv_pressure;
    private ActivityMainBinding binding;
    private static final String TAG = "____Main___";
    public static String ACTIVITY_RECOGNITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater()); // binder for framelayout
        setContentView(binding.getRoot());

        tv_heartRate = binding.textHEARTRATE;
        tv_pressure = binding.textPressure;

        checkPermission();
        checkSensorAvailability();

    }

    private void checkPermission() { // step 3 started (according to content detail)

        // Runtime permission ------------
        if (checkSelfPermission(Manifest.permission.BODY_SENSORS) // check runtime permission for BODY_SENSORS
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.BODY_SENSORS}, 1); // If BODY_SENSORS permission has not been taken before then ask for the permission with popup
        } else {
            Log.d(TAG, "ALREADY GRANTED"); //if BODY_SENSORS is allowed for this app then print this line in log.
        }
    }


    private void checkSensorAvailability() {
        SensorManager mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE)); //Step 5: SensorManager Instantiate

        //List of integrated sensor of device---------
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL); //Step 6: List of integrated sensors.
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Sensor sensor : sensors) {

            arrayList.add(sensor.getName()); // put integrated sensor list in arraylist
        }
        Log.d(TAG, " " + arrayList); // print the arraylist in log.


        //check accessibility for 3rd party developers/public-------
        if ((mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)) != null) { //Step 7: Check for particular sensor, if return value then the sensor is accessible
            tv_heartRate.setText(tv_heartRate.getText() + " Accessible"); // Show textView
            tv_heartRate.setText(tv_heartRate.getText() + " Accessible"); // Show textView
            tv_heartRate.setTextColor(Color.parseColor("#32cd32")); //text color of textView
        } else { //Step 6: If return null, then sensor is not accessible and entered into else method.
            tv_heartRate.setText(tv_heartRate.getText() + " Inaccessible");
            tv_heartRate.setTextColor(Color.parseColor("#FF0000")); // textColor
        }

        if ((mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)) != null) { // step 7
            tv_pressure.setText(tv_pressure.getText() + " Accessible");
            tv_pressure.setTextColor(Color.parseColor("#32cd32"));
        } else {
            tv_pressure.setText(tv_pressure.getText() + " Inaccessible");
            tv_pressure.setTextColor(Color.parseColor("#454B1B"));
        }

    }
}