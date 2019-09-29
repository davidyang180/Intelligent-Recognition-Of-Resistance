package com.recognition.demo.liebiao;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public interface Linker<T> {

    /**
     * Returns the index of your registered binders for your item. The result should be in range of
     * {@code [0, one-to-multiple-binders.length)}.
     *
     * <p>Note: The argument of {@link OneToManyFlow#to(ItemViewBinder[])} is the
     * one-to-multiple-binders.</p>
     *
     * @param t Your item data
     * @return The index of your registered binders
     * @see OneToManyFlow#to(ItemViewBinder[])
     * @see OneToManyEndpoint#withLinker(Linker)
     */
    @IntRange(from = 0) int index(@NonNull T t);
}