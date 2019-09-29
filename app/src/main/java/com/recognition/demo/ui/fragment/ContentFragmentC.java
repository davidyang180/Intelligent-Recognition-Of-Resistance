package com.recognition.demo.ui.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.recognition.demo.R;
import com.recognition.demo.liebiao.AbsAboutActivity;
import com.recognition.demo.liebiao.Category;
import com.recognition.demo.liebiao.Items;
import com.recognition.demo.liebiao.License;
import com.recognition.demo.liebiao.OnRecommendedClickedListener;
import com.recognition.demo.liebiao.PicassoImageLoader;
import com.recognition.demo.liebiao.Recommended;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class ContentFragmentC extends AbsAboutActivity implements ScreenShotable, OnRecommendedClickedListener {
    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    public static final String BOOK = "Book";
    public static final String PAINT = "Paint";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";

    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    public ImageView imageView;

    public static ContentFragmentC newInstance() {
        return new ContentFragmentC();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.about_page_main_activity;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageLoader(new PicassoImageLoader());
        setOnRecommendedClickedListener(this);
        setHasOptionsMenu(true);
    }
    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        AssetManager mgr = getFragmentActivity().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "SketchGothicSchool.ttf");
        slogan.setTypeface(tf);
        slogan.setText("Open Source Licenses");
        slogan.setTextColor(this.getResources().getColor(R.color.textColor));
        slogan.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
    }
    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("Open Source Licenses"));
        items.add(new License("Intelligent Recognition of Resistance", "davidyang180", License.APACHE_2, "https://github.com/davidyang180/Intelligent-Electronic-Community"));
    }

    @Override
    public boolean onRecommendedClicked(@NonNull View itemView, @NonNull Recommended recommended) {
        Toast.makeText(getFragmentActivity(), "onRecommendedClicked: " + recommended.appName, Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.contain);
    }
    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragmentC.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
