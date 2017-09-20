package com.album.andela_github_challenge.interfaces;


/**
 * Listens for item clicks
 */

public interface  ClickListener {
    void onItemClicked(int position);
    boolean onItemLongClicked(int position);
}
