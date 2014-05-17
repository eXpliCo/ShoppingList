package com.explico.thisorthat.shoppinglist.app;

/**
 * Created by eXpliCo on 2014-05-16.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingList extends Fragment {
    ArrayAdapter<String> adapter;
    ArrayList<String> mStringArray;
    String mFilename = "shoppinglist.txt";
    public ShoppingList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        Context context = this.getActivity().getApplicationContext();

        ListView listView = (ListView) rootView.findViewById(R.id.shoppingListView);

        mStringArray = new ArrayList<String>();

        try {
            FileInputStream fileInputStream = context.openFileInput(mFilename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";

            while((line = bufferedReader.readLine()) != null){
                mStringArray.add(line.substring(1));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.item_checkable, this.mStringArray);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView lView = (CheckedTextView) view;
                lView.setChecked(!lView.isChecked());
            }
        });

        return rootView;
    }
}
