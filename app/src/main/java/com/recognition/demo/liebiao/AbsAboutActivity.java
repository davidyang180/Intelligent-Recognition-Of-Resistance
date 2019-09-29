package com.recognition.demo.liebiao;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.recognition.demo.R;
import com.recognition.demo.common.MyLazyFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbsAboutActivity extends MyLazyFragment {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private LinearLayout headerContentLayout;
    private Unbinder mButterKnife;
    private @Nullable
    ImageLoader imageLoader;
    private Items items;
    private MultiTypeAdapter adapter;
    private TextView slogan, version;
    private RecyclerView recyclerView;
    private boolean initialized;
    private @Nullable
    OnRecommendedClickedListener onRecommendedClickedListener;
    private @Nullable
    OnContributorClickedListener onContributorClickedListener;


    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version){}
    protected abstract void onItemsCreated(@NonNull Items items);


    protected void onTitleViewCreated(@NonNull CollapsingToolbarLayout collapsingToolbar) {}


    public void setImageLoader(@NonNull ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        if (initialized) {
            adapter.notifyDataSetChanged();
        }
    }




    public @Nullable ImageLoader getImageLoader() {
        return imageLoader;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.about_page_main_activity;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        toolbar = findViewById(R.id.toolbar);
        ImageView icon = findViewById(R.id.icon);
        slogan = findViewById(R.id.slogan);
        version = findViewById(R.id.version);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        headerContentLayout = findViewById(R.id.header_content_layout);
        onTitleViewCreated(collapsingToolbar);
        onCreateHeader(icon, slogan, version);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        onApplyPresetAttrs();
        recyclerView = findViewById(R.id.list);
        adapter = new MultiTypeAdapter();
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(Card.class, new CardViewBinder());
        adapter.register(Line.class, new LineViewBinder());
        adapter.register(Contributor.class, new ContributorViewBinder(this));
        adapter.register(License.class, new LicenseViewBinder());
        adapter.register(Recommended.class, new RecommendedViewBinder(this));
        items = new Items();
        onItemsCreated(items);
        adapter.setItems(items);
        adapter.setHasStableIds(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(adapter));
        recyclerView.setAdapter(adapter);
        initialized = true;
        setHasOptionsMenu(true);
        return view;
    }


    /*@Override @SuppressWarnings("deprecation")
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        mActivity.onPostCreate();
        adapter = new MultiTypeAdapter();
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(Card.class, new CardViewBinder());
        adapter.register(Line.class, new LineViewBinder());
        adapter.register(Contributor.class, new ContributorViewBinder(this));
        adapter.register(License.class, new LicenseViewBinder());
        adapter.register(Recommended.class, new RecommendedViewBinder(this));
        items = new Items();
        onItemsCreated(items);
        adapter.setItems(items);
        adapter.setHasStableIds(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(adapter));
        recyclerView.setAdapter(adapter);
        initialized = true;
    }*/


    public void onApplyPresetAttrs() {
        final TypedArray a =((AppCompatActivity) getActivity()).getTheme().obtainStyledAttributes(R.styleable.AbsAboutActivity);
        //final TypedArray a = obtainStyledAttributes(R.styleable.AbsAboutActivity);
        Drawable headerBackground = a.getDrawable(R.styleable.AbsAboutActivity_aboutPageHeaderBackground);
        if (headerBackground != null) {
            setHeaderBackground(headerBackground);
        }
        Drawable headerContentScrim = a.getDrawable(R.styleable.AbsAboutActivity_aboutPageHeaderContentScrim);
        if (headerContentScrim != null) {
            setHeaderContentScrim(headerContentScrim);
        }
        @ColorInt
        int headerTextColor = a.getColor(R.styleable.AbsAboutActivity_aboutPageHeaderTextColor, -1);
        if (headerTextColor != -1) {
            setHeaderTextColor(headerTextColor);
        }
        Drawable navigationIcon = a.getDrawable(R.styleable.AbsAboutActivity_aboutPageNavigationIcon);
        if (navigationIcon != null) {
            setNavigationIcon(navigationIcon);
        }
        a.recycle();
    }


    /**
     * Use {@link #setHeaderBackground(int)} instead.
     *
     * @param resId The resource id of header background
     */
    @Deprecated
    public void setHeaderBackgroundResource(@DrawableRes int resId) {
        setHeaderBackground(resId);
    }


    private void setHeaderBackground(@DrawableRes int resId) {
        setHeaderBackground(ContextCompat.getDrawable(getFragmentActivity(), resId));
    }


    private void setHeaderBackground(@NonNull Drawable drawable) {
        ViewCompat.setBackground(headerContentLayout, drawable);
    }


    /**
     * Set the drawable to use for the content scrim from resources. Providing null will disable
     * the scrim functionality.
     *
     * @param drawable the drawable to display
     */
    public void setHeaderContentScrim(@NonNull Drawable drawable) {
        collapsingToolbar.setContentScrim(drawable);
    }


    public void setHeaderContentScrim(@DrawableRes int resId) {
        setHeaderContentScrim(ContextCompat.getDrawable(getFragmentActivity(), resId));
    }


    public void setHeaderTextColor(@ColorInt int color) {
        collapsingToolbar.setCollapsedTitleTextColor(color);
        slogan.setTextColor(color);
        version.setTextColor(color);
    }


    /**
     * Set the icon to use for the toolbar's navigation button.
     *
     * @param resId Resource ID of a drawable to set
     */
    public void setNavigationIcon(@DrawableRes int resId) {
        toolbar.setNavigationIcon(resId);
    }


    public void setNavigationIcon(@NonNull Drawable drawable) {
        toolbar.setNavigationIcon(drawable);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void setTitle(@NonNull CharSequence title) {
        collapsingToolbar.setTitle(title);
    }


    public Toolbar getToolbar() {
        return toolbar;
    }


    public CollapsingToolbarLayout getCollapsingToolbar() {
        return collapsingToolbar;
    }


    public Items getItems() {
        return items;
    }


    public MultiTypeAdapter getAdapter() {
        return adapter;
    }


    public TextView getSloganTextView() {
        return slogan;
    }


    public TextView getVersionTextView() {
        return version;
    }


    public void setOnRecommendedClickedListener(@Nullable OnRecommendedClickedListener listener) {
        this.onRecommendedClickedListener = listener;
    }


    public @Nullable OnRecommendedClickedListener getOnRecommendedClickedListener() {
        return onRecommendedClickedListener;
    }


    public void setOnContributorClickedListener(@Nullable OnContributorClickedListener listener) {
        this.onContributorClickedListener = listener;
    }


    public @Nullable OnContributorClickedListener getOnContributorClickedListener() {
        return onContributorClickedListener;
    }





}
