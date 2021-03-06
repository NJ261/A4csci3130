package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;

import java.util.Map;

public class DetailViewActivity extends Activity {

    private EditText nameField, emailField, userNumber, userAddress;
    Contact receivedPersonInfo;
    private MyApplicationData appState;
    private Spinner dropdown1, dropdown2;
    private FirebaseListAdapter<Contact> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");
        appState = ((MyApplicationData) getApplicationContext());
        nameField = (EditText) findViewById(R.id.name);
        emailField = (EditText) findViewById(R.id.email);
        userNumber = (EditText) findViewById(R.id.userNumber);
        userAddress = (EditText) findViewById(R.id.userAddress);

        // spinner 1: for business type
        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Fisher", " Distributor", " Processor", "Fish Monger" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown1.setAdapter(adapter);
        dropdown1.setSelection(0);

        // spinner 2: for province selection
        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        String[] items2 = new String[]{" ", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"} ;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        //set the spinners adapter to the previously created one.
        dropdown2.setAdapter(adapter2);
        dropdown2.setSelection(0);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            emailField.setText(receivedPersonInfo.email);
            userNumber.setText(receivedPersonInfo.number);
            userAddress.setText(receivedPersonInfo.address);
        }
    }

    public void updateContact(View v){

        String uID = receivedPersonInfo.uid;
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String userNum = userNumber.getText().toString();
        String userAdd = userAddress.getText().toString();
        String businessType = dropdown1.getSelectedItem().toString();
        String province = dropdown2.getSelectedItem().toString();

        String totalData[] = {name, email, userNum, userAdd, businessType, province};
        String totalDataField[] = {"name", "email", "number", "address", "businessType", "province"};

        for(int j=0; j<6; j++) {
            appState.firebaseReference.child(uID).child(totalDataField[j]).setValue(totalData[j]);
        }
        finish();

    }

    public void eraseContact(View v)
    {
        String uID = receivedPersonInfo.uid;
        appState.firebaseReference.child(uID).removeValue();
        finish();
    }
}
