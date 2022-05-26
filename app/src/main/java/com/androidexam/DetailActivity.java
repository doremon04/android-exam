package com.androidexam;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidexam.dao.IStudentDAO;
import com.androidexam.dao.StudentDAOImpl;
import com.androidexam.databinding.ActivityDetailBinding;
import com.androidexam.entity.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private int mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int idS = getIntent().getExtras().getInt("ids");

        if (idS > 0) {
            loadStudent(idS);
            binding.btnAdd.setEnabled(false);
            binding.btnEdit.setOnClickListener(view -> update());
            binding.btnEdit.setEnabled(true);
            binding.btnDelete.setEnabled(true);
            return;
        }

        binding.btnAdd.setOnClickListener(view -> create());
        binding.btnAdd.setEnabled(true);
        binding.btnEdit.setEnabled(false);
        binding.btnDelete.setEnabled(false);
    }

    public void openDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = (datePicker, year, month, day) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year, month, day);
            EditText edt = (EditText) view;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            edt.setText(fmt.format(calendar1.getTime()));
        };

        DatePickerDialog dpd = new DatePickerDialog(DetailActivity.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
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

    private void loadStudent(int idS) {
        IStudentDAO studentDAO = new StudentDAOImpl(this);
        Student c = studentDAO.selectById(idS);
        mID = c.getId();

        binding.edtName.setText(c.getName());
//        binding.subjectsSpinner.s(c.getClassName());
        binding.edtPhone.setText(c.getPhone());
        binding.edtEmail.setText(c.getEmail());
        binding.edtBirthday.setText(c.getBirthday().toString());
        binding.radMale.setChecked(c.getGender());
    }

    private void create() {
        String name = binding.edtName.getText().toString();
        String phone = binding.edtPhone.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String className = binding.subjectsSpinner.getSelectedItem().toString();
        String birthdayStr = binding.edtBirthday.getText().toString();
        boolean gender = binding.radMale.isChecked();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Student s = new Student(name, className, gender, phone, birthday, email);
        IStudentDAO studentDAO = new StudentDAOImpl(this);
        boolean result = studentDAO.insert(s);
        if (result) {
            Toast.makeText(this, "Add new successfully", Toast.LENGTH_SHORT).show();

            // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, "Add new error", Toast.LENGTH_SHORT).show();

    }

    private void update() {
        String name = binding.edtName.getText().toString();
        String phone = binding.edtPhone.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String className = binding.subjectsSpinner.getSelectedItem().toString();
        String birthdayStr = binding.edtBirthday.getText().toString();
        boolean gender = binding.radMale.isChecked();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Student s = new Student(mID, name, className, gender, phone, birthday, email);
        IStudentDAO studentDAO = new StudentDAOImpl(this);
        boolean result = studentDAO.insert(s);
        if (result) {
            Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();

            // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, "Update error", Toast.LENGTH_SHORT).show();
    }
}