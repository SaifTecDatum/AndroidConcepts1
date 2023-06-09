package com.myapps.androidconcepts.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.AirlinesRetrofitActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.AllTypesRetrofitActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.CollapsingToolbarActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.EcommerceRetrofitActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.JsonVolleyActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.PUT_PATCH_DELETE_RetrofitActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.PaginationRetroRcyclrActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.PostRetrofitActivity;
import com.myapps.androidconcepts.Activities.Retrofit_Volley.RetroRecylrActivity;
import com.myapps.androidconcepts.Content_Providers.ContentProviders_Activity;
import com.myapps.androidconcepts.Helpers.Constants;
import com.myapps.androidconcepts.HttpCalls.MainApplication;
import com.myapps.androidconcepts.Models.Retrofit_Model;
import com.myapps.androidconcepts.R;
import com.myapps.androidconcepts.RxJava.RxJavaActivity;
import com.myapps.androidconcepts.Z_MVP_DesignPattern.MVP_Activity;
import com.myapps.androidconcepts.y_MVVM_DesignPattern.MVVM_Activity;
import com.myapps.androidconcepts.z_kotlin.activities.KotlinMainActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TextView tv_retrofit, tv_userName, tv_userStatus, tv_mobileNo;
    private CircleImageView CIV_profilePic;
    private AppCompatImageButton imgBtn_editUser, imgBtn_kotlin, imgBtn_RoomDB, imgBtn_brdcstRcvrs;
    private AdView mAdView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String currentUserId, retrieveFacebookPic, retrieveFacebookName, retrieveProfilePic;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;

    public void InitializeFields() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_retrofit = findViewById(R.id.tv_retrofit);
        tv_retrofit.setText("");
        mAdView = findViewById(R.id.adView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        progressDialog = new ProgressDialog(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.users);
        currentUserId = mAuth.getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: Event Called");

        InitializeFields();

        NavigationDragger();

        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Call<List<Retrofit_Model>> call = MainApplication.getRestClient().getMyApi().getModel();

        call.enqueue(new Callback<List<Retrofit_Model>>() {
            @Override
            public void onResponse(Call<List<Retrofit_Model>> call, Response<List<Retrofit_Model>> response) {
                if (response.isSuccessful()) {
                    List<Retrofit_Model> modelList = response.body();

                    for (int i = 0; i < modelList.size(); i++) {

                        tv_retrofit.append(
                                "S.no: " + modelList.get(i).getId() +
                                        "\nName: " + modelList.get(i).getName() +
                                        "\nEmail: " + modelList.get(i).getEmail() +
                                        "\nBody: " + modelList.get(i).getBody() + "\n\n");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Retrofit_Model>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something Went Wrong.." + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        NavigationViewMethod();


        //Deep Linking process - by clicking a link wch is outside the app like in notes,msg apps we're opening this App.
        //and before clicking that link we need to insert the same link in intentFilters of certain activity in manifest file.
        Intent deepLinkIntent = getIntent();
        Uri uri = deepLinkIntent.getData();

        if (uri != null) {
            String path = uri.toString();
            Toast.makeText(this, "Path: " + path, Toast.LENGTH_SHORT).show();
        }

    }

    private void NavigationDragger() {

        try {
            Field dragger = null;                                                    //mRightDragger for right obviously
            dragger = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            dragger.setAccessible(true);

            ViewDragHelper draggerObj = null;
            draggerObj = (ViewDragHelper) dragger.get(drawerLayout);

            Field mEdgeSize = null;
            mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
            mEdgeSize.setAccessible(true);

            int edge = 0;
            edge = mEdgeSize.getInt(draggerObj);

            mEdgeSize.setInt(draggerObj, edge * 15);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void LogoutMethod() {
        mAuth.signOut();

        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logOut();
        loginManager.unregisterCallback(CallbackManager.Factory.create());

        /*LoginManager.getInstance().logOut();
        LoginManager.getInstance().unregisterCallback(CallbackManager.Factory.create());*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mAuth.signOut();
                    Intent googleLogoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                    googleLogoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(googleLogoutIntent);
                    finish();
                    Toast.makeText(MainActivity.this, "Successfully LoggedOut..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        Toast.makeText(MainActivity.this, "Successfully LoggedOut..", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void NavigationViewMethod() {
        View view = navigationView.getHeaderView(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CIV_profilePic = view.findViewById(R.id.profilePic);
        tv_userName = view.findViewById(R.id.tv_userName);
        tv_userName.setText("");
        tv_userStatus = view.findViewById(R.id.tv_userStatus);
        tv_userStatus.setText("");
        tv_mobileNo = view.findViewById(R.id.tv_mobileNo);
        tv_mobileNo.setText("");
        imgBtn_editUser = view.findViewById(R.id.imgBtn_editUser);
        imgBtn_kotlin = view.findViewById(R.id.imgBtn_kotlin);
        imgBtn_RoomDB = view.findViewById(R.id.imgBtn_RoomDB);
        imgBtn_brdcstRcvrs = view.findViewById(R.id.imgBtn_brdcstRcvrs);

        imgBtn_kotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KotlinMainActivity.class));

                Snackbar snackbar = Snackbar.make(drawerLayout, "You have just Entered in Kotlin..", Snackbar.LENGTH_INDEFINITE)
                        .setTextColor(Color.GREEN).setAction("Okay..", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Snackbar snackbar1 = Snackbar.make(drawerLayout, "Thank you..!", Snackbar.LENGTH_SHORT).setTextColor(Color.YELLOW);
                                snackbar1.show();
                            }
                        }).setActionTextColor(Color.RED);
                snackbar.show();

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        imgBtn_RoomDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RoomDatabaseActivity.class));
                Toast.makeText(MainActivity.this, "Welcome to SQLite Room DB frm NavView..", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        imgBtn_brdcstRcvrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BroadcastRcvrsActivity.class));
                Toast.makeText(MainActivity.this, "Welcome to Broadcast Receivers..", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        imgBtn_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        userRef.child(currentUserId).child(Constants.authType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue().equals(Constants.googleAuth)) {
                    retrieveGoogleDB();
                } else if (snapshot.exists() && snapshot.getValue().equals(Constants.phoneAuth)) {
                    retrievePhoneDB();
                } else if (snapshot.exists() && snapshot.getValue().equals(Constants.nrmlAuth)) {
                    retrieveNrmlAuthDB();
                } else if (snapshot.exists() && snapshot.getValue().equals(Constants.facebookAuth)) {
                    retrieveFacebookDB();
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong in DB..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        toggle.syncState();                                 //showsToggleOnToolbar,otherwiseWon'tShow.
        drawerLayout.addDrawerListener(toggle);
        navigationView.setItemIconTintList(null);          //givesColorForNav_MenuItems,otherwiseShowsBlack&White.

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contacts_CntPrvds:

                        setProgressDialog();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 2000);

                        startActivity(new Intent(MainActivity.this, ContentProviders_Activity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Contacts using ContentProviders..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.services:
                        startActivity(new Intent(MainActivity.this, ServiceActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Services..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rcylr_retrofit:
                        startActivity(new Intent(MainActivity.this, RetroRecylrActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened RecyclerView Retrofit..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.colpsngBar:
                        startActivity(new Intent(MainActivity.this, CollapsingToolbarActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Collapsing Toolbar..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.volleyJson:
                        startActivity(new Intent(MainActivity.this, JsonVolleyActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Json Parsing via Volley..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ninePatchImg:
                        startActivity(new Intent(MainActivity.this, NinePatchActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Nine Patch Activity..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_customView:
                        startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Custom View Activity..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.paymentGateway:
                        startActivity(new Intent(MainActivity.this, Payment_Gateway_Activity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Payment Gateway Activity..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.livePayment:
                        startActivity(new Intent(MainActivity.this, PaymentGateway_Live_Activity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Live Payment Gateway Activity..", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.paginationRetroRcyclr:
                        startActivity(new Intent(MainActivity.this, PaginationRetroRcyclrActivity.class));
                        Toast.makeText(MainActivity.this, "You have just Opened Pagination Activity..", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    public void retrieveGoogleDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userContact)
                        && !snapshot.hasChild(Constants.userStatus)) {
                    String retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();
                    String retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    tv_userName.setText(retrieveGoogleName);
                    Picasso.get().load(retrieveGooglePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);

                    Toast.makeText(MainActivity.this, "Please update Contact & Status..", Toast.LENGTH_SHORT).show();
                } else if (snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userContact)
                        && !snapshot.hasChild(Constants.userStatus)) {
                    String retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    tv_userName.setText(retrieveGoogleName);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);

                    Toast.makeText(MainActivity.this, "Please update Contact & Status..", Toast.LENGTH_SHORT).show();
                } else if (!snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.googlePic)
                        && snapshot.hasChild(Constants.userFullName) && snapshot.hasChild(Constants.userStatus)
                        && snapshot.hasChild(Constants.userContact)) {

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    String retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveGooglePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                } else if (snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userStatus) && snapshot.hasChild(Constants.userContact)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                } else if (!snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.googlePic) && snapshot.hasChild(Constants.googleName)
                        && snapshot.hasChild(Constants.userStatus) && snapshot.hasChild(Constants.userContact)) {

                    String retrieveGoogleName = snapshot.child(Constants.googleName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    String retrieveGooglePic = snapshot.child(Constants.googlePic).getValue().toString();

                    tv_userName.setText(retrieveGoogleName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveGooglePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void retrievePhoneDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(Constants.profilePic) &&
                        !snapshot.hasChild(Constants.userFullName) && !snapshot.hasChild(Constants.userStatus)) {

                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    tv_mobileNo.setText(retrieveContact);
                    Toast.makeText(MainActivity.this, "Please update Profile Info..", Toast.LENGTH_SHORT).show();
                } else if (!snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.userFullName) && !snapshot.hasChild(Constants.userStatus)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.profilePic)) {
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    tv_mobileNo.setText(retrieveContact);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);

                    Toast.makeText(MainActivity.this, "Please update Name & Status..", Toast.LENGTH_SHORT).show();
                } else {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);

                    Toast.makeText(MainActivity.this, "Total info updated Successfully..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void retrieveNrmlAuthDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userStatus)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);

                    Toast.makeText(MainActivity.this, "Please Upload Profile Pic & Status..", Toast.LENGTH_SHORT).show();
                } else if (!snapshot.hasChild(Constants.profilePic)) {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);

                    Toast.makeText(MainActivity.this, "Please Upload Profile Pic..", Toast.LENGTH_SHORT).show();
                } else if (!snapshot.hasChild(Constants.userStatus)) {

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);

                    Toast.makeText(MainActivity.this, "Please Upload your Status..", Toast.LENGTH_SHORT).show();
                } else {
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();
                    String retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void retrieveFacebookDB() {
        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFacebookName);
                } else if (snapshot.hasChild(Constants.facebookName) && !snapshot.hasChild(Constants.facebookPic)
                        && !snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    tv_userName.setText(retrieveFacebookName);
                } else if (snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                } else if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && !snapshot.hasChild(Constants.userFullName)
                        && !snapshot.hasChild(Constants.userContact) && !snapshot.hasChild(Constants.userStatus)) {

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    retrieveFacebookName = snapshot.child(Constants.facebookName).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFacebookName);
                } else if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveProfilePic = snapshot.child(Constants.profilePic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveProfilePic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (!snapshot.hasChild(Constants.facebookPic) && snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else if (snapshot.hasChild(Constants.facebookPic) && !snapshot.hasChild(Constants.facebookName)
                        && !snapshot.hasChild(Constants.profilePic) && snapshot.hasChild(Constants.userFullName)
                        && snapshot.hasChild(Constants.userContact) && snapshot.hasChild(Constants.userStatus)) {

                    retrieveFacebookPic = snapshot.child(Constants.facebookPic).getValue().toString();
                    String retrieveFullName = snapshot.child(Constants.userFullName).getValue().toString();
                    String retrieveContact = snapshot.child(Constants.userContact).getValue().toString();
                    String retrieveStatus = snapshot.child(Constants.userStatus).getValue().toString();

                    Picasso.get().load(retrieveFacebookPic).placeholder(R.drawable.profile_pic).into(CIV_profilePic);
                    tv_userName.setText(retrieveFullName);
                    tv_mobileNo.setText(retrieveContact);
                    tv_userStatus.setText(retrieveStatus);
                } else {
                    Toast.makeText(MainActivity.this, "Something Went Wrong in Retrieving DB..!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.logout) {
            LogoutMethod();
        } else if (item.getItemId() == R.id.viewPager) {
            startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
        } else if (item.getItemId() == R.id.listView) {
            startActivity(new Intent(MainActivity.this, ListViewActivity.class));
        } else if (item.getItemId() == R.id.sqLite) {
            startActivity(new Intent(MainActivity.this, SQLiteActivity.class));
        } else if (item.getItemId() == R.id.SecndSQLite) {
            startActivity(new Intent(MainActivity.this, SecndSQLiteActivity.class));
        } else if (item.getItemId() == R.id.adMob) {
            startActivity(new Intent(MainActivity.this, AdMobActivity.class));
        } else if (item.getItemId() == R.id.videoPlayer) {
            startActivity(new Intent(MainActivity.this, MyVideosActivity.class));
        } else if (item.getItemId() == R.id.postRetrofit) {
            startActivity(new Intent(MainActivity.this, PostRetrofitActivity.class));
        } else if (item.getItemId() == R.id.googleMaps) {
            startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class));
        }
        else if (item.getItemId() == R.id.openCamera) {
            startActivity(new Intent(MainActivity.this, CameraActivity.class));
        }
        else if (item.getItemId() == R.id.sharedPreference) {
            startActivity(new Intent(MainActivity.this, SharedPrefrncActivity.class));
        } else if (item.getItemId() == R.id.eCommerceRcylrRetro) {
            startActivity(new Intent(MainActivity.this, EcommerceRetrofitActivity.class));
        } else if (item.getItemId() == R.id.airlinesRcylrRetro) {
            startActivity(new Intent(MainActivity.this, AirlinesRetrofitActivity.class));
        } else if (item.getItemId() == R.id.allTypeRetrofit) {
            startActivity(new Intent(MainActivity.this, AllTypesRetrofitActivity.class));
        } else if (item.getItemId() == R.id.putPatchDeleteRetrofit) {
            startActivity(new Intent(MainActivity.this, PUT_PATCH_DELETE_RetrofitActivity.class));
        } else if (item.getItemId() == R.id.MVP_designPattern) {
            startActivity(new Intent(MainActivity.this, MVP_Activity.class));
        } else if (item.getItemId() == R.id.RxJava) {
            startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
        } else if (item.getItemId() == R.id.MVVM_designPattern) {
            startActivity(new Intent(MainActivity.this, MVVM_Activity.class));
        } else if (item.getItemId() == R.id.FragToFrag) {
            startActivity(new Intent(MainActivity.this, FragToFragActivity.class));
        } else if (item.getItemId() == R.id.navComponent) {
            startActivity(new Intent(MainActivity.this, NavigationComponent_Activity.class));
        } else if (item.getItemId() == R.id.device2pc) {
            startActivity(new Intent(MainActivity.this, DeviceToPC_Activity.class));
        }

        else if (item.getItemId() == R.id.oneToAnotherApp) {
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.fbPackageName);               //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.fbMessengerPackageName);      //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.instagramPackageName);        //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.googleHangoutsPackageName);   //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.flipkartPackageName);         //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.phonePePackageName);          //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.indianGasPackageName);        //working
            //Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.hotStarPackageName);          //working

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Constants.whatsappPackageName);           //working

            if (launchIntent != null) {
                startActivity(launchIntent);
                Toast.makeText(this, "Opening  " + launchIntent.getPackage().toUpperCase() + "  App from this App..!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Package doesn't exists..!", Toast.LENGTH_SHORT).show();
            }
        }

        else if (item.getItemId() == R.id.openWeather) {
            startActivity(new Intent(MainActivity.this, WeatherActivity.class));
            Toast.makeText(this, "Opening Weather Activity..!", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.practicePlace) {
            startActivity(new Intent(MainActivity.this, PracticeActivity.class));
            Toast.makeText(this, "Opening Practice Activity..!", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.android_Charts) {
            startActivity(new Intent(MainActivity.this, ChartAndroidActivity.class));
            Toast.makeText(this, "Opening Charts Activity..!", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(MainActivity.this, "Error In Menu Options Section..", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }

    private void setProgressDialog() {
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("shortly opening Contacts from Content Providers.");
        progressDialog.setIcon(R.drawable.call_yellows);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        /*progressDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
        params.y = 100;
        progressDialog.getWindow().setAttributes(params);*/

        progressDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Event Called");

        //Have Kept these lifeCycle methods here & in NinePatchActivity to understand the process b/w Activity's lifecycle.
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Event Called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Utilities.onResumeToRegister(this); //NoInternet facility kept in comment bcoz we have offline db facility for
        //weatherActivity which would be opened by this activity's menuItems .So in order to reach that activity we must need to comment this too.

        Log.d(TAG, "onResume: Event Called");
    }

    @Override
    protected void onPause() {
        super.onPause();


        //Utilities.onPauseToUnRegister(this);

        Log.d(TAG, "onPause: Event Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Event Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Event Called");
    }

}