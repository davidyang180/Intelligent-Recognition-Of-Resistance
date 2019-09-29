package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;

public interface OneToManyEndpoint<T> {

    /**
     * Sets a linker to link the items and binders by array index.
     *
     * @param linker the row linker
     * @see Linker
     */
    void withLinker(@NonNull Linker<T> linker);

    /**
     * Sets a class linker to link the items and binders by the class instance of binders.
     *
     * @param classLinker the class linker
     * @see ClassLinker
     */
    void withClassLinker(@NonNull ClassLinker<T> classLinker);
}