package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentGateway_Live_Activity extends AppCompatActivity implements PaymentResultListener {
    private final String order_receipt_no = "Receipt No. " + System.currentTimeMillis() / 1000;
    private final String order_reference_no = "Reference No. #" + System.currentTimeMillis() / 1000;
    private TextView tv_payText;
    private AppCompatButton btn_payment;
    private EditText et_payment;
    private Checkout checkout;
    private RazorpayClient razorpayClient;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_payments);

        tv_payText = findViewById(R.id.tv_payText);
        tv_payText.setText("");
        btn_payment = findViewById(R.id.btn_payment);
        et_payment = findViewById(R.id.et_payment);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Initialize client
        try {
            razorpayClient = new RazorpayClient(getResources().getString(R.string.razorpay_key_id), getResources().getString(R.string.razorpay_secret_key));
            Checkout.preload(getApplicationContext());
            checkout = new Checkout();
        } catch (RazorpayException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>();
        razorpayClient.addHeaders(headers);

        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", 100); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", order_receipt_no);
            orderRequest.put("payment_capture", true);

            order = razorpayClient.Orders.create(orderRequest);

            btn_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(et_payment.getText().toString().trim())) {
                        et_payment.setError("Amount didn't Entered..!");
                    } else {
                        tv_payText.setText("");
                        startPayment(order);
                    }
                }
            });

            et_payment.setText("");
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage() + "Error");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void startPayment(Order order) {
        checkout.setKeyID(String.valueOf(R.string.razorpay_key_id));
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.app_logo);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "MGD Apps..");
            options.put("description", order_reference_no);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order.get("id"));
            options.put("currency", "INR");

            Double amount = Double.parseDouble(et_payment.getText().toString().trim());
            amount = amount * 100;

            options.put("amount", amount);

            checkout.open(PaymentGateway_Live_Activity.this, options);
        } catch (Exception e) {
            Toast.makeText(PaymentGateway_Live_Activity.this, "Something went Wrong: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(PaymentGateway_Live_Activity.this, s, Toast.LENGTH_LONG).show();
        tv_payText.append("Payment ID: " + s + "\nOrder ID: " + order.get("id") + "\n" + order_reference_no);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(PaymentGateway_Live_Activity.this, "Error: " + s, Toast.LENGTH_LONG).show();
        tv_payText.append("Error: " + s);
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