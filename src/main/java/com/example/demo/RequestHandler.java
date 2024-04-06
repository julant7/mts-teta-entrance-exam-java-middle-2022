package com.example.demo;

import com.example.demo.enums.CommandType;
import com.example.demo.enums.ResultStatus;
import com.example.demo.service.CommandService;
import com.example.demo.service.CommandServiceImpl;

import java.util.Objects;

public class RequestHandler {
    // инициализация по умолчанию
    private final CommandService commandService = new CommandServiceImpl();

    public RequestHandler() {
    }

    public String execute(String request) {
        String[] requestBody = request.split(" ");
        CommandType commandType;
        String possibleCommandType, sender = "", arg = "";
        if (!(requestBody.length == 1 || requestBody.length == 3)) return ResultStatus.WRONG_FORMAT.toString();
        else if (requestBody.length == 1) {
            if (!Objects.equals(requestBody[0], "__DELETE_ALL")) return ResultStatus.WRONG_FORMAT.toString();
            possibleCommandType = requestBody[0];
        }
        else {
            sender = requestBody[0];
            possibleCommandType = requestBody[1];
            arg = requestBody[2];
        }

        try {
            commandType = CommandType.valueOf(possibleCommandType);
        }
        catch (IllegalArgumentException e) {
            return ResultStatus.WRONG_FORMAT.toString();
        }

        switch (commandType) {
            case CREATE_TASK:
                return this.commandService.createTask(sender, arg);
            case CLOSE_TASK:
                return this.commandService.closeTask(sender, arg);
            case DELETE_TASK:
                return this.commandService.deleteTask(sender, arg);
            case REOPEN_TASK:
                return this.commandService.reopenTask(sender, arg);
            case LIST_TASK:
                return this.commandService.getListTask(sender, arg);
            case __DELETE_ALL:
                this.commandService.deleteAll();
        }
        return "";
    }
}
