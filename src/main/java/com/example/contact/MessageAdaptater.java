package com.example.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdaptater extends BaseAdapter {
    private Context context;
    private List<MessageItem> messageItemList;
    private LayoutInflater inflater;

    public  MessageAdaptater(Context context, List<MessageItem>messageItemList)
    {
        this.context = context;
        this.messageItemList = messageItemList;
        this.inflater = LayoutInflater.from(context); //context = info de l'app
    }

    //return le nombre d'item
    public int getCount() {
        return messageItemList.size();
    }

    //retourn l'élément
    public MessageItem getItem(int position) {
        return messageItemList.get(position);
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) { //il nous permet de personnaliser chaque element
        view = inflater.inflate(R.layout.adaptater_message, null);


        //recupération des infos à propos de l'item
        MessageItem currentItem = getItem(i);
        //String itemTitle = currentItem.getTitle();
        String itemContenu = currentItem.getContenu();

        //recupération contenu
        TextView itemContenuView = view.findViewById(R.id.item_contenu);
        itemContenuView.setText(itemContenu);


        return view;
    }
}


