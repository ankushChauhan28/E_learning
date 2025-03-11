package com.example.creative_learn.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creative_learn.R;
import com.example.creative_learn.adapters.CourseAdapter;
import com.example.creative_learn.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {
    private RecyclerView recyclerView;
    private CourseAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courses, container, false);
        
        recyclerView = root.findViewById(R.id.coursesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        List<Course> courses = getCourses();
        adapter = new CourseAdapter(courses, this::onCourseClick);
        recyclerView.setAdapter(adapter);
        
        return root;
    }

    private void onCourseClick(Course course) {
        Bundle args = new Bundle();
        args.putString("title", course.getTitle());
        
        Navigation.findNavController(requireView())
            .navigate(R.id.action_courses_to_courseDetails, args);
    }

    private List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        
        courses.add(new Course(
            1,
            "C Language Tutorials",
            "Learn C Programming from scratch",
            "https://example.com/c",
            R.drawable.c_img));
            
        courses.add(new Course(
            2,
            "Data Structures & Algorithms",
            "Master Data Structures and Algorithms",
            "https://example.com/dsa",
            R.drawable.dsa_img));
            
        courses.add(new Course(
            3,
            "Java Tutorials For Beginners",
            "Complete Java Programming Course",
            "https://example.com/java",
            R.drawable.java_img));
            
        courses.add(new Course(
            4,
            "Python Programming",
            "Python for Beginners",
            "https://example.com/python",
            R.drawable.python_img));
            
        courses.add(new Course(
            5,
            "Web Development Essentials",
            "Learn Web Development",
            "https://example.com/web",
            R.drawable.web_img));
            
        courses.add(new Course(
            6,
            "C++ Programming",
            "C++ Tutorial for Beginners",
            "https://example.com/cpp",
            R.drawable.cpp_img));
            
        courses.add(new Course(
            7,
            "Artificial Intelligence",
            "Introduction to AI",
            "https://example.com/ai",
            R.drawable.ai_img));
            
        courses.add(new Course(
            8,
            "Machine Learning",
            "ML Fundamentals",
            "https://example.com/ml",
            R.drawable.ml_img));
            
        return courses;
    }
} 