/******************************************/
/** Name         : Blaise LUBUMA
 /** Student ID   : S2219962
 /** Programme of Study : COMPUTING YEAR 3
 /*******************************************/

package gcu.mpd.lubuma_blaise_s2219962;

import java.io.Serializable;

public class myCurrencyRate implements Serializable {

    private String currCode;
    private String currName;
    private double currRate;
    private String rateDate;
    private String rateTime;

    public myCurrencyRate() {
        currCode ="";
        currName = "";
        currRate = 0.0000;
        rateDate = "";
        rateTime = "";
    }

    public myCurrencyRate(String mycurr, String mycurrName, double myRate, String myDate, String myTime){
        currCode = mycurr;
        currName = mycurrName;
        currRate = myRate;
        rateDate = myDate;
        rateTime = myTime;
    }

    public String getcurr() {
        return currCode;
    }

    public void setcurr(String curr) {
        this.currCode = curr;
    }

    public String getcurrName() {
        return currName;
    }

    public void setcurrName(String currName) {
        this.currName = currName;
    }

    public double getRate() {
        return currRate;
    }

    public void setRate(double rate) {
        this.currRate = rate;
    }

    public String getDate() {
        return rateDate;
    }

    public void setDate(String date) {
        this.rateDate = date;
    }

    public String getTime() {
        return rateTime;
    }

    public void setTime(String time) {
        this.rateTime = time;
    }

    public String toString(){
        String temp;
        //temp = currName +  " " + currCode + " " + currRate + " " +  rateDate + " " + rateTime;

        temp = currName +  " - " + currCode + "\n" + currRate;
        //String test [] = new String[Integer.parseInt(temp)];
        //temp = currName ;
        return temp;
    }
}
