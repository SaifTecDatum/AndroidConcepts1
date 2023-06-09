package com.myapps.androidconcepts.Content_Providers;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ContentProviders_Activity extends AppCompatActivity {
    private static final String TAG = "ContentProviders_Act";
    private final ArrayList<String> contactList = new ArrayList<>();
    private TextView textView;
    private ListView listView;
    private AppCompatButton button;
    private final int permissionRqstCode = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_providers);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.btn_contntProvdr);
        textView = findViewById(R.id.tv_numOfContacts);
        textView.setText("");

        if (ContextCompat.checkSelfPermission(ContentProviders_Activity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContentProviders_Activity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, permissionRqstCode);
        } else {
            fetchingContactsWithoutDuplicates();
            //fetchingContactsFromContentProviders();
        }

        /*if (ContextCompat.checkSelfPermission(ContentProviders_Activity.this,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContentProviders_Activity.this,
                    new String[]{Manifest.permission.READ_SMS}, permissionRqstCode);
        } else {
            fetchingInboxMsgs();
        }*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listView.getVisibility() == View.VISIBLE) {
                    listView.setVisibility(View.GONE);
                    button.setText("View Contacts");
                } else {
                    listView.setVisibility(View.VISIBLE);
                    button.setText("Close Contacts");
                }
            }
        });
    }

    private void fetchingInboxMsgs() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Telephony.Sms.Inbox.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String smsAddress = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.ADDRESS));
                    String smsID = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox._ID));
                    String smsBody = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.BODY));
                    String smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.DATE));
                    String smsServiceCenter = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.SERVICE_CENTER));
                    String smsCreator = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.Inbox.CREATOR));

                    //Date dateFormat = new Date(Long.parseLong(smsDate));      //weCanUseAnyOneOfTheseTwoForDate&Time.
                    String timeStamp = new SimpleDateFormat("hh:mm a  ,  dd MMM yyyy").format(Long.parseLong(smsDate));


                    contactList.add("\nAddress: " + smsAddress + "                   " + "Id: " + smsID + "\n\n" +
                            "Message: " + smsBody + "\n\n" +
                            "Date: " + timeStamp + "\n\n" +
                            "ServiceCenter: " + smsServiceCenter + "\n" +
                            "Creator: " + smsCreator + "\n");
                }
                while (cursor.moveToNext());
            }
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ContentProviders_Activity.this, android.R.layout.simple_list_item_1, contactList);
        listView.setAdapter(adapter);
    }

    private void fetchingContactsFromContentProviders() {       //hereWe'reGettingDuplicatesToo.actualContacts:322,gettingContacts:728

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, "DISPLAY_NAME ASC");

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String cName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String cNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int type = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));

                    switch (type) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            Log.e(TAG, "TYPE_HOME: Not Inserted");
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:

                            contactList.add("Name: " + cName + "\nMobile No.: " + cNumber);

                            /*if (!cNumber.contains(" ") && !cNumber.contains("+")) {
                                contactList.add("Name: " + cName + "\nMobile No.: " + cNumber);
                            }*/

                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            Log.e(TAG, "TYPE_WORK: Not Inserted");
                            break;
                    }

                }
                while (cursor.moveToNext());
                textView.append("Total No.of Contacts: " + contactList.size());
            }
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ContentProviders_Activity.this, android.R.layout.simple_list_item_1, contactList);
        listView.setAdapter(adapter);
    }

    private void fetchingContactsWithoutDuplicates() {
        /*PresentContacts:322 in mobile contacts app, GettingContacts:287 after filters.
         Got 2 duplicates(SajeedContact) & some contacts wch have space in between are hided
         by our condition to avoid duplicates. (looksLike thr is another process to fetch all)*/

        ContentResolver contentResolver = getContentResolver();
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor1 = contentResolver.query(contactsUri, null, null, null, "DISPLAY_NAME ASC");
        String lastNumber = "0";

        //textView.append("Total No.of Contacts: " + cursor1.getCount());

        if (cursor1.getCount() > 0) {
            if (cursor1.moveToFirst()) {
                do {
                    String contactNumber = null;
                    String contact_Id = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String contact_Name = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                    if (Integer.parseInt(cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                        //below we're keeping condition that if contactID of commonDataKinds.Phone uri & Contacts uri matches then we're taking contactNumber to avoid duplicates.
                        Uri commonDataUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        Cursor cursor2 = contentResolver.query(commonDataUri, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", new String[]{contact_Id}, null);

                        if (cursor2.getCount() > 0) {
                            if (cursor2.moveToFirst()) {
                                do {
                                    contactNumber = cursor2.getString(cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.e(TAG, "fetchingContactsWithoutDuplicates: LastNumber: " + lastNumber);
                                    Log.e(TAG, "fetchingContactsWithoutDuplicates: Number: " + contactNumber);

                                    if (contactNumber.equals(lastNumber)) {

                                    } else {
                                        lastNumber = contactNumber;
                                        Log.e(TAG, "fetchingContactsWithoutDuplicates: lastNumber1: " + lastNumber);

                                        int type = cursor2.getInt(cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));

                                        switch (type) {
                                            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                                Log.e(TAG, "TYPE_HOME: Not Inserted");
                                                break;
                                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:

                                                if (!contactNumber.contains(" ")) {
                                                    contactList.add("Name: " + contact_Name + "\nMobile No.: " + contactNumber);
                                                }

                                                /*if (!contactNumber.contains(" ") && !contactNumber.contains("+")) {
                                                }*/
                                                break;
                                            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                                Log.e(TAG, "TYPE_WORK: Not Inserted");
                                                break;
                                        }
                                    }
                                } while (cursor2.moveToNext());
                            }
                        }
                        cursor2.close();
                    }
                } while (cursor1.moveToNext());
                textView.append("Total No.of Contacts: " + contactList.size());
            }
        }
        cursor1.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ContentProviders_Activity.this, android.R.layout.simple_list_item_1, contactList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRqstCode) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.READ_CONTACTS)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            fetchingContactsWithoutDuplicates();
                            //fetchingContactsFromContentProviders();
                        } else {
                            Toast.makeText(ContentProviders_Activity.this, "Permission Denied by you..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    /*if (permissions[i].equals(Manifest.permission.READ_SMS)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            fetchingInboxMsgs();
                        } else {
                            Toast.makeText(ContentProviders_Activity.this, "Permission Denied by you..!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }*/
                }
            }
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