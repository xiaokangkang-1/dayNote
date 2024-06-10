package com.example.daynote;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.daynote.bean.Note;
import com.example.daynote.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle, etContent;

    private NoteDbOpenHelper mDbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etTitle = findViewById(R.id.et_context);
        etContent = findViewById(R.id.et_title);
        mDbOpenHelper = new NoteDbOpenHelper(this);
        initData();
    }

    private void initData() {
        mDbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void add(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空");
            return;
        }
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getTime());
        long l = mDbOpenHelper.insertData(note);
        if (l > 0) {
            ToastUtil.toastShort(this, "添加成功");
            finish();
        } else {
            ToastUtil.toastShort(this, "添加失败");
        }
    }
    private String getTime() {
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}