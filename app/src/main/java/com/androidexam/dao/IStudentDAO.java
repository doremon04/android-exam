package com.androidexam.dao;

import com.androidexam.entity.Student;

import java.util.List;

public interface IStudentDAO {
    public List<Student> select();
    public boolean insert(Student c);
    public boolean update(Student c);
    public boolean delete(int id);
}
