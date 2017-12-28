package mmulla4.project2.uic.edu.fedcash;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by noman on 26/10/17.
 */

public class CallResultFragment extends Fragment {

    private static final String TAG = "CallResultFragment";


    private TextView resultText;

    private int mCurrIdx = -1;

    private int mCallArrLen;


    public void setIndex(int mCurrIdx){
        this.mCurrIdx=mCurrIdx;
    }
    public int getIndex(){
        return mCurrIdx;
    }



    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Landmark Website at position newIndex
    void showCallAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mCallArrLen)
            return;
         setIndex(newIndex);

        resultText.setText(CallActivity.mcallResultArray[getIndex()]);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");


        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        View v = inflater.inflate(R.layout.call_result,
                container, false);
        resultText = (TextView) v.findViewById(R.id.result_text);
        return v;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        resultText = (TextView) getActivity().findViewById(R.id.result_text);
        mCallArrLen = CallActivity.mcallListArray.length;

    }


}
