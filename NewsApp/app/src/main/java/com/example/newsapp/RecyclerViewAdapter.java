package com.example.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newsapp.model.NewsItem;

import java.util.ArrayList;

/**
 * Created by Angie on 6/25/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemHolder> {
    private ArrayList<NewsItem> data;
    ItemClickListener listener;

//    public RecyclerViewAdapter(ArrayList<NewsItem> data) {
//        this.data = data;
//    }

    public RecyclerViewAdapter(ArrayList<NewsItem> data, ItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(String url);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.list_item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }
    @Override
    public void onBindViewHolder (ItemHolder holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView publishedAt;
        private String url;

        ItemHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            publishedAt = (TextView) view.findViewById(R.id.publishedAt);
            description = (TextView) view.findViewById(R.id.description);
            view.setOnClickListener(this);
        }

        public void bind(int pos) {
            NewsItem item = data.get(pos);
            title.setText(item.getTitle());
            publishedAt.setText(item.getPublishedAt());
            description.setText(item.getDescription());
            url = item.getUrl();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(url);
        }
    }
}
