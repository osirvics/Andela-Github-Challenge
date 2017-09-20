package com.album.andela_github_challenge.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Victor on 3/7/2017.
 */

@SuppressWarnings("unused")
public class Item implements Parcelable {
    /**
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
     */


    private String login;
    private int id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private double score;

    public Item (){
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }

    public String getGravatarId() {
        return gravatar_id;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatar_id = gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowersUrl() {
        return followers_url;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followers_url = followersUrl;
    }

    public String getFollowingUrl() {
        return following_url;
    }

    public void setFollowingUrl(String followingUrl) {
        this.following_url = followingUrl;
    }

    public String getGistsUrl() {
        return gists_url;
    }

    public void setGistsUrl(String gistsUrl) {
        this.gists_url = gistsUrl;
    }

    public String getStarredUrl() {
        return starred_url;
    }

    public void setStarredUrl(String starredUrl) {
        this.starred_url = starredUrl;
    }

    public String getSubscriptionsUrl() {
        return subscriptions_url;
    }

    public void setSubscriptionsUrl(String subscriptionsUrl) {
        this.subscriptions_url = subscriptionsUrl;
    }

    public String getOrganizationsUrl() {
        return organizations_url;
    }

    public void setOrganizationsUrl(String organizationsUrl) {
        this.organizations_url = organizationsUrl;
    }

    public String getReposUrl() {
        return repos_url;
    }

    public void setReposUrl(String reposUrl) {
        this.repos_url = reposUrl;
    }

    public String getEventsUrl() {
        return events_url;
    }

    public void setEventsUrl(String eventsUrl) {
        this.events_url = eventsUrl;
    }

    public String getReceivedEventsUrl() {
        return received_events_url;
    }

    public void setReceivedEventsUrl(String receivedEventsUrl) {
        this.received_events_url = receivedEventsUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSiteAdmin() {
        return site_admin;
    }

    public void setSiteAdmin(boolean siteAdmin) {
        this.site_admin = siteAdmin;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    protected Item(Parcel in) {
        login = in.readString();
        id = in.readInt();
        avatar_url = in.readString();
        gravatar_id = in.readString();
        url = in.readString();
        html_url = in.readString();
        followers_url = in.readString();
        following_url = in.readString();
        gists_url = in.readString();
        starred_url = in.readString();
        subscriptions_url = in.readString();
        organizations_url = in.readString();
        repos_url = in.readString();
        events_url = in.readString();
        received_events_url = in.readString();
        type = in.readString();
        site_admin = in.readByte() != 0x00;
        score = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeInt(id);
        dest.writeString(avatar_url);
        dest.writeString(gravatar_id);
        dest.writeString(url);
        dest.writeString(html_url);
        dest.writeString(followers_url);
        dest.writeString(following_url);
        dest.writeString(gists_url);
        dest.writeString(starred_url);
        dest.writeString(subscriptions_url);
        dest.writeString(organizations_url);
        dest.writeString(repos_url);
        dest.writeString(events_url);
        dest.writeString(received_events_url);
        dest.writeString(type);
        dest.writeByte((byte) (site_admin ? 0x01 : 0x00));
        dest.writeDouble(score);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}