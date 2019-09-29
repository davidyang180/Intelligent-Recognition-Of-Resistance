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

import com.recognition.demo.BuildConfig;
import com.recognition.demo.R;
import com.recognition.demo.liebiao.AbsAboutActivity;
import com.recognition.demo.liebiao.Card;
import com.recognition.demo.liebiao.Category;
import com.recognition.demo.liebiao.Contributor;
import com.recognition.demo.liebiao.GsonJsonConverter;
import com.recognition.demo.liebiao.Items;
import com.recognition.demo.liebiao.OnContributorClickedListener;
import com.recognition.demo.liebiao.OnRecommendedClickedListener;
import com.recognition.demo.liebiao.PicassoImageLoader;
import com.recognition.demo.liebiao.Recommended;
import com.recognition.demo.liebiao.RecommendedLoaderDelegate;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends AbsAboutActivity implements ScreenShotable, OnRecommendedClickedListener, OnContributorClickedListener {
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

    public static ContentFragment newInstance() {
        return new ContentFragment();
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

    /*public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImageLoader(new PicassoImageLoader());
        setOnRecommendedClickedListener(this);
        setOnContributorClickedListener(this);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.david);
        AssetManager mgr = getFragmentActivity().getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "SketchGothicSchool.ttf");
        slogan.setTypeface(tf);
        slogan.setText("Intelligent Recognition of Resistance");
        slogan.setTextColor(this.getResources().getColor(R.color.textColor));
        slogan.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
        version.setText("v" + BuildConfig.VERSION_NAME);
        version.setTextColor(this.getResources().getColor(R.color.textColor));
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card(getString(R.string.card_content)));
/*
        items.add(new Category("Developers"));
        items.add(new Contributor(R.drawable.avatar_drakeet, "drakeet", "Developer & designer", "http://weibo.com/drak11t"));
        items.add(new Contributor(R.drawable.avatar_drakeet, "黑猫酱", "Developer", "https://drakeet.me"));
        items.add(new Contributor(R.drawable.avatar_drakeet, "小艾大人", "Developer"));

        items.add(new Category("我独立开发的应用"));
        items.add(new Recommended(
                0, getString(R.string.pure_writer),
                "https://storage.recommend.wetolink.com/storage/app_recommend/images/YBMHN6SRpZeF0VHbPZWZGWJ2GyB6uaPx.png",
                "com.drakeet.purewriter",
                "快速的纯文本编辑器，我们希望写作能够回到原本的样子：纯粹、有安全感、随时、绝对不丢失内容、具备良好的写作体验。",
                "https://www.coolapk.com/apk/com.drakeet.purewriter",
                "2017-10-09 16:46:57",
                "2017-10-09 16:46:57", 2.93, true)
        );
        items.add(new Recommended(
                1, getString(R.string.pure_mosaic),
                "http://image.coolapk.com/apk_logo/2016/0831/ic_pure_mosaic-2-for-16599-o_1argff2ddgvt1lfv1b3mk2vd6pq-uid-435200.png",
                "me.drakeet.puremosaic",
                "专注打码的轻应用，包含功能：传统马赛克、毛玻璃效果、选区和手指模式打码，更有创新型高亮打码和 LowPoly 风格马赛克。只为满足一个纯纯的打码需求，让打码也能成为一种赏心悦目。",
                "https://www.coolapk.com/apk/me.drakeet.puremosaic",
                "2017-10-09 16:46:57",
                "2017-10-09 16:46:57", 2.64, true)
        );*/
        // Load more Recommended items from remote server asynchronously
        RecommendedLoaderDelegate.attach(this, items.size(), /*new MoshiJsonConverter() */ new GsonJsonConverter() );
        // or
        // RecommendedLoader.getInstance().loadInto(this, items.size());
    /*
        items.add(new Category("Open Source Licenses"));
        items.add(new License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        */
    }


    @Override
    public boolean onRecommendedClicked(@NonNull View itemView, @NonNull Recommended recommended) {
        Toast.makeText(getFragmentActivity(), "onRecommendedClicked: " + recommended.appName, Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public boolean onContributorClicked(@NonNull View itemView, @NonNull Contributor contributor) {
        if (contributor.name.equals("杨大为")) {
            Toast.makeText(getFragmentActivity(), "onContributorClicked: " + contributor.name, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.contain);
    }
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }
*/
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        imageView= (ImageView) rootView.findViewById(R.id.image1);
        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
       // mImageView.setClickable(true);
       // mImageView.setFocusable(true);
        //mImageView.setImageResource(res);
        return rootView;
    }
*/
    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

