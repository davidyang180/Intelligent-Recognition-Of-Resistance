package com.recognition.demo.liebiao;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MultiTypeAdapter";

    private @NonNull
    List<?> items;
    private @NonNull
    TypePool typePool;
    protected @Nullable
    LayoutInflater inflater;


    /**
     * Constructs a MultiTypeAdapter with an empty items list.
     */
    public MultiTypeAdapter() {
        this(Collections.emptyList());
    }


    /**
     * Constructs a MultiTypeAdapter with a items list.
     *
     * @param items the items list
     */
    public MultiTypeAdapter(@NonNull List<?> items) {
        this(items, new MultiTypePool());
    }


    /**
     * Constructs a MultiTypeAdapter with a items list and an initial capacity of TypePool.
     *
     * @param items the items list
     * @param initialCapacity the initial capacity of TypePool
     */
    public MultiTypeAdapter(@NonNull List<?> items, int initialCapacity) {
        this(items, new MultiTypePool(initialCapacity));
    }


    /**
     * Constructs a MultiTypeAdapter with a items list and a TypePool.
     *
     * @param items the items list
     * @param pool the type pool
     */
    public MultiTypeAdapter(@NonNull List<?> items, @NonNull TypePool pool) {
        this.items = items;
        this.typePool = pool;
    }


    /**
     * Registers a type class and its item view binder. If you have registered the class,
     * it will override the original binder(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     * <p>
     * Note that the method should not be called after
     * {@link RecyclerView#setAdapter(RecyclerView.Adapter)}, or you have to call the setAdapter
     * again.
     * </p>
     *
     * @param clazz the class of a item
     * @param binder the item view binder
     * @param <T> the item data type
     */
    public <T> void register(
            @NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
        checkAndRemoveAllTypesIfNeed(clazz);
        typePool.register(clazz, binder, new DefaultLinker<T>());
    }


    /**
     * Registers a type class to multiple item view binders. If you have registered the
     * class, it will override the original binder(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     * <p>
     * Note that the method should not be called after
     * {@link RecyclerView#setAdapter(RecyclerView.Adapter)}, or you have to call the setAdapter
     * again.
     * </p>
     *
     * @param clazz the class of a item
     * @param <T> the item data type
     * @return {@link OneToManyFlow} for setting the binders
     * @see #register(Class, ItemViewBinder)
     */
    @CheckResult
    public <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz) {
        checkAndRemoveAllTypesIfNeed(clazz);
        return new OneToManyBuilder<>(this, clazz);
    }


    /**
     * Registers all of the contents in the specified type pool. If you have registered a
     * class, it will override the original binder(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     * <p>
     * Note that the method should not be called after
     * {@link RecyclerView#setAdapter(RecyclerView.Adapter)}, or you have to call the setAdapter
     * again.
     * </p>
     *
     * @param pool type pool containing contents to be added to this adapter inner pool
     * @see #register(Class, ItemViewBinder)
     * @see #register(Class)
     */
    public void registerAll(@NonNull final TypePool pool) {
        final int size = pool.getClasses().size();
        for (int i = 0; i < size; i++) {
            registerWithoutChecking(
                    pool.getClasses().get(i),
                    pool.getItemViewBinders().get(i),
                    pool.getLinkers().get(i)
            );
        }
    }


    /**
     * Sets and updates the items atomically and safely. It is recommended to use this method
     * to update the items with a new wrapper list or consider using {@link CopyOnWriteArrayList}.
     *
     * <p>Note: If you want to refresh the list views after setting items, you should
     * call {@link RecyclerView.Adapter#notifyDataSetChanged()} by yourself.</p>
     *
     * @param items the new items list
     * @since v2.4.1
     */
    public void setItems(@NonNull List<?> items) {
        this.items = items;
    }


    @NonNull
    public List<?> getItems() {
        return items;
    }


    /**
     * Set the TypePool to hold the types and view binders.
     *
     * @param typePool the TypePool implementation
     */
    public void setTypePool(@NonNull TypePool typePool) {
        this.typePool = typePool;
    }


    @NonNull
    public TypePool getTypePool() {
        return typePool;
    }


    @Override
    public final int getItemViewType(int position) {
        Object item = items.get(position);
        return indexInTypesOf(item);
    }


    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewBinder<?, ?> binder = typePool.getItemViewBinders().get(indexViewType);
        binder.adapter = this;
        assert inflater != null;
        return binder.onCreateViewHolder(inflater, parent);
    }


    /**
     * This method is deprecated and unused. You should not call this method.
     * <p>
     * If you need to call the binding, use {@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder,
     * int, List)} instead.
     * </p>
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @throws IllegalAccessError By default.
     * @deprecated Call {@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)}
     * instead.
     */
    @Override @Deprecated
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        throw new IllegalAccessError("You should not call this method. " +
                "Call RecyclerView.Adapter#onBindViewHolder(holder, position, payloads) instead.");
    }


    @Override @SuppressWarnings("unchecked")
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        Object item = items.get(position);
        ItemViewBinder binder = typePool.getItemViewBinders().get(holder.getItemViewType());
        binder.onBindViewHolder(holder, item, payloads);
    }


    @Override
    public final int getItemCount() {
        return items.size();
    }


    /**
     * Called when a view created by this adapter has been recycled, and passes the event to its
     * associated binder.
     *
     * @param holder The ViewHolder for the view being recycled
     * @see RecyclerView.Adapter#onViewRecycled(RecyclerView.ViewHolder)
     * @see ItemViewBinder#onViewRecycled(RecyclerView.ViewHolder)
     */
    @Override @SuppressWarnings("unchecked")
    public final void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewRecycled(holder);
    }


    /**
     * Called by the RecyclerView if a ViewHolder created by this Adapter cannot be recycled
     * due to its transient state, and passes the event to its associated item view binder.
     *
     * @param holder The ViewHolder containing the View that could not be recycled due to its
     * transient state.
     * @return True if the View should be recycled, false otherwise. Note that if this method
     * returns <code>true</code>, RecyclerView <em>will ignore</em> the transient state of
     * the View and recycle it regardless. If this method returns <code>false</code>,
     * RecyclerView will check the View's transient state again before giving a final decision.
     * Default implementation returns false.
     * @see RecyclerView.Adapter#onFailedToRecycleView(RecyclerView.ViewHolder)
     * @see ItemViewBinder#onFailedToRecycleView(RecyclerView.ViewHolder)
     */
    @Override @SuppressWarnings("unchecked")
    public final boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return getRawBinderByViewHolder(holder).onFailedToRecycleView(holder);
    }


    /**
     * Called when a view created by this adapter has been attached to a window, and passes the
     * event to its associated item view binder.
     *
     * @param holder Holder of the view being attached
     * @see RecyclerView.Adapter#onViewAttachedToWindow(RecyclerView.ViewHolder)
     * @see ItemViewBinder#onViewAttachedToWindow(RecyclerView.ViewHolder)
     */
    @Override @SuppressWarnings("unchecked")
    public final void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewAttachedToWindow(holder);
    }


    /**
     * Called when a view created by this adapter has been detached from its window, and passes
     * the event to its associated item view binder.
     *
     * @param holder Holder of the view being detached
     * @see RecyclerView.Adapter#onViewDetachedFromWindow(RecyclerView.ViewHolder)
     * @see ItemViewBinder#onViewDetachedFromWindow(RecyclerView.ViewHolder)
     */
    @Override @SuppressWarnings("unchecked")
    public final void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewDetachedFromWindow(holder);
    }


    @NonNull
    private ItemViewBinder getRawBinderByViewHolder(@NonNull RecyclerView.ViewHolder holder) {
        return typePool.getItemViewBinders().get(holder.getItemViewType());
    }


    int indexInTypesOf(@NonNull Object item) throws BinderNotFoundException {
        int index = typePool.firstIndexOf(item.getClass());
        if (index != -1) {
            @SuppressWarnings("unchecked")
            Linker<Object> linker = (Linker<Object>) typePool.getLinkers().get(index);
            return index + linker.index(item);
        }
        throw new BinderNotFoundException(item.getClass());
    }


    private void checkAndRemoveAllTypesIfNeed(@NonNull Class<?> clazz) {
        if (!typePool.getClasses().contains(clazz)) {
            return;
        }
        Log.w(TAG, "You have registered the " + clazz.getSimpleName() + " type. " +
                "It will override the original binder(s).");
        while (true) {
            int index = typePool.getClasses().indexOf(clazz);
            if (index != -1) {
                typePool.getClasses().remove(index);
                typePool.getItemViewBinders().remove(index);
                typePool.getLinkers().remove(index);
            } else {
                break;
            }
        }
    }


    <T> void registerWithLinker(
            @NonNull Class<? extends T> clazz,
            @NonNull ItemViewBinder<T, ?> binder,
            @NonNull Linker<T> linker) {
        typePool.register(clazz, binder, linker);
    }


    /** A safe register method base on the TypePool's safety for TypePool. */
    @SuppressWarnings("unchecked")
    private void registerWithoutChecking(
            @NonNull Class clazz, @NonNull ItemViewBinder itemViewBinder, @NonNull Linker linker) {
        checkAndRemoveAllTypesIfNeed(clazz);
        typePool.register(clazz, itemViewBinder, linker);
    }
}
