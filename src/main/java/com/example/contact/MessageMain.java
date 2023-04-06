package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageMain extends AppCompatActivity {
    private ListView listTest;
    List<MessageItem> messageItemList = new ArrayList<>();

    private Button button;
    private EditText input;

    //SharedPreferences sharedPreferences; // nous permet la sauvergade des messages meme si on quit l'app
    private static final String  SHARED_NAME = "mypref";
    private static final String  KEY_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //button = (Button) findViewById(R.id.btnPageAdd);
        button = (Button) findViewById(R.id.btnAddTest);
        input = (EditText) findViewById(R.id.inputMsgTest);
        listTest = (ListView) findViewById(R.id.listViewTest);
        //sharedPreferences = getSharedPreferences(SHARED_NAME, MODE_PRIVATE);
        messageItemList = PrefConfig.readListFromPref(this);

        if(messageItemList == null)
            messageItemList = new ArrayList<>();
        addMessage();

        //ListAdapter list = new ArrayAdapter<MessageItem>(getApplicationContext(), android.R.layout.simple_list_item_1, messageItemList);
        ListAdapter list = new MessageAdaptater(getApplicationContext(), messageItemList);

        //Afficher notre adaptater contact
        listTest.setAdapter(list);
        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /*lors d'un clique sur un message on ouvre un nouveau tab qui affiche le message sur lequel on a cliqué pour l'envoyer comme message
            l'utilisateur a la possibilté de modifier ce message avant envoi
             */
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MessageMain.this, MainSendReponse.class);
                //récupérer le message cliquer
                MessageItem item = (MessageItem) adapterView.getItemAtPosition(i);

                //récuéperer notre listPhone envoyer par Mainacivity
                Bundle bundle = getIntent().getExtras();
                ArrayList<String> listPhone = bundle.getStringArrayList("listPhone");

                //Envoyer notre listPhone et le message selection au tab MainSendReponse qui gère l'envoie des message
                intent.putExtra("item", item.getContenu());
                intent.putExtra("listPhone", listPhone);

                startActivity(intent);
            }
        });

        /*Quand on clique sur le bouton on fait appel à notre fonction addItem pour ajouter le message saisi
        si aucun message n'est saisi, on affiche un message qui demande à l'utilisateur de saisir un texte*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString(); // récupérer la valeur saisi dans le champ de texte
                MessageItem sms = new MessageItem(input.getText().toString());
                if(text == null  || text.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Veuillez saisir un message", Toast.LENGTH_SHORT).show();
                }else{
                    messageItemList.add(sms);
                    PrefConfig.writeListInpref(getApplicationContext(), messageItemList);
                    input.setText("");
                }
                listTest.setAdapter(list);
            }
        });

    }

    /* Fonction qui permet d'ajouter son propre message de réponse
    il prend en paramètre le message reçu de l'input pour l'ajouter après à notre listView*/
    /*public void addItem(MessageItem message)
    {
        messageItemList.add(message);
        ListAdapter list = new ArrayAdapter<MessageItem>(getApplicationContext(), android.R.layout.simple_list_item_1, messageItemList);
        PrefConfig.writeListInpref(getApplicationContext(), messageItemList);
        listTest.setAdapter(list);
    }*/


    //function qui affiche quelques message par défaut
    public void addMessage()
    {
        String contenu = "Bonjour, je suis en route. Dans 5min je serai là-bas!";
        String contenu1 = "Bonjour, je suis indisponible pour le moment! Je vous répondrai dès que possible. Merci";
       //// messageItemList.add(contenu1);
        //String message = sharedPreferences.getString(KEY_MESSAGE, null);
        /*if(message != null){
            messageItemList.add(message);
        }*/
    }
}