package com.album.andela_github_challenge.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.album.andela_github_challenge.R;
import com.album.andela_github_challenge.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * A placeholder fragment containing a simple view.
 */
public  class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    @Override
    public void onStart() {
        super.onStart();
    }

    String username, avatar_url, link;
    int pos;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_IMG_URL = "image_url";
    private static final String ARG_GITHUB_LINK = "github_link";
    private static final String ARG_USERNAME = "username";
    CoordinatorLayout coordinatorLayout;
    private ImageView imageView;
    CardView card;
    TextView userName;
    LinearLayout github, share;
    ImageView share_img,github_img;

    public PlaceholderFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.pos = args.getInt(ARG_SECTION_NUMBER);
        this.avatar_url = args.getString(ARG_IMG_URL);
        this.link = args.getString(ARG_GITHUB_LINK);
        this.username = args.getString(ARG_USERNAME);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, String avatar_url, String link, String username) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_IMG_URL, avatar_url);
        args.putString(ARG_GITHUB_LINK,link);
        args.putString(ARG_USERNAME,username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id
                .main_content);
        imageView = (ImageView) rootView.findViewById(R.id.detail_image);
        userName = (TextView) rootView.findViewById(R.id.username);
        share = (LinearLayout) rootView.findViewById(R.id.share_link);
        github = (LinearLayout) rootView.findViewById(R.id.github_link);
        share_img = (ImageView) rootView.findViewById(R.id.share);
        github_img = (ImageView) rootView.findViewById(R.id.github);
        card = (CardView) rootView.findViewById(R.id.card);
        card.setAlpha(1f);
        userName.setText(username);
        applyPalette();
        displayImage();
        share.setOnClickListener(share_profile);
        github.setOnClickListener(github_web);
        share_img.setColorFilter(ContextCompat.getColor(getActivity(),R.color.grey_600));
        github_img.setColorFilter(ContextCompat.getColor(getActivity(),R.color.grey_600));
        return rootView;
    }


    private void displayImage(){
        Glide.with(this)
                .load(avatar_url)
                .placeholder(R.color.user_background)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public void applyPalette(){
        Glide.with(this).load(avatar_url).asBitmap()
                .error(R.color.placeholder_grey_20).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Palette.from(resource)
                        .generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                final int[] colors = Utility.getAvailableColor(getActivity(),palette);
                                int colorTopp = colors[0];
                                card.setCardBackgroundColor(colorTopp);
                                Palette.Swatch  swatch = palette.getMutedSwatch();
                                if(swatch != null){
                                    userName.setTextColor(Utility.getBlackWhiteColor(swatch.getTitleTextColor()));
                                }

                            }
                        });
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);

            }
        });
    }

    View.OnClickListener share_profile = (new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,  getString(R.string.share_msg,username,link));
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
        }
    });

    View.OnClickListener github_web = (new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            if(link!=null){
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        }
    });

}