package mmulla4.project2.uic.edu.fedcash;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class CallActivity extends Activity implements CallListFragment.ListSelectionListener{

    public static String[] mcallListArray;
    public static String[] mcallResultArray;

    private FrameLayout callList, callResult;
    private FragmentManager mFragmentManager;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private CallResultFragment callResultFragment;

    private static final String TAG_RETAINED_FRAGMENT = "CallResultFragment";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Get the string arrays with the Landmarks and urls
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<ApiResult> apiResultList = databaseHandler.getAllResults();

        List<String> apiCallList = new ArrayList<>();
        List<String> apiCallResultList = new ArrayList<>();
        for(ApiResult apiResult : apiResultList){
            apiCallList.add(apiResult.getApiCall() +"\n"+apiResult.getApiValues());
            apiCallResultList.add(apiResult.getApiResult());

        }
        mcallListArray = new String[apiCallList.size()];
        mcallResultArray = new String[apiCallResultList.size()];
        int z=0;
       for(String str : apiCallList){
           mcallListArray[z] = str;
           z++;
       }
       z=0;
        for(String str : apiCallResultList){
            mcallResultArray[z] = str;
            z++;
        }
        z=0;


        setContentView(R.layout.activity_calls);

        // Get references to the CallListFragment and to the CallResultFragment
        callList = (FrameLayout) findViewById(R.id.call_list);
        callResult = (FrameLayout) findViewById(R.id.call_result);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        callResultFragment = (CallResultFragment) mFragmentManager.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if(callResultFragment == null){
            callResultFragment = new CallResultFragment();
        }

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        //Add CallListFragment to the Layout
        fragmentTransaction.replace(R.id.call_list,
                new CallListFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });



    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setLayout();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setLayout();
        }
    }

    private void setLayout() {

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if(!callResultFragment.isAdded()){
                callList.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                callResult.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                callList.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
                callResult.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
        } else {
            if(!callResultFragment.isAdded()){
                callList.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                callResult.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                // Make the CallList take 1/3 of the layout's width
                callList.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the CallResult take 2/3's of the layout's width
                callResult.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
        }
    }

    @Override
    public void onListSelection(int index) {
        // If the CallResult has not been added, add it now
        if (!callResultFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            // Add the callresult to the layout
            fragmentTransaction.add(R.id.call_result,
                    callResultFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (callResultFragment.getShownIndex() != index) {
            // Tell the callresult to show the quote string at position index
            callResultFragment.showCallAtIndex(index);

        }
    }

}




