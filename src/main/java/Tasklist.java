import duke_task.Deadline;
import duke_task.Event;
import duke_task.Task;
import duke_task.ToDo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

class Tasklist {
    private ArrayList<Task> taskList = new ArrayList<>();

    void add(Task task) {
        taskList.add(task);
    }

    void deleteComplete(int size, Task currTask) {
        printLine();
        System.out.println("     Noted. I've removed this task: \n       " +
                currTask +
                "\n     Now you have " + (size - 1) + " tasks in the list.");
        printLine();
        System.out.println();
    }

    String getDescription(String[] commandArr) throws DukeException {
        StringJoiner description = new StringJoiner(" ");
        int index;
        if (commandArr[0].equals("todo")) {
            index = commandArr.length;
        } else if (commandArr[0].equals("deadline")) {
            index = Arrays.asList(commandArr).indexOf("/by");
        } else {
            index = Arrays.asList(commandArr).indexOf("/at");
        }

        for (int i = 1; i < index; i++) {
            description.add(commandArr[i]);
        }

        if (index == -1) {
            throw new DukeException("    ____________________________________________________________\n" +
                    "     ☹ OOPS!!! The timing of a " + commandArr[0] + " cannot be empty.\n" +
                    "    ____________________________________________________________\n");
        } else if (description.toString().equals("")) {
            throw new DukeException("    ____________________________________________________________\n" +
                    "     ☹ OOPS!!! The description of a " + commandArr[0] + " cannot be empty.\n" +
                    "    ____________________________________________________________\n");
        }

        return description.toString();
    }

    String getTime(String[] commandArr) throws DukeException {
        StringJoiner timing = new StringJoiner(" ");
        int index;
        if (commandArr[0].equals("deadline")) {
            index = Arrays.asList(commandArr).indexOf("/by");
        } else {
            index = Arrays.asList(commandArr).indexOf("/at");
        }

        for (int i = index + 1; i < commandArr.length; i++) {
            timing.add(commandArr[i]);
        }

        if (index == -1 || timing.toString().equals("")) {
            throw new DukeException("    ____________________________________________________________\n" +
                    "     ☹ OOPS!!! The timing of a " + commandArr[0] + " cannot be empty.\n" +
                    "    ____________________________________________________________\n");
        }

        return timing.toString();
    }

    void printTask(Task task, int size) {
        printLine();
        System.out.println("     Got it. I've added this task: \n       " + task);
        System.out.printf("     Now you have %d tasks in the list.\n", size);
        printLine();
        System.out.println();
    }

    void taskComplete(Task currTask) {
        printLine();
        System.out.println("     Nice! I've marked this task as done: \n       " + currTask);
        printLine();
        System.out.println();
    }

    void printArray() {
        printLine();
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("     " + (i + 1) + "." + taskList.get(i));
        }
        printLine();
        System.out.println();
    }

    Task get(int index) {
        return taskList.get(index);
    }

    void remove(int index) {
        taskList.remove(index);
    }

    int size() {
        return taskList.size();
    }

    void loadEvents(ArrayList<String> textArr) {
        for (int i = 0; i < textArr.size(); i++) {
            String[] str = textArr.get(i).split(" \\| ");
            Task task;

            if (str[0].equals("T")) {
                task = new ToDo(str[2]);
            } else if (str[0].equals("D")) {
                task = new Deadline(str[2], str[3]);
            } else {
                task = new Event(str[2], str[3]);
            }

            if (str[1].equals("\u2713")) {
                task.markAsDone();
            }

            System.out.println("     " + (i + 1) + "." + task.toString());

            taskList.add(task);
        }

        printLine();
        System.out.println();
    }

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }
}
