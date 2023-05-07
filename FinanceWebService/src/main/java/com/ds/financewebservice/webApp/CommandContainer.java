package com.ds.financewebservice.webApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 */
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class CommandContainer {

    private static final Logger log = LoggerFactory.getLogger(CommandContainer.class);

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("noCommand", new NoCommand());

        // client commands
        commands.put("logs", new LogsCommand());
        commands.put("analytics", new AnalyticsCommand());

        log.debug("Command container was successfully initialized");
        log.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}

