package vn.nguyen.andrew.appchat.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.nguyen.andrew.appchat.R;
import vn.nguyen.andrew.appchat.fragment.ListView.PopupListView;

/**
 * Created by trunganh on 09/10/2016.
 */
public class PopupAdapter extends ArrayAdapter<PopupListView> {
    private Context c;
    LayoutInflater inflater;
    private List<PopupListView> pl = new ArrayList<PopupListView>();
    public PopupAdapter(Context context, List<PopupListView> pl) {
        super(context, R.layout.popup_layout, pl);
        this.c = context;
        this.pl = pl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.popup_layout, parent, false);
        }
        PopupListView plview = pl.get(position);
        TextView contentText = (TextView) view.findViewById(R.id.popup_content);

        String content = plview.getContent();

        contentText.setText(content);
        return view;
    }
}
