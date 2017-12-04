package com.lits.buddycare.ui.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lits.buddycare.R;
import com.lits.buddycare.data.model.User;
import com.lits.buddycare.data.model.Wish;
import com.lits.buddycare.util.Constants;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


/**
 * Created by a.trykashnyi on 05.10.16.
 */

public class FeedAdapter extends RealmRecyclerViewAdapter<Wish, FeedAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    FeedAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Wish> data) {
        super(data, true);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wish wish = getData().get(position);
        User user = wish.getUser();
        if (user != null) {
            holder.textName.setText(user.getName());
            Glide.with(context).load(user.getPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_account).error(R.drawable.ic_account))
                    .into(holder.imgAvatar);
        }
        if (Constants.TYPE_WISH.equals(wish.getType())) {
            holder.textType.setText(R.string.type_wish);
            holder.textType.setBackgroundResource(R.color.colorBlue);
        } else {
            holder.textType.setText(R.string.type_can_do);
            holder.textType.setBackgroundResource(R.color.colorOrange);
        }
        holder.textWish.setText(wish.getTitle());
    }

    @Override
    public int getItemCount() {
        return getData() == null ? 0 : getData().size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView textName;
        private TextView textType;
        private TextView textWish;

        ViewHolder(final View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            textName = itemView.findViewById(R.id.text_name);
            textType = itemView.findViewById(R.id.text_type);
            textWish = itemView.findViewById(R.id.text_wish);
            itemView.setOnClickListener(view -> {
                if (onItemClickListener != null && getAdapterPosition() != -1) {
                    onItemClickListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }

}
