 /******************************************/
 /** Name         : Blaise LUBUMA
 /** Student ID   : S2219962
 /** Programme of Study : COMPUTING YEAR 3
 /*******************************************/

package gcu.mpd.lubuma_blaise_s2219962;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
public class myAdapter extends ArrayAdapter<myCurrencyRate> {
    String [] countries;
    int [] flags ;
    Context mContext;
    public myAdapter(@NonNull Context context, String[] countryNames, int [] countryFlags) {
        super(context, R.layout.mylistview);
        this.countries = countryNames;
        this.flags = countryFlags;
        this.mContext = context;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    public int[] getFlags() {
        return flags;
    }

    public void setFlags(int[] flags) {
        this.flags = flags;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return countries.length;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder mViewHolder = new viewHolder();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.mylistview, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (viewHolder)convertView.getTag();
        }

        mViewHolder.mFlag.setImageResource(flags[position]);
        Log.e("FlagsLength", String.valueOf(flags.length));
        mViewHolder.mName.setText(countries[position]);

        return convertView;
    }
    static class viewHolder{
        ImageView mFlag;
        TextView mName;
    }
}
