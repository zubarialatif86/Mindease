package com.example.mindease;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JournalAdaptor extends RecyclerView.Adapter<JournalAdaptor.JournalViewHolder> {

    private List<JournalEntry> journalList;

    public JournalAdaptor(List<JournalEntry> journalList) {
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalEntry entry = journalList.get(position);
        
        holder.tvItemMood.setText(entry.mood);
        holder.tvItemContent.setText(entry.content);
        holder.tvItemStress.setText("Stress: " + entry.stressLevel);
        
        // Format timestamp to readable date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String dateString = sdf.format(new Date(entry.timestamp));
        holder.tvItemDate.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    static class JournalViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemMood, tvItemDate, tvItemContent, tvItemStress;
        
        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemMood = itemView.findViewById(R.id.tvItemMood);
            tvItemDate = itemView.findViewById(R.id.tvItemDate);
            tvItemContent = itemView.findViewById(R.id.tvItemContent);
            tvItemStress = itemView.findViewById(R.id.tvItemStress);
        }
    }
}