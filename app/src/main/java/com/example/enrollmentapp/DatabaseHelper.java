package com.example.enrollmentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "enrollmentApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String TABLE_ENROLLMENTS = "enrollments";

    // Common Column Names
    private static final String COLUMN_ID = "id";

    // Users Table Columns
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Subjects Table Columns
    private static final String COLUMN_SUBJECT_NAME = "name";
    private static final String COLUMN_SUBJECT_CREDITS = "credits";

    // Enrollments Table Columns
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_SUBJECT_ID = "subject_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
                + COLUMN_USER_PASSWORD + " TEXT"
                + ");";

        // Create Subjects Table
        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SUBJECT_NAME + " TEXT,"
                + COLUMN_SUBJECT_CREDITS + " INTEGER"
                + ");";

        // Create Enrollments Table
        String CREATE_ENROLLMENTS_TABLE = "CREATE TABLE " + TABLE_ENROLLMENTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_SUBJECT_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "),"
                + "FOREIGN KEY(" + COLUMN_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECTS + "(" + COLUMN_ID + ")"
                + ");";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SUBJECTS_TABLE);
        db.execSQL(CREATE_ENROLLMENTS_TABLE);

        // Prepopulate some subjects
        prepopulateSubjects(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENTS);
        onCreate(db);
    }

    private void prepopulateSubjects(SQLiteDatabase db) {
        String[] subjects = {"Mathematics", "Physics", "Chemistry", "Biology", "History"};
        int[] credits = {3, 4, 3, 2, 2};

        for (int i = 0; i < subjects.length; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SUBJECT_NAME, subjects[i]);
            values.put(COLUMN_SUBJECT_CREDITS, credits[i]);
            db.insert(TABLE_SUBJECTS, null, values);
        }
    }

    // User-related operations
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean isLoggedIn = cursor.moveToFirst();
        cursor.close();
        return isLoggedIn;
    }

    // Subject-related operations
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUBJECTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME));
                int credits = cursor.getInt(cursor.getColumnIndex(COLUMN_SUBJECT_CREDITS));

                subjects.add(new Subject(id, name, credits));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }

    // Enrollment-related operations
    public boolean enrollSubject(int userId, int subjectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_SUBJECT_ID, subjectId);

        long result = db.insert(TABLE_ENROLLMENTS, null, values);
        return result != -1;
    }

    public List<Subject> getEnrolledSubjects(int userId) {
        List<Subject> enrolledSubjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + TABLE_SUBJECTS + ".* FROM " + TABLE_ENROLLMENTS +
                " INNER JOIN " + TABLE_SUBJECTS +
                " ON " + TABLE_ENROLLMENTS + "." + COLUMN_SUBJECT_ID + "=" + TABLE_SUBJECTS + "." + COLUMN_ID +
                " WHERE " + TABLE_ENROLLMENTS + "." + COLUMN_USER_ID + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME));
                int credits = cursor.getInt(cursor.getColumnIndex(COLUMN_SUBJECT_CREDITS));

                enrolledSubjects.add(new Subject(id, name, credits));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return enrolledSubjects;
    }

    public int getTotalCredits(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_SUBJECT_CREDITS + ") AS totalCredits FROM " + TABLE_ENROLLMENTS +
                " INNER JOIN " + TABLE_SUBJECTS +
                " ON " + TABLE_ENROLLMENTS + "." + COLUMN_SUBJECT_ID + "=" + TABLE_SUBJECTS + "." + COLUMN_ID +
                " WHERE " + TABLE_ENROLLMENTS + "." + COLUMN_USER_ID + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        int totalCredits = 0;
        if (cursor.moveToFirst()) {
            totalCredits = cursor.getInt(cursor.getColumnIndex("totalCredits"));
        }
        cursor.close();
        return totalCredits;
    }
}
