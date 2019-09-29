package com.recognition.demo.ui.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.recognition.demo.R;
import com.recognition.demo.common.MyLazyFragment;

import butterknife.BindView;

public class Homefragment extends MyLazyFragment {

    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.button)
    Button mBtn;

    public static Homefragment newInstance() {
        return new Homefragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {

        mBtn.setText("按钮");

        //初始化时设置使用菜单栏
        setHasOptionsMenu(true);

    }

    @Override
    protected void initData() {
        initView();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //加载Fragment中菜单栏的布局
        inflater.inflate(R.menu.fragment_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Fragment中菜单栏点击事件
        switch (item.getItemId()) {
            case R.id.menu_settings:
                toast("fragment菜单栏第一项");
                break;
            case R.id.menu_about:
                toast("fragment菜单栏第二项");
                break;
            case R.id.menu_quit:
                toast("fragment菜单栏第三项");
                break;
            default:
                break;
        }
        return true;
    }
}
