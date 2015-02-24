package com.nutsdev.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutsdev.pojo.Information;
import com.nutsdev.materialtest.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by n1ck on 03.01.2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;
    List<Information> data = Collections.emptyList();

    public RecyclerAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        Log.d("myLogs", "onCreateViewHolder called");
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Information current = data.get(position);
        Log.d("myLogs", "onBindViewHolder called " + position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView title;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // ставим клик листенер на целую строку
            itemView.setOnLongClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        /*    icon.setOnClickListener(this);
            title.setOnClickListener(this); */
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(context, "ROW clicked on position " + getPosition(), Toast.LENGTH_SHORT).show();
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        /*    switch (v.getId()) {
                case R.id.listIcon:
                    Toast.makeText(context, "icon clicked on position " + getPosition(), Toast.LENGTH_SHORT).show();
                    delete(getPosition());
                    break;
                case R.id.listText:
                // лучше не делать запуск новой активити в адаптере, а делать это в активити/фрагменте
                //    context.startActivity(new Intent(context, SubActivity.class));
                    if (clickListener != null) {
                        clickListener.itemClicked(v, getPosition());
                    }
                    Toast.makeText(context, "text clicked on position " + getPosition(), Toast.LENGTH_SHORT).show();
                    break;
            } */
        }

        @Override
        public boolean onLongClick(View v) {
            delete(getPosition()); // удаляет выбранную строку по долгому нажатию
            return true;
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
