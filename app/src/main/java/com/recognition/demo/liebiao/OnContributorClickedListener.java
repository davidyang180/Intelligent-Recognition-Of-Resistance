package com.recognition.demo.liebiao;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

public interface OnContributorClickedListener {

    @CheckResult
    boolean onContributorClicked(@NonNull View itemView, @NonNull Contributor contributor);
}