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

    public void checkNamePhoneEmail(String command, String data) throws WrongEmailException, WrongPhoneException,
            WrongCommandException, NotInListException, WrongNameException {
        String[] components = data.split("\\s+");
        if (command.equals("add")) {
            String phoneFormat = "\\+\\d{11}";
            String emailFormat = "[\\w.-]+@\\w+.\\w+";
            if (components.length != 4) throw new WrongCommandException();
            else if (!components[2].matches(emailFormat)) throw new WrongEmailException();
            else if (!components[3].matches(phoneFormat)) throw new WrongPhoneException();
            else {
                name = components[0] + " " + components[1];
                email = components[2];
                phone = components[3];
            }
        }
         else {
            if (components.length != 2) throw new WrongCommandException();
            else if (!storage.containsKey(components[0] + " " + components[1])) throw new NotInListException();
            }
        String nameFormat = "[a-zA-Zа-яА-я]+\\s[a-zA-Zа-яА-я]+";
        if (!(components[0] + " " + components[1]).matches(nameFormat)) throw new WrongNameException();
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