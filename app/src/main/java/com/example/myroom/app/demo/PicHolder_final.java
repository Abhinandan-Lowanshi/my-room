package com.example.myroom.app.demo;

import android.net.Uri;

public class PicHolder_final {
    String caption,url;

    public PicHolder_final() {
    }

    Uri uri;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
