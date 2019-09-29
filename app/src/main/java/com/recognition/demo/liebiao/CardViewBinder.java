package com.recognition.demo.liebiao;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.recyclerview.widget.RecyclerView;
import com.recognition.demo.R;


/**
 * @author drakeet
 */
@SuppressWarnings("WeakerAccess")
public class CardViewBinder extends ItemViewBinder<Card, CardViewBinder.ViewHolder> {

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.about_page_item_card, parent, false);
        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Card card) {
        holder.content.setText(card.content);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(com.romainpiel.titanic.library.R.id.content);
        }
    }


    protected long getItemId(@NonNull Card item) {
        return item.hashCode();
    }
}