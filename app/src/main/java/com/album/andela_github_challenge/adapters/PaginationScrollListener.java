package com.album.andela_github_challenge.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Victor on 3/7/2017.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    LinearLayoutManager layoutManager;
    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0)
            {
                loadMoreItems();
            }
        }
    }
    protected abstract void loadMoreItems();
    public abstract int getTotalPageCount();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();
}