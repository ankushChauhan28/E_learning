package com.example.creative_learn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creative_learn.R;
import com.example.creative_learn.models.CourseSection;

import java.util.List;

public class CourseSectionAdapter extends RecyclerView.Adapter<CourseSectionAdapter.ViewHolder> {
    private final List<CourseSection> sections;
    private final OnSectionClickListener listener;

    public interface OnSectionClickListener {
        void onSectionClick(CourseSection section);
    }

    public CourseSectionAdapter(List<CourseSection> sections, OnSectionClickListener listener) {
        this.sections = sections;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseSection section = sections.get(position);
        holder.bind(section);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView descriptionText;

        ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.sectionTitle);
            descriptionText = itemView.findViewById(R.id.sectionDescription);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSectionClick(sections.get(position));
                }
            });
        }

        void bind(CourseSection section) {
            titleText.setText(section.getTitle());
            descriptionText.setText(section.getDescription());
        }
    }
} 