package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todoapp.backend.AppViewModel;
import com.example.todoapp.backend.ToDo;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    private EditText editText;
    private MaterialButton button;
    private TextView textView;
    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.update_item_ET);
        button = findViewById(R.id.update_button);
        textView = findViewById(R.id.update_item_TV);

        String s = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        editText.setText(s);
        textView.setText("Update " + s);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(MainActivity.isMVC){
                    intent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
                    intent.putExtra(MainActivity.KEY_ITEM_POSITION,
                            getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                }else{
                    viewModel = new ViewModelProvider(EditActivity.this)
                            .get(AppViewModel.class);
                    viewModel.getAllTodoItems().observe(EditActivity.this,
                            new Observer<List<ToDo>>() {
                        @Override
                        public void onChanged(List<ToDo> toDos) {
                            ToDo newItem = toDos.get(
                                    getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                            newItem.setTodo(editText.getText().toString());
                            viewModel.updateToDoItem(newItem);
                        }
                    });
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}