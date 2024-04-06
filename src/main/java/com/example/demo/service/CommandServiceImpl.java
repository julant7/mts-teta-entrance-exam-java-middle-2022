package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.enums.ResultStatus;
import com.example.demo.enums.TaskStatus;

import java.util.*;

public class CommandServiceImpl implements CommandService {

    // Нужно быстро искать, существует ли открытая/закрытая задача с таким названием. Для этой цели используем HashSet
    static Set<String> openAndCloseTasks = new HashSet<>();

    // Нужно быстро искать, существует ли данный пользователь
    static Map<String, ArrayList<Task>> tasksOfUsers = new HashMap<>();

    @Override
    public String createTask(String sender, String arg) {
        if (openAndCloseTasks.contains(arg))
            return ResultStatus.ERROR.toString();
        openAndCloseTasks.add(arg);
        if (!tasksOfUsers.containsKey(sender)) tasksOfUsers.put(sender, new ArrayList<>());
        tasksOfUsers.get(sender).add(new Task(arg, TaskStatus.CREATED));
        return ResultStatus.CREATED.toString();
    }

    @Override
    public String closeTask(String sender, String arg) {
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

    @Override
    public String deleteTask(String sender, String arg) {
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

    @Override
    public String reopenTask(String sender, String arg) {
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

    @Override
    public String getListTask(String sender, String arg) {
        if (!tasksOfUsers.containsKey(arg))
            return ResultStatus.ERROR.toString();
        ArrayList<Task> mapWithTasks = tasksOfUsers.get(arg);
        List<String> namesOfTasks = new ArrayList<>();
        for (Task task: mapWithTasks) {
            namesOfTasks.add(task.getName());
        }
        return ResultStatus.TASKS + " " + namesOfTasks;
    }

    @Override
    public void deleteAll() {
        openAndCloseTasks.clear();
        tasksOfUsers.clear();
    }
}
