package com.lits.buddycare.ui.profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lits.buddycare.R;
import com.lits.buddycare.data.model.Wish;
import com.lits.buddycare.ui.feed.OnItemClickListener;
import com.lits.buddycare.util.Constants;

import java.util.List;


/**
 * Created by a.trykashnyi on 05.10.16.
 */

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder>  {

    private OnItemClickListener itemClickListener;
    private List<Wish> wishes;


    @Override
    public WishAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish, parent, false);
        return new WishAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wish wish = wishes.get(position);
        if (Constants.TYPE_WISH.equals(wish.getType())) {
            holder.textType.setText(R.string.type_wish);
            holder.textType.setBackgroundResource(R.color.colorBlue);
            holder.btnCanDo.setVisibility(View.VISIBLE);
        } else {
            holder.textType.setText(R.string.type_can_do);
            holder.textType.setBackgroundResource(R.color.colorOrange);
            holder.btnCanDo.setVisibility(View.GONE);
        }
        holder.textWish.setText(wish.getTitle());
    }

    @Override
    public int getItemCount() {
        return wishes == null ? 0 : wishes.size();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItems(List<Wish> items) {
        this.wishes = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textType;
        private TextView textWish;
        private Button btnCanDo;

        ViewHolder(final View itemView) {
            super(itemView);
            textType = itemView.findViewById(R.id.text_type);
            textWish = itemView.findViewById(R.id.text_wish);
            btnCanDo = itemView.findViewById(R.id.btn_can_do);
        }
    }

}
