package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Payment_Gateway_Activity extends AppCompatActivity implements PaymentResultListener {   //codeToRemember: 1   easy
    private final String order_reference_no = "Reference No. #" + System.currentTimeMillis() / 1000;
    private EditText et_payment;
    private AppCompatButton btn_payment;
    private TextView tv_payText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        tv_payText = findViewById(R.id.tv_payText);
        tv_payText.setText("");
        btn_payment = findViewById(R.id.btn_payment);
        et_payment = findViewById(R.id.et_payment);

        Checkout.preload(getApplicationContext());          //codeToRemember: 2      easy

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_payment.getText().toString().trim())) {
                    et_payment.setError("Amount didn't Entered..!");
                } else {
                    startPayment();
                }
            }
        });
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID(Constants.razorpayKey_Id);           //codeToRemember: 3    easy
        checkout.setImage(R.drawable.app_logo);

        //Pass your payment options to the Razorpay Checkout as a JSONObject
        try {
            JSONObject options = new JSONObject();

            options.put("name", "MGD Apps..");
            options.put("description", order_reference_no);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");    //In live payments we must give order_id
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

            Double amount = Double.parseDouble(et_payment.getText().toString().trim());
            amount = amount * 100;
            options.put("amount", amount);     //pass amount in currency subunits. Ex: 300 x 100 = 30000(subunitsValue)

            options.put("prefill.email", "saifmsu15@gmail.com");
            options.put("prefill.contact", "9494641266");

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);      //codeToRemember: 4   easy
            retryObj.put("max_count", 4);

            options.put("retry", retryObj);

            checkout.open(Payment_Gateway_Activity.this, options);
        } catch (Exception e) {
            Toast.makeText(Payment_Gateway_Activity.this, "Something went Wrong: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Payment_Gateway_Activity.this, "Payment Success ID: " + s, Toast.LENGTH_LONG).show();
        tv_payText.append("Amount: " + et_payment.getText().toString().trim() + "\nPayment ID: " + s + "\n" + order_reference_no);
        et_payment.setText("");
    }

    @Override
    public void onPaymentError(int i, String s) {
        tv_payText.append("Payment Failed & Cause is: " + i + " & " + s);
        et_payment.setText("");
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