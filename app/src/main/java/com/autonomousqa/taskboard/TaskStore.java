package com.autonomousqa.taskboard;

import java.util.ArrayList;
import java.util.List;

/** The signed-in user's tasks, kept in memory while the app runs. */
final class TaskStore {
    static final int MAX_TITLE = 80;
    private static final List<Task> TASKS = new ArrayList<>();

    static {
        TASKS.add(new Task("Buy milk", true));
        TASKS.add(new Task("Write report", false));
        TASKS.add(new Task("Call Alex", false));
    }

    private TaskStore() {}

    static List<Task> all() {
        return TASKS;
    }

    static void add(String title) {
        String capitalized = title.substring(0, 1).toUpperCase() + title.substring(1);
        TASKS.add(new Task(capitalized, false));
    }

    static int done() {
        int count = 0;
        for (Task task : TASKS) {
            if (task.done) {
                count++;
            }
        }
        return count;
    }
}
