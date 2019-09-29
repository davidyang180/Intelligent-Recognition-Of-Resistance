package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MultiTypePool implements TypePool {

    private @NonNull
    final List<Class<?>> classes;
    private @NonNull final List<ItemViewBinder<?, ?>> binders;
    private @NonNull final List<Linker<?>> linkers;


    /**
     * Constructs a MultiTypePool with default lists.
     */
    public MultiTypePool() {
        this.classes = new ArrayList<>();
        this.binders = new ArrayList<>();
        this.linkers = new ArrayList<>();
    }


    /**
     * Constructs a MultiTypePool with default lists and a specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     */
    public MultiTypePool(int initialCapacity) {
        this.classes = new ArrayList<>(initialCapacity);
        this.binders = new ArrayList<>(initialCapacity);
        this.linkers = new ArrayList<>(initialCapacity);
    }


    /**
     * Constructs a MultiTypePool with specified lists.
     *
     * @param classes the list for classes
     * @param binders the list for binders
     * @param linkers the list for linkers
     */
    public MultiTypePool(
            @NonNull List<Class<?>> classes,
            @NonNull List<ItemViewBinder<?, ?>> binders,
            @NonNull List<Linker<?>> linkers) {
        this.classes = classes;
        this.binders = binders;
        this.linkers = linkers;
    }


    @Override
    public <T> void register(
            @NonNull Class<? extends T> clazz,
            @NonNull ItemViewBinder<T, ?> binder,
            @NonNull Linker<T> linker) {
        classes.add(clazz);
        binders.add(binder);
        linkers.add(linker);
    }


    @Override
    public int firstIndexOf(@NonNull final Class<?> clazz) {
        int index = classes.indexOf(clazz);
        if (index != -1) {
            return index;
        }
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return -1;
    }


    @NonNull @Override
    public List<Class<?>> getClasses() {
        return classes;
    }


    @NonNull @Override
    public List<ItemViewBinder<?, ?>> getItemViewBinders() {
        return binders;
    }


    @NonNull
    public List<Linker<?>> getLinkers() {
        return linkers;
    }
}

