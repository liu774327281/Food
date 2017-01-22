package org.mobiletrain.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.mobiletrain.food.R;
import org.mobiletrain.food.bean.Food;

import java.util.List;

/**
 * Created by wangsong on 2016/6/15.
 */
public class BlankFGLvAdapter extends BaseAdapter {
    private List<Food> list;
    private Context context;
    private LayoutInflater inflater;

    public BlankFGLvAdapter(Context context, List<Food> list) {
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
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.foodImg = (ImageView) convertView.findViewById(R.id.food_img);
            viewHolder.description = (TextView) convertView.findViewById(R.id.food_description);
            viewHolder.keywords = (TextView) convertView.findViewById(R.id.keywords);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Food food = list.get(position);
        viewHolder.description.setText(food.getDescription());
        viewHolder.keywords.setText(food.getKeywords());
        Glide.with(context).load(food.getImg()).centerCrop().placeholder(R.mipmap.ic_launcher)
                .crossFade(1000).into(viewHolder.foodImg);
        return convertView;
    }

    class ViewHolder {
        ImageView foodImg;
        TextView description, keywords;
    }
}
