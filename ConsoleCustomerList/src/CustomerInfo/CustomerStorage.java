package CustomerInfo;

import Exceptions.*;

import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;
    private String name, email, phone;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        storage.put(name, new Customer(name, phone, email));
    }

    public void checkNamePhoneEmail(String command, String data) throws AllExceptions {
        String[] components = data.split("\\s+");
        if (command.equals("add")) {
            String phoneFormat = "\\+\\d{11}";
            String emailFormat = "[\\w.-]+@\\w+.\\w+";
            String phoneError = "Wrong phone format! Right phone format: +79215637722";
            String emailError = "Wrong email format! Right email format: vasily.petrov@gmail.com";
            String addError = "Wrong add command format! Right format: add Василий Петров vasily.petrov@gmail.com +79215637722";
            if (components.length != 4) throw new WrongAddCommandException(addError);
            else if (!components[2].matches(emailFormat)) throw new WrongEmailException(emailError);
            else if (!components[3].matches(phoneFormat)) throw new WrongPhoneException(phoneError);
            else {
                name = components[0] + " " + components[1];
                email = components[2];
                phone = components[3];
            }
        }
         else {
            String removeError = "Wrong remove command format! Right format: remove Василий Петров";
            String noUserError = "No such user!";
            if (components.length != 2) throw new WrongRemoveCommandException(removeError);
            else if (!storage.containsKey(components[0] + " " + components[1])) throw new NotInListException(noUserError);
            }
        String nameFormat = "[a-zA-Zа-яА-я]+\\s[a-zA-Zа-яА-я]+";
        String nameError = "Wrong name format! Right name format: Василий Петров";
        if (!(components[0] + " " + components[1]).matches(nameFormat)) throw new WrongNameException(nameError);
        }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
}