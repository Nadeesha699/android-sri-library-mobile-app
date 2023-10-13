package com.example.bookstrore;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {

    private List<ListItem> itemList;
    private List<ListItem> filteredList;
    private Context context;

    public CustomAdapter(Context context, List<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.filteredList = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = filteredList.get(position);
        holder.button.setText(item.getButtonText());
        holder.textView1.setText(item.getTextView1Text());
        holder.textView2.setText(item.getTextView2Text());
        holder.textView3.setText(item.getTextView3Text());
        holder.imageView.setImageResource(item.getImageResource());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LibraryActivity.class);
                intent.putExtra("data",1);
                intent.putExtra("name",holder.textView1.getText().toString());
                startActivity(context,intent,null);
            }
        });
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.Button1);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                filteredList.clear();
                if (filterPattern.isEmpty()) {
                    filteredList.addAll(itemList);
                } else {
                    for (ListItem item : itemList) {
                        if (item.getButtonText().toLowerCase().contains(filterPattern) ||
                                item.getTextView1Text().toLowerCase().contains(filterPattern) ||
                                item.getTextView2Text().toLowerCase().contains(filterPattern) ||
                                item.getTextView3Text().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

}
