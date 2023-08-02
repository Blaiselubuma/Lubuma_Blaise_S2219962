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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class Fragment_3 extends Fragment implements View.OnClickListener, Serializable {
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
    private RadioGroup rdGroup;
    private RadioButton mRdDollar;
    private RadioButton mRdJapan;
    private RadioButton mRdEuro;



    public Fragment_3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container, false);
        FrTextView = (TextView) v.findViewById(R.id.Fr3TextView);
        fromText = (TextView) v.findViewById(R.id.textViewFrom);
        destText = (TextView) v.findViewById(R.id.textViewDestination);
        directionText = (TextView) v.findViewById(R.id.textViewDirection);
        directionText.setOnClickListener(this);
        mResult = (TextView) v.findViewById(R.id.result);
        //codeCurrEdit = (EditText) v.findViewById(R.id.editTextCodeCurr);
        amount = (EditText) v.findViewById(R.id.editTextAmount);
        okButton = (Button) v.findViewById(R.id.button_Ok);
        okButton.setOnClickListener(this);
        convertButton = (Button) v.findViewById(R.id.button_Convert);
        convertButton.setVisibility(View.GONE);
        convertButton.setOnClickListener(this);
        fr1MainView = (View) v.findViewById(R.id.fragment3);
        fr1MainView.setBackgroundColor(Color.GRAY);
        back = (Button) v.findViewById(R.id.buttonBack);
        clear = (Button) v.findViewById(R.id.buttonClear);
        back.setOnClickListener(this);
        clear.setOnClickListener(this);
        rdGroup = (RadioGroup) v.findViewById(R.id.Fr3RadioGroup);
        mRdDollar = (RadioButton) v.findViewById(R.id.rdUsd);
        mRdEuro = (RadioButton) v.findViewById(R.id.rdEuro);
        mRdJapan = (RadioButton) v.findViewById(R.id.rdJapan);
        mRdDollar.toggle();

        mRdDollar.setOnClickListener(this);
        mRdEuro.setOnClickListener(this);
        mRdJapan.setOnClickListener(this);

        rdGroup.setEnabled(true);

        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    int flag = 0;
    String mText;
    public void onClick(View v) {
        ArrayList<myCurrencyRate> items = (ArrayList<myCurrencyRate>) getArguments().getSerializable("bundle_key");
        ArrayList<myCurrencyRate> allCurrencies = new ArrayList<>();
        allCurrencies.addAll(items);

        if (v == okButton) {
            if (mRdDollar.isChecked() ) {
                mText = "USD";
            } else if(mRdEuro.isChecked()) {
                mText = "EUR";
            } else if (mRdJapan.isChecked()) {
                mText = "JPY";
            }


            String vCode = mText;
            Log.e("Selected button", mText);

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
           if (mCurr == null) {
                codeCurrEdit.requestFocus();
                codeCurrEdit.setError("NO CURRENCY FOUND");
            } else {
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
            double mAmount;

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

