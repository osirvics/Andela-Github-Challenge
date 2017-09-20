package com.album.andela_github_challenge.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.album.andela_github_challenge.R;
import com.album.andela_github_challenge.interfaces.ClickListener;
import com.album.andela_github_challenge.interfaces.PaginationAdapterCallback;
import com.album.andela_github_challenge.models.Item;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Item> users;
    private Context context;
    public static final int ITEM = 0;
    public static final int LOADING = 1;
    public ClickListener clickListener;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;
    private final RequestManager glide;

    public UserAdapter(@NonNull Context context, @NonNull ArrayList<Item> users, RequestManager mGlide) {
        this.users = users;
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        this.clickListener = (ClickListener) context;
        this.glide = mGlide;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View view = layoutInflater
                        .inflate(R.layout.list_item, parent, false);
                viewHolder = new UserHolder(view, clickListener);
                break;
            case LOADING:
                View view2 = layoutInflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(view2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                final UserHolder userHolder = (UserHolder) holder;
                Item user = users.get(position);
                userHolder.username.setText(user.getLogin());
                glide.load(user.getAvatarUrl())
                        .placeholder(R.color.user_background)
                        .override(480,400)
                        .centerCrop()
                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .thumbnail(glide
//                                .load(url.getThumb())
//                                .override(100, 100)
//                                .transform(new BlurTransformation(context)))
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                           boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(userHolder.avatar);
      break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.progressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        //super.onViewRecycled(holder);
        if(holder instanceof UserHolder ){
            final UserHolder userholder = (UserHolder) holder;
            Glide.clear(userholder.avatar);
        }
    }

    public Item getItem(int position) {
        return users.get(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == users.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Item mc) {
        users.add(mc);
        notifyItemInserted(users.size() - 1);
    }

    public void addAll(ArrayList<Item> mcList) {
        for (Item mc : mcList) {
            add(mc);
        }
    }

    public void remove(Item city) {
        int position = users.indexOf(city);
        if (position > -1) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = users.size() - 1;
        Item item = getItem(position);
        if (item != null) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }



    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show flag indicating when wheather to show or not
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(users.size() - 1);
        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public static class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView username;
        public ImageView avatar;
        public final View mView;
        public ClickListener listener;

        public UserHolder(View itemView, ClickListener listener) {
            super(itemView);
            this.listener = listener;
            mView = itemView;
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            username = (TextView) itemView.findViewById(R.id.username);
            mView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClicked(getLayoutPosition());
            }
        }
    }

    public  class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ProgressBar progressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loadmore_retry:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();
                    break;
            }
        }
    }
}
