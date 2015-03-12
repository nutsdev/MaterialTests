package com.nutsdev.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nutsdev.materialtest.R;

import java.util.ArrayList;

/**
 * Created by user on 12.03.15.
 */
public class AdapterRecyclerItemAnimations extends RecyclerView.Adapter<AdapterRecyclerItemAnimations.Holder> {

    private ArrayList<String> listData = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public AdapterRecyclerItemAnimations(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = layoutInflater.inflate(R.layout.custom_row_item_animations, parent, false);
        Holder holder = new Holder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        String data = listData.get(position);
        holder.textDataItem.setText(data);
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(String item) {
        listData.add(item);
        notifyItemInserted(listData.size());
    }

    public void removeItem(String item) {
        int position = listData.indexOf(item);
        if (position != -1) {
            listData.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView textDataItem;
        ImageButton buttonDelete;

        public Holder(View itemView) {
            super(itemView);
            textDataItem = (TextView) itemView.findViewById(R.id.text_item);
            buttonDelete = (ImageButton) itemView.findViewById(R.id.button_delete);
        }
    }

}
