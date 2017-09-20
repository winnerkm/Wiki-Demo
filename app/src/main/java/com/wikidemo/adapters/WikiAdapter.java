package com.wikidemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wikidemo.R;
import com.wikidemo.activities.WebViewActivity;
import com.wikidemo.model.WikiResponse.Page;
import com.wikidemo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winnerkm on 19/09/17.
 */

public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.MyViewHolder> {
    private Context mContext;
    private List<Page> pageList = new ArrayList<>();

    public WikiAdapter(Context context, List<Page> pages) {
        this.mContext = context;
        this.pageList = pages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(pageList.get(position).getTitle());
        if (pageList.get(position).getTerms() != null
                && pageList.get(position).getTerms().getDescription() != null
                && pageList.get(position).getTerms().getDescription().size() > 0)
            holder.des.setText(pageList.get(position).getTerms().getDescription().get(0));


        if (pageList.get(position).getThumbnail() != null
                && pageList.get(position).getThumbnail().getSource() != null)
            Picasso.with(mContext).load(pageList.get(position).getThumbnail().getSource())
                    .placeholder(R.drawable.place_holder).into(holder.image);

        holder.rowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);

                if (pageList.get(position).getThumbnail() != null
                        && pageList.get(position).getThumbnail().getSource() != null)
                    intent.putExtra("url", Constants.BASE_URL+pageList.get(position).getTitle());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (pageList != null && pageList.size()>0)
            return pageList.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, des;
        public ImageView image;
        RelativeLayout rowContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            des = (TextView) itemView.findViewById(R.id.des);
            image = (ImageView) itemView.findViewById(R.id.image_view);
            rowContainer = (RelativeLayout) itemView.findViewById(R.id.row_container);
        }
    }
}
