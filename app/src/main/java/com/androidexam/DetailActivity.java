package com.androidexam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int idS = getIntent().getExtras().getInt("ids");

        if (idS > 0) {
            loadStudent(idS);
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

    public int setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                return i;
            }
        }

        return 0;
    }

    private void loadStudent(int idS) {
        IStudentDAO studentDAO = new StudentDAOImpl(this);
        Student c = studentDAO.selectById(idS);

        binding.edtName.setText(c.getName());

        int index = setSpinText(binding.subjectsSpinner, c.getClassName());
        binding.subjectsSpinner.setSelection(index);
        binding.edtPhone.setText(c.getPhone());
        binding.edtEmail.setText(c.getEmail());

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthday = sdf.format(c.getBirthday());
            binding.edtBirthday.setText(birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (c.getGender()) {
            binding.radMale.setChecked(true);
            binding.radFemale.setChecked(false);
        } else {
            binding.radFemale.setChecked(true);
            binding.radMale.setChecked(false);
        }

        binding.btnAdd.setEnabled(false);

        binding.btnEdit.setOnClickListener(view -> update(c.getId()));
        binding.btnEdit.setEnabled(true);

        binding.btnDelete.setOnClickListener(view -> delete(c.getId()));
        binding.btnDelete.setEnabled(true);
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

    private void update(int id) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Select your answer.");

        // Ask the final question
        builder.setMessage("Are you sure to edit?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Do something when user clicked the Yes button
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

            Student s = new Student(id, name, className, gender, phone, birthday, email);
            IStudentDAO studentDAO = new StudentDAOImpl(DetailActivity.this);
            boolean result = studentDAO.update(s);
            if (result) {
                Toast.makeText(DetailActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();

                // go to main activity
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(DetailActivity.this, "Update error", Toast.LENGTH_SHORT).show();
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", (dialog, which) -> {
            // Do something when No button clicked
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    private void delete(int id) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Select your answer.");

        // Ask the final question
        builder.setMessage("Are you sure to delete?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", (dialog, which) -> {
            IStudentDAO studentDAO = new StudentDAOImpl(DetailActivity.this);
            boolean result = studentDAO.delete(id);
            if (result) {
                Toast.makeText(DetailActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();

                // go to main activity
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(DetailActivity.this, "Delete error", Toast.LENGTH_SHORT).show();
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", (dialog, which) -> {
            // Do something when No button clicked
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
}