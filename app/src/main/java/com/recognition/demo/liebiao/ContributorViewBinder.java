package com.recognition.demo.liebiao;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recognition.demo.R;

import static android.net.Uri.parse;

//import androidx.recyclerview.widget.RecyclerView;

/**
 * @author drakeet
 */
@SuppressWarnings("WeakerAccess")
public class ContributorViewBinder extends ItemViewBinder<Contributor, ContributorViewBinder.ViewHolder> {

    private @NonNull final AbsAboutActivity activity;


    public ContributorViewBinder(@NonNull AbsAboutActivity activity) {
        this.activity = activity;
    }


    @NonNull @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.about_page_item_contributor, parent, false), activity);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Contributor contributor) {
        holder.avatar.setImageResource(contributor.avatarResId);
        holder.name.setText(contributor.name);
        holder.desc.setText(contributor.desc);
        holder.data = contributor;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView avatar;
        public TextView name;
        public TextView desc;
        public Contributor data;
        protected @NonNull final AbsAboutActivity activity;


        public ViewHolder(View itemView, @NonNull AbsAboutActivity activity) {
            super(itemView);
            this.activity = activity;
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            OnContributorClickedListener listener = activity.getOnContributorClickedListener();
            if (listener != null && listener.onContributorClicked(v, data)) {
                return;
            }
            if (data.url != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(parse(data.url));
                try {
                    v.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    protected long getItemId(@NonNull Contributor item) {
        return item.hashCode();
    }
}