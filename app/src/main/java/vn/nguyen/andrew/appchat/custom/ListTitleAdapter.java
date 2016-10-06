package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by trunganh on 06/10/2016.
 */
public class ListTitleAdapter extends BaseAdapter {
    Context context;
    String text;
    BaseAdapter parentAdapter;

    public ListTitleAdapter(Context c, String textToShow) {
        this(c, textToShow, null);
    }

    public ListTitleAdapter(Context c, String textToShow, BaseAdapter dependentAdapter) {
        super();
        context = c;
        text = textToShow;

        if(dependentAdapter != null){
            parentAdapter = dependentAdapter;
        }
    }

    @Override
    public int getCount() {
        if(parentAdapter != null){
            if(parentAdapter.getCount() == 0){
                return 0;
            }
        }
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = new LinearLayout(context);
        TextView textView = new TextView(context);
        textView.setText(text);

        layout.addView(textView);

        return layout;
    }
}
