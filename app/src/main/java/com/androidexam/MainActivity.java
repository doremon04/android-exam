package com.androidexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androidexam.adapter.AdapterStudent;
import com.androidexam.dao.IStudentDAO;
import com.androidexam.dao.StudentDAOImpl;
import com.androidexam.databinding.ActivityMainBinding;
import com.androidexam.entity.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Student> lst;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadStudents();
    }

    private void loadStudents() {
        IStudentDAO studentDAO = new StudentDAOImpl(this);
        lst = studentDAO.select();

        AdapterStudent adapterStudent = new AdapterStudent(this, lst);
        binding.listStudents.setAdapter(adapterStudent);

        binding.listStudents.setOnItemClickListener((adapterView, view, pos, l) -> {
            Intent intent = new Intent(this, DetailActivity.class);
            int id = lst.get(pos).getId();
            intent.putExtra("idb", id);
            startActivity(intent);
        });

        registerForContextMenu(binding.listStudents);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.homePage:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.signupPage:
                intent = new Intent(this, DetailActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}