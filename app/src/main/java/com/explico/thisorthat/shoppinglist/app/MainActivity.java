package com.explico.thisorthat.shoppinglist.app;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.FileOutputStream;


public class MainActivity extends ActionBarActivity  implements EnterProductDialogFragment.NoticeDialogListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    String mFilename = "shoppinglist.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ManageList(), "ManageList")
                    .commit();
        }
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.shopping_list) {
            Fragment newFragment = new ShoppingList();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, newFragment)
                    .commit();
        }
        else if (id == R.id.products) {
            Fragment newFragment = new ManageList();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, newFragment, "ManageList")
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPickProductDialog()
    {
        Log.d(TAG, "ProductDialog - ShowDialog - Add");
        DialogFragment dialog = new EnterProductDialogFragment();
        dialog.show(this.getSupportFragmentManager(), "Add_Product");
    }

    public void showRemoveProductDialog()
    {
        Log.d(TAG, "ProductDialog - ShowDialog - Remove");
        DialogFragment dialog = new EnterProductDialogFragment();
        dialog.show(this.getSupportFragmentManager(), "Remove_Product");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, EditText editText) {
        Log.d(TAG, "ProductDialog - Positive");
        if(editText.getText().toString().trim().length() > 0) {
            ManageList myFragment = (ManageList) getSupportFragmentManager().findFragmentByTag("ManageList");
            if(dialog.getTag().equals("Add_Product"))
                myFragment.addProduct(editText.getText().toString().trim());
            else if (dialog.getTag().equals("Remove_Product"))
                myFragment.removeProduct(editText.getText().toString().trim());
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.d(TAG, "ProductDialog - Negative");
    }
}
