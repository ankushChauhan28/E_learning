package com.example.creative_learn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creative_learn.R;
import com.example.creative_learn.models.Course;

import java.util.List;

public class EnrolledCoursesAdapter extends RecyclerView.Adapter<EnrolledCoursesAdapter.ViewHolder> {
    private List<Course> courses;
    private OnCourseClickListener listener;

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    public EnrolledCoursesAdapter(List<Course> courses, OnCourseClickListener listener) {
        this.courses = courses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_enrolled_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, courseProgress;
        ImageView thumbnailImage;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.courseTitle);
            thumbnailImage = itemView.findViewById(R.id.courseThumbnail);
            courseProgress = itemView.findViewById(R.id.courseProgress);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCourseClick(courses.get(position));
                }
            });
        }

        void bind(Course course) {
            titleText.setText(course.getTitle());
            thumbnailImage.setImageResource(course.getThumbnailResId());
            courseProgress.setText(String.format("Progress: %d%%", course.getProgress()));
            progressBar.setProgress(course.getProgress());
        }
    }
} 