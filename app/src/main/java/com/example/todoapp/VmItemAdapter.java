package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.backend.ToDo;

import java.util.ArrayList;
import java.util.List;

public class VmItemAdapter extends RecyclerView.Adapter<VmItemAdapter.Holder> {

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    public interface OnClickListener{
        void onItemClicked(int position);
    }

    public void setItems(List<ToDo> items){
        this.items = items;
    }
    private List<ToDo> items = new ArrayList<>();
    public ToDo getItem(int position){
        return items.get(position);
    }
    VmItemAdapter.OnLongClickListener onLongClickListener;
    VmItemAdapter.OnClickListener onClickListener;

    public void setOnLongClickListener(VmItemAdapter.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public VmItemAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent,false);
        return new VmItemAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VmItemAdapter.Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_name);
        }

        public void bind(ToDo item) {
            ToDo mItem = item;
            tv.setText(mItem.getTodo());
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
