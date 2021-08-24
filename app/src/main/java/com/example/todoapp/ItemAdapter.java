package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    public interface OnClickListener{
        void onItemClicked(int position);
    }

    List<String> items;
    OnLongClickListener onLongClickListener;
    OnClickListener onClickListener;

    /**
     * Takes three parameters in order to create the adapter. The list will populate the adapter and
     * both listeners will dictate how the items within the adapter behave.
     *
     * @param items Takes a list of ToDo objects
     * @param onLongClickListener to add to the items within the list
     * @param onClickListener to add to the items within the list
     */
    public ItemAdapter(List<String> items, OnLongClickListener onLongClickListener,
                       OnClickListener onClickListener){
        this.items = items;
        this.onLongClickListener = onLongClickListener;
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView tv;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_name);
        }
        public void bind(String s) {
            tv.setText(s);
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
