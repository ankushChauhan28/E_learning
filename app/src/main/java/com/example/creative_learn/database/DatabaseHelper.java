package com.example.creative_learn.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.creative_learn.R;
import com.example.creative_learn.models.Course;
import com.example.creative_learn.models.CourseSection;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements AutoCloseable {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "Elearning.db";
    private static final int DATABASE_VERSION = 5;

    // Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_COURSE_SECTIONS = "course_sections";
    private static final String TABLE_VIDEO_PROGRESS = "video_progress";
    
    // Common columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PROFILE_IMAGE = "profile_image";
    
    // Course columns
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_THUMBNAIL = "thumbnail";
    
    // Course sections columns
    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String COLUMN_VIDEO_FILE = "video_file";
    private static final String COLUMN_VIDEO_RESOURCE = "video_resource";

    // Video progress columns
    private static final String COLUMN_VIDEO_ID = "video_id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_PROGRESS = "progress";
    private static final String COLUMN_IS_COMPLETED = "is_completed";
    private static final String COLUMN_LAST_POSITION = "last_position";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Create users table with profile image column
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"
                    + COLUMN_EMAIL + " TEXT UNIQUE,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_PROFILE_IMAGE + " TEXT"
                    + ")";
                    
            // Create courses table with thumbnail column
            String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_THUMBNAIL + " TEXT"
                    + ")";

            // Create course sections table
            String CREATE_COURSE_SECTIONS_TABLE = "CREATE TABLE " + TABLE_COURSE_SECTIONS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_COURSE_ID + " INTEGER,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_VIDEO_FILE + " TEXT,"
                    + COLUMN_VIDEO_RESOURCE + " INTEGER,"
                    + "FOREIGN KEY(" + COLUMN_COURSE_ID + ") REFERENCES " 
                    + TABLE_COURSES + "(" + COLUMN_ID + ")"
                    + ")";

            // Create video progress table
            String CREATE_VIDEO_PROGRESS_TABLE = "CREATE TABLE " + TABLE_VIDEO_PROGRESS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_VIDEO_ID + " TEXT,"
                    + COLUMN_USER_EMAIL + " TEXT,"
                    + COLUMN_PROGRESS + " FLOAT,"
                    + COLUMN_IS_COMPLETED + " INTEGER DEFAULT 0,"
                    + COLUMN_LAST_POSITION + " LONG,"
                    + "UNIQUE(" + COLUMN_VIDEO_ID + ", " + COLUMN_USER_EMAIL + ")"
                    + ")";

            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_COURSES_TABLE);
            db.execSQL(CREATE_COURSE_SECTIONS_TABLE);
            db.execSQL(CREATE_VIDEO_PROGRESS_TABLE);
            
            // Insert default courses
            insertDefaultCourses(db);
            insertDefaultCourseSections(db);
            
            Log.d(TAG, "Database tables created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error creating database tables", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 5) {
                // Create video progress table if it doesn't exist
                String CREATE_VIDEO_PROGRESS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_VIDEO_PROGRESS + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_VIDEO_ID + " TEXT,"
                        + COLUMN_USER_EMAIL + " TEXT,"
                        + COLUMN_PROGRESS + " FLOAT,"
                        + COLUMN_IS_COMPLETED + " INTEGER DEFAULT 0,"
                        + COLUMN_LAST_POSITION + " LONG,"
                        + "UNIQUE(" + COLUMN_VIDEO_ID + ", " + COLUMN_USER_EMAIL + ")"
                        + ")";
                db.execSQL(CREATE_VIDEO_PROGRESS_TABLE);
                Log.d(TAG, "Video progress table created successfully");
            }
            
            if (oldVersion < 4) {
                // Previous upgrade logic
                db.execSQL("ALTER TABLE " + TABLE_USERS + 
                    " ADD COLUMN " + COLUMN_PROFILE_IMAGE + " TEXT");
            }
            
            Log.d(TAG, "Database upgraded from " + oldVersion + " to " + newVersion);
        } catch (Exception e) {
            Log.e(TAG, "Error upgrading database", e);
        }
    }

    private void insertDefaultCourses(SQLiteDatabase db) {
        String[] courses = {
            "C Language Tutorials|Learn C Programming from scratch|https://www.youtube.com/watch?v=7Dh73z3icd8&list=PLu0W_9lII9aiXlHcLx-mDH1Qul38wD3aR|c_img",
            "Data Structures & Algorithms|Learn DSA concepts|https://www.youtube.com/watch?v=2ZLl8GAk1X4|dsa_img",
            "Java Tutorials For Beginners|Complete Java Course|https://www.youtube.com/watch?v=UmnCZ7-9yDY|java_img",
            "C++ Tutorials For Beginners|Learn C++ from scratch|https://www.youtube.com/watch?v=ZzaPdXTrSb8|cpp_img",
            "Introduction to Python|Python Tutorial for Beginners|https://www.youtube.com/watch?v=UrsmFxEIp5k|python_img",
            "Basics Of Artificial Intelligence|Learn AI fundamentals|https://www.youtube.com/watch?v=JMUxmLyrhSk|ai_img",
            "Introduction to Machine Learning|ML for beginners|https://www.youtube.com/watch?v=LvC68w9JS4Y|ml_img",
            "Web Development Tutorials For Beginners|Learn web development from scratch|https://www.youtube.com/watch?v=4WjtQjPQGIs|web_img"
        };

        for (String courseData : courses) {
            String[] data = courseData.split("\\|");
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, data[0]);
            values.put(COLUMN_DESCRIPTION, data[1]);
            values.put(COLUMN_URL, data[2]);
            values.put(COLUMN_THUMBNAIL, data[3]);
            db.insert(TABLE_COURSES, null, values);
        }
    }

    private void insertDefaultCourseSections(SQLiteDatabase db) {
        // C Programming Sections
        long cCourseId = getCourseId(db, "C Language Tutorials");
        if (cCourseId != -1) {
            insertCourseSection(db, cCourseId,
                "Introduction to C Programming",
                "Learn the fundamentals and basics of C programming language",
                "intro_c_vid.mp4",
                R.raw.intro_c_vid);
                
            insertCourseSection(db, cCourseId,
                "Basic Structure of C Language",
                "Understanding the core structure and syntax of C programs",
                "struct_c_vid.mp4",
                R.raw.struct_c_vid);
                
            insertCourseSection(db, cCourseId,
                "Variables and Data Types in C",
                "Learn about different variables and data types used in C",
                "var_data_c_vid.mp4",
                R.raw.var_data_c_vid);
                
            insertCourseSection(db, cCourseId,
                "Operators in C",
                "Understanding different operators and their usage in C",
                "op_c_vid.mp4",
                R.raw.op_c_vid);
                
            insertCourseSection(db, cCourseId,
                "If Else Control Statements in C",
                "Learn about conditional statements in C",
                "ifelse_c_vid.mp4",
                R.raw.ifelse_c_vid);
                
            insertCourseSection(db, cCourseId,
                "Switch Case Statement in C",
                "Understanding switch case statements and their implementation",
                "switch_c_vid.mp4",
                R.raw.switch_c_vid);
                
            insertCourseSection(db, cCourseId,
                "Loops in C",
                "Learn about different types of loops in C",
                "loop_c_vid.mp4",
                R.raw.loop_c_vid);
        }

        // DSA Sections
        long dsaCourseId = getCourseId(db, "Data Structures & Algorithms");
        if (dsaCourseId != -1) {
            insertCourseSection(db, dsaCourseId,
                "Introduction to DSA",
                "Basic concepts and importance of Data Structures",
                "dsa_vid.mp4",
                R.raw.dsa_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Arrays in DSA: Definition and Concepts",
                "Understanding arrays and their implementation",
                "ds_arr_vid.mp4",
                R.raw.ds_arr_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Introduction to Multidimensional Arrays in DSA",
                "Learn about multidimensional arrays and their usage",
                "md_arr_vid.mp4",
                R.raw.md_arr_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Overview of Data Structures",
                "Comprehensive overview of different data structures",
                "into_ds_vid.mp4",
                R.raw.intro_ds_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Definition and Advantages of Data Structures",
                "Understanding the benefits and applications of data structures",
                "def_ds_adv_vid.mp4",
                R.raw.def_ds_adv_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Types of Data Structures",
                "Learn about various types of data structures",
                "type_ds_vid.mp4",
                R.raw.type_ds_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Basics of Dynamic Memory Allocation",
                "Understanding memory allocation in programming",
                "dma_vid.mp4",
                R.raw.dma_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Memory Allocation in C: Using malloc()",
                "Learn about malloc() function for memory allocation",
                "dma_malloc_vid.mp4",
                R.raw.dma_malloc_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Memory Allocation in C: Using calloc()",
                "Understanding calloc() function for memory allocation",
                "dma_calloc_vid.mp4",
                R.raw.dma_calloc_vid);
                
            insertCourseSection(db, dsaCourseId,
                "Memory Reallocation in C: Using realloc()",
                "Learn about realloc() function for memory reallocation",
                "dma_realloc_vid.mp4",
                R.raw.dma_realloc_vid);
        }

        // Java Programming Sections
        long javaCourseId = getCourseId(db, "Java Tutorials For Beginners");
        if (javaCourseId != -1) {
            insertCourseSection(db, javaCourseId,
                "Introduction to Java Programming",
                "Basic concepts and introduction to Java",
                "intro_java_vid.mp4",
                R.raw.intro_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Java Packages, Classes, and Methods",
                "Understanding Java packages, classes, and methods",
                "class_method_java_vid.mp4",
                R.raw.class_method_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Access Modifiers in Java",
                "Learn about public, private, and static modifiers",
                "pub_pri_java_vid.mp4",
                R.raw.pub_pri_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Variables in Java",
                "Understanding variables in Java",
                "var_java_vid.mp4",
                R.raw.var_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Data Types in Java",
                "Learn about different data types in Java",
                "data_java_vid.mp4",
                R.raw.data_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Using the Scanner Class in Java",
                "Understanding Scanner class for input",
                "scanner_java_vid.mp4",
                R.raw.scanner_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Reading Keyboard Input in Java",
                "Learn how to read keyboard input",
                "read_java_vid.mp4",
                R.raw.read_java_vid);
                
            insertCourseSection(db, javaCourseId,
                "Literals in Java",
                "Understanding literals in Java",
                "let_java_vid.mp4",
                R.raw.let_java_vid);
        }

        // Python Section
        long pythonCourseId = getCourseId(db, "Introduction to Python");
        if (pythonCourseId != -1) {
            insertCourseSection(db, pythonCourseId,
                "Python for Beginners: One-Shot Tutorial",
                "Complete Python programming tutorial",
                "python_vid.mp4",
                R.raw.python_vid);
        }

        // Web Development Sections
        long webCourseId = getCourseId(db, "Web Development Tutorials For Beginners");
        if (webCourseId != -1) {
            insertCourseSection(db, webCourseId,
                "Introduction to Web Development",
                "Basic concepts of web development",
                "intro_web_vid.mp4",
                R.raw.intro_web_vid);
                
            insertCourseSection(db, webCourseId,
                "What is HTML?",
                "Understanding HTML basics",
                "html_vid.mp4",
                R.raw.html_vid);
                
            insertCourseSection(db, webCourseId,
                "CSS Properties Overview",
                "Learn about CSS properties",
                "pro_css_vid.mp4",
                R.raw.pro_css_vid);
                
            insertCourseSection(db, webCourseId,
                "Understanding the CSS Box Model",
                "Learn about CSS box model",
                "box_css_vid.mp4",
                R.raw.box_css_vid);
                
            insertCourseSection(db, webCourseId,
                "What is Responsiveness in Web Design?",
                "Understanding responsive design",
                "res_css_vid.mp4",
                R.raw.res_css_vid);
                
            insertCourseSection(db, webCourseId,
                "CSS Flexbox Layout",
                "Learn about CSS flexbox",
                "flexbox_css_vid.mp4",
                R.raw.flexbox_css_vid);
                
            insertCourseSection(db, webCourseId,
                "Creating Animations with CSS",
                "Learn CSS animations",
                "anim_css_vid.mp4",
                R.raw.anim_css_vid);
        }

        // C++ Section
        long cppCourseId = getCourseId(db, "C++ Tutorials For Beginners");
        if (cppCourseId != -1) {
            insertCourseSection(db, cppCourseId,
                "C++ One-Shot Tutorial",
                "Complete C++ programming tutorial",
                "cpp_vid.mp4",
                R.raw.cpp_vid);
        }

        // AI Sections
        long aiCourseId = getCourseId(db, "Basics Of Artificial Intelligence");
        if (aiCourseId != -1) {
            insertCourseSection(db, aiCourseId,
                "Introduction to AI",
                "Basic concepts of AI",
                "ai_vid.mp4",
                R.raw.ai_vid);
                
            insertCourseSection(db, aiCourseId,
                "What is Artificial Intelligence?",
                "Understanding AI fundamentals",
                "a_i_vid.mp4",
                R.raw.a_i_vid);
                
            insertCourseSection(db, aiCourseId,
                "Explanation of AI Concepts",
                "Detailed explanation of AI concepts",
                "exp_ai_vid.mp4",
                R.raw.exp_ai_vid);
        }

        // ML Sections
        long mlCourseId = getCourseId(db, "Introduction to Machine Learning");
        if (mlCourseId != -1) {
            insertCourseSection(db, mlCourseId,
                "Introduction to Machine Learning",
                "Basic concepts of Machine Learning",
                "ml_vid.mp4",
                R.raw.ml_vid);
                
            insertCourseSection(db, mlCourseId,
                "AI vs. ML vs. Deep Learning: Key Differences",
                "Understanding the differences between AI, ML, and Deep Learning",
                "ai_ml_dl_vid.mp4",
                R.raw.ai_ml_dl_vid);
        }
    }

    private long getCourseId(SQLiteDatabase db, String courseTitle) {
        Cursor cursor = db.query(TABLE_COURSES,
            new String[]{COLUMN_ID},
            COLUMN_TITLE + "=?",
            new String[]{courseTitle},
            null, null, null);
            
        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            cursor.close();
            return id;
        }
        return -1;
    }

    private void insertCourseSection(SQLiteDatabase db, long courseId, 
        String title, String description, String videoFile, int videoResource) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_ID, courseId);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_VIDEO_FILE, videoFile);
        values.put(COLUMN_VIDEO_RESOURCE, videoResource);
        db.insert(TABLE_COURSE_SECTIONS, null, values);
    }

    // User registration
    public boolean registerUser(String name, String phone, String email, String password) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_PHONE, phone);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            long result = db.insert(TABLE_USERS, null, values);
            return result != -1;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // User login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_ID};
            String selection = COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
            String[] selectionArgs = {email, password};
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_COURSES, 
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_URL, COLUMN_THUMBNAIL}, 
                null, null, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                    String url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL));
                    String thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL));
                    
                    int thumbnailResId = getResourceId(thumbnail, "drawable", context);
                    if (thumbnailResId == 0) {
                        thumbnailResId = R.drawable.ic_courses;
                    }
                    
                    Course course = new Course(id, title, description, url, thumbnailResId);
                    courses.add(course);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        
        return courses;
    }

    public boolean resetPassword(String email, String newPassword) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PASSWORD, newPassword);
            String whereClause = COLUMN_EMAIL + "=?";
            String[] whereArgs = {email};
            int result = db.update(TABLE_USERS, values, whereClause, whereArgs);
            return result > 0;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_ID};
            String selection = COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private int getResourceId(String resourceName, String resourceType, Context context) {
        try {
            return context.getResources().getIdentifier(
                resourceName, resourceType, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_courses;
        }
    }

    public boolean updateUserName(String email, String newName) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, newName);
            
            int rowsAffected = db.update(TABLE_USERS, values, 
                COLUMN_EMAIL + "=?", new String[]{email});
                
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error updating user name", e);
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public String getUserName(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_NAME};
            String selection = COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            }
            return "";
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Add method to update profile image
    public boolean updateProfileImage(String email, String imageData) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROFILE_IMAGE, imageData);
            
            int rowsAffected = db.update(TABLE_USERS, values, 
                COLUMN_EMAIL + "=?", new String[]{email});
                
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error updating profile image", e);
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // Add method to get profile image
    public String getProfileImage(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_PROFILE_IMAGE};
            String selection = COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE));
            }
            return null;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Add method to get course sections
    public List<CourseSection> getCourseSections(String courseTitle) {
        List<CourseSection> sections = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = this.getReadableDatabase();
            
            // First get course ID
            long courseId = getCourseId(db, courseTitle);
            
            if (courseId != -1) {
                // Get sections for this course
                cursor = db.query(TABLE_COURSE_SECTIONS,
                    null,
                    COLUMN_COURSE_ID + "=?",
                    new String[]{String.valueOf(courseId)},
                    null, null, null);
                    
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        sections.add(new CourseSection(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIDEO_FILE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIDEO_RESOURCE))
                        ));
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        
        return sections;
    }

    // Update video progress
    public void updateVideoProgress(String videoId, String userEmail, float progress, long position) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROGRESS, progress);
            values.put(COLUMN_LAST_POSITION, position);
            values.put(COLUMN_IS_COMPLETED, progress >= 95 ? 1 : 0);

            // Try to update existing record
            String whereClause = COLUMN_VIDEO_ID + "=? AND " + COLUMN_USER_EMAIL + "=?";
            String[] whereArgs = {videoId, userEmail};
            int updated = db.update(TABLE_VIDEO_PROGRESS, values, whereClause, whereArgs);

            // If no record exists, insert new one
            if (updated == 0) {
                values.put(COLUMN_VIDEO_ID, videoId);
                values.put(COLUMN_USER_EMAIL, userEmail);
                db.insert(TABLE_VIDEO_PROGRESS, null, values);
            }
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // Get video progress
    public float getVideoProgress(String videoId, String userEmail) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_PROGRESS};
            String selection = COLUMN_VIDEO_ID + "=? AND " + COLUMN_USER_EMAIL + "=?";
            String[] selectionArgs = {videoId, userEmail};
            
            cursor = db.query(TABLE_VIDEO_PROGRESS, columns, selection, selectionArgs, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PROGRESS));
            }
            return 0f;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Check if video is completed
    public boolean isVideoCompleted(String videoId, String userEmail) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_IS_COMPLETED};
            String selection = COLUMN_VIDEO_ID + "=? AND " + COLUMN_USER_EMAIL + "=?";
            String[] selectionArgs = {videoId, userEmail};
            
            cursor = db.query(TABLE_VIDEO_PROGRESS, columns, selection, selectionArgs, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1;
            }
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    // Get last video position
    public long getLastVideoPosition(String videoId, String userEmail) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_LAST_POSITION};
            String selection = COLUMN_VIDEO_ID + "=? AND " + COLUMN_USER_EMAIL + "=?";
            String[] selectionArgs = {videoId, userEmail};
            
            cursor = db.query(TABLE_VIDEO_PROGRESS, columns, selection, selectionArgs, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LAST_POSITION));
            }
            return 0L;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    public void createVideoProgressTable() {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            String CREATE_VIDEO_PROGRESS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_VIDEO_PROGRESS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_VIDEO_ID + " TEXT,"
                    + COLUMN_USER_EMAIL + " TEXT,"
                    + COLUMN_PROGRESS + " FLOAT,"
                    + COLUMN_IS_COMPLETED + " INTEGER DEFAULT 0,"
                    + COLUMN_LAST_POSITION + " LONG,"
                    + "UNIQUE(" + COLUMN_VIDEO_ID + ", " + COLUMN_USER_EMAIL + ")"
                    + ")";
            db.execSQL(CREATE_VIDEO_PROGRESS_TABLE);
            Log.d(TAG, "Video progress table created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error creating video progress table", e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    @Override
    public void close() {
        super.close();
    }

    public String[] getUserDetails(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String[] columns = {COLUMN_NAME, COLUMN_PHONE};
            String selection = COLUMN_EMAIL + "=?";
            String[] selectionArgs = {email};
            
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            
            if (cursor != null && cursor.moveToFirst()) {
                return new String[]{
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                };
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error getting user details", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
} 