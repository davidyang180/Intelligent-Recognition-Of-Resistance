package com.recognition.demo.liebiao;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final @NonNull
    MultiTypeAdapter adapter;
    private final Class<?>[] dividerClasses = { License.class, Recommended.class };


    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param adapter The MultiTypeAdapter
     */
    DividerItemDecoration(@NonNull MultiTypeAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (adapter.getItemCount() == 0) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        List<?> items = adapter.getItems();
        int position = parent.getChildAdapterPosition(view);
        boolean should = false;
        for (int i = 0; !should && i < dividerClasses.length; i++) {
            should = position + 1 < items.size()
                    && items.get(position).getClass().isAssignableFrom(dividerClasses[i])
                    && (items.get(position + 1).getClass().isAssignableFrom(dividerClasses[i]));
        }
        if (should) {
            outRect.set(0, 0, 0, 1);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
