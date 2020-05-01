import CustomerInfo.CustomerStorage;
import Exceptions.*;

import java.util.Scanner;

public class Main
{
    private static String addCommand = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static String commandExamples = "\t" + addCommand + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";

    private static String commandError = "Wrong command! Available command examples: \n" +
            commandExamples;
    private static String helpText = "Command examples:\n" + commandExamples;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        for(;;) {
            String[] tokens;
            String command = scanner.nextLine();
            tokens = command.split("\\s+", 2);
            try {
                checkCommandFormat(tokens[0]);
                switch (tokens[0]) {
                    case "add": executor.addCustomer(tokens[1]);
                        break;
                    case "list": executor.listCustomers();
                        break;
                    case "remove":executor.removeCustomer(tokens[1]);
                        break;
                    case "count": System.out.println("There are " + executor.getCount() + " customers");
                        break;
                    case "help": System.out.println(helpText);
                        break;
                }
            } catch (AllExceptions e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void checkCommandFormat (String command) {
        if (!(command.equals("add") || command.equals("list")
                || command.equals("remove") || command.equals("count") || command.equals("help")))
            throw new WrongCommandException(commandError);
    }
}
