package com.recognition.demo.liebiao;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

public interface OneToManyFlow<T> {

    /**
     * Sets some item view binders to the item type.
     *
     * @param binders the item view binders
     * @return end flow operator
     */
    @NonNull @CheckResult
    @SuppressWarnings("unchecked")
    OneToManyEndpoint<T> to(@NonNull ItemViewBinder<T, ?>... binders);
}