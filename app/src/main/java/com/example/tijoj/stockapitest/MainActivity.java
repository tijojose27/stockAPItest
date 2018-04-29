package com.example.tijoj.stockapitest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public EditText editText;
    public Button button;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuff = editText.getText().toString();
//                if(!stuff.equals("")) {
//                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
//                    intent.putExtra("TICKER", stuff);
//                    startActivity(intent);
//                }

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    startActivity(intent);
            }
        });
    }
}
