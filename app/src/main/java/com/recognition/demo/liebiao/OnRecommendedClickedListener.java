package com.recognition.demo.liebiao;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/11/17
 *    desc   : MVP 通用性接口
 */
public interface OnRecommendedClickedListener {

    @CheckResult
    boolean onRecommendedClicked(@NonNull View itemView, @NonNull Recommended recommended);
}