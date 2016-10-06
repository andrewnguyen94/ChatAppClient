package vn.nguyen.andrew.appchat.custom;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by trunganh on 06/10/2016.
 */
public class MergerAdapter extends BaseAdapter implements SectionIndexer {
    protected ArrayList<ListAdapter> pieces = new ArrayList<ListAdapter>();
    protected String noItemsText;

    public MergerAdapter(){
        super();
    }

    public void addAdapter(ListAdapter adapter){
        pieces.add(adapter);
        adapter.registerDataSetObserver(new CascadeDataSetObserver());
    }

    @Override
    public int getCount() {
        int total = 0;
        for(ListAdapter piece : pieces){
            total += piece.getCount();
        }
        if(total == 0 && noItemsText != null){
            total = 1;
        }
        return (total);
    }

    @Override
    public Object getItem(int position) {
        for(ListAdapter piece : pieces){
            int size = piece.getCount();
            if(position < size){
                return piece.getItem(position);
            }

            position -= size;
        }
        return (null);
    }

    public void setNoItemsText(String noItemsText) {
        this.noItemsText = noItemsText;
    }

    public ListAdapter getAdapter(int position){
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece);
            }

            position -= size;
        }

        return (null);
    }

    @Override
    public int getViewTypeCount() {
        int total = 0;
        for(ListAdapter piece : pieces){
            total += piece.getViewTypeCount();
        }
        return (Math.max(total, 1));
    }

    @Override
    public int getItemViewType(int position) {
        int typeOffset = 0;
        int result = -1;

        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                result = typeOffset + piece.getItemViewType(position);
                break;
            }

            position -= size;
            typeOffset += piece.getViewTypeCount();
        }

        return (result);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece.isEnabled(position));
            }

            position -= size;
        }

        return (false);
    }

    @Override
    public long getItemId(int position) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece.getItemId(position));
            }

            position -= size;
        }

        return (-1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        for(ListAdapter piece : pieces){
            int size = piece.getCount();

            if(position < size){
                return (piece.getView(position, convertView, parent));
            }

            position -= size;
        }

        if(noItemsText != null){
            TextView textView = new TextView(parent.getContext());
            textView.setText(noItemsText);
            return textView;
        }
        return (null);
    }

    @Override
    public Object[] getSections() {
        ArrayList<Object> sections = new ArrayList<Object>();

        for(ListAdapter piece : pieces){
            if(piece instanceof SectionIndexer){
                Object[] curSections = ((SectionIndexer) piece).getSections();

                if(curSections != null){
                    for (Object section : curSections) {
                        sections.add(section);
                    }
                }
            }
        }
        if (sections.size() == 0) {
            return (null);
        }

        return (sections.toArray(new Object[0]));
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        int position = 0;
        for(ListAdapter piece : pieces){
            if(piece instanceof SectionIndexer){
                Object[] sections = ((SectionIndexer) piece).getSections();
                int numSections = 0;

                if (sections != null) {
                    numSections = sections.length;
                }

                if (sectionIndex < numSections) {
                    return (position + ((SectionIndexer) piece)
                            .getPositionForSection(sectionIndex));
                } else if (sections != null) {
                    sectionIndex -= numSections;
                }
            }

            position += piece.getCount();
        }
        return (0);
    }

    @Override
    public int getSectionForPosition(int position) {
        int section = 0;

        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                if (piece instanceof SectionIndexer) {
                    return (section + ((SectionIndexer) piece)
                            .getSectionForPosition(position));
                }

                return (0);
            } else {
                if (piece instanceof SectionIndexer) {
                    Object[] sections = ((SectionIndexer) piece).getSections();

                    if (sections != null) {
                        section += sections.length;
                    }
                }
            }

            position -= size;
        }

        return (0);
    }


    private class CascadeDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            notifyDataSetInvalidated();
        }
    }
}
