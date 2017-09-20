package com.album.andela_github_challenge.models;

/**
 * Created by Victor on 3/7/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Model class representing data returned from Github
 */
@SuppressWarnings("unused")
public class User implements Parcelable {
    /**
     *  {
     "total_count": 12,
     "incomplete_results": false,
     "items": [
     {
     "login": "mojombo",
     "id": 1,
     "avatar_url": "https://secure.gravatar.com/avatar/25c7c18223fb42a4c6ae1c8db6f50f9b?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
     "gravatar_id": "",
     "url": "https://api.github.com/users/mojombo",
     "html_url": "https://github.com/mojombo",
     "followers_url": "https://api.github.com/users/mojombo/followers",
     "subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
     "organizations_url": "https://api.github.com/users/mojombo/orgs",
     "repos_url": "https://api.github.com/users/mojombo/repos",
     "received_events_url": "https://api.github.com/users/mojombo/received_events",
     "type": "User",
     "score": 105.47857
     }
     ]
     }
     */
    public User(){

    }
    private int total_count;
    private boolean incompleteResults;
    private ArrayList<Item> items;

    public int getTotal() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }


    protected User(Parcel in) {
        total_count = in.readInt();
        incompleteResults = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            items = new ArrayList<Item>();
            in.readList(items, Item.class.getClassLoader());
        } else {
            items = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total_count);
        dest.writeByte((byte) (incompleteResults ? 0x01 : 0x00));
        if (items == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(items);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}