package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;

final class DefaultLinker<T> implements Linker<T> {

    @Override
    public int index(@NonNull T t) {
        return 0;
    }
}
