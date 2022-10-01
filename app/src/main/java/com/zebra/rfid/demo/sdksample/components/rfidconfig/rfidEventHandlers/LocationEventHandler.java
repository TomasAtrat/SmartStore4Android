package com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers;

import android.os.AsyncTask;
import android.util.Log;

import com.zebra.rfid.api3.HANDHELD_TRIGGER_EVENT_TYPE;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.api3.RfidReadEvents;
import com.zebra.rfid.api3.RfidStatusEvents;
import com.zebra.rfid.api3.STATUS_EVENT_TYPE;
import com.zebra.rfid.api3.TagData;

public class LocationEventHandler implements RfidEventsListener {

    private static final String TAG = "LocationEventHandler";

    private RFIDReader reader;
    private ResponseHandlerInterface responseHandlerInterface;

    public LocationEventHandler(RFIDReader reader, ResponseHandlerInterface responseHandlerInterface) {
        this.reader = reader;
        this.responseHandlerInterface = responseHandlerInterface;
    }

    @Override
    public void eventReadNotify(RfidReadEvents rfidReadEvents) {
        TagData[] myTags = reader.Actions.getReadTags(100);
        if (myTags != null) {
            for (int index = 0; index < myTags.length; index++) {
                if (myTags[index].isContainsLocationInfo()) {
                    short dist = myTags[index].LocationInfo.getRelativeDistance();
                    Log.d(TAG, "Tag relative distance " + dist);
                }

                if (myTags[index].isContainsMultiTagLocateInfo()) {
                    short dist = myTags[index].MultiTagLocateInfo.getRelativeDistance();
                    Log.d(TAG, "Tag relative distance " + dist);
                }
            }
        }

        new AsyncDataUpdate(responseHandlerInterface).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myTags);
    }

    @Override
    public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {
        Log.d(TAG, "Status Notification: " + rfidStatusEvents.StatusEventData.getStatusEventType());
        if (rfidStatusEvents.StatusEventData.getStatusEventType() == STATUS_EVENT_TYPE.HANDHELD_TRIGGER_EVENT) {
            if (rfidStatusEvents.StatusEventData.HandheldTriggerEventData.getHandheldEvent() == HANDHELD_TRIGGER_EVENT_TYPE.HANDHELD_TRIGGER_PRESSED) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        responseHandlerInterface.handleTriggerPress(true);
                        return null;
                    }
                }.execute();
            }
            if (rfidStatusEvents.StatusEventData.HandheldTriggerEventData.getHandheldEvent() == HANDHELD_TRIGGER_EVENT_TYPE.HANDHELD_TRIGGER_RELEASED) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        responseHandlerInterface.handleTriggerPress(false);
                        return null;
                    }
                }.execute();
            }
        }
    }
}
