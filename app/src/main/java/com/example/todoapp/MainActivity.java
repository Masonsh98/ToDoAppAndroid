package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.backend.AppViewModel;
import com.example.todoapp.backend.ToDo;
import com.google.android.material.button.MaterialButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    public static final int MVVM_TEXT_CODE = 5;
    public static boolean isMVC;

    private List<String> items;
    private RecyclerView recycler;
    private MaterialButton button;
    private EditText editText;
    private ItemAdapter adapter;
    private VmItemAdapter vmAdapter;
    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.item_recycler);
        button = findViewById(R.id.add_button);
        editText = findViewById(R.id.info_ET);


        /**
         * This is the MVC architecture provided to me through the prework guides.
         */

        isMVC = true;
        loadItems();

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                adapter.notifyItemRemoved(position);
                saveItems();
            }
        };

        ItemAdapter.OnClickListener onClickListener = new ItemAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(KEY_ITEM_TEXT, items.get(position));
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(intent,EDIT_TEXT_CODE);
            }
        };

        adapter = new ItemAdapter(items,onLongClickListener,onClickListener);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Add new todo item", Toast.LENGTH_SHORT).show();
                }else{
                    String s = editText.getText().toString();
                    items.add(s);
                    adapter.notifyItemInserted(items.size()-1);
                    Toast.makeText(MainActivity.this, s + " added", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    saveItems();
                }
            }
        });

        /**
         * This is the MVVM architecture code that performs the same functions as the prework
         * requires but uses Android's Room class to eliminate lifecycle errors. Update the comments
         * to see that both ways work. :)
         */
          /*
        isMVC = false;
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        //Set up the onLongClickListener to delete the item in the position.
        VmItemAdapter.OnLongClickListener onLongClickListener = new VmItemAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Toast.makeText(MainActivity.this,
                        "item " + (position + 1) + " has been deleted.",
                        Toast.LENGTH_SHORT).show();
                viewModel.deleteToDoItem(vmAdapter.getItem(position));
                vmAdapter.notifyItemChanged(position);
            }
        };
        //Set up the onClickListener to navigate into the activity where there they can update
        VmItemAdapter.OnClickListener onClickListener = new VmItemAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(KEY_ITEM_TEXT, vmAdapter.getItem(position).getTodo());
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(intent,MVVM_TEXT_CODE);
            }
        };
        //This is where we populate the adapter
        vmAdapter = new VmItemAdapter();
        recycler.setAdapter(vmAdapter);
        vmAdapter.setOnLongClickListener(onLongClickListener);
        vmAdapter.setOnClickListener(onClickListener);
        viewModel.getAllTodoItems().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                vmAdapter.setItems(toDos);
                vmAdapter.notifyDataSetChanged();
            }
        });
        //Setup the buttons logic.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Add new todo item",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String s = editText.getText().toString();
                    viewModel.insertToDoItem(new ToDo(s));
                    vmAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, s + " added",
                            Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }
            }
        });
          */

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "failed loadItems: ", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "saveItems: ", e);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_TEXT_CODE:
                    String itemText = data.getStringExtra(KEY_ITEM_TEXT);
                    int position = data.getExtras().getInt(KEY_ITEM_POSITION);
                    items.set(position, itemText);
                    adapter.notifyItemChanged(position);
                    saveItems();
                    Toast.makeText(this, "item " + (position + 1) + " updated!", Toast.LENGTH_SHORT).show();
                    break;
                case MVVM_TEXT_CODE:
                    break;
                default:
                    Log.w("MainActivity", "Unknown call ");
                    break;
            }
        }
    }
}