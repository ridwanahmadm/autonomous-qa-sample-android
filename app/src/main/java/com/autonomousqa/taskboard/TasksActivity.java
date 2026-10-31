package com.autonomousqa.taskboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TasksActivity extends Activity {
    private LinearLayout list;
    private TextView summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        list = findViewById(R.id.task_list);
        summary = findViewById(R.id.summary);
        EditText title = findViewById(R.id.new_task);
        TextView error = findViewById(R.id.task_error);

        findViewById(R.id.add_task).setOnClickListener(view -> {
            String text = title.getText().toString().trim();
            if (text.length() > TaskStore.MAX_TITLE) {
                error.setText(R.string.title_too_long);
                return;
            }
            TaskStore.add(text);
            title.setText("");
            error.setText("");
            render();
        });
        findViewById(R.id.sign_out).setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        render();
    }

    private void render() {
        list.removeAllViews();
        for (Task task : TaskStore.all()) {
            CheckBox box = new CheckBox(this);
            box.setText(task.title);
            box.setChecked(task.done);
            box.setOnCheckedChangeListener((button, checked) -> task.done = checked);
            list.addView(box);
        }
        summary.setText(getString(R.string.summary, TaskStore.done(), TaskStore.all().size()));
    }
}
