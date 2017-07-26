package com.example.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.data.Contract;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;
    public static final String TAG = "myadapter";

    public MyAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }
    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.list_item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView publishedAt;
        ImageView img;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            publishedAt = (TextView)view.findViewById(R.id.publishedAt);
            img = (ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            cursor.moveToPosition(pos);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            publishedAt.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE));
//            Log.d(TAG, url);
            if(url != null){
                Picasso.with(context)
                        .load(url)
                        .into(img);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }

}

