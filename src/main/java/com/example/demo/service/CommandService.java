package com.example.demo.service;

public interface CommandService {
    String createTask(String sender, String arg);
    String closeTask(String sender, String arg);
    String deleteTask(String sender, String arg);
    String reopenTask(String sender, String arg);
    String getListTask(String sender, String arg);
    void deleteAll();
}
