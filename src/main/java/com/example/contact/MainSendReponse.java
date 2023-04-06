package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainSendReponse extends AppCompatActivity {

    Button send;
    EditText reponse;
    EditText contact;
    //EditText title;
    int messeageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_send_message);
        initActivity();

        //récuéper le message cliquer et l'afficher dans le input
        String message = getIntent().getStringExtra("item");
        //String contacts = getIntent().getStringExtra("contacts");
        String contacts = "";
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> listPhone = bundle.getStringArrayList("listPhone");

        Intent intent = new Intent(MainSendReponse.this, MessageMain.class);
        intent.putExtra("listPhone", listPhone);


        for(String phone : listPhone )
            contacts = contacts + phone;

        //remplir nos champs par le numéro et le message sélectionner
        reponse.setText(message);
        contact.setText(contacts);



    }

    public void initActivity()
    {
        send = (Button) findViewById(R.id.btnAdd);
        reponse = (EditText) findViewById(R.id.inputMessage);
        contact = (EditText) findViewById(R.id.inputContact);
        createOnclickSendButton();
    }

    //founction pour envoyer le messages grâce à SmsManager qui recoit le message et les numéro
    private void createOnclickSendButton() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager.getDefault().sendTextMessage(contact.getText().toString(), null, reponse.getText().toString(), null, null);
                reponse.setText("");
                contact.setText("");
            }
        });
        Toast.makeText(this, "f", Toast.LENGTH_SHORT).show();
    }
}