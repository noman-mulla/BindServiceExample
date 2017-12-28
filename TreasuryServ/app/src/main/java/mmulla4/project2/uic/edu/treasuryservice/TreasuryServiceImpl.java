package mmulla4.project2.uic.edu.treasuryservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TreasuryServiceImpl extends Service {

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static int status=0;

   // private static final String MONTHLY_CASH_QUERY = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=select%20*%20from%20(SELECT%20%22date%22,%20%22year%22,%20%22month%22,%20%22day%22,%20%22weekday%22,%20%22is_total%22,%20%22open_today%22%20FROM%20t1%20WHERE%20(%22date%22%20%3E%20%272016-01-00%27%20AND%20%22date%22%20%3C%20%272016-12-06%27%20and%20account%20=%20%22Total%20Operating%20Balance%22)%20order%20by%20%22date%22)%20where%20%22day%22%20%3C%206";

    private final TreasuryService.Stub mBinder = new TreasuryService.Stub() {


        @Override
        public List monthlyCash(int year) throws RemoteException {

                status = 2;


            StringBuffer MONTHLY_CASH_QUERY = new StringBuffer("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=select%20*%20from%20(SELECT%20%22date%22,%20%22year%22,%20%22month%22,%20%22day%22,%20%22weekday%22,%20%22is_total%22,%20%22open_today%22%20FROM%20t1%20WHERE%20(%22date%22%20%3E%20%27");
            MONTHLY_CASH_QUERY.append(year);
            MONTHLY_CASH_QUERY.append("-01-00%27%20AND%20%22date%22%20%3C%20%27");
            MONTHLY_CASH_QUERY.append(year);
            MONTHLY_CASH_QUERY.append("-12-06%27%20and%20account%20=%20%22Total%20Operating%20Balance%22)%20order%20by%20%22date%22)%20where%20%22day%22%20%3C%206");

            StringBuffer buffer = getStringBuffer(MONTHLY_CASH_QUERY);

            //List to hold opening cash for each month's first working day
            List<Integer> cashList = new ArrayList<>();

            int monthCounter = 1;

            //Json to Object

            ObjectMapper mapper = new ObjectMapper();

            try{

                MonthlyCash[] monthlyCashList = mapper.readValue(buffer.toString(), MonthlyCash[].class);
                Log.i("Object - ",monthlyCashList.toString());
                for(MonthlyCash monthlyCash : monthlyCashList){
                    if(monthCounter == Integer.parseInt(monthlyCash.getMonth())){
                        monthCounter++;
                        cashList.add(Integer.parseInt(monthlyCash.getOpen_today()));
                    }
                    if(monthCounter == 13){
                        break;
                    }
                }

            }catch (JsonGenerationException e) {
                Log.e("Error",e.toString());
            } catch (JsonMappingException e) {
                Log.e("Error",e.toString());
            } catch (IOException e) {
                Log.e("Error",e.toString());
            }

                status = 1;


            return cashList;
        }

        @NonNull
        private StringBuffer getStringBuffer(StringBuffer QUERY) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuffer buffer = new StringBuffer();

            try {
                URL url = new URL(QUERY.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));


                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.i("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }



            } catch (MalformedURLException e) {
                Log.e("Error",e.toString());
            } catch (IOException e) {
                Log.e("Error",e.toString());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    Log.e("Error",e.toString());
                }
            }
            return buffer;
        }

        @Override
        public List dailyCash(int day, int month, int year, int workingDays) throws RemoteException {

                status = 2;


            StringBuffer DAILY_CASH_QUERY = new StringBuffer();
            DAILY_CASH_QUERY.append("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT%20%22date%22,%20%22year%22,%20%22month%22,%20%22day%22,%20%22weekday%22,%20%22is_total%22,%20%22open_today%22%20FROM%20t1%20WHERE%20%22date%22%20%3E%20%27");
            DAILY_CASH_QUERY.append(year).append("-").append(month).append("-").append(day);
            DAILY_CASH_QUERY.append("%27%20and%20account%20=%20%22Total%20Operating%20Balance%22%20order%20by%20%22date%22%20limit%20");
            DAILY_CASH_QUERY.append(workingDays).append(";");
            StringBuffer buffer = getStringBuffer(DAILY_CASH_QUERY);
            List<Integer> dailyCashList = new ArrayList<>();
               //Json to Object

            ObjectMapper mapper = new ObjectMapper();

            try{

                MonthlyCash[] monthlyCashList = mapper.readValue(buffer.toString(), MonthlyCash[].class);
                Log.i("Object - ",monthlyCashList.toString());
                for(MonthlyCash monthlyCash : monthlyCashList){

                    dailyCashList.add(Integer.parseInt(monthlyCash.getOpen_today()));

                }

            }catch (JsonGenerationException e) {
                Log.e("Error",e.toString());
            } catch (JsonMappingException e) {
                Log.e("Error",e.toString());
            } catch (IOException e) {
                Log.e("Error",e.toString());
            }

                status = 1;


            return dailyCashList;
        }

        @Override
        public int yearlyAvg(int year) throws RemoteException {

               status = 2;

            // query -
            StringBuffer YEARLY_AVG_QUERY = new StringBuffer();
            YEARLY_AVG_QUERY.append("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=Select%20Avg(open_today)%20as%20avg%20from(SELECT%20%22year%22,%20%22open_today%22%20FROM%20t1%20WHERE%20%22year%22%20=%20%27");
            YEARLY_AVG_QUERY.append(year);
            YEARLY_AVG_QUERY.append("%27%20and%20account%20=%20%22Total%20Operating%20Balance%22);");
            StringBuffer buffer = getStringBuffer(YEARLY_AVG_QUERY);

            //Json to Object

            ObjectMapper mapper = new ObjectMapper();
            int avg=0;


            try{

                 YearlyAvg[] yearlyAvg = mapper.readValue(buffer.toString(), YearlyAvg[].class);
                    avg = (int) Double.parseDouble(yearlyAvg[0].getAvg());

            }catch (JsonGenerationException e) {
                Log.e("Error",e.toString());
            } catch (JsonMappingException e) {
                Log.e("Error",e.toString());
            } catch (IOException e) {
                Log.e("Error",e.toString());
            }


                status = 1;


            return avg;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

            status = 1;

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

            status=0;

        return super.onUnbind(intent);
    }
}
