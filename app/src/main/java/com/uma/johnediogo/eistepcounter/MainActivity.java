package com.uma.johnediogo.eistepcounter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
    private Button btnSet;
    private EditText edtGoal;
    private float dailyGoal = 0.0f;
    private SensorManager sensorManager;
    private TextView count;
    boolean activityRunning;
    private boolean setted = false, first = true;
    private float initialValue=0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = (TextView) findViewById(R.id.count);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        btnSet = (Button) findViewById(R.id.btnSet);
        edtGoal = (EditText) findViewById(R.id.edtGoal);
        count.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(first)
        {
            first = false;
            initialValue = event.values[0];
            return;
        }
        if(activityRunning) {
            count.setText(String.valueOf(event.values[0]-initialValue));
            if((event.values[0] - initialValue) >= dailyGoal && setted){
                count.setTextColor(Color.parseColor("#FF00C514"));

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setGoal(View v){
        if(!setted){
            edtGoal.setEnabled(false);
            btnSet.setEnabled(false);
            dailyGoal = Float.parseFloat(edtGoal.getText().toString());
            setted = true;
        }
    }

}
