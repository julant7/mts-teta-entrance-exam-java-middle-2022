package com.example.demo;

import com.example.demo.enums.ResultStatus;
import com.example.demo.enums.TaskStatus;

import java.util.*;

public class Command {
    // Нужно быстро искать, существует ли открытая/закрытая задача с таким названием. Для этой цели используем HashSet
    static Set<String> openAndCloseTasks = new HashSet<>();

    // Нужно быстро искать, существует ли данный пользователь
    static Map<String, ArrayList<Task>> tasksOfUsers = new HashMap<>();
    String sender = "", arg = "",  possibleCommandType;

    public String createTask() {
        if (openAndCloseTasks.contains(arg))
            return ResultStatus.ERROR.toString();
        openAndCloseTasks.add(arg);
        if (!tasksOfUsers.containsKey(sender)) tasksOfUsers.put(sender, new ArrayList<>());
        tasksOfUsers.get(sender).add(new Task(arg, TaskStatus.CREATED));
        return ResultStatus.CREATED.toString();
    }
    public String closeTask() {
        if (!tasksOfUsers.containsKey(sender)) {
            if (openAndCloseTasks.contains(arg)) return ResultStatus.ACCESS_DENIED.toString();
            return ResultStatus.ERROR.toString();
        }
        ArrayList<Task> tasksOfSender = tasksOfUsers.get(sender);
        for (Task task : tasksOfSender) {
            if (Objects.equals(task.getName(), arg) && task.getTaskStatus() == TaskStatus.CREATED) {
                task.setTaskStatus(TaskStatus.CLOSED);
                return ResultStatus.CLOSED.toString();
            }
        }
        if (openAndCloseTasks.contains(arg)) return ResultStatus.ACCESS_DENIED.toString();
        return ResultStatus.ERROR.toString();

    }
    public String deleteTask() {
        if (!tasksOfUsers.containsKey(sender)) {
            if (openAndCloseTasks.contains(arg)) return ResultStatus.ACCESS_DENIED.toString();
            return ResultStatus.ERROR.toString();
        }
        ArrayList<Task> tasksOfSender = tasksOfUsers.get(sender);
        for (Task task : tasksOfSender) {
            if (Objects.equals(task.getName(), arg)) {
                if (task.getTaskStatus() == TaskStatus.CLOSED) {
                    tasksOfSender.remove(task);
                    openAndCloseTasks.remove(arg);
                    return ResultStatus.DELETED.toString();
                }
                return ResultStatus.ERROR.toString();
            }
        }
        if (openAndCloseTasks.contains(arg)) return ResultStatus.ACCESS_DENIED.toString();
        return ResultStatus.ERROR.toString();
    }
    public String reopenTask() {
        if (!tasksOfUsers.containsKey(sender)) {
            if (openAndCloseTasks.contains(arg)) return ResultStatus.ACCESS_DENIED.toString();
            return ResultStatus.ERROR.toString();
        }

        ArrayList<Task> tasksOfSender = tasksOfUsers.get(sender);
        for (Task task : tasksOfSender) {

            if (Objects.equals(task.getName(), arg)) {
                if (task.getTaskStatus() == TaskStatus.CLOSED) {
                    task.setTaskStatus(TaskStatus.CREATED);
                    return ResultStatus.REOPENED.toString();
                }
                return ResultStatus.ERROR.toString();
            }
        }

        if (openAndCloseTasks.contains(arg)) return ResultStatus.ACCESS_DENIED.toString();
        return ResultStatus.ERROR.toString();
    }
    public String getListTask() {
        if (!tasksOfUsers.containsKey(arg))
            return ResultStatus.ERROR.toString();
        ArrayList<Task> mapWithTasks = tasksOfUsers.get(arg);
        List<String> namesOfTasks = new ArrayList<>();
        for (Task task: mapWithTasks) {
            namesOfTasks.add(task.getName());
        }
        return ResultStatus.TASKS + " " + namesOfTasks;
    }

    public void deleteAll() {
        openAndCloseTasks.clear();
        tasksOfUsers.clear();
    }

}
