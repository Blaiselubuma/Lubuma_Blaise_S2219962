
/**
 * Name         : Blaise LUBUMA
 * /** Student ID   : S2219962
 * /** Programme of Study : COMPUTING YEAR 3
 * /
 *******************************************/
/** Name         : Blaise LUBUMA
 /** Student ID   : S2219962
 /** Programme of Study : COMPUTING YEAR 3
 /*******************************************/

package gcu.mpd.lubuma_blaise_s2219962;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity<Myadapter> extends AppCompatActivity implements OnClickListener, Serializable {
    private Fragment_1 fragment1;
    private Fragment_2 fragment2;
    private Fragment_3 fragment3;
    private Button ok;
    private String result;
    private String url1 = "";
    private String urlSource = "https://www.fx-exchange.com/gbp/rss.xml";
    private ListView listView;
    private static volatile ArrayList mItemsList = null;
    private View mainView;
    private RadioGroup rdGroupCurrency;
    private RadioButton rdList;
    private RadioButton rdSearch;
    private RadioButton rdSummary;
    private TextView rateDate;
    private TextView listViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ok = (Button) findViewById(R.id.button_Ok);
        ok.setOnClickListener(this);
        mainView = (View) findViewById(R.id.mainView);
        mainView.setBackgroundColor(R.drawable.poundimage);
        rateDate = (TextView) findViewById(R.id.mDate);

        rdGroupCurrency = (RadioGroup) findViewById(R.id.rdGroupMain);
        rdList = (RadioButton) findViewById(R.id.rdList);
        rdSearch = (RadioButton) findViewById(R.id.rdSearch);
        rdSummary = (RadioButton) findViewById(R.id.rdSummary);
        rdList.setOnClickListener(this);
        rdSearch.setOnClickListener(this);
        rdSummary.setOnClickListener(this);
        rdGroupCurrency.setEnabled(true);
        rdList.toggle();
    }

    @Override
    public void onClick(View aview) {

        if (aview == ok) {
            if (rdList.isChecked()) {
                ConnectivityManager myConnexion = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = myConnexion.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    // do something
                    startProgress();
                } else {
                    //Toast.makeText(getApplicationContext(),"This app cannot run, please check your Internet Connexion",Toast.LENGTH_SHORT).show();
                    Log.e("Connexion", "Check your Internet connexion");
                    finish();
                    System.exit(0);
                }

            } else if (rdSearch.isChecked()) {
                listView.setAdapter(null);
                fragment1 = new Fragment_1();
                fragment2 = new Fragment_2();

                Bundle bundle = new Bundle();
                bundle.putSerializable("bundle_key", mItemsList);
                fragment1.setArguments(bundle);

                FragmentManager manager1 = getSupportFragmentManager();
                FragmentTransaction transaction1 = manager1.beginTransaction();
                transaction1.replace(R.id.fragmentFr1, fragment1);
                transaction1.commit();

            } else if (rdSummary.isChecked()) {
                listView.setAdapter(null);
                fragment3 = new Fragment_3();

                Bundle bundle = new Bundle();
                bundle.putSerializable("bundle_key", mItemsList);
                fragment3.setArguments(bundle);

                FragmentManager manager3 = getSupportFragmentManager();
                FragmentTransaction transaction3 = manager3.beginTransaction();
                transaction3.replace(R.id.fragmentFr3, fragment3);
                transaction3.commit();
            }
        }
    }

    public void startProgress() {
        new Thread(new Task(urlSource)).start();
    }

    private class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            try {
                aurl = new URL(url);
                yc = aurl.openConnection();

                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                // Throwing away the first 2 header lines before parsing
                while ((inputLine = in.readLine()) != null) {
                    int i = inputLine.indexOf(">");
                    inputLine = inputLine.substring(i + 1);
                    int y = inputLine.indexOf(">");
                    inputLine = inputLine.substring(y + 1);
                    int x = inputLine.indexOf("/ttl>");
                    inputLine = inputLine.substring(x + 5);

                    //Cleaning up the rss feed
                    result = result + inputLine;
                    result = result.replace("null", "");
                    result = result.replace("</lastBuildDate>", "</lastbuilddate>");
                    result = result.replace("</pubDate>", "</pubdate>");
                    result = result.replace("<pubDate>", "<pubdate>");
                    result = result.replace("</channel>", "");
                    result = result.replace("</rss>", "");
                    Log.e("MyTag", result);
                }
                in.close();
            } catch (IOException ae) {
                //Toast.makeText(MainActivity.this, "You have to be connected to the internet for this application to work", Toast.LENGTH_LONG).show();
                finish();
                Log.e("MyTag", "ioexception");
            }
            myCurrencyRate item = null;
            LinkedList<myCurrencyRate> alist = new LinkedList<>();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser myParser = factory.newPullParser();
                myParser.setInput(new StringReader(result));

                int eventType = myParser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        if (myParser.getName().equalsIgnoreCase("item")) {
                            item = new myCurrencyRate();
                        } else if (myParser.getName().equalsIgnoreCase("title")) {
                            String temp = myParser.nextText();
                            //Extract the currency code
                            String currCode = temp.substring(temp.length() - 5);
                            currCode = currCode.substring(1, 4);
                            item.setcurr(currCode);

                            //Extract the currency name
                            int x = temp.indexOf("/");
                            temp = temp.substring(x + 1);
                            temp = temp.substring(0, temp.length() - 5);
                            item.setcurrName(temp);

                        } else if (myParser.getName().equalsIgnoreCase("pubDate")) {
                            String temp = myParser.nextText();
                            String myDate = temp.substring(0, 15);//Extract date
                            String mytime = temp.substring(16, 27);//Extract time
                            item.setDate(myDate);
                            item.setTime(mytime);

                        } else if (myParser.getName().equalsIgnoreCase("Description")) {
                            String temp = myParser.nextText();
                            int i = temp.indexOf("=");
                            temp = temp.substring(i + 2);

                            //Extract rate value
                            temp = temp.split(" ")[0].trim();
                            item.setRate(Double.parseDouble(temp));
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (myParser.getName().equalsIgnoreCase("item")) {
                            alist.add(item);
                        }
                    }
                    eventType = myParser.next();
                }
            } catch (XmlPullParserException ael) {
                Log.e("MyTag", "Parsing error" + ael.toString());
            } catch (IOException ae1) {
                Log.e("MyTag", "IO error during parsing");
            }
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    ArrayList<myCurrencyRate> allCurrencies = new ArrayList<>();
                    allCurrencies.addAll(alist);
                    mItemsList = new ArrayList<>(alist);

                    Log.d("UI thread", "I am the UI thread");

                    /**********************Filling a new array with data from arraylist*************/
                    int n = 0;
                    String[] array = new String[alist.size()];
                    String mName = null;
                    String mCode = null;
                    String mDate = null;
                    String mTime = null;

                    for (myCurrencyRate i : alist) {
                        mName = "" + i.getcurrName();
                        mCode = "" + i.getcurr();
                        String mRate = "" + i.getRate();
                        mDate = "" + i.getDate();
                        mTime = "" + i.getTime();
                        String str = mName + " - " + mCode + "\n" + mRate;
                        array[n] = str;
                        n++;
                    }
                    Arrays.sort(array);//Sorting my countries array to match with countries flags
                    /*******************************************************************************/

                    int[] countryFlags = {
                            R.drawable.afghanistan, R.drawable.albania, R.drawable.algeria, R.drawable.angola, R.drawable.argentina, R.drawable.armenia, R.drawable.arubaflorin, R.drawable.australia,
                            R.drawable.azerbaijan, R.drawable.bahamas, R.drawable.bahrain, R.drawable.bangladesh, R.drawable.barbados, R.drawable.belarusbyn, R.drawable.belarusbyr, R.drawable.belize,
                            R.drawable.bermuda, R.drawable.bhutan, R.drawable.bitcoin, R.drawable.bolivia, R.drawable.botswana, R.drawable.brazil, R.drawable.brunei,
                            R.drawable.bulgaria, R.drawable.burundi, R.drawable.cfaxafbceao, R.drawable.cfaxofbceao, R.drawable.cambodia, R.drawable.canada, R.drawable.capeverde, R.drawable.cayman,
                            R.drawable.chile, R.drawable.china, R.drawable.colombia, R.drawable.comoros, R.drawable.congodemocraticrepublicof, R.drawable.convertiblemarkbosniaherzegovina, R.drawable.costarica, R.drawable.croatia,
                            R.drawable.cuba, R.drawable.czechrepublic, R.drawable.denmark, R.drawable.djibouti, R.drawable.dominicanrepublic, R.drawable.eastcaribbean, R.drawable.egypt,
                            R.drawable.eritrea, R.drawable.estonia, R.drawable.ethiopia, R.drawable.europe, R.drawable.falklandislands, R.drawable.fiji, R.drawable.gambia,
                            R.drawable.georgia, R.drawable.ghana, R.drawable.guatemala, R.drawable.guinea, R.drawable.guyana, R.drawable.haiti, R.drawable.honduras,
                            R.drawable.hongkong, R.drawable.hungary, R.drawable.iceland, R.drawable.india, R.drawable.indonesia, R.drawable.iran, R.drawable.iraq, R.drawable.israel,
                            R.drawable.jamaica, R.drawable.japan, R.drawable.jordan, R.drawable.kazakhstan, R.drawable.kenya, R.drawable.kuwait, R.drawable.kyrgyzstan, R.drawable.laos, R.drawable.latvia, R.drawable.lebanon, R.drawable.lesotho, R.drawable.liberia,
                            R.drawable.libya, R.drawable.lithuania, R.drawable.macau, R.drawable.macedonia, R.drawable.madagascar, R.drawable.malawi, R.drawable.malaysia,
                            R.drawable.maldives, R.drawable.mauritania, R.drawable.mauritius, R.drawable.mexico, R.drawable.moldova, R.drawable.mongolia, R.drawable.morocco,
                            R.drawable.mozambique, R.drawable.myanmar, R.drawable.namibia, R.drawable.nepal, R.drawable.netherlandantille, R.drawable.newtaiwan, R.drawable.newzealand,
                            R.drawable.nicaragua, R.drawable.nigeria, R.drawable.northkorea, R.drawable.norway, R.drawable.oman, R.drawable.pacific, R.drawable.pakistan, R.drawable.panama,
                            R.drawable.papuanewguinea, R.drawable.paraguay, R.drawable.peru, R.drawable.philippines, R.drawable.poland, R.drawable.qatar, R.drawable.romania,
                            R.drawable.russia, R.drawable.rwanda, R.drawable.salvadoran, R.drawable.samoa, R.drawable.saotomeandprincipe, R.drawable.saudiarabia, R.drawable.serbia,
                            R.drawable.seychelles, R.drawable.sierraleone, R.drawable.singapore, R.drawable.slovakia, R.drawable.solomonislands, R.drawable.somalia, R.drawable.southafrica, R.drawable.southkorea,
                            R.drawable.srilanka, R.drawable.sthelena, R.drawable.sudan, R.drawable.suriname, R.drawable.swaziland, R.drawable.sweden, R.drawable.switzerland,
                            R.drawable.syria, R.drawable.tajikistan, R.drawable.tanzania, R.drawable.thailand, R.drawable.tonga, R.drawable.trinidadandtobago, R.drawable.tunisia,
                            R.drawable.turkey, R.drawable.turkmenistan, R.drawable.usa, R.drawable.uganda, R.drawable.ukraine, R.drawable.unitedarabemirates, R.drawable.uruguay, R.drawable.uzbekistan, R.drawable.vanuatu, R.drawable.venezuela, R.drawable.vietnam, R.drawable.yemen, R.drawable.zambia, R.drawable.zambia, R.drawable.zimbabwe};

                    rateDate.setText("Date : " + mDate + " " + mTime);
                    listView = (ListView) findViewById(R.id.listView);
                    myAdapter myadapter = new myAdapter(MainActivity.this, array, countryFlags);
                    listView.setAdapter(myadapter);
                    String mCurr = null;
                    String mNameCurr = null;
                    int position = 0;
                    int val = 0;
                    String mRate = null;
                    int aPosition = 0;
                    while (alist.size() > val) {
                        for (myCurrencyRate k : alist) {
                            if (k.getRate() < 1) {
                                mCurr = k.getcurr();
                                mNameCurr = k.getcurrName();
                                mRate = String.valueOf(k.getRate());
                                for(int i=0; i < array.length; i++)
                                    if(array[i].contains(mCurr))
                                        aPosition = i;
                                //listView.getItemAtPosition(aPosition);

                                //listView.getChildAt(aPosition).setBackgroundColor(getResources().getColor(R.color.teal_200));
                                //String test = String.valueOf(listView.getItemAtPosition(aPosition));
                                Log.e("test", String.valueOf(alist.size()));
                                //listView.getChildAt(aPosition).setBackgroundColor(Color.MAGENTA);
                                //listView.setBackgroundColor(Color.GREEN);
                                //position = alist.indexOf(mCurr);
                                //listView.getItemAtPosition(aPosition);
                                listView.getFocusables(aPosition);

                                //listView.setSelection(aPosition);
                                //listView.setSelected(true);
                                //listView.getChildAt(aPosition).setBackgroundColor(Color.MAGENTA);
                                //listView.setBackgroundColor(Color.GREEN);
                                //listView.setSelector(android.R.color.holo_green_light);
                                Log.e("High rate", mRate + " " + mCurr + " " + aPosition);
                                //Log.e("current item",mCurrentItem);
                                //listView.getChildAt(test).setBackgroundColor(getResources().getColor(R.color.teal_200));
                            }
                        }
                        val++;
                    }




                    /************Selecting item in the listview and send its value to a fragment*************/
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> myView, View view, int position, long id) {
                            Bundle bundle = new Bundle();
                            String currentItem = null;
                            if (position < myView.getCount()) {
                                currentItem = String.valueOf((array[position].toString()));
                                int z = currentItem.indexOf("-");
                                currentItem = currentItem.substring(z + 2, z + 5);
                            }

                            listView.setAdapter(null);
                            bundle.putSerializable("myCurrentItem", currentItem);
                            bundle.putSerializable("bundle_key", mItemsList);

                            FragmentManager manager2 = getSupportFragmentManager();
                            FragmentTransaction transaction2 = manager2.beginTransaction();
                            Fragment_2 fraCurrentItem = new Fragment_2();
                            fraCurrentItem.setArguments(bundle);
                            transaction2.replace(R.id.fragmentFr2, fraCurrentItem);
                            transaction2.commit();
                        }
                    });
                    /******************************************************************************************/




                }
            });
        }
    }
}
