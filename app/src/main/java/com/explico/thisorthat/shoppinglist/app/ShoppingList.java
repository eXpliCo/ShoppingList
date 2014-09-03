package com.explico.thisorthat.shoppinglist.app;

/**
 * Created by eXpliCo on 2014-05-16.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
    ListView mListView = null;
    public ShoppingList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        mListView = (ListView) rootView.findViewById(R.id.shoppingListView);
        Button clearCheckedButton = (Button) rootView.findViewById(R.id.buttonClearChecked);
        mStringArray = new ArrayList<String>();

        this.readListFromFile();

        adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), R.layout.item_checkable, this.mStringArray);

        mListView.setAdapter(adapter);

        clearCheckedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int len = mListView.getCount();
                int offSet = 0;
                SparseBooleanArray checked = mListView.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        mStringArray.remove(i);
                        offSet++;
                    }
                }
                writeListToFile();
                mListView.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void readListFromFile() {
        Context context = this.getActivity().getApplicationContext();
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
    }

    public void writeListToFile()
    {
        Context context = ShoppingList.this.getActivity().getApplicationContext();
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(mFilename, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamReader = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamReader);
            for(String string : mStringArray) {
                bufferedWriter.write("0" + string);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
