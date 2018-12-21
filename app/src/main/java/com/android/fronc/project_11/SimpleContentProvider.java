package com.android.fronc.project_11;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Vector;

public class SimpleContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.android.fronc.project_11.SimpleContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/lines";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String ID = "id";
    static final String CONTENT = "content";

    static final int CONTENTS = 1;
    static final int CONTENT_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "lines", CONTENTS);
        uriMatcher.addURI(PROVIDER_NAME, "lines/#", CONTENT_ID);
    }

    static Vector<String> dataCollector = new Vector<>();

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
//        switch (uriMatcher.match(uri)) {
//            case CONTENTS:
//                return PROVIDER_NAME + "";
//        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri _uri;
        int dcId;

        String dcContent = values.get(CONTENT).toString();
        dataCollector.add(dcContent);
        dcId = dataCollector.indexOf(dcContent);
        _uri = ContentUris.withAppendedId(CONTENT_URI, dcId);
        getContext().getContentResolver().notifyChange(_uri, null);
        System.out.println(dataCollector);

        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        Uri _uri;
        int dcId;
        int deleted = 0;

        switch (uriMatcher.match(uri)) {
            case CONTENTS:
                dataCollector.clear();
                System.out.println(dataCollector);
                break;

            case CONTENT_ID:
                dcId = Integer.valueOf(selection);
                _uri = ContentUris.withAppendedId(CONTENT_URI, dcId);
                dataCollector.add(Integer.valueOf(selection), "<null>");
                getContext().getContentResolver().notifyChange(_uri, null);
                deleted = Integer.valueOf(selection);
                System.out.println(dataCollector);
                break;

            default:
                break;
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        Uri _uri;
        String dcContent;
        int dcId;
        int update = 0;

        switch (uriMatcher.match(uri)) {
            case CONTENTS:
                dcContent = values.get(CONTENT).toString();
                for (int i = 0; i < dataCollector.size(); i++) {
                    dataCollector.set(i, dcContent);
                }
                System.out.println(dataCollector);
                break;

            case CONTENT_ID:
                dcContent = values.get(CONTENT).toString();
                dcId = Integer.valueOf(selection);
                _uri = ContentUris.withAppendedId(CONTENT_URI, dcId);
                dataCollector.add(Integer.valueOf(selection), dcContent);
                getContext().getContentResolver().notifyChange(_uri, null);
                update = Integer.valueOf(selection);
                System.out.println(dataCollector);
                break;

            default:
                break;
        }

        return update;
    }
}
