package duke.component;

import duke.command.Command;
import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;

import duke.exception.EmptyDescriptionException;
import duke.exception.UnknownCommandException;
import duke.exception.WrongFormatException;
import duke.task.ToDo;
import duke.task.Deadline;
import duke.task.Event;

public class Parser {
    /**
     * Parses command and generate task accordingly.
     * @param command
     * @return
     * @throws UnknownCommandException
     * @throws EmptyDescriptionException
     * @throws WrongFormatException
     */
    public static Command parse(String command) throws UnknownCommandException, EmptyDescriptionException, WrongFormatException {
        String[] parameters = command.split(" ");
        switch (parameters[0]) {
        case "bye": {
            return new ExitCommand();
        }
        case "list": {
            return new ListCommand();
        }
        case "done": {
            if (command.length() <= 5) {
                throw new WrongFormatException();
            }
            return new DoneCommand(Integer.parseInt(parameters[1]) - 1);
        }
        case "delete": {
            if (command.length() <= 7) {
                throw new WrongFormatException();
            }
            return new DeleteCommand(Integer.parseInt(parameters[1]) - 1);
        }
        case "todo": {
            if (command.trim().length() <= 4) {
                throw new EmptyDescriptionException("todo");
            }
            ToDo td = new ToDo(command.substring(5, command.length()));
            return new AddCommand(td);
        }
        case "deadline": {
            return parseDeadline(command);
        }
        case "event": {
            return parseEvent(command);
        }
        case "find": {
            if (command.length() <= 5) {
                throw new WrongFormatException();
            }
            String keyWord = command.substring(5, command.length());
            return new FindCommand(keyWord);
        }
        default: {
            throw new UnknownCommandException();
        }
        }
    }

    public static AddCommand parseDeadline(String command) throws WrongFormatException, EmptyDescriptionException {
        int slash = command.indexOf("/by");
        if (slash == -1) {
            throw new WrongFormatException();
        }
        else if (slash <= 10 || command.split(" ").length <= 2) {
            throw new EmptyDescriptionException("deadline");
        }
        String name = command.substring(9, slash - 1);
        String date = command.substring(slash + 4, command.length());

        Deadline dl = new Deadline(name, date);
        return new AddCommand(dl);
    }

    public static AddCommand parseEvent(String command) throws WrongFormatException, EmptyDescriptionException {
        int slash = command.indexOf("/at");
        if (slash == -1) {
            throw new WrongFormatException();
        }
        else if (slash <= 7 || command.split(" ").length <= 2) {
            throw new EmptyDescriptionException("event");
        }
        String name = command.substring(6, slash - 1);
        String date = command.substring(slash + 4, command.length());

        Event e = new Event(name, date);
        return new AddCommand(e);
    }
}
