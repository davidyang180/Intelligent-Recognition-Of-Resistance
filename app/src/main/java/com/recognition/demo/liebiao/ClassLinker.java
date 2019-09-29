package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;

public interface ClassLinker<T> {

    /**
     * Returns the class of your registered binders for your item.
     *
     * @param t Your item data
     * @return The index of your registered binders
     * @see OneToManyEndpoint#withClassLinker(ClassLinker)
     */
    @NonNull Class<? extends ItemViewBinder<T, ?>> index(@NonNull T t);
}