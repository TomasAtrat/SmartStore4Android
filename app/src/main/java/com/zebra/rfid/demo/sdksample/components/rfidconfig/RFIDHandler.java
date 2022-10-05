package com.zebra.rfid.demo.sdksample.components.rfidconfig;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zebra.rfid.api3.Antennas;
import com.zebra.rfid.api3.Antennas.RFMode;
import com.zebra.rfid.api3.DYNAMIC_POWER_OPTIMIZATION;
import com.zebra.rfid.api3.ENUM_TRANSPORT;
import com.zebra.rfid.api3.ENUM_TRIGGER_MODE;
import com.zebra.rfid.api3.INVENTORY_STATE;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RFID_EVENT_TYPE;
import com.zebra.rfid.api3.RFModes;
import com.zebra.rfid.api3.ReaderDevice;
import com.zebra.rfid.api3.Readers;
import com.zebra.rfid.api3.RfidEventsListener;
import com.zebra.rfid.api3.SCAN_BATCH_MODE;
import com.zebra.rfid.api3.SESSION;
import com.zebra.rfid.api3.SL_FLAG;
import com.zebra.rfid.api3.START_TRIGGER_TYPE;
import com.zebra.rfid.api3.STOP_TRIGGER_TYPE;
import com.zebra.rfid.api3.TriggerInfo;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.actionStrategies.RfidActionStrategy;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.factories.RfidConfigFactory;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.factories.RfidFactory;
import com.zebra.rfid.demo.sdksample.components.rfidconfig.rfidEventHandlers.ResponseHandlerInterface;

import java.util.ArrayList;

public class RFIDHandler implements Readers.RFIDReaderEventHandler {

    final static String TAG = "RFID_SAMPLE";

    private static Readers readers;
    private static ArrayList<ReaderDevice> availableRFIDReaderList;
    private static ReaderDevice readerDevice;
    private static RFIDReader reader;
    private RfidEventsListener eventHandler;

    TextView textView;
    private ResponseHandlerInterface responseHandlerInterface;
    private RfidUseCase useCase;
    private AppCompatActivity context;
    private RfidActionStrategy actionStrategy;

    private int MAX_POWER = 300;
    String readername = "RFD8500123";

    public static RFIDReader getReader() {
        return reader;
    }

    public void onCreate(AppCompatActivity activity,
                         ResponseHandlerInterface responseHandlerInterface,
                         RfidUseCase useCase,
                         TextView statusTextViewRFID) {

        context = activity;
        textView = statusTextViewRFID;
        this.responseHandlerInterface = responseHandlerInterface;
        this.useCase = useCase;

        InitSDK();
    }

    private boolean isReaderConnected() {
        if (reader != null && reader.isConnected())
            return true;
        else {
            Log.d(TAG, "reader is not connected");
            return false;
        }
    }

    private void InitSDK() {
        Log.d(TAG, "InitSDK");
        if (readers == null) {
            new CreateInstanceTask().execute();
        } else
            new ConnectionTask().execute();
    }

    private synchronized void GetAvailableReader() {
        Log.d(TAG, "GetAvailableReader");
        if (readers != null) {
            readers.attach(this);
            try {
                if (readers.GetAvailableRFIDReaderList() != null) {
                    availableRFIDReaderList = readers.GetAvailableRFIDReaderList();
                    if (availableRFIDReaderList.size() != 0) {
                        // if single reader is available then connect it
                        if (availableRFIDReaderList.size() == 1) {
                            readerDevice = availableRFIDReaderList.get(0);
                            reader = readerDevice.getRFIDReader();
                        } else {
                            // search reader specified by name
                            for (ReaderDevice device : availableRFIDReaderList) {
                                if (device.getName().equals(readername)) {
                                    readerDevice = device;
                                    reader = readerDevice.getRFIDReader();
                                }
                            }
                        }
                    }
                }
            } catch (InvalidUsageException ie) {
                ie.printStackTrace();
            }
        }
    }

    private synchronized String connect() {
        if (reader != null) {
            Log.d(TAG, "connect " + reader.getHostName());
            try {
                if (!reader.isConnected()) {
                    reader.connect();
                    ConfigureReader();
                    return "Conectado";
                }
            } catch (InvalidUsageException e) {
                e.printStackTrace();
            } catch (OperationFailureException e) {
                e.printStackTrace();
                Log.d(TAG, "OperationFailureException " + e.getVendorMessage());
                String des = e.getResults().toString();
                return "Conexión fallida: " + des;
            }
        }
        return "";
    }

