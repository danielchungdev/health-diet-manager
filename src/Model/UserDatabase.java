package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserDatabase implements Database{

    HashMap<String, User> userDatabase = new HashMap<String, User>();

    public UserDatabase() throws IOException {
        this.readData();
    }

    public HashMap<String, User> getUserDatabase() {
        return userDatabase;
    }

    public User getUser(String name){
        return userDatabase.get(name);
    }

    public void addUser(String username, String password,String name, int age, Double weight, Double calories) throws IOException {
        User newUser = new User(username, password, name ,age, weight, calories);
        userDatabase.put(name, newUser);
        this.saveData();
    }

    @Override
    public void readData() throws IOException {
        try {
            File csvFile = new File("src/data/userPass.csv");
            Scanner csvReader = new Scanner(csvFile);
            while (csvReader.hasNextLine()) {
                String[] data = csvReader.nextLine().split(",");
                String username = data[0];
                String password = data[1];
                String name = data[2];
                int age = Integer.parseInt(data[3]);
                Double weight = Double.parseDouble(data[4]);
                Double calories = Double.parseDouble(data[5]);
                User newUser = new User(username, password, name,age, weight, calories);
                userDatabase.put(username, newUser);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    @Override
    public void saveData() throws IOException {
        try {
            FileWriter writter = new FileWriter("src/data/userPass.csv");
            for (Map.Entry entry : userDatabase.entrySet()) {
                User currentUser = (User) entry.getValue();
                writter.write(currentUser.getUsername());
                writter.write(",");
                writter.write(currentUser.getPassword());
                writter.write(",");
                writter.write(currentUser.getName());
                writter.write(",");
                writter.write(Integer.toString(currentUser.getAge()));
                writter.write(",");
                writter.write(Double.toString(currentUser.getWeight()));
                writter.write(",");
                writter.write(Double.toString(currentUser.getCalories()));
                writter.write("\n");
            }
            writter.flush();
            writter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
}