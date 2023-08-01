/******************************************/
/** Name         : Blaise LUBUMA
 /** Student ID   : S2219962
 /** Programme of Study : COMPUTING YEAR 3
 /*******************************************/

package gcu.mpd.lubuma_blaise_s2219962;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class Fragment_1 extends Fragment implements View.OnClickListener, Serializable {

    private Fragment1Listener listener;
    private TextView FrTextView;
    private TextView fromText;
    private TextView destText;
    private TextView directionText;
    private TextView mResult;

    private String mCurr = null;
    private String mNameCurr;
    private String mDate;
    private double mRate;
    private EditText codeCurrEdit;
    private EditText amount;
    private Button okButton;
    private Button convertButton;
    private View fr1MainView;
    private Button back;
    private Button clear;

    public interface Fragment1Listener {

        void onInputSendDisplayTypeSelection(String input);

    }

    public Fragment_1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__1, container, false);
        FrTextView = (TextView) v.findViewById(R.id.Fr1TextView);
        fromText = (TextView) v.findViewById(R.id.textViewFrom);
        destText = (TextView) v.findViewById(R.id.textViewDestination);
        directionText = (TextView) v.findViewById(R.id.textViewDirection);
        directionText.setOnClickListener(this);
        mResult = (TextView) v.findViewById(R.id.result);
        codeCurrEdit = (EditText) v.findViewById(R.id.editTextCodeCurr);
        amount = (EditText) v.findViewById(R.id.editTextAmount);
        okButton = (Button) v.findViewById(R.id.button_Ok);
        okButton.setOnClickListener(this);
        convertButton = (Button) v.findViewById(R.id.button_Convert);
        convertButton.setVisibility(View.GONE);
        convertButton.setOnClickListener(this);
        fr1MainView = (View) v.findViewById(R.id.fragment1);
        fr1MainView.setBackgroundColor(Color.GRAY);
        back = (Button) v.findViewById(R.id.buttonBack);
        clear = (Button) v.findViewById(R.id.buttonClear);
        back.setOnClickListener(this);
        clear.setOnClickListener(this);
        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    int flag = 0;
    public void onClick(View v) {
        ArrayList<myCurrencyRate> items = (ArrayList<myCurrencyRate>) getArguments().getSerializable("bundle_key");
        ArrayList<myCurrencyRate> allCurrencies = new ArrayList<>();
        allCurrencies.addAll(items);
        String mText = String.valueOf(codeCurrEdit.getText());

        if (v == okButton) {
            String vCode = codeCurrEdit.getText().toString();
            //String vAmount = amount.getText().toString();
            int val = 0;
            while (items.size() > val) {
                for (myCurrencyRate k : items) {
                    if (k.getcurr().equalsIgnoreCase(mText)) {
                        mCurr = k.getcurr();
                        mNameCurr = k.getcurrName();
                        mRate = k.getRate();
                    }
                }
                val++;
            }
            if (vCode.length() == 0) {
                codeCurrEdit.requestFocus();
                codeCurrEdit.setError("FIELD CANNOT BE EMPTY");
            } else if (!vCode.matches("[a-zA-Z ]+")) {
                codeCurrEdit.requestFocus();
                codeCurrEdit.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            } else if (mCurr == null) {
                codeCurrEdit.requestFocus();
                codeCurrEdit.setError("NO CURRENCY FOUND");
            } else {
                Log.e("Code ", String.valueOf(mCurr));
                Log.e("Currency ", String.valueOf(mNameCurr));
                Log.e("Rate ", String.valueOf(mRate));
                FrTextView.setText(" 1GBP = " + mRate + " " + mCurr + " (" + mNameCurr + ")");
                fromText.setText("FROM " + mCurr + " - " + mNameCurr);
                directionText.setText(" TO ");
                destText.setText("GBP - Pound");
                convertButton.setVisibility(View.VISIBLE);
                amount.setHint("Enter amount to convert");
            }

        } else if (v == directionText) {

            if (flag == 0) {
                fromText.setText("FROM GBP - Pound");
                destText.setText(mCurr + " - " + mNameCurr);
                flag = 1;
            } else if (flag == 1) {
                fromText.setText("FROM " + mCurr + " - " + mNameCurr);
                destText.setText("GBP - POUND");
                flag = 0;
            }

        } else if (v == convertButton) {
            String vAmount = amount.getText().toString();
            Log.e("vAmount", vAmount);
            double mAmount;
            Log.e("Flag", String.valueOf(flag) + " " + fromText.getText() + " " + destText.getText());
            if (vAmount.length() == 0) {
                amount.requestFocus();
                amount.setError("FIELD CANNOT BE EMPTY");
            } else if (flag == 1) {
                mAmount = (Double.parseDouble(amount.getText().toString())) * mRate;
                String result = String.format("%.2f", mAmount);
                mResult.setText(amount.getText() + " " + "GBP" + " ==> " + result + " " + mCurr);
            } else if (flag == 0) {
                mAmount = (Double.parseDouble(amount.getText().toString())) / mRate;
                String result = String.format("%.2f", mAmount);
                mResult.setText(amount.getText() + " " + mCurr + " ==> " + result + " GBP");
            }
        } else if (v == back) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else if (v == clear) {

        }
    }
}