    private void ConfigureReader() {
        Log.d(TAG, "ConfigureReader " + reader.getHostName());
        if (reader.isConnected()) {
            TriggerInfo triggerInfo = new TriggerInfo();
            triggerInfo.StartTrigger.setTriggerType(START_TRIGGER_TYPE.START_TRIGGER_TYPE_IMMEDIATE);
            triggerInfo.StopTrigger.setTriggerType(STOP_TRIGGER_TYPE.STOP_TRIGGER_TYPE_IMMEDIATE);

            try {
                // receive events from reader
                RfidConfigFactory rfidConfigFactory = RfidFactory.getRfidConfigFactory(useCase, reader, responseHandlerInterface);
                if (eventHandler == null)
                    eventHandler = rfidConfigFactory.createEventHandler();

                actionStrategy = rfidConfigFactory.createRfidActionStrategy();

                reader.Events.addEventsListener(eventHandler);

                reader.Events.setHandheldEvent(true);

                reader.Events.setTagReadEvent(true);

                reader.Events.setAttachTagDataWithReadEvent(false);

                reader.Config.setTriggerMode(ENUM_TRIGGER_MODE.RFID_MODE, true);

                reader.Config.setStartTrigger(triggerInfo.StartTrigger);
                reader.Config.setStopTrigger(triggerInfo.StopTrigger);

                reader.Config.setUniqueTagReport(true);

                MAX_POWER = reader.ReaderCapabilities.getTransmitPowerLevelValues().length - 1;

                reader.Config.Antennas.setAntennaRfConfig(1, rfidConfigFactory.createAntennaRfConfig());

                reader.Config.Antennas.setSingulationControl(1, rfidConfigFactory.createSingulationControl());

                reader.Actions.PreFilters.deleteAll();

            } catch (InvalidUsageException | OperationFailureException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void disconnect() {
        Log.d(TAG, "disconnect " + reader);
        try {
            if (reader != null) {
                reader.Events.removeEventsListener(eventHandler);
                reader.disconnect();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Disconnected");
                    }
                });
            }
        } catch (InvalidUsageException e) {
            e.printStackTrace();
        } catch (OperationFailureException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void dispose() {
        try {
            if (readers != null) {
                reader = null;
                readers.Dispose();
                readers = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void executeRfidAction(String [] params) {
        if (!isReaderConnected())
            return;
        try {
            this.actionStrategy.performRfidAction(params);
        } catch (InvalidUsageException | OperationFailureException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopRfidAction() {
        if (!isReaderConnected())
            return;
        try {
            this.actionStrategy.stopRfidAction();
        } catch (InvalidUsageException | OperationFailureException e) {
            e.printStackTrace();
        }
    }

    //region AsyncTasks

    private class CreateInstanceTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "CreateInstanceTask");
            InvalidUsageException invalidUsageException = null;
            readers = new Readers(context, ENUM_TRANSPORT.ALL);
            try {
                availableRFIDReaderList = readers.GetAvailableRFIDReaderList();
            } catch (InvalidUsageException e) {
                e.printStackTrace();
            }
            if (invalidUsageException != null) {
                readers.Dispose();
                readers = null;
                if (readers == null) {
                    readers = new Readers(context, ENUM_TRANSPORT.BLUETOOTH);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new ConnectionTask().execute();
        }
    }

    private class ConnectionTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            Log.d(TAG, "ConnectionTask");
            GetAvailableReader();
            if (reader != null)
                return connect();
            return "Failed to find or connect reader";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }
    }

    //endregion

    // region Reader Appearance events
    @Override
    public void RFIDReaderAppeared(ReaderDevice readerDevice) {
        Log.d(TAG, "RFIDReaderAppeared " + readerDevice.getName());
        new ConnectionTask().execute();
    }

    @Override
    public void RFIDReaderDisappeared(ReaderDevice readerDevice) {
        Log.d(TAG, "RFIDReaderDisappeared " + readerDevice.getName());
        if (readerDevice.getName().equals(reader.getHostName()))
            disconnect();
    }

    //endregion

    //region Activity life cycle behavior

    public String onResume() {
        return connect();
    }

    public void onPause() {
        disconnect();
    }

    public void onDestroy() {
        dispose();
    }

    //endregion

}
