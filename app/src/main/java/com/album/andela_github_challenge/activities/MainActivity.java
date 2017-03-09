package com.album.andela_github_challenge.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.album.andela_github_challenge.DividerItemDecoration;
import com.album.andela_github_challenge.GridMarginDecoration;
import com.album.andela_github_challenge.R;
import com.album.andela_github_challenge.adapters.PaginationScrollListener;
import com.album.andela_github_challenge.adapters.UserAdapter;
import com.album.andela_github_challenge.api.GithubApiClient;
import com.album.andela_github_challenge.api.GithubApiService;
import com.album.andela_github_challenge.interfaces.ClickListener;
import com.album.andela_github_challenge.interfaces.PaginationAdapterCallback;
import com.album.andela_github_challenge.models.Item;
import com.album.andela_github_challenge.models.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaginationAdapterCallback, ClickListener {
    private RecyclerView grid;
    private ProgressBar empty;
    private UserAdapter adapter;
    LinearLayout errorLayout;
    private ArrayList<Item> items;
    User users;
    Button btnRetry;
    TextView txtError;
    int pageLoadCount = 0;
    private static final int PAGE_START = 1;
    //Indicates if footer ProgressBar is shown
    private boolean isLoading = false;
    private boolean isLastPage = false;
    //setting initial value to ten
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    public static final int PER_PAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        setupView();
        btnRetry.setOnClickListener(retry);
        displayData();
    }

    View.OnClickListener retry = (new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideErrorView();
            displayData();
        }
    });


    private void populateList() {
        adapter = new UserAdapter(this, items, this,this, Glide.with(this));
        grid.setAdapter(adapter);
        int spacing =  getResources().getDimensionPixelSize(R.dimen.rc_padding_left);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), spacing);
        grid.addItemDecoration(dividerItemDecoration);
        empty.setVisibility(View.GONE);
        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
    }

    public void setupView(){
        grid = (RecyclerView) findViewById(R.id.image_grid);
        empty = (ProgressBar) findViewById(R.id.empty);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        txtError = (TextView) findViewById(R.id.error_txt_cause);
        final int columns = getResources().getInteger(R.integer.gallery_columns);
        final GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, columns);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        grid.setLayoutManager(gridLayoutManager);

        grid.addItemDecoration(new GridMarginDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)){
                    case UserAdapter.ITEM:
                        return 1;
                    case UserAdapter.LOADING:
                        if( columns==1){
                            return 1;
                        }
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        grid.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }
            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void displayData() {
        if (items != null) {
            populateList();
        } else {
            GithubApiService apiService =
                    GithubApiClient.getClient().create(GithubApiService.class);
            Call<User> call = apiService.getSearchUsers(currentPage,PER_PAGE);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code()==200){
                            users =  new User();
                            users = response.body();
                        items =  new ArrayList<>();
                            items = users.getItems();
                            populateList();
                        int totalCount = users.getTotal();
                        if(totalCount % PER_PAGE ==0)
                            TOTAL_PAGES = (totalCount/PER_PAGE);
                        else TOTAL_PAGES = (totalCount/PER_PAGE) + 1;
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                    showErrorView(t);
                }
            });
        }
    }

    private void loadNextPage() {
        pageLoadCount +=1;
        GithubApiService apiService =
                GithubApiClient.getClient().create(GithubApiService.class);
        Call<User> call = apiService.getSearchUsers(currentPage,PER_PAGE);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                 if(response.code()==200){
                    adapter.removeLoadingFooter();
                    isLoading = false;
                    final User user = response.body();
                    final ArrayList<Item> results = user.getItems();
                    adapter.addAll(results);
                    adapter.notifyDataSetChanged();
                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                adapter.showRetry(true, fetchErrorMessage(t));
            }
        });
    }

    /**
     * @param throwable required for {@link #fetchErrorMessage(Throwable)}
     */
    private void showErrorView(Throwable throwable) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            txtError.setText(fetchErrorMessage(throwable));
        }
    }
    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }
        return errorMsg;
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putParcelableArrayListExtra("data",items);
        intent.putExtra("pos", position);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }
}
