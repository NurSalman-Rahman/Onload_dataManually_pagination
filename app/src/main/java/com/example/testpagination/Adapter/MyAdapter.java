package com.example.testpagination.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpagination.Interface.ILoadMore;
import com.example.testpagination.Model.Item;
import com.example.testpagination.R;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder
{
    public ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);

        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_id);
    }
}

class ItemViewHolder extends  RecyclerView.ViewHolder
{

    public TextView name;
/*    length;*/

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);


        name = (TextView)itemView.findViewById(R.id.textName_id);
/*        length = (TextView)itemView.findViewById(R.id.textLength_id);*/
    }
}


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  final int VIEW_TYPE_ITEM = 0,VIEW_TYPE_LOADING =1;

    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<Item> items;
    int visibleThreshold = 5;
    int lastVisibleItem,totalItemCount;

    public MyAdapter(RecyclerView recyclerView,Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;


        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem= linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold))
                {
                    if (loadMore !=null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });

    }

    //press cltl +o


    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ?  VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;

    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_layout,parent,false);
            return  new ItemViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.itrm_loading,parent,false);
            return  new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof  ItemViewHolder)
        {

            Item item  = items.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.name.setText(position +" " +items.get(position).getName());
            /*viewHolder.length.setText(String.valueOf(items.get(position).getLength()));*/
        }
        else if (holder instanceof  LoadingViewHolder)
        {
             LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
             loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded() {
        isLoading = false;

    }


}
