package com.recognition.demo.liebiao;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Contributor {

    public @DrawableRes
    final int avatarResId;
    public @NonNull
    final String name;
    public @NonNull final String desc;
    public @Nullable
    String url;


    public Contributor(@DrawableRes int avatarResId, @NonNull String name, @NonNull String desc) {
        this(avatarResId, name, desc, null);
    }


    public Contributor(
            @DrawableRes int avatarResId,
            @NonNull String name,
            @NonNull String desc,
            @Nullable String url) {

        this.avatarResId = avatarResId;
        this.name = name;
        this.desc = desc;
        this.url = url;
    }
}
