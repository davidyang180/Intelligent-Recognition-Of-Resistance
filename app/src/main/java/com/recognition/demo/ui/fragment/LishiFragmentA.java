package com.recognition.demo.ui.fragment;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recognition.demo.R;
import com.recognition.demo.common.MyLazyFragment;
import com.recognition.demo.helper.Typefaces;
import com.recognition.demo.ui.activity.LoginActivity;
import com.recognition.demo.ui.activity.RegisterActivity;
import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LishiFragmentA extends MyLazyFragment {
    private int systemVersion;
    private  TextView  tv;
    private Unbinder mButterKnife;
    public static LishiFragmentA newInstance() {
        return new LishiFragmentA();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_lishi;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_c_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        super.onCreate(savedInstanceState);
        TitanicTextView re = (TitanicTextView) findViewById(R.id.my_text_view);
        // set fancy typeface
        re.setTypeface(Typefaces.get(getFragmentActivity(), "Satisfy-Regular.ttf"));
        // start animation
        new Titanic().start(re);
        tv  =  (TextView) findViewById(R.id.tv);
        String htmlLinkText=
                "<a href='aaa'>登录</a>或<a href='bbb'>注册</a>以获取查询记录";
        tv.setText(Html.fromHtml(htmlLinkText));  //将字符串格式化成html文本
        tv.setMovementMethod(LinkMovementMethod.getInstance());//使TextView可以执行链接
//        tv.setAutoLinkMask(Linkify.WEB_URLS);
        CharSequence text  =  tv.getText();
        if (text instanceof Spannable){
            int  end  =  text.length();
            Spannable sp  =  (Spannable)tv.getText();
            //获取textView中的多个链接组成数组
            URLSpan[] urls = sp.getSpans( 0 , end, URLSpan. class );
            SpannableStringBuilder style = new  SpannableStringBuilder(text);

            style.clearSpans(); // should clear old spans
            for (URLSpan url : urls){
                MyURLSpan myURLSpan  =   new  MyURLSpan(url.getURL());
                //给链接设置样式等，例如链接处的下划线，字体颜色等，及其单击事件的添加
                style.setSpan(myURLSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            style.setSpan(new TypefaceSpan("Satisfy-Regular.ttf"), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(style);
        }
        return view;
    }
    private  class  MyURLSpan extends ClickableSpan {

        private  String mUrl;
        MyURLSpan(String url) {
            mUrl  = url;
        }
        @Override
        public void onClick(View widget) {
            //  TODO Auto-generated method stub
            //当textView中有多个连接要执行时，可以根据mUrl来区分是哪一个链接没单击了，例如这里的两个链接分别是aaa和bbb
            switch (mUrl) {
                case "aaa":
                    startActivity(LoginActivity.class);
                    break;
                case "bbb":
                    startActivity(RegisterActivity.class);
                    break;
            }
        }
    }
    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}
