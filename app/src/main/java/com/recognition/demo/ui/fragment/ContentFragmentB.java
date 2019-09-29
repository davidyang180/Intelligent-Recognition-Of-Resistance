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
import com.recognition.demo.liebiao.Contributor;
import com.recognition.demo.liebiao.GsonJsonConverter;
import com.recognition.demo.liebiao.Items;
import com.recognition.demo.liebiao.OnRecommendedClickedListener;
import com.recognition.demo.liebiao.PicassoImageLoader;
import com.recognition.demo.liebiao.Recommended;
import com.recognition.demo.liebiao.RecommendedLoaderDelegate;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class ContentFragmentB extends AbsAboutActivity implements ScreenShotable, OnRecommendedClickedListener {
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

    public static ContentFragmentB newInstance() {
        return new ContentFragmentB();
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
        slogan.setText("Development related");
        slogan.setTextColor(this.getResources().getColor(R.color.textColor));
        slogan.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
    }
    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("Developers"));
        items.add(new Contributor(R.drawable.avatar_drakeet, "杨大为", "Developer & designer", "https://user.qzone.qq.com/1256216375/infocenter"));
        items.add(new Contributor(R.drawable.avatar_drakeet, "刘焱鑫", "Developer", "https://user.qzone.qq.com/928989005?source=aiostar"));
        items.add(new Contributor(R.drawable.avatar_drakeet, "吴豆豆", "Developer","https://user.qzone.qq.com/1109300975?source=aiostar"));
        items.add(new Category("团队独立开发的应用"));
        items.add(new Recommended(
                0, getString(R.string.app1),
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3831893439,3753751586&fm=26&gp=0.jpg",
                "com.hjq.demo",
                "利用图像识别技术和色环编码规律设计软件，可智能低容错识别电阻参数。",
                "https://www.coolapk.com/apk/com.drakeet.purewriter",
                "2019-9-21 16:46:57",
                "2019-9-21 16:46:57", 26.13, true)
        );
        RecommendedLoaderDelegate.attach(this, items.size(), /*new MoshiJsonConverter() */ new GsonJsonConverter() );
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
                ContentFragmentB.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
