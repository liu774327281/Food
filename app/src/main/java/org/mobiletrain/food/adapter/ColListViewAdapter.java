package org.mobiletrain.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mobiletrain.food.R;
import org.mobiletrain.food.bean.CollectBean;

import java.util.List;

/**
 * Created by wangsong on 2016/6/16.
 */
public class ColListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CollectBean> list;

    public ColListViewAdapter(Context context, List<CollectBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.col_listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.content = ((TextView) convertView.findViewById(R.id.content));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.content.setText(list.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView content;
    }
}
