package com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers;

import com.zebra.rfid.api3.TagData;

public interface ResponseHandlerInterface {
        void handleTagdata(TagData[] tagData);

        void handleTriggerPress(boolean pressed);
        //void handleStatusEvents(Events.StatusEventData eventData);
    }