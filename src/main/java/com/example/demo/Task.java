package com.example.demo;

import com.example.demo.enums.TaskStatus;

public class Task {
    private String name;
    private TaskStatus taskStatus;
    public Task(String name, TaskStatus taskStatus) {
        this.name = name;
        this.taskStatus = taskStatus;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
