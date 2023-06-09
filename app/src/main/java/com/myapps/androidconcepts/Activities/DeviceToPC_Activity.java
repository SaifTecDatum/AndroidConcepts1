package com.myapps.androidconcepts.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.myapps.androidconcepts.Helpers.MessageSender;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class DeviceToPC_Activity extends AppCompatActivity {
    private EditText et_message;
    private AppCompatButton btn_submit;

    //Youtube Link: https://www.youtube.com/watch?v=29y4X65ZUwE
    //TCP/IP Sockets - Send and Receive Data from Android Device to PC & PC to device.
    //completed half video, have to complete remaining half. Need to download NetBeans to run output on PC.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_to_pc);

        et_message = findViewById(R.id.et_message);
        btn_submit = findViewById(R.id.btn_submit);




    }

    public void send(View v) {

        MessageSender messageSender = new MessageSender();
        messageSender.execute(et_message.getText().toString().trim());


    }


    @Override
    protected void onResume() {
        super.onResume();

        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Utilities.onPauseToUnRegister(this);
    }
}