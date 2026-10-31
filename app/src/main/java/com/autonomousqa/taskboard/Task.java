package com.autonomousqa.taskboard;

final class Task {
    final String title;
    boolean done;

    Task(String title, boolean done) {
        this.title = title;
        this.done = done;
    }
}
