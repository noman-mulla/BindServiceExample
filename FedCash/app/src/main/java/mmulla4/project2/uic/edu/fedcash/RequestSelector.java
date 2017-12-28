package mmulla4.project2.uic.edu.fedcash;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import mmulla4.project2.uic.edu.treasuryservice.TreasuryService;

public class RequestSelector extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{


    private boolean isBound = false;
    private TreasuryService treasuryService;
    private Spinner spinnerApis,monthlyCashSpinner;
    private TextView yearText;
    private Button getResult,pastResult,unbindService;
    private TextView resultText;
    private EditText dayText,monthText,workingText;

    private static final String API_CALL_MONTHLY_CASH = "MONTHLY CASH";

    private static final String API_CALL_DAILY_CASH = "DAILY CASH";

    private static final String API_CALL_YEARLY_AVG = "YEARLY AVG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_selector);
        //initializes all views
        initializeViews();

    }

    private void initializeViews() {
        spinnerApis = (Spinner) findViewById(R.id.spinnerApis);
        monthlyCashSpinner = (Spinner) findViewById(R.id.monthlyCashSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterApiCalls = ArrayAdapter.createFromResource(this,
                R.array.api_calls, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterApiCalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerApis.setAdapter(adapterApiCalls);
        spinnerApis.setOnItemSelectedListener(this);

        yearText =(TextView) findViewById(R.id.yearText);
        getResult = (Button) findViewById(R.id.getResult);
        resultText = (TextView) findViewById(R.id.resultText);
        dayText = (EditText) findViewById(R.id.dayText);
        monthText = (EditText) findViewById(R.id.monthText);
        workingText = (EditText) findViewById(R.id.workingText);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterYears = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        monthlyCashSpinner.setAdapter(adapterYears);
        monthlyCashSpinner.setOnItemSelectedListener(this);
        pastResult = (Button) findViewById(R.id.pastResult);
        unbindService = (Button) findViewById(R.id.unbindService);

        getResult.setOnClickListener(this);
        pastResult.setOnClickListener(this);
        unbindService.setOnClickListener(this);

    }


    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //binds to service if not bound
        if(!isBound){
            boundService();
        }
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {

            treasuryService = TreasuryService.Stub.asInterface(iservice);

            isBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {

            treasuryService = null;

            isBound = false;

        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       int parentId = parent.getId();

        switch(parentId){
            case R.id.spinnerApis:
                //Handles visibility of different views on the basis of api selection

                switch (position){
                    case 0:
                        //select one
                        resultText.setText("");
                        yearText.setVisibility(View.GONE);
                        monthlyCashSpinner.setVisibility(View.GONE);
                        resultText.setVisibility(View.GONE);
                        getResult.setVisibility(View.GONE);
                        dayText.setVisibility(View.GONE);
                        monthText.setVisibility(View.GONE);
                        workingText.setVisibility(View.GONE);

                        break;
                    case 1:
                        //monthly cash
                        resultText.setText("");
                        yearText.setVisibility(View.VISIBLE);
                        monthlyCashSpinner.setSelection(0);
                        monthlyCashSpinner.setVisibility(View.VISIBLE);
                        getResult.setVisibility(View.VISIBLE);
                        resultText.setVisibility(View.VISIBLE);
                        dayText.setVisibility(View.GONE);
                        monthText.setVisibility(View.GONE);
                        workingText.setVisibility(View.GONE);
                        break;
                    case 2:
                        //daily cash
                        resultText.setText("");
                        monthText.setText("");
                        dayText.setText("");
                        workingText.setText("");
                        yearText.setVisibility(View.VISIBLE);
                        monthlyCashSpinner.setSelection(0);
                        monthlyCashSpinner.setVisibility(View.VISIBLE);
                        resultText.setVisibility(View.VISIBLE);
                        getResult.setVisibility(View.VISIBLE);
                        dayText.setVisibility(View.VISIBLE);
                        monthText.setVisibility(View.VISIBLE);
                        workingText.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        //yearly average
                        resultText.setText("");
                        yearText.setVisibility(View.VISIBLE);
                        monthlyCashSpinner.setSelection(0);
                        monthlyCashSpinner.setVisibility(View.VISIBLE);
                        resultText.setVisibility(View.VISIBLE);
                        getResult.setVisibility(View.VISIBLE);
                        dayText.setVisibility(View.GONE);
                        monthText.setVisibility(View.GONE);
                        workingText.setVisibility(View.GONE);

                        break;
                }
                break;

        }





    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void boundService(){
        //binds to service if not yet bound
        if(!isBound){
            boolean b = false;
            Intent i = new Intent(TreasuryService.class.getName());

            ResolveInfo info = getPackageManager().resolveService(i, PackageManager.MATCH_ALL);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
            if (b) {
                Log.i("Service Binding", "bindService() succeeded!");
            } else {
                Log.i("Service Binding", "bindService() failed!");
            }
        }
    }

    private Handler mHandler = new Handler() ;

    @Override
    public void onClick(View v) {
        //manages all button click

        switch (v.getId()){


            case R.id.getResult:
                //Click to get Result from API

        final DatabaseHandler databaseHandler = new DatabaseHandler(this);


        boundService();

        int spinnerApi = spinnerApis.getSelectedItemPosition();

        switch (spinnerApi) {
            //checks which api call is selected
            case 1:
                //monthly cash
                if (isBound) {


                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int year = Integer.parseInt(monthlyCashSpinner.getSelectedItem().toString());
                                //call treasury service to get Monthly cash
                                final List<Integer> monthlyCash = treasuryService.monthlyCash(year);
                                Thread.sleep(3000);
                                if (!monthlyCash.isEmpty()) {
                                    ApiResult apiResult = new ApiResult();
                                    apiResult.setApiCall(API_CALL_MONTHLY_CASH);
                                    apiResult.setApiValues("Year = " + year);
                                    apiResult.setApiResult("Result - " + monthlyCash.toString());
                                    //stores the result in Database for future reference
                                    databaseHandler.insertRow(apiResult);

                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //display the result on textview in UI Thread
                                            resultText.setText(monthlyCash.toString());
                                        }
                                    });

                                }
                            } catch (RemoteException r) {
                                Log.e("Remote Exception", r.toString());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    t1.start();

                }

                break;
            case 2:
                //daily cash
                if (isBound) {

                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int dayOfMonth = Integer.parseInt(dayText.getText().toString());
                                int monthOfYear = Integer.parseInt(monthText.getText().toString());
                                int year = Integer.parseInt(monthlyCashSpinner.getSelectedItem().toString());
                                int workingDay = Integer.parseInt(workingText.getText().toString());
                                //calls treasury service to get daily cash
                                final List<Integer> dailyCash = treasuryService.dailyCash(dayOfMonth, monthOfYear, year, workingDay);

                                Thread.sleep(3000);
                                if (!dailyCash.isEmpty()) {

                                    ApiResult apiResult = new ApiResult();
                                    apiResult.setApiCall(API_CALL_DAILY_CASH);
                                    apiResult.setApiValues("Year = " + year + " Month=" + monthOfYear + " Day=" + dayOfMonth + " Working Days=" + workingDay);
                                    apiResult.setApiResult("Result - " + dailyCash.toString());
                                    //stores result in database
                                    databaseHandler.insertRow(apiResult);


                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //display on ui thread
                                            resultText.setText(dailyCash.toString());
                                        }
                                    });

                                }

                            } catch (RemoteException r) {
                                Log.e("Remote Exception", r.toString());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    t1.start();
                }
                break;
            case 3:
                //yearly average

                if (isBound) {

                    Thread t1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int year = Integer.parseInt(monthlyCashSpinner.getSelectedItem().toString());
                                //calls treasury service to get yearly average
                                final int yearlyAvg = treasuryService.yearlyAvg(year);
                                Thread.sleep(3000);

                                ApiResult apiResult = new ApiResult();
                                apiResult.setApiCall(API_CALL_YEARLY_AVG);
                                apiResult.setApiValues("Year = " + year);
                                apiResult.setApiResult("Result - " + yearlyAvg);
                                //stores the result in database
                                databaseHandler.insertRow(apiResult);

                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //show the result on ui
                                        resultText.setText(String.valueOf(yearlyAvg));
                                    }
                                });


                            } catch (RemoteException r) {
                                Log.e("Remote Exception", r.toString());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    t1.start();

                }


                break;
        }
        break;

            case R.id.pastResult:
                //calls fragment activity to display past activity

                Intent i = new Intent(this,CallActivity.class);
                startActivity(i);

                break;

            case R.id.unbindService:
                //unbinds service if it is bound
                if (isBound) {

                    unbindService(this.mConnection);
                    isBound = false;

                }

                break;



    }

    }
}
