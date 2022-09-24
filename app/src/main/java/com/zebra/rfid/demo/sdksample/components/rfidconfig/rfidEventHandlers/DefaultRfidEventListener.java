package com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers;

import android.os.AsyncTask;
import android.util.Log;

import com.zebra.rfid.api3.ACCESS_OPERATION_CODE;
import com.zebra.rfid.api3.ACCESS_OPERATION_STATUS;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.api3.RfidReadEvents;
import com.zebra.rfid.api3.RfidStatusEvents;
import com.zebra.rfid.api3.TagData;

public class DefaultRfidEventListener implements RfidEventsListener {

    private static final String TAG = "DefaultRfidEventListener";

    private RFIDReader reader;
    private ResponseHandlerInterface responseHandlerInterface;

    public DefaultRfidEventListener(RFIDReader reader, ResponseHandlerInterface responseHandlerInterface){
        this.reader = reader;
        this.responseHandlerInterface = responseHandlerInterface;
    }

    @Override
    public void eventReadNotify(RfidReadEvents rfidReadEvents) {
        TagData[] myTags = reader.Actions.getReadTags(100);
        if (myTags != null) {
            for (int index = 0; index < myTags.length; index++) {
                Log.d(TAG, "Tag ID " + myTags[index].getTagID());
                if (myTags[index].getOpCode() == ACCESS_OPERATION_CODE.ACCESS_OPERATION_READ &&
                        myTags[index].getOpStatus() == ACCESS_OPERATION_STATUS.ACCESS_SUCCESS) {
                    if (myTags[index].getMemoryBankData().length() > 0) {
                        Log.d(TAG, " Mem Bank Data " + myTags[index].getMemoryBankData());
                    }
                }
                if (myTags[index].isContainsLocationInfo()) {
                    short dist = myTags[index].LocationInfo.getRelativeDistance();
                    Log.d(TAG, "Tag relative distance " + dist);
                }
            }
            // possibly if operation was invoked from async task and still busy
            // handle tag data responses on parallel thread thus THREAD_POOL_EXECUTOR
            new AsyncDataUpdate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myTags);
        }
    }

    @Override
    public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {

    }


    public class AsyncDataUpdate extends AsyncTask<TagData[], Void, Void> {
        @Override
        protected Void doInBackground(TagData[]... params) {
            responseHandlerInterface.handleTagdata(params[0]);
            return null;
        }
    }

}
