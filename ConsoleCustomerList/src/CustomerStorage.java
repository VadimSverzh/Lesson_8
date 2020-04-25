import Exceptions.NotInListException;
import Exceptions.WrongEmailException;
import Exceptions.WrongNameException;
import Exceptions.WrongPhoneException;

import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;
    private String nameFormat = "[a-zA-Zа-яА-я]+\\s[a-zA-Zа-яА-я]+";
    private String phoneFormat = "\\+\\d{11}";
    private String emailFormat = "[\\w.-]+@\\w+.\\w+";
    private String name, email, phone;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        storage.put(name, new Customer(name, phone, email));
    }

    public void checkCustomerFormat(String data) throws WrongNameException, WrongEmailException, WrongPhoneException {
        String[] components = data.split("\\s+");

        if (!(components[0] + " " + components[1]).matches(nameFormat)) throw new WrongNameException();
            else if (!components[2].matches(emailFormat)) throw new WrongEmailException();
            else if (!components[3].matches(phoneFormat)) throw new WrongPhoneException();
            else {name = components[0] + " " + components[1];
                email = components[2];
                phone = components[3];
        }
    }

    public void checkRemoval(String name) throws WrongNameException, NotInListException {
        if (!name.matches(nameFormat)) throw new WrongNameException();
        else if (!storage.containsKey(name)) throw new NotInListException();
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