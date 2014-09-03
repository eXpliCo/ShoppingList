package com.explico.thisorthat.shoppinglist.app;

/**
 * Created by eXpliCo on 2014-05-16.
 */

import android.app.Activity;
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
public class ManageList extends Fragment{
    ArrayAdapter<String> adapter;
    ArrayList<String> mStringArray;
    String mFilename = "shoppinglist.txt";
    MainActivity mMainActivity = null;
    ListView mListView = null;

    public ManageList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        Context context = this.getActivity().getApplicationContext();
        mListView = (ListView) rootView.findViewById(R.id.productsListView);
        mStringArray = new ArrayList<String>();

        this.readListFromFile();

        adapter = new ArrayAdapter<String>(context, R.layout.item_checkable, this.mStringArray);
        mListView.setAdapter(adapter);

        Button addProductButton = (Button) rootView.findViewById(R.id.addProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivity.showPickProductDialog();
            }
        });

        Button removeProductButton = (Button) rootView.findViewById(R.id.removeProductButton);
        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int len = mListView.getCount();
                SparseBooleanArray checked = mListView.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        mStringArray.remove(i);
                    }
                }
                writeListToFile();
                mListView.clearChoices();
                adapter.notifyDataSetChanged();
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

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mMainActivity = (MainActivity) activity;
    }

    public void addProduct(String product){
        mStringArray.add(String.valueOf(product));
        adapter.notifyDataSetChanged();

        this.writeListToFile();
    }

    public void removeProduct(String product){
        mStringArray.remove(String.valueOf(product));
        adapter.notifyDataSetChanged();

        this.writeListToFile();

    }

    public void writeListToFile()
    {
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
}
