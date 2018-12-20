package com.android.fronc.project_11;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextID;
    private EditText editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextID = (EditText) findViewById(R.id.et_id);
        editTextContent = (EditText) findViewById(R.id.et_content);
    }

    public void onClickAdd(View view) {
        ContentValues values = new ContentValues();
        values.put(SimpleContentProvider.ID, editTextID.getText().toString());
        values.put(SimpleContentProvider.CONTENT, editTextContent.getText().toString());

        Uri uri = getContentResolver().insert(SimpleContentProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickDelete(View view) {

    }

    public void onClickUpdate(View view) {

    }
}
