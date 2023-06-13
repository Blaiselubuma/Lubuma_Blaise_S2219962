package gcu.mpd.lubuma_blaise_s2219962;

public class myCurrencyRate {

    private String currency;
    private String country;
    private double rate;
    private String date;

    public myCurrencyRate() {
        currency ="";
        country = "";
        rate = Double.parseDouble("");
        date = "";
    }

    public myCurrencyRate(String myCurrency, String myCountry, double myRate, String myDate){
        currency = myCurrency;
        country = myCountry;
        rate = myRate;
        date = myDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        String temp;
        temp = currency +  " " + country + " " + rate + " " +  date;
        return temp;
    }
}
