package com.example.creative_learn.ui.explore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ScrollView;

import com.example.creative_learn.R;
import com.example.creative_learn.adapters.CourseAdapter;
import com.example.creative_learn.database.DatabaseHelper;
import com.example.creative_learn.models.Course;

import java.util.List;

public class ExploreFragment extends Fragment {
    private CourseAdapter courseAdapter;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        
        progressBar = root.findViewById(R.id.progressBar);
        
        setupRecyclerView(root);
        loadCourses();
        
        return root;
    }

    private void setupRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.coursesRecyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
            List<Course> courses = databaseHelper.getAllCourses();
            
            courseAdapter = new CourseAdapter(courses, course -> {
                Bundle args = new Bundle();
                args.putString("title", course.getTitle());
                args.putString("url", course.getUrl());
                args.putInt("thumbnail", course.getThumbnailResId());
                
                Navigation.findNavController(requireView())
                    .navigate(R.id.nav_course_details, args);
            });
            
            recyclerView.setAdapter(courseAdapter);
        }
    }

    private void loadCourses() {
        mainHandler.post(() -> {
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        new Thread(() -> {
            // Simulate network delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            mainHandler.post(() -> {
                setupRecyclerView(getView());
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }).start();
    }
} 