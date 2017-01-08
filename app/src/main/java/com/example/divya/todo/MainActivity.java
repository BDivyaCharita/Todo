package com.example.divya.todo;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> items;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();


        lvItems = (ListView)findViewById(R.id.lvItems);
        items= new ArrayList<String>();
        itemsAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();

    }
    public void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {

                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.custom_dialog);

                final TextView textView = (TextView) dialog.findViewById(R.id.editText);
                Button btnSave          = (Button) dialog.findViewById(R.id.save);
                Button btnCancel        = (Button) dialog.findViewById(R.id.cancel);
                dialog.show();

                if(btnSave.isSelected()){
                    items.remove(position);
                    itemsAdapter.notifyDataSetChanged();
                    saveItems();

                }
                if(btnCancel.isSelected()){
                    itemsAdapter.notifyDataSetChanged();
                    saveItems();
                }

               return  true;
            }
        });
    }



    public void addTodoItem(View view){
        EditText etNewItem= (EditText)findViewById(R.id.etNewItem);
        itemsAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
        saveItems();
    }
    private void readItems(){
        File filesDir= getFilesDir();
        File todoFile=  new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException e){
            items= new ArrayList<String>();
            e.printStackTrace();
        }

    }
    private void saveItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{

            FileUtils.writeLines(todoFile, items);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
