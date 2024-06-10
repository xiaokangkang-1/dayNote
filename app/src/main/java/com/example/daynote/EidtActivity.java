package com.example.daynote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.daynote.bean.Note;
import com.example.daynote.utils.ToastUtil;

import java.io.Serializable;

public class EidtActivity extends AppCompatActivity {

    private Note note;
    private NoteDbOpenHelper dbOpenHelper;

    private EditText etTitle, etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eidt);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_context);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }
        dbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void save(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if(TextUtils.isEmpty(title)){
            ToastUtil.toastShort(this, "标题不能为空");
            return;
        }
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(String.valueOf(System.currentTimeMillis()));
        if (dbOpenHelper.updateData(note) > 0) {
            ToastUtil.toastShort(this, "保存成功");
            finish();
        } else {
            ToastUtil.toastShort(this, "保存失败");
        }
    }
}