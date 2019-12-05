package com.example.smb1;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Product> list;
    private Context context;
    private static final String extra_add_edit = "add_edit";
    private OnItemClickListener listener;

    public MyAdapter(List<Product> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Product p = list.get(position);
        holder.name.setText(p.getName());
        holder.amount.setText(p.getAmount()+"");
        holder.price.setText(p.getPrice()+"");
        holder.isComplete.setChecked(p.isComplete());
        holder.id = p.getId();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView name;
        private TextView amount;
        private TextView price;
        private CheckBox isComplete;
        private long id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            amount = itemView.findViewById(R.id.product_amount);
            price = itemView.findViewById(R.id.product_price);
            isComplete = itemView.findViewById(R.id.product_colected);
            itemView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(list.get(position));
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Options");
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Edit");
            menu.add(this.getAdapterPosition(), v.getId(), 0, "Delete");
        }
    }

    public Product getProductAtIndex(int index){

        return list.get(index);
    }

    public interface OnItemClickListener{
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
