package com.example.creative_learn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creative_learn.R;
import com.example.creative_learn.models.CourseContent;

import java.util.List;

public class CourseContentAdapter extends RecyclerView.Adapter<CourseContentAdapter.ViewHolder> {
    private final List<CourseContent> contentList;
    private final OnContentClickListener listener;

    public interface OnContentClickListener {
        void onContentClick(CourseContent content);
    }

    public CourseContentAdapter(List<CourseContent> contentList, OnContentClickListener listener) {
        this.contentList = contentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseContent content = contentList.get(position);
        holder.bind(content);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleText;
        final TextView descriptionText;
        final ImageButton playButton;

        ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.contentTitle);
            descriptionText = itemView.findViewById(R.id.contentDescription);
            playButton = itemView.findViewById(R.id.playButton);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onContentClick(contentList.get(position));
                }
            });

            playButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onContentClick(contentList.get(position));
                }
            });
        }

        void bind(CourseContent content) {
            titleText.setText(content.getTitle());
            descriptionText.setText(content.getDescription());
        }
    }
} 