package com.explico.thisorthat.shoppinglist.app;

/**
 * Created by eXpliCo on 2014-05-16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
public class ManageList extends Fragment {
    ArrayAdapter<String> adapter;
    ArrayList<String> mStringArray;
    String mFilename = "shoppinglist.txt";
    public ManageList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        Context context = this.getActivity().getApplicationContext();
        ListView listView = (ListView) rootView.findViewById(R.id.productsListView);
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

        adapter = new ArrayAdapter<String>(context, R.layout.item, this.mStringArray);

        listView.setAdapter(adapter);

        Button addProductButton = (Button) rootView.findViewById(R.id.addProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = ManageList.this.getActivity().getApplicationContext();
                Editable text = ((EditText) ManageList.this.getView().findViewById(R.id.addProductText)).getText();
                if(text.toString().trim().length() <= 0)
                    return;
                mStringArray.add(String.valueOf(text));
                adapter.notifyDataSetChanged();
                text.clear();
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
        });

        Button removeProductButton = (Button) rootView.findViewById(R.id.removeProductButton);
        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable text = ((EditText) ManageList.this.getView().findViewById(R.id.addProductText)).getText();
                if(text.toString().trim().length() <= 0)
                    return;
                mStringArray.remove(String.valueOf(text));
                adapter.notifyDataSetChanged();
                text.clear();
                Context context = ManageList.this.getActivity().getApplicationContext();
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
        });

        Button resetProductButton = (Button) rootView.findViewById(R.id.resetProductButton);
        resetProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStringArray.clear();
                adapter.notifyDataSetChanged();

                Context context = ManageList.this.getActivity().getApplicationContext();
                context.deleteFile(mFilename);
            }

        });

        return rootView;
    }
}
