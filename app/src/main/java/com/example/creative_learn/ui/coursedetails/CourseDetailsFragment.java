package com.example.creative_learn.ui.coursedetails;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.creative_learn.R;
import com.example.creative_learn.adapters.CourseSectionAdapter;
import com.example.creative_learn.database.DatabaseHelper;
import com.example.creative_learn.models.CourseSection;

import java.util.List;
import java.util.ArrayList;

public class CourseDetailsFragment extends Fragment {
    private String courseTitleStr;
    private TextView courseTitleView;
    private ImageView courseHeaderImage;
    private DatabaseHelper databaseHelper;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(requireContext());
        databaseHelper.createVideoProgressTable();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_course_details, container, false);

        courseHeaderImage = root.findViewById(R.id.courseHeaderImage);
        courseTitleView = root.findViewById(R.id.courseTitle);

        if (getArguments() != null) {
            courseTitleStr = getArguments().getString("title");
            if (courseTitleView != null) {
                courseTitleView.setText(courseTitleStr);
            }
            setupRecyclerView(root);
            setupHeaderImage(courseTitleStr);
        }

        return root;
    }

    private void setupRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.coursesRecyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            List<CourseSection> sections = getSectionsForCourse(courseTitleStr);
            CourseSectionAdapter adapter = new CourseSectionAdapter(sections, this::onSectionClick);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setupHeaderImage(String courseTitle) {
        if (courseHeaderImage != null) {
            int imageResId = getHeaderImageForCourse(courseTitle);
            courseHeaderImage.setImageResource(imageResId);
        }
    }

    private int getHeaderImageForCourse(String courseTitle) {
        switch (courseTitle) {
            case "C Language Tutorials":
                return R.drawable.c_img;
            case "Data Structures & Algorithms":
                return R.drawable.dsa_img;
            case "Java Tutorials For Beginners":
                return R.drawable.java_img;
            case "Python Programming":
                return R.drawable.python_img;
            case "Web Development Essentials":
                return R.drawable.web_img;
            case "C++ Programming":
                return R.drawable.cpp_img;
            case "Artificial Intelligence":
                return R.drawable.ai_img;
            case "Machine Learning":
                return R.drawable.ml_img;
            default:
                return R.drawable.c_img;
        }
    }

    private void onSectionClick(CourseSection section) {
        Bundle args = new Bundle();
        args.putString("title", section.getTitle());
        args.putString("description", section.getDescription());
        args.putString("videoFileName", section.getVideoFileName());
        args.putInt("videoResourceId", section.getVideoResourceId());

        Navigation.findNavController(requireView())
            .navigate(R.id.action_courseDetails_to_videoPlayer, args);
    }

    private List<CourseSection> getSectionsForCourse(String courseTitle) {
        List<CourseSection> sections = new ArrayList<>();
        
        switch (courseTitle) {
            case "C Language Tutorials":
                sections.add(new CourseSection(
                    "Introduction to C Programming",
                    "Basic concepts, environment setup, first program",
                    "intro_c_vid.mp4",
                    R.raw.intro_c_vid));
                    
                sections.add(new CourseSection(
                    "Basic Structure of C Language",
                    "Understanding core structure and syntax",
                    "struct_c_vid.mp4",
                    R.raw.struct_c_vid));
                    
                sections.add(new CourseSection(
                    "Variables and Data Types in C",
                    "Learn about different variables and data types",
                    "var_data_c_vid.mp4",
                    R.raw.var_data_c_vid));
                    
                sections.add(new CourseSection(
                    "Operators in C",
                    "Understanding different operators",
                    "op_c_vid.mp4",
                    R.raw.op_c_vid));
                    
                sections.add(new CourseSection(
                    "If Else Control Statements in C",
                    "Learn about conditional statements",
                    "ifelse_c_vid.mp4",
                    R.raw.ifelse_c_vid));
                    
                sections.add(new CourseSection(
                    "Switch Case Statement in C",
                    "Understanding switch case statements",
                    "switch_c_vid.mp4",
                    R.raw.switch_c_vid));
                    
                sections.add(new CourseSection(
                    "Loops in C",
                    "Learn about different types of loops",
                    "loop_c_vid.mp4",
                    R.raw.loop_c_vid));
                break;

            case "Data Structures & Algorithms":
                sections.add(new CourseSection(
                    "Introduction to DSA",
                    "Basic concepts of Data Structures",
                    "dsa_vid.mp4",
                    R.raw.dsa_vid));
                    
                sections.add(new CourseSection(
                    "Arrays in DSA: Definition and Concepts",
                    "Understanding arrays implementation",
                    "ds_arr_vid.mp4",
                    R.raw.ds_arr_vid));
                    
                sections.add(new CourseSection(
                    "Introduction to Multidimensional Arrays in DSA",
                    "Learn about multidimensional arrays",
                    "md_arr_vid.mp4",
                    R.raw.md_arr_vid));
                    
                sections.add(new CourseSection(
                    "Overview of Data Structures",
                    "Comprehensive overview",
                    "intro_ds_vid.mp4",
                    R.raw.intro_ds_vid));
                    
                sections.add(new CourseSection(
                    "Definition and Advantages of Data Structures",
                    "Understanding benefits and applications",
                    "def_ds_adv_vid.mp4",
                    R.raw.def_ds_adv_vid));
                    
                sections.add(new CourseSection(
                    "Types of Data Structures",
                    "Learn about various types",
                    "type_ds_vid.mp4",
                    R.raw.type_ds_vid));
                    
                sections.add(new CourseSection(
                    "Basics of Dynamic Memory Allocation",
                    "Understanding memory allocation",
                    "dma_vid.mp4",
                    R.raw.dma_vid));
                    
                sections.add(new CourseSection(
                    "Memory Allocation in C: Using malloc()",
                    "Learn about malloc function",
                    "dma_malloc_vid.mp4",
                    R.raw.dma_malloc_vid));
                    
                sections.add(new CourseSection(
                    "Memory Allocation in C: Using calloc()",
                    "Understanding calloc function",
                    "dma_calloc_vid.mp4",
                    R.raw.dma_calloc_vid));
                    
                sections.add(new CourseSection(
                    "Memory Reallocation in C: Using realloc()",
                    "Learn about realloc function",
                    "dma_realloc_vid.mp4",
                    R.raw.dma_realloc_vid));
                break;

            case "Java Tutorials For Beginners":
                sections.add(new CourseSection(
                    "Introduction to Java Programming",
                    "Basic concepts of Java",
                    "intro_java_vid.mp4",
                    R.raw.intro_java_vid));
                    
                sections.add(new CourseSection(
                    "Java Packages, Classes, and Methods",
                    "Understanding Java structure",
                    "class_method_java_vid.mp4",
                    R.raw.class_method_java_vid));
                    
                sections.add(new CourseSection(
                    "Access Modifiers in Java",
                    "Public, Private, and Static",
                    "pub_pri_java_vid.mp4",
                    R.raw.pub_pri_java_vid));
                    
                sections.add(new CourseSection(
                    "Variables in Java",
                    "Understanding Java variables",
                    "var_java_vid.mp4",
                    R.raw.var_java_vid));
                    
                sections.add(new CourseSection(
                    "Data Types in Java",
                    "Learn about Java data types",
                    "data_java_vid.mp4",
                    R.raw.data_java_vid));
                    
                sections.add(new CourseSection(
                    "Using the Scanner Class in Java",
                    "Input handling in Java",
                    "scanner_java_vid.mp4",
                    R.raw.scanner_java_vid));
                    
                sections.add(new CourseSection(
                    "Reading Keyboard Input in Java",
                    "Learn input methods",
                    "read_java_vid.mp4",
                    R.raw.read_java_vid));
                    
                sections.add(new CourseSection(
                    "Literals in Java",
                    "Understanding Java literals",
                    "let_java_vid.mp4",
                    R.raw.let_java_vid));
                break;

            case "Python Programming":
                sections.add(new CourseSection(
                    "Python for Beginners: One-Shot Tutorial",
                    "Complete Python programming tutorial",
                    "python_vid.mp4",
                    R.raw.python_vid));
                break;

            case "Web Development Essentials":
                sections.add(new CourseSection(
                    "Introduction to Web Development",
                    "Basic concepts of web development",
                    "intro_web_vid.mp4",
                    R.raw.intro_web_vid));
                    
                sections.add(new CourseSection(
                    "What is HTML?",
                    "Understanding HTML basics",
                    "html_vid.mp4",
                    R.raw.html_vid));
                    
                sections.add(new CourseSection(
                    "CSS Properties Overview",
                    "Learn about CSS properties",
                    "pro_css_vid.mp4",
                    R.raw.pro_css_vid));
                    
                sections.add(new CourseSection(
                    "Understanding the CSS Box Model",
                    "Learn about CSS box model",
                    "box_css_vid.mp4",
                    R.raw.box_css_vid));
                    
                sections.add(new CourseSection(
                    "What is Responsiveness in Web Design?",
                    "Understanding responsive design",
                    "res_css_vid.mp4",
                    R.raw.res_css_vid));
                    
                sections.add(new CourseSection(
                    "CSS Flexbox Layout",
                    "Learn about CSS flexbox",
                    "flexbox_css_vid.mp4",
                    R.raw.flexbox_css_vid));
                    
                sections.add(new CourseSection(
                    "Creating Animations with CSS",
                    "Learn CSS animations",
                    "anim_css_vid.mp4",
                    R.raw.anim_css_vid));
                break;

            case "C++ Programming":
                sections.add(new CourseSection(
                    "C++ One-Shot Tutorial",
                    "Complete C++ programming tutorial",
                    "cpp_vid.mp4",
                    R.raw.cpp_vid));
                break;

            case "Artificial Intelligence":
                sections.add(new CourseSection(
                    "Introduction to AI",
                    "Basic concepts of AI",
                    "ai_vid.mp4",
                    R.raw.ai_vid));
                    
                sections.add(new CourseSection(
                    "What is Artificial Intelligence?",
                    "Understanding AI fundamentals",
                    "a_i_vid.mp4",
                    R.raw.a_i_vid));
                    
                sections.add(new CourseSection(
                    "Explanation of AI Concepts",
                    "Detailed explanation of AI concepts",
                    "exp_ai_vid.mp4",
                    R.raw.exp_ai_vid));
                break;

            case "Machine Learning":
                sections.add(new CourseSection(
                    "Introduction to Machine Learning",
                    "Basic concepts of Machine Learning",
                    "ml_vid.mp4",
                    R.raw.ml_vid));
                    
                sections.add(new CourseSection(
                    "AI vs. ML vs. Deep Learning: Key Differences",
                    "Understanding the differences between AI, ML, and Deep Learning",
                    "ai_ml_dl_vid.mp4",
                    R.raw.ai_ml_dl_vid));
                break;
        }
        
        return sections;
    }
} 