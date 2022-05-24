package com.androidexam.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tblStudent (\n" +
                "    id       INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    name STRING            NOT NULL,\n" +
                "    className STRING        NOT NULL,\n" +
                "    birthday DATE        NOT NULL,\n" +
                "    gender BIT        NOT NULL,\n" +
                "    phone    VARCHAR (11),\n" +
                "    email    VARCHAR (255) \n" +
                ");\n";
        db.execSQL(sql);

        sql = "INSERT INTO tblStudent(name, className, birthday, gender, phone, email) VALUES ('Ngọc Thị Trinh', 'Java', '2020-12-12', 0, '0123456789', 'ngoctrinh@gmail.com')";
        db.execSQL(sql);

        sql = "INSERT INTO tblStudent(name, className, birthday, gender, phone, email) VALUES ('Trương Vô Kỵ', 'PHP', '2020-12-12', 1, '0789456132', 'voky@gmail.com')";
        db.execSQL(sql);

        sql = "INSERT INTO tblStudent(name, className, birthday, gender, phone, email) VALUES ('Hoàng Dược Sư', 'Android', '2020-12-12', 0, '0456123789', 'duocsu@gmail.com')";
        db.execSQL(sql);

        sql = "INSERT INTO tblStudent(name, className, birthday, gender, phone, email) VALUES ('Quách Văn Tĩnh', 'C#', '2020-12-12', 1, '0456145679', 'quachtinh@gmail.com')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
