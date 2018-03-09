package com.di.jchannel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.di.jchannel.Model.Content;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by schecterza on 6/7/2017 AD.
 */

public class ContentAdapterReV extends RecyclerView.Adapter<ContentAdapterReV.ViewHolder> {

    Context mContext;
    ArrayList<String> tmpBg;
    ArrayList<String> tmpTitles;
    String[] titles;
    String[] bg;
    private AdapterView.OnItemClickListener onItemClickListener;

    public ContentAdapterReV(Context context, List<Content> contents, int size, AdapterView.OnItemClickListener onItemClickListener) {

        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
        titles = new String[size];
        bg = new String[size];
        tmpBg = new ArrayList<>();
        tmpTitles = new ArrayList<>();

        for (Content content : contents) {

            tmpTitles.add(content.getTitle());
            tmpBg.add((content.getBgImage().replaceAll("\\s", "%20")));

        }

        tmpTitles.toArray(titles);
        tmpBg.toArray(bg);

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgViewContent;
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            imgViewContent = (ImageView) itemView.findViewById(R.id.imgview_content);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(null, v, getAdapterPosition(), v.getId());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (!titles[position].isEmpty()) {
            holder.tvContent.setText(titles[position]);
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.bringToFront();
        }

        ImageView imageView = holder.imgViewContent;
        Glide.with(mContext).load(bg[position]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }


}
