package com.recognition.demo.liebiao;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

class OneToManyBuilder<T> implements OneToManyFlow<T>, OneToManyEndpoint<T> {

    private @NonNull
    final MultiTypeAdapter adapter;
    private @NonNull final Class<? extends T> clazz;
    private ItemViewBinder<T, ?>[] binders;


    OneToManyBuilder(@NonNull MultiTypeAdapter adapter, @NonNull Class<? extends T> clazz) {
        this.clazz = clazz;
        this.adapter = adapter;
    }


    @Override @NonNull @CheckResult
    @SafeVarargs
    public final OneToManyEndpoint<T> to(@NonNull ItemViewBinder<T, ?>... binders) {
        this.binders = binders;
        return this;
    }


    @Override
    public void withLinker(@NonNull Linker<T> linker) {
        doRegister(linker);
    }


    @Override
    public void withClassLinker(@NonNull ClassLinker<T> classLinker) {
        doRegister(ClassLinkerWrapper.wrap(classLinker, binders));
    }


    private void doRegister(@NonNull Linker<T> linker) {
        for (ItemViewBinder<T, ?> binder : binders) {
            adapter.registerWithLinker(clazz, binder, linker);
        }
    }
}
