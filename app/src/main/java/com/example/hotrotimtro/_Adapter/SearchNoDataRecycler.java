package com.example.hotrotimtro._Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotrotimtro.R;

public class SearchNoDataRecycler extends RecyclerView.Adapter<SearchNoDataRecycler.SearchNoDataViewHolder> {
    @NonNull
    public SearchNoDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_no_data_found_recycler, parent, false);
        return new SearchNoDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNoDataViewHolder viewHolder, int position) {
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class SearchNoDataViewHolder extends RecyclerView.ViewHolder {
        public SearchNoDataViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}