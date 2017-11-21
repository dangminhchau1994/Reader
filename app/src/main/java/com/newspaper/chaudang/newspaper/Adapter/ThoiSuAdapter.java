package com.newspaper.chaudang.newspaper.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newspaper.chaudang.newspaper.Model.ThoiSu;
import com.newspaper.chaudang.newspaper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chau Dang on 7/31/2017.
 */

public class ThoiSuAdapter extends BaseAdapter {

    Context context;
    ArrayList<ThoiSu> thoiSuArrayList = new ArrayList<>();

    public ThoiSuAdapter(Context context, ArrayList<ThoiSu> thoiSuArrayList) {
        this.context = context;
        this.thoiSuArrayList = thoiSuArrayList;
    }

    @Override
    public int getCount() {
        return thoiSuArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return thoiSuArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.thoisu_row, null);
            holder = new ViewHolder();

            holder.txtTenThoiSu = row.findViewById(R.id.rowtenThoiSu);
            holder.imgHinhThoiSu = row.findViewById(R.id.rowimgThoiSu);

            row.setTag(holder);

        } else {

            holder = (ViewHolder) row.getTag();

        }

        ThoiSu thoiSu = thoiSuArrayList.get(i);

        holder.txtTenThoiSu.setMaxLines(2);
        holder.txtTenThoiSu.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtTenThoiSu.setText(thoiSu.getTenThoiSu());


        Picasso.with(context).load(thoiSu.getHinhanh()).into(holder.imgHinhThoiSu);

        return row;
    }

    public class ViewHolder {

        ImageView imgHinhThoiSu;
        TextView txtTenThoiSu;

    }
}
