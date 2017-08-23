package com.tigerrobocop.liv.pn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.R;

import java.util.List;

/**
 * Created by Livia on 22/08/2017.
 */

public class APODAdapter extends ArrayAdapter<APOD> {

    public APODAdapter(Context context, List<APOD> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.mImg = (ImageView)convertView.findViewById(R.id.img_image);
            viewHolder.mTxtCopyright = (TextView)convertView.findViewById(R.id.lbl_copyright);
            viewHolder.mTxtTitle = (TextView)convertView.findViewById(R.id.lbl_title);

            convertView.setTag(viewHolder);
        }else  {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        APOD item = getItem(position);

        if(item != null){
            Picasso.with(getContext()).load(item.url).into(viewHolder.mImg);
            viewHolder.mTxtTitle.setText(item.title);
            viewHolder.mTxtCopyright.setText(item.copyright);
        }

        return convertView;
    }
}

class ViewHolder {
    ImageView mImg;
    TextView mTxtTitle;
    TextView mTxtCopyright;
}