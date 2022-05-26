package com.androidexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidexam.R;
import com.androidexam.databinding.ActivityDetailBinding;
import com.androidexam.databinding.ItemStudentBinding;
import com.androidexam.entity.Student;

import java.util.List;

public class AdapterStudent extends ArrayAdapter<Student> {
    private final Context mCtx;
    private final List<Student> mList;

    public AdapterStudent(@NonNull Context context, @NonNull List<Student> objects) {
        super(context, R.layout.item_student, objects);
        this.mCtx = context;
        this.mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(this.mCtx).inflate(R.layout.item_student, null);
        }
        Student c = mList.get(position);
        TextView txtClassName = v.findViewById(R.id.itemClassName);
        TextView txtName = v.findViewById(R.id.itemName);
        TextView txtPhone = v.findViewById(R.id.itemPhone);
        TextView txtEmail = v.findViewById(R.id.itemEmail);
        ImageView itemGender = v.findViewById(R.id.itemGender);

        txtClassName.setText(c.getClassName());
        txtName.setText(c.getName());
        txtPhone.setText(c.getPhone());
        txtEmail.setText(c.getEmail());

        if (c.getGender()) {
            itemGender.setImageResource(R.drawable.ic_male);
        } else {
            itemGender.setImageResource(R.drawable.ic_female);
        }

        return v;
    }
}
