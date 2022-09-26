package com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers;

import android.os.AsyncTask;

import com.zebra.rfid.api3.TagData;

public class AsyncDataUpdate extends AsyncTask<TagData[], Void, Void> {
    private ResponseHandlerInterface responseHandlerInterface;

    public AsyncDataUpdate(ResponseHandlerInterface responseHandlerInterface) {
        this.responseHandlerInterface = responseHandlerInterface;
    }

    @Override
    protected Void doInBackground(TagData[]... params) {
        responseHandlerInterface.handleTagdata(params[0]);
        return null;
    }
}