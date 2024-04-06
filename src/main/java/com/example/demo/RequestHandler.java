package com.example.demo;

import com.example.demo.enums.CommandType;
import com.example.demo.enums.ResultStatus;

import java.util.Objects;

public class RequestHandler {
    public static String execute(String request) {
        Command command = new Command();
        String[] requestBody = request.split(" ");
        CommandType commandType;
        String possibleCommandType;
        if (!(requestBody.length == 1 || requestBody.length == 3)) return ResultStatus.WRONG_FORMAT.toString();
        else if (requestBody.length == 1) {
            if (!Objects.equals(requestBody[0], "__DELETE_ALL")) return ResultStatus.WRONG_FORMAT.toString();
            possibleCommandType = requestBody[0];
        }
        else {
            command.sender = requestBody[0];
            possibleCommandType = requestBody[1];
            command.arg = requestBody[2];
        }

        try {
            commandType = CommandType.valueOf(possibleCommandType);
        }
        catch (IllegalArgumentException e) {
            return ResultStatus.WRONG_FORMAT.toString();
        }

        command.possibleCommandType = possibleCommandType;

        switch (commandType) {
            case CREATE_TASK:
                return command.createTask();
            case CLOSE_TASK:
                return command.closeTask();
            case DELETE_TASK:
                return command.deleteTask();
            case REOPEN_TASK:
                return command.reopenTask();
            case LIST_TASK:
                return command.getListTask();
            case __DELETE_ALL:
                command.deleteAll();
        }
        return "";
    }
}
