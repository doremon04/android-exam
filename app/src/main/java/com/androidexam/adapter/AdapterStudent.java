package com.androidexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidexam.R;
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

        txtClassName.setText(c.getClassName());
        txtName.setText(c.getName());
        txtPhone.setText(c.getPhone());
        txtEmail.setText(c.getEmail());

        return v;
    }
}
