package com.android.fronc.project_11;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private EditText editTextID;
    private EditText editTextContent;
    private ListView listViewContents;
    private CustomArrayAdapter customArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextID = (EditText) findViewById(R.id.et_id);
        editTextContent = (EditText) findViewById(R.id.et_content);
        listViewContents = (ListView) findViewById(R.id.lv_contents);
        customArrayAdapter = new CustomArrayAdapter(this,  SimpleContentProvider.dataCollector);
        listViewContents = (ListView) findViewById(R.id.lv_contents);
        listViewContents.setAdapter(customArrayAdapter);
    }

    public void onClickAdd(View view) {
        ContentValues values = new ContentValues();
        values.put(SimpleContentProvider.CONTENT, editTextContent.getText().toString());

        Uri uri = getContentResolver().insert(SimpleContentProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        customArrayAdapter.notifyDataSetChanged();
    }

    public void onClickDelete(View view) {
        Uri uriUpdate = Uri.withAppendedPath(SimpleContentProvider.CONTENT_URI, editTextID.getText().toString());
        String id = editTextID.getText().toString().trim();
        if (id.equals("")) {
            final int count = getContentResolver().delete(SimpleContentProvider.CONTENT_URI, null, null);
            Toast.makeText(getBaseContext(), SimpleContentProvider.CONTENT_URI.toString(), Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), count + " lines has been deleted", Toast.LENGTH_LONG).show();
                }
            }, 4000);
        } else {
            getContentResolver().delete(uriUpdate, id, null);
            Toast.makeText(getBaseContext(), SimpleContentProvider.CONTENT_URI.toString() + "/" + id, Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "1 lines has been deleted", Toast.LENGTH_LONG).show();
                }
            }, 4000);
        }
        customArrayAdapter.notifyDataSetChanged();
    }

    public void onClickUpdate(View view) {
        ContentValues values = new ContentValues();
        Uri uriUpdate;
        String id = editTextID.getText().toString().trim();
        values.put(SimpleContentProvider.CONTENT, editTextContent.getText().toString());

        if (id.equals("")) {
            final int count = getContentResolver().update(SimpleContentProvider.CONTENT_URI, values, null, null);
            Toast.makeText(getBaseContext(), SimpleContentProvider.CONTENT_URI.toString(), Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), count + " lines has been updated", Toast.LENGTH_LONG).show();
                }
            }, 4000);
        } else {
            uriUpdate = Uri.withAppendedPath(SimpleContentProvider.CONTENT_URI, id);
            getContentResolver().update(uriUpdate, values, id, null);
            Toast.makeText(getBaseContext(), SimpleContentProvider.CONTENT_URI.toString() + "/" + id, Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "1 lines has been updated", Toast.LENGTH_LONG).show();
                }
            }, 4000);
        }
        customArrayAdapter.notifyDataSetChanged();
    }
}

class CustomArrayAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private List<String> list;

    public CustomArrayAdapter(Activity activity, List<String> list) {
        super(activity, R.layout.listlayoutforcontentprovider, list);
        this.activity = activity;
        this.list = list;
    }

    static class ViewHolder {
        public TextView tvId;
        public TextView tvContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listlayoutforcontentprovider, null, true);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView) view.findViewById(R.id.tv_id);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvId.setText(String.valueOf(position));
        viewHolder.tvContent.setText(list.get(position));
        return view;
    }
}
