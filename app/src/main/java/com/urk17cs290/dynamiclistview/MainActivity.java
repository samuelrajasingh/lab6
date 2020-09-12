package com.urk17cs290.dynamiclistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText input;
    EditText input1;
    List<String> initialList= Arrays.asList("Apple","Dell","HP");
    ArrayList<String> fruitList=new ArrayList<>(initialList);
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,fruitList);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        switch(item.getItemId()){
            case R.id.addId:
                input=new EditText(this);
                input.setGravity(Gravity.CENTER);
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setView(input);
                builder.setTitle("Enter item");
                builder.setPositiveButton("ADD", (dialog,which)->{
                        String data=input.getText().toString();
                        fruitList.add(data);
                        arrayAdapter.notifyDataSetChanged();
                }).setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
                builder.show();
                break;
            case R.id.editId:
                input1=new EditText(this);
                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setView(input1);
                builder1.setTitle("Edit here");
                final AdapterView.AdapterContextMenuInfo menuinfo1= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                builder1.setPositiveButton("EDIT", (dialog, which) -> {
                    String data=input1.getText().toString();
                    int index1=menuinfo1.position;
                    fruitList.remove(index1);
                    fruitList.add(index1,data);
                    arrayAdapter.notifyDataSetChanged();

                });
                builder1.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
                builder1.show();
                break;
            case R.id.deleteId:
                AdapterView.AdapterContextMenuInfo menuinfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index=menuinfo.position;
                fruitList.remove(index);
                arrayAdapter.notifyDataSetChanged();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return true;
    }
}