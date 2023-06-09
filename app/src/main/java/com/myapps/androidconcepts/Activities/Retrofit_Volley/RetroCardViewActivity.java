package com.myapps.androidconcepts.Activities.Retrofit_Volley;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Models.RecyclerRetrofit_Model;
import com.myapps.androidconcepts.R;

public class RetroCardViewActivity extends AppCompatActivity {
    private TextView tv_Id, tv_Name, tv_Username, tv_Email, tv_Street, tv_Suite, tv_City, tv_ZipCode,
            tv_Phone, tv_Website, tv_CompanyName, tv_CatchPhrase, tv_Bs;
    private AppCompatButton CVBtn_addressDArrow, CVBtn_cmpnyDArrow;
    private LinearLayout LinLay_CvChildAddress, LinLay_CvChildCompany;
    private RecyclerRetrofit_Model retrofitModel;

    private void initializeFields() {
        tv_Id = findViewById(R.id.tv_Id);
        tv_Id.setText("");
        tv_Name = findViewById(R.id.tv_Name);
        tv_Name.setText("");
        tv_Username = findViewById(R.id.tv_Username);
        tv_Username.setText("");
        tv_Email = findViewById(R.id.tv_Email);
        tv_Email.setText("");
        tv_Street = findViewById(R.id.tv_Street);
        tv_Street.setText("");
        tv_Suite = findViewById(R.id.tv_Suite);
        tv_Suite.setText("");
        tv_City = findViewById(R.id.tv_City);
        tv_City.setText("");
        tv_ZipCode = findViewById(R.id.tv_ZipCode);
        tv_ZipCode.setText("");
        tv_Phone = findViewById(R.id.tv_Phone);
        tv_Phone.setText("");
        tv_Website = findViewById(R.id.tv_Website);
        tv_Website.setText("");
        tv_CompanyName = findViewById(R.id.tv_CompanyName);
        tv_CompanyName.setText("");
        tv_CatchPhrase = findViewById(R.id.tv_CatchPhrase);
        tv_CatchPhrase.setText("");
        tv_Bs = findViewById(R.id.tv_Bs);
        tv_Bs.setText("");
        CVBtn_addressDArrow = findViewById(R.id.CVBtn_addressDArrow);
        CVBtn_cmpnyDArrow = findViewById(R.id.CVBtn_cmpnyDArrow);
        LinLay_CvChildAddress = findViewById(R.id.LinLay_CvChildAddress);
        LinLay_CvChildCompany = findViewById(R.id.LinLay_CvChildCompany);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_card_view);

        initializeFields();

        CVBtn_addressDArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LinLay_CvChildAddress.getVisibility() == View.GONE) {
                    LinLay_CvChildAddress.setVisibility(View.VISIBLE);
                } else {
                    LinLay_CvChildAddress.setVisibility(View.GONE);
                }
            }
        });

        CVBtn_cmpnyDArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LinLay_CvChildCompany.getVisibility() == View.GONE) {
                    LinLay_CvChildCompany.setVisibility(View.VISIBLE);
                } else {
                    LinLay_CvChildCompany.setVisibility(View.GONE);
                }
            }
        });

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.parcelable_Key)) {
            retrofitModel = getIntent().getExtras().getParcelable(Constants.parcelable_Key);

            tv_Id.append("ID: " + retrofitModel.getId());
            tv_Name.append("Name: " + retrofitModel.getName());
            tv_Username.append("Username: " + retrofitModel.getUsername());
            tv_Email.append("Email: " + retrofitModel.getEmail());
            tv_Street.append("Street: " + retrofitModel.getAddress().getStreet());
            tv_Suite.append("Suite: " + retrofitModel.getAddress().getSuite());
            tv_City.append("City: " + retrofitModel.getAddress().getCity());
            tv_ZipCode.append("ZipCode: " + retrofitModel.getAddress().getZipcode());
            tv_Phone.append("Phone: " + retrofitModel.getPhone());
            tv_Website.append("Website: " + retrofitModel.getWebsite());
            tv_CompanyName.append("Company Name: " + retrofitModel.getCompany().getName());
            tv_CatchPhrase.append("Company Info: " + retrofitModel.getCompany().getCatchPhrase());
            tv_Bs.append("Domain: " + retrofitModel.getCompany().getBs());
        }

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