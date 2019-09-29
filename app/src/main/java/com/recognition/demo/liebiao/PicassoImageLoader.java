package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author drakeet
 */
public class PicassoImageLoader implements ImageLoader {

    @Override
    public void load(@NonNull ImageView imageView, @NonNull String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
