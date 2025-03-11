package com.example.creative_learn.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creative_learn.R;
import com.example.creative_learn.adapters.CourseAdapter;
import com.example.creative_learn.models.Course;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private CourseAdapter courseAdapter;
    private List<Course> allCourses;
    private List<Course> filteredCourses;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize course lists
        initializeCourses();
        setupSearchView(root);
        setupRecyclerView(root);

        return root;
    }

    private void initializeCourses() {
        allCourses = new ArrayList<>();
        filteredCourses = new ArrayList<>();
        
        // Add all courses
        allCourses.add(new Course(1, 
            "C Language Tutorials", 
            "Complete C programming tutorial for beginners to advanced level",
            "https://www.youtube.com/watch?v=7Dh73z3icd8&list=PLu0W_9lII9aiXlHcLx-mDH1Qul38wD3aR",
            R.drawable.c_img));

        allCourses.add(new Course(2,
            "Introduction to Data Structures & Algorithms",
            "Learn DSA concepts with practical implementations",
            "https://www.youtube.com/watch?v=2ZLl8GAk1X4",
            R.drawable.dsa_img));

        allCourses.add(new Course(3,
            "Java Tutorials For Beginners",
            "Complete Java programming tutorial series",
            "https://www.youtube.com/watch?v=UmnCZ7-9yDY",
            R.drawable.java_img));

        allCourses.add(new Course(4,
            "C++ Tutorials For Beginners",
            "Learn C++ programming from scratch",
            "https://www.youtube.com/watch?v=ZzaPdXTrSb8",
            R.drawable.cpp_img));

        allCourses.add(new Course(5,
            "Introduction to Python",
            "Complete Python programming tutorial for beginners",
            "https://www.youtube.com/watch?v=UrsmFxEIp5k",
            R.drawable.python_img));

        allCourses.add(new Course(6,
            "Basics Of Artificial Intelligence",
            "Learn the fundamentals of AI and its applications",
            "https://www.youtube.com/watch?v=JMUxmLyrhSk",
            R.drawable.ai_img));

        allCourses.add(new Course(7,
            "Introduction to Machine Learning",
            "Comprehensive guide to Machine Learning concepts",
            "https://www.youtube.com/watch?v=LvC68w9JS4Y",
            R.drawable.ml_img));

        allCourses.add(new Course(8,
            "Web Development Tutorials For Beginners",
            "Learn web development from scratch to advanced",
            "https://www.youtube.com/watch?v=4WjtQjPQGIs",
            R.drawable.web_img));

        filteredCourses.addAll(allCourses);
    }

    private void setupSearchView(View root) {
        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCourses(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCourses(newText);
                return true;
            }
        });
    }

    private void setupRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        courseAdapter = new CourseAdapter(filteredCourses, course -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(course.getUrl()));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Unable to open course", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(courseAdapter);
    }

    private void filterCourses(String query) {
        filteredCourses.clear();
        
        if (query.isEmpty()) {
            filteredCourses.addAll(allCourses);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Course course : allCourses) {
                if (course.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                    course.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                    filteredCourses.add(course);
                }
            }
        }
        
        courseAdapter.notifyDataSetChanged();
    }
} 