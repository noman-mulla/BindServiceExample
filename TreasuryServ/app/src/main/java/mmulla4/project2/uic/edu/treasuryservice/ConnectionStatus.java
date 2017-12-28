package mmulla4.project2.uic.edu.treasuryservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ConnectionStatus extends AppCompatActivity {

    TreasuryServiceImpl treasuryService;

    TextView statusService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connection_status);
        statusService = (TextView) findViewById(R.id.statusService);
    }


    @Override
    protected void onResume() {
        super.onResume();

        int status = TreasuryServiceImpl.status;
        String statusMsg = "";
        if(status == 0){
            statusMsg = "Not yet bound";
        }else if (status == 1){
            statusMsg = "Bound to one or more clients but idle";
        }else if(status ==2){
            statusMsg = "Bound and running an API method";
        }
        statusService.setText("Current Serice Status-"+"\n"+statusMsg);
        Log.i("Check","onResume getting called");
    }
}
