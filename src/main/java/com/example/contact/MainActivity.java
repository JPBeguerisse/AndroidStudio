package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactListener{
    private Button button;

    ArrayList<ContactItem> contactItemList = new ArrayList<>();

    RecyclerView recyclerView;
    ContactAdapter adapter;

    public boolean isPermissionGranted() {
        // Return true if user has given his permission to read incoming messages
        return ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestContactPermission() {
        // Ask user for his permission to read incoming messages
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_CONTACTS)) {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
    }

    @SuppressLint({"Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recupContacts();

        if (!isPermissionGranted()) {
            requestContactPermission();
        }

        recyclerView = findViewById(R.id.recycler_view);

        setRecyclerView();
        
    }
    /*
     * Recupération des contact
     *
     * */
    @SuppressLint("Range")
    public ArrayList<ContactItem> getContacts()
    {
        //access au contenu
        ContentResolver contentResolver = this.getContentResolver();

        //récupération des contact dans un curseur
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);

        if (cursor == null )
        {
            Log.d("Recup", "Erreur de cursor");
        }else{
            //parcours des contact
            while(cursor.moveToNext())
            {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //ajout des contacts dans la list
                contactItemList.add(new ContactItem(name, phone));
                //Afficher notre adaptater contact
                // ListView contactListView = findViewById(R.id.contact_list_view);
                //contactListView.setAdapter(new ContactAdaptater(this, contactItemList));
            }

            //fermer le cursor
            cursor.close();
        }
        return contactItemList;
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(this,getContacts(), this);
        recyclerView.setAdapter(adapter);

    }


    //quand y'un un clique sur les checkbox on appelle cet méthode qui nous permet de cliquer sur une bouton pour envoyer les numéros qu'on a sélectionner
    @Override
    public void onContactChange(ArrayList<ContactItem> array) {

        button = (Button) findViewById(R.id.btnReponse);

        //Event sur le button pour ouvrir l'activity des message
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //créer un arraylist où on va ajouter nos numéro de contacts qu'on selectionnera avec le checkbox
                ArrayList<String> listPhone = new ArrayList<>();
                for(ContactItem n : array)
                    listPhone.add(n.getPhone());

                //si on clique sur le boutton on déclenche aucun evenement si aucun contact n'est sélectionner
                if(listPhone != null)
                {
                    //Intent intent = new Intent(MainActivity.this, MessageMain.class);
                    Intent intent = new Intent(MainActivity.this, MessageMain.class);

                    //envoyer notre listPhone qui contient les numéro vers MessageMain
                    intent.putExtra("listPhone", listPhone);
                    startActivity(intent);
                }
            }
        });


    }
}