package com.example.demo;

import java.io.IOException;

public class DemoApplication {

  public static void main(String[] args) {
    Server server = new Server();
    try {
      server.start();
      System.out.println(RequestHandler.execute("VASYA CREATE_TASK CleanRoom"));

      System.out.println(RequestHandler.execute("PETYA DELETE_TASK CleanRoom"));

      System.out.println(RequestHandler.execute("PETYA CREATE_TASK Task1"));

      System.out.println(RequestHandler.execute("PETYA CREATE_TASK Task2"));

      System.out.println(RequestHandler.execute("VASYA LIST_TASK PETYA"));

      System.out.println(RequestHandler.execute("VASYA CREATE_TASK CleanRoom"));

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

}
