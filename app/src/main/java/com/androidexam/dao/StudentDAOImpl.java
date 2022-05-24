package com.androidexam.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androidexam.entity.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {
    private SQLiteDatabase mDB;

    public StudentDAOImpl(Context ctx) {
        DatabaseHelper helper = new DatabaseHelper(ctx);
        mDB = helper.getWritableDatabase();
    }

    @Override
    public List<Student> select() {
        String sql = "SELECT * FROM tblStudent";
        List<Student> list = new ArrayList<>();
        Cursor c = mDB.rawQuery(sql, null);
        while (c.moveToNext()) {
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex("name"));
            @SuppressLint("Range") String className = c.getString(c.getColumnIndex("className"));
            @SuppressLint("Range") boolean gender = c.getInt(c.getColumnIndex("gender")) > 0;
//            @SuppressLint("Range") String birthdayStr = c.getString(c.getColumnIndex("birthday"));
            @SuppressLint("Range") String phone = c.getString(c.getColumnIndex("phone"));
            @SuppressLint("Range") String email = c.getString(c.getColumnIndex("email"));

//            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = null;
//            try {
//                birthday = sdf.parse(birthdayStr);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

            Student s = new Student(id, name, className, gender, phone, birthday, email);
            list.add(s);
        }
        return list;
    }

    @Override
    public boolean insert(Student c) {
        ContentValues cv = new ContentValues();
        cv.put("name", c.getName());
        cv.put("className", c.getClassName());
        cv.put("gender", c.getGender());
        cv.put("birthday", c.getBirthday().toString());
        cv.put("phone", c.getPhone());
        cv.put("email", c.getEmail());
        long newId = mDB.insert("tblStudent", null, cv);
        return newId > 0;
    }

    @Override
    public boolean update(Student c) {

        ContentValues cv = new ContentValues();
        cv.put("name", c.getName());
        cv.put("className", c.getClassName());
        cv.put("gender", c.getGender());
        cv.put("birthday", c.getBirthday().toString());
        cv.put("phone", c.getPhone());
        cv.put("email", c.getEmail());
        long newId = mDB.update("tblStudent", cv, " id = " + c.getId(), null);
        return newId > 0;
    }

    @Override
    public boolean delete(int id) {
        long newId = mDB.delete("tblStudent", "id =" + id, null);
        return newId > 0;
    }
}
