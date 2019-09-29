package com.recognition.demo.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recognition.demo.R;
import com.recognition.demo.common.MyLazyFragment;
import com.recognition.demo.helper.ActivityStackManager;
import com.recognition.demo.ui.activity.AboutActivity;
import com.recognition.demo.ui.activity.Accountsecurity;
import com.recognition.demo.ui.activity.LoginActivity;
import com.recognition.demo.ui.activity.RegisterActivity;
import com.recognition.demo.widget.SettingBar;
import com.recognition.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目界面跳转示例
 */
public class TestFragmentD extends MyLazyFragment
        implements SwitchButton.OnCheckedChangeListener {

    private Unbinder mButterKnife;

    @BindView(R.id.sb_setting_auto)
    SettingBar mAutoLoginView;
    @BindView(R.id.sb_setting_switch)
    SwitchButton mAutoSwitchView;
    @BindView(R.id.den)
    TextView den;


    public static TestFragmentD newInstance() {
        return new TestFragmentD();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_d;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_d_title;
    }

    @Override
    protected void initView() {

        // 设置切换按钮的监听
        mAutoSwitchView.setOnCheckedChangeListener(this);

    }

    protected void initData() {


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        super.onCreate(savedInstanceState);
        String htmlLinkText =
                "<a href='aaa'>登录</a>/<a href='bbb'>注册</a>";
        den.setText(Html.fromHtml(htmlLinkText));  //将字符串格式化成html文本
        den.setMovementMethod(LinkMovementMethod.getInstance());//使TextView可以执行链接
//        tv.setAutoLinkMask(Linkify.WEB_URLS);
        CharSequence text = den.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) den.getText();
            //获取textView中的多个链接组成数组
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans(); // should clear old spans
            for (URLSpan url : urls) {
                TestFragmentD.MyURLSpan myURLSpan = new TestFragmentD.MyURLSpan(url.getURL());
                //给链接设置样式等，例如链接处的下划线，字体颜色等，及其单击事件的添加
                style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            den.setText(style);
        }
        return view;
    }

    private class MyURLSpan extends ClickableSpan {

        private String mUrl;

        MyURLSpan(String url) {
            mUrl = url;
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
        @Override
        public void updateDrawState(TextPaint den) {
            super.updateDrawState(den);
            den.setColor(Color.argb(255, 255, 164, 102)); // 设置字体颜色
            den.setUnderlineText(false); //去掉下划线
        }
    }

    @OnClick({R.id.sb_setting_language, R.id.sb_setting_update, R.id.sb_setting_agreement, R.id.sb_setting_about,
            R.id.sb_setting_banbenhao, R.id.sb_setting_auto, R.id.sb_setting_exit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_setting_language:
                break;
            case R.id.sb_setting_update:
                break;
            case R.id.sb_setting_agreement:
                startActivity(Accountsecurity.class);
                break;
            case R.id.sb_setting_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.sb_setting_auto: // 自动登录
                mAutoSwitchView.setChecked(!mAutoSwitchView.isChecked());
                break;
            case R.id.sb_setting_cache: // 清空缓存

                break;
            case R.id.sb_setting_exit: // 退出登录
                startActivity(LoginActivity.class);
                // 进行内存优化，销毁掉所有的界面
                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
                break;
            default:
                break;
        }

    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }

    /*   @OnClick({R.id.btn_test_dialog, R.id.btn_test_login, R.id.btn_test_register, R.id.btn_test_forget, R.id.btn_test_reset,
               R.id.btn_test_setting, R.id.btn_test_about, R.id.btn_test_browser, R.id.btn_test_pay})
       public void onClick(View v) {
           switch (v.getId()) {
               case R.id.btn_test_dialog:
                   startActivity(DialogActivity.class);
                   break;
               case R.id.btn_test_login:
                   startActivity(LoginActivity.class);
                   break;
               case R.id.btn_test_register:
                   startActivity(RegisterActivity.class);
                   break;
               case R.id.btn_test_forget:
                   startActivity(PasswordForgetActivity.class);
                   break;
               case R.id.btn_test_reset:
                   startActivity(PasswordResetActivity.class);
                   break;
               case R.id.btn_test_setting:
                   startActivity(SettingActivity.class);
                   break;
               case R.id.btn_test_about:
                   startActivity(AboutActivity.class);
                   break;
               case R.id.btn_test_browser:
                   startActivity(WebActivity.class);
                   break;
               case R.id.btn_test_pay:
                   new MessageDialog.Builder(getFragmentActivity())
                           .setTitle("捐赠") // 标题可以不用填写
                           .setMessage("如果您觉得这个开源项目很棒，希望它能更好地坚持开发下去，可否愿意花一点点钱（推荐 10.24 元）作为对于开发者的激励")
                           .setConfirm("支付宝")
                           .setCancel(null) // 设置 null 表示不显示取消按钮
                           //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                           .setListener(new MessageDialog.OnListener() {

                               @Override
                               public void onConfirm(Dialog dialog) {
                                   try {
                                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2FFKX04202G4K6AVCF5GIY66%3F_s%3Dweb-other"));
                                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                       startActivity(intent);
                                       ToastUtils.show("这个开源项目因为您的支持而能够不断更新、完善，非常感谢您的支持");
                                   } catch (Exception e) {
                                       ToastUtils.show("打开支付宝失败");
                                   }
                               }

                               @Override
                               public void onCancel(Dialog dialog) {}
                           })
                           .show();
                   break;
               default:
                   break;
           }
       }
   */
    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}
