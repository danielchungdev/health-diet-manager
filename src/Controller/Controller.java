package Controller;

import View.View;
import Model.*;
import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private Model model;
    private View view;
    private User user;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        initView();
    }

    public void initView(){
        System.out.println(this.view.getFrame().getComponents().length);

        JPanel loginContainer = new JPanel();
        JLabel welcome = new JLabel("Welcome to the wellness Manager", SwingConstants.CENTER);
        welcome.setBounds(0, 0, 300, 100);
        loginContainer.add(welcome);

        JButton login = new JButton("Login");
        login.setBounds(0, 100, 300, 100);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login");
                clear(loginContainer);
                login(loginContainer);
            }
        });
        loginContainer.add(login);

        JButton register = new JButton("Register");
        register.setBounds(0, 200, 300, 100);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Register");
                clear(loginContainer);
                register(loginContainer);
            }
        });
        loginContainer.add(register);
        this.view.getFrame().add(loginContainer);
        loginContainer.setLayout(null);
        loginContainer.setVisible(true);


    }

    private void clear(JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    public void register(JPanel panel){
        JLabel usernameLabel = new JLabel("Select a username:");
        usernameLabel.setBounds(0, 0, 300,20);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField();
        usernameText.setBounds(0, 20, 300,20);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("Select a password:");
        passwordLabel.setBounds(0, 40, 300, 20);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(0, 60, 300, 20);
        panel.add(passwordText);

        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setBounds(0, 80, 300, 20);
        panel.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(0, 100, 300, 20);
        panel.add(nameText);

        JLabel ageLabel = new JLabel("Enter your age: ");
        ageLabel.setBounds(0, 120, 300, 20);
        panel.add(ageLabel);

        JTextField ageText = new JTextField();
        ageText.setBounds(0, 140, 300, 20);
        panel.add(ageText);

        JLabel weightLabel = new JLabel("Enter your weight: ");
        weightLabel.setBounds(0, 160, 300, 20);
        panel.add(weightLabel);

        JTextField weightText = new JTextField();
        weightText.setBounds(0, 180, 300, 20);
        panel.add(weightText);

        JLabel caloriesLabel = new JLabel("Enter your calories goal: ");
        caloriesLabel.setBounds(0, 200, 300, 20);
        panel.add(caloriesLabel);

        JTextField caloriesText = new JTextField();
        caloriesText.setBounds(0, 220, 300, 20);
        panel.add(caloriesText);

        JButton register = new JButton("Register");
        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setBounds(0, 240, 300, 20);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                String password = new String(passwordText.getPassword());
                String name = nameText.getText();
                int age = Integer.parseInt(ageText.getText());
                Double weight = Double.parseDouble(weightText.getText());
                Double calories = Double.parseDouble(caloriesText.getText());
                try {
                    model.getUserDatabase().addUser(username, password, name ,age, weight, calories);
//                    model.getUserDatabase().saveData();
                    JOptionPane.showMessageDialog(view.getFrame(), "User Registered");
                    clear(panel);
                    initView();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(view.getFrame(), "There was an error");
                    ioException.printStackTrace();
                }
            }
        });
        panel.add(register);

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBounds(0, 280, 300, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                initView();
            }
        });
        panel.add(back);

    }

    public void showTableFoods(JPanel panel){
        this.view.getFrame().setSize(590,500);
        String[] columns = {"Name", "Type", "Calories", "Fats", "Carbohydrates", "Protein"};

        HashMap<String, Food > data = model.getFoodDatabase().getFoodsDatabase();
        String[][] dataParsed  = new String[data.keySet().size()][];
        int i = 0;
        for(String key : data.keySet()){
            String name = data.get(key).getName();
            String type = data.get(key).getType();

            String calories = Double.toString(data.get(key).getCalories());
            String fat = Double.toString(data.get(key).getFats());
            String carbohydrates = Double.toString(data.get(key).getCarbohydrates());
            String protein = Double.toString(data.get(key).getProteins());

            String[] entry = {name, type,calories,fat,carbohydrates,protein};
            dataParsed[i] = entry;
            i++;
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(0,370,590,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().setSize(300, 335);
                clear(panel);
                recipeOptions(panel);
            }
        });
        panel.add(backButton);

        JTable table = new JTable(dataParsed,columns){
            public boolean editCellAt(int row, int col, java.util.EventObject e){
                return false;
            }
        };

       JScrollPane jsp =  new JScrollPane(table);
       jsp.setBounds(0,0,600,300);
       table.setBounds(0, 0, 600,300);
       panel.add(jsp);
    }


    public void showTableExcercise(JPanel panel){
        this.view.getFrame().setSize(600, 300);
        String[] columns = {"Name", "Calories Burnt"};

        HashMap<String, Exercise > data = model.getExerciseDatabase().getExerciseDatabase();
        String[][] dataParsed  = new String[data.keySet().size()][];
        int i = 0;
        for(String key : data.keySet()){
            String name = data.get(key).getName();
            String calories = Double.toString(data.get(key).getCaloriesBurnt());
            String[] entry = {name, calories};
            dataParsed[i] = entry;
            i++;
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(0,200,590,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().setSize(300, 335);
                clear(panel);
                exerciseOptions(panel);
            }
        });
        panel.add(backButton);

        JTable table = new JTable(dataParsed,columns){
            public boolean editCellAt(int row, int col, java.util.EventObject e){
                return false;
            }
        };

        JScrollPane jsp =  new JScrollPane(table);
        jsp.setBounds(0,0,600,300);
        table.setBounds(0, 0, 600,300);

        panel.add(jsp);
    }

    public void showTableLog(JPanel panel){
        this.view.getFrame().setSize(610, 400);
        String[] columns = {"Log", "Calories of the day", "Fats of the day", "Carbohydrates of the day", "Protein of the day"};

        HashMap<String, LogEntry > data = model.getLogDatabase().getLogDatabase();
        String[][] dataParsed  = new String[data.keySet().size()][];
        int i = 0;
        for(String key : data.keySet()){
            String log = data.get(key).getDate();
            String calories = Double.toString(data.get(key).getCaloriesOfEntry());
            String fats = Double.toString(data.get(key).getFatsOfEntry());
            String carbs = Double.toString(data.get(key).getCarbohydratesOfEntry());
            String protein = Double.toString(data.get(key).getProteinsOfEntry());
            String[] entry = {log, calories,fats,carbs,protein};
            dataParsed[i] = entry;
            i++;
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(0,300,590,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().setSize(300, 335);
                clear(panel);
                logEntryOptions(panel);
            }
        });
        panel.add(backButton);

        JTable table = new JTable(dataParsed,columns){
            public boolean editCellAt(int row, int col, java.util.EventObject e){
                return false;
            }
        };

        JScrollPane jsp =  new JScrollPane(table);
        jsp.setBounds(0,0,600,300);
        table.setBounds(0, 0, 600,300);
        panel.add(jsp);
    }

    public void login(JPanel panel){
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(0, 0, 300,20);
        panel.add(usernameLabel);

        JTextField usernameText = new JTextField();
        usernameText.setBounds(0, 30, 300,20);
        panel.add(usernameText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(0, 50, 300, 20);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(0, 80, 300, 20);
        panel.add(passwordText);

        JButton login = new JButton("Login");
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setBounds(0, 120, 300, 20);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                String password = new String(passwordText.getPassword());
                if (model.getUserDatabase().getUserDatabase().containsKey(username)){
                    if (model.getUserDatabase().getUser(username).getPassword().equals(password)){
                        user = model.getUserDatabase().getUser(username);
                        System.out.println("allowed");
                        clear(panel);
                        applicationWorkFlow(panel);
                    }
                    else{
                        JOptionPane.showMessageDialog(view.getFrame(), "Wrong password");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(view.getFrame(), "User doesn't exist");
                }
            }
        });
        panel.add(login);

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBounds(0, 160, 300, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back");
                clear(panel);
                initView();
            }
        });
        panel.add(back);
    }

    public void applicationWorkFlow(JPanel panel){
        JLabel welcome = new JLabel("Welcome Back " + user.getName() + "!");
        welcome.setBounds(0, 0, 300,20);
        panel.add(welcome);

        JLabel question = new JLabel("What would you like to do? ");
        question.setBounds(0, 30, 300, 20);
        panel.add(question);

        JButton recipes = new JButton("Recipe");
        recipes.setAlignmentX(Component.CENTER_ALIGNMENT);
        recipes.setBounds(0, 50, 300,60);
        recipes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("recipe");
                clear(panel);
                recipeOptions(panel);
            }
        });
        panel.add(recipes);

        JButton logEntry = new JButton("Log Entry");
        logEntry.setAlignmentX(Component.CENTER_ALIGNMENT);
        logEntry.setBounds(0, 110, 300, 60);
        logEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Log entry");
                clear(panel);
                logEntryOptions(panel);
            }
        });
        panel.add(logEntry);

        JButton exercise = new JButton("Exercise");
        exercise.setAlignmentX(Component.CENTER_ALIGNMENT);
        exercise.setBounds(0, 170, 300, 60);
        exercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exercise");
                clear(panel);
                exerciseOptions(panel);
            }
        });
        panel.add(exercise);

        JButton user = new JButton("User");
        user.setAlignmentX(Component.CENTER_ALIGNMENT);
        user.setBounds(0, 230, 300, 60);
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("User");
                clear(panel);
                userOptions(panel);
            }
        });
        panel.add(user);
    }

    public void recipeOptions(JPanel panel){
        JLabel header = new JLabel("Recipe Options");
        header.setBounds(0,0, 300, 20);
        panel.add(header);

        JButton display = new JButton("Display all recipes & foods");
        display.setBounds(0, 30, 300, 60);
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               clear(panel);
                showTableFoods(panel);
            }
        });
        panel.add(display);

        JButton add = new JButton("Add Recipe or food");
        add.setBounds(0, 90, 300, 60);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add food");
                clear(panel);
                addFood(panel);
            }
        });
        panel.add(add);

        JButton remove = new JButton("Remove recipe or food");
        remove.setBounds(0, 150, 300, 60);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                removeFood(panel);
            }
        });
        panel.add(remove);

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBounds(0, 210, 300, 60);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back");
                clear(panel);
                applicationWorkFlow(panel);
            }
        });
        panel.add(back);
    }

    public void removeFood(JPanel panel){
        JLabel header = new JLabel("Remove Food: ");
        header.setBounds(0,0, 300, 20);
        panel.add(header);

        JLabel foodLabel = new JLabel("Enter the food to be removed: ");
        foodLabel.setBounds(0, 20, 300, 20);
        panel.add(foodLabel);

        JTextField foodText = new JTextField();
        foodText.setBounds(0, 40, 300, 20);
        panel.add(foodText);

        JButton remove = new JButton("Remove food");
        remove.setBounds(0, 80, 300, 40);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String food = foodText.getText();
                if(model.getFoodDatabase().getFoodsDatabase().containsKey(food)){
                    try {
                        model.getFoodDatabase().removeFood(food);
                        foodText.setText("");
                        JOptionPane.showMessageDialog(view.getFrame(), "Food removed");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(view.getFrame(), "Error removing food");
                        ioException.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(view.getFrame(), "Food doesn't exist");
                }
            }
        });
        panel.add(remove);

        JButton back = new JButton("Back");
        back.setBounds(0, 140, 300, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                recipeOptions(panel);
            }
        });
        panel.add(back);

    }

    public void addFood(JPanel panel){
        JButton food = new JButton("Add Food");
        food.setAlignmentX(Component.CENTER_ALIGNMENT);
        food.setBounds(0, 0, 300, 100);
        food.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                addBasicFood(panel);
            }
        });
        panel.add(food);

        JButton recipe = new JButton("Add Recipe");
        recipe.setAlignmentX(Component.CENTER_ALIGNMENT);
        recipe.setBounds(0, 100, 300, 100);
        recipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                addRecipeFood(panel);
            }
        });
        panel.add(recipe);

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBounds(0, 200, 300, 100);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back");
                clear(panel);
                recipeOptions(panel);
            }
        });
        panel.add(back);
    }

    public void addRecipeFood(JPanel panel){
        HashMap<Food, Double> ingredientList = new HashMap<Food, Double>();

        JLabel header = new JLabel("Add a recipe");
        header.setBounds(0, 0, 300, 20);
        panel.add(header);

        JLabel nameLabel = new JLabel("Recipe name: ");
        nameLabel.setBounds(0, 40, 300, 20);
        panel.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(0, 60, 300, 20);
        panel.add(nameText);

        JLabel ingredientLabel = new JLabel("Ingredients: (name,amount)");
        ingredientLabel.setBounds(0,80, 300, 20);
        panel.add(ingredientLabel);

        JTextField ingredientText = new JTextField();
        ingredientText.setBounds(0, 100, 300, 20);
        panel.add(ingredientText);

        JButton addIngredient = new JButton("Add Ingredient");
        addIngredient.setAlignmentX(Component.CENTER_ALIGNMENT);
        addIngredient.setBounds(0, 120, 300, 20);
        addIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] ingredient = ingredientText.getText().split(",");
                if(model.getFoodDatabase().getFoodsDatabase().containsKey(ingredient[0])){
                    ingredientText.setText("");
                    ingredientList.put(model.getFoodDatabase().getFoodsDatabase().get(ingredient[0]), Double.parseDouble(ingredient[1]));
                    JOptionPane.showMessageDialog(view.getFrame(), "Ingredient Added!");
                    System.out.println(ingredientList.size());
                }
                else{
                    JOptionPane.showMessageDialog(view.getFrame(), "Food doesn't exist");
                }
            }
        });
        panel.add(addIngredient);

        JButton addRecipe = new JButton("Add Recipe");
        addRecipe.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRecipe.setBounds(0, 160, 300, 20);
        addRecipe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                try {
                    model.getFoodDatabase().addRecipe(name, ingredientList);
                    JOptionPane.showMessageDialog(view.getFrame(), "Recipe added!");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(view.getFrame(), "There was an error");
                    ioException.printStackTrace();
                }
            }
        });
        panel.add(addRecipe);

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBounds(0, 180, 300, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                recipeOptions(panel);
            }
        });
        panel.add(back);

    }

    public void addBasicFood(JPanel panel){
        JLabel header = new JLabel("Add a basic Food");
        header.setBounds(0, 0, 300, 20);
        panel.add(header);

        JLabel nameLabel = new JLabel("Food name:");
        nameLabel.setBounds(0, 20, 300, 20);
        panel.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(0,40, 300, 20);
        panel.add(nameText);

        JLabel caloriesLabel = new JLabel("Calories:");
        caloriesLabel.setBounds(0, 60, 300, 20);
        panel.add(caloriesLabel);

        JTextField caloriesText = new JTextField();
        caloriesText.setBounds(0, 80, 300, 20);
        panel.add(caloriesText);

        JLabel fatLabel = new JLabel("Fat: ");
        fatLabel.setBounds(0, 100, 300, 20);
        panel.add(fatLabel);

        JTextField fatText = new JTextField();
        fatText.setBounds(0, 120, 300, 20);
        panel.add(fatText);

        JLabel carbsLabel = new JLabel("Carbohydrates: ");
        carbsLabel.setBounds(0, 140, 300, 20);
        panel.add(carbsLabel);

        JTextField carbsText = new JTextField();
        carbsText.setBounds(0, 160, 300, 20);
        panel.add(carbsText);

        JLabel proteinLabel = new JLabel("Proteins: ");
        proteinLabel.setBounds(0, 180, 300, 20);
        panel.add(proteinLabel);

        JTextField proteinText = new JTextField();
        proteinText.setBounds(0, 200, 300, 20);
        panel.add(proteinText);

        JButton addFood = new JButton("Add food");
        addFood.setAlignmentX(Component.CENTER_ALIGNMENT);
        addFood.setBounds(0, 240, 300, 30);
        addFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                Double calories = Double.parseDouble(caloriesText.getText());
                Double fat = Double.parseDouble(fatText.getText());
                Double carbohydrates = Double.parseDouble(carbsText.getText());
                Double protein = Double.parseDouble(proteinText.getText());
                if(!model.getFoodDatabase().getFoodsDatabase().containsKey(name)){
                    try {
                        model.getFoodDatabase().addBasicFood(name, calories, fat, carbohydrates, protein);
                        JOptionPane.showMessageDialog(view.getFrame(), "Basic food has been added");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(view.getFrame(), "There's an error");
                        ioException.printStackTrace();
                    }
                }
            }
        });
        panel.add(addFood);

        JButton back = new JButton("Back");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBounds(0, 280, 300, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                addFood(panel);
            }
        });
        panel.add(back);
    }


    public void showSingleLog(JPanel panel){

        JLabel header = new JLabel("Searching Log");
        header.setBounds(0,0,300,20);
        panel.add(header);

        JLabel searchLbl = new JLabel("Enter Log Date(YYYY/MM/DD): ");
        searchLbl.setBounds(0,30,300,20);
        panel.add(searchLbl);

        JTextField searchTF = new JTextField();
        searchTF.setBounds(0,50,300,20);
        panel.add(searchTF);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(0,80,300,30);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                logDescription(panel, searchTF.getText());
            }
        });
        panel.add(searchBtn);

        JButton backButton = new JButton("Back");
        backButton.setBounds(0,120,300,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                logEntryOptions(panel);
            }
        });
        panel.add(backButton);
    }

    public void logDescription(JPanel panel, String date){
        view.getFrame().setSize(600, 500);
        LogEntry logEntry = model.getLogDatabase().getLogDatabase().get(date);

        JLabel header = new JLabel("Showing log of: " + date);
        header.setBounds(0, 0, 200, 30);
        panel.add(header);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 40, 600, 290);

        //Nutrients
        JPanel nutrientsPanel = new JPanel();

        nutrientsPanel.setLayout(null);

        JLabel nutrientsHeader = new JLabel("Nutrients", SwingConstants.CENTER);
        nutrientsHeader.setBounds(0, 0, 300, 20);
        nutrientsPanel.add(nutrientsHeader);

        JLabel recordedWeight = new JLabel("Weight Goal: " + Double.toString(logEntry.getRecordedWeight()));
        recordedWeight.setBounds(0, 30, 300, 20);
        nutrientsPanel.add(recordedWeight);

        JLabel recordedCalories = new JLabel("Calories Goal: " + Double.toString(logEntry.getRecordedCalories()));
        recordedCalories.setBounds(0, 50, 300, 20);
        nutrientsPanel.add(recordedCalories);

        JLabel consumedCalories = new JLabel("Consumed Calories: " + Double.toString(logEntry.getCaloriesOfEntry()));
        consumedCalories.setBounds(0, 90, 300, 20);
        nutrientsPanel.add(consumedCalories);

        JLabel consumedFats = new JLabel("Consumed Fats: " + Double.toString(logEntry.getFatsOfEntry()));
        consumedFats.setBounds(0, 110, 300, 20);
        nutrientsPanel.add(consumedFats);

        JLabel consumedCarbs = new JLabel("Consumed Carbohydrates: " + Double.toString(logEntry.getCarbohydratesOfEntry()));
        consumedCarbs.setBounds(0, 130, 300, 20);
        nutrientsPanel.add(consumedCarbs);

        JLabel consumedProtein = new JLabel("Consumed Proteins: " + Double.toString(logEntry.getProteinsOfEntry()));
        consumedProtein.setBounds(0, 150, 300, 20);
        nutrientsPanel.add(consumedProtein);

        if (logEntry.getRecordedCalories() < logEntry.getCaloriesOfEntry()){
            JLabel over = new JLabel("Over the calories goal.");
            over.setBounds(0, 180, 300, 20);
            nutrientsPanel.add(over);
        }
        else{
            JLabel under = new JLabel("Under calories goal.");
            under.setBounds(0, 180, 300, 20);
            nutrientsPanel.add(under);
        }

        tabbedPane.addTab("Nutrients", nutrientsPanel);

        //Foods Consumed
        JPanel foodsConsumedPanel = new JPanel();

        foodsConsumedPanel.setLayout(null);

        JLabel foodsConsumedHeader = new JLabel("Foods Consumed", SwingConstants.CENTER);
        foodsConsumedHeader.setBounds(0, 0, 300, 20);
        foodsConsumedPanel.add(foodsConsumedHeader);

        int y = 30;
        ArrayList<Pair> foodsConsumed =  logEntry.getFoodsConsumed();

        for (int i = 0; i < foodsConsumed.size(); i++){
            Food currentFood = (Food) foodsConsumed.get(i).getKey();
            JLabel food = new JLabel(currentFood.getName() + "  #"  + Double.toString((Double) foodsConsumed.get(i).getValue()));
            food.setBounds(0, y, 300, 20);
            foodsConsumedPanel.add(food);
            y += 20;
        }

        tabbedPane.addTab("Foods Consumed", foodsConsumedPanel);

        //Exercises made
        JPanel exercisePanel = new JPanel();
        exercisePanel.setLayout(null);
        JLabel exerciseHeader = new JLabel("Exercise made", SwingConstants.CENTER);
        exerciseHeader.setBounds(0, 0, 300, 20);
        exercisePanel.add(exerciseHeader);

        int x = 30;
        ArrayList<Pair> exercisesMade = logEntry.getExercisesMade();

        if(exercisesMade.size() != 0) {
            for (int o = 0; o < exercisesMade.size(); o++) {
                Exercise currentExercise = (Exercise) exercisesMade.get(o).getKey();
                JLabel exercise = new JLabel(currentExercise.getName() + " for: " + Double.toString((Double) exercisesMade.get(o).getValue()) + " minutes");
                exercise.setBounds(0, x, 300, 20);
                exercisePanel.add(exercise);
                x += 20;
            }
        }
        else{
            JLabel answer = new JLabel("No exercises made this day");
            answer.setBounds(0, 30, 300, 20);
            exercisePanel.add(answer);
        }

        tabbedPane.add("Exercise", exercisePanel);

        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(null);

        Double hundredPercent = (logEntry.getFatsOfEntry() + logEntry.getCarbohydratesOfEntry() + logEntry.getProteinsOfEntry());
        if(hundredPercent > 0) {
            Double fatsPercent = Double.valueOf(Math.round((logEntry.getFatsOfEntry() * 100) / hundredPercent));
            Double carbohydratesPercent = Double.valueOf(Math.round((logEntry.getCarbohydratesOfEntry() * 100) / hundredPercent));
            Double proteinsPercent = Double.valueOf(Math.round((logEntry.getProteinsOfEntry() * 100) / hundredPercent));
            String fats = this.calculateGraph(fatsPercent);
            String carbohydrates = this.calculateGraph(carbohydratesPercent);
            String proteins = this.calculateGraph(proteinsPercent);
            String total = this.calculateGraph(hundredPercent);

            JLabel fatsLabel = new JLabel(String.format("%30s%-30s", "Fats: " + Double.toString(fatsPercent) + "%: ", fats));
            fatsLabel.setBounds(0, 30, 600, 30);
            graphPanel.add(fatsLabel);

            JLabel carbsLabel = new JLabel(String.format("%30s%-30s", "Carbs: " + Double.toString(carbohydratesPercent) + "%: ", carbohydrates));
            carbsLabel.setBounds(0, 70, 600, 30);
            graphPanel.add(carbsLabel);

            JLabel proteinLabel = new JLabel(String.format("%30s%-30s", "Proteins: " + Double.toString(proteinsPercent) + "%: ", proteins));
            proteinLabel.setBounds(0, 110, 600, 30);
            graphPanel.add(proteinLabel);
        }

        tabbedPane.add("Graph", graphPanel);

        panel.add(tabbedPane);


        JButton back = new JButton("Back");
        back.setBounds(0, 400, 300, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().setSize(300, 335);
                clear(panel);
                showSingleLog(panel);
            }
        });
        panel.add(back);
    }

    private String calculateGraph(Double percentage){
        String returnString = "";
        for (int i = 0; i < percentage / 2; i++){
            returnString += "#";
        }
        return returnString;
    }


    public void modifyLogs(JPanel panel){

        this.view.getFrame().setSize(300, 600);

        JLabel header = new JLabel("Only the filled in sections will update.");
        header.setBounds(0,0,300,20);
        panel.add(header);


        JLabel logDatelbl = new JLabel("Log Date to modify(YYYY/MM/DD):");
        logDatelbl.setBounds(0,30,300,20);
        panel.add(logDatelbl);

        JTextField logDateTF = new JTextField();
        logDateTF.setBounds(0,60,300,20);
        panel.add(logDateTF);


        JLabel addFoodlbl = new JLabel("Add New Food");
        addFoodlbl.setBounds(0,90,300,20);
        panel.add(addFoodlbl);

        JTextField addFoodTF = new JTextField();
        addFoodTF.setBounds(0,110,300,20);
        panel.add(addFoodTF);

        JLabel foodAmountlbl = new JLabel("Food Amount(Decimal):");
        foodAmountlbl.setBounds(0,140,300,20);
        panel.add(foodAmountlbl);

        JTextField foodAmountTF = new JTextField();
        foodAmountTF.setBounds(0,170,300,20);
        panel.add(foodAmountTF);

        JLabel weightlbl = new JLabel("Change Weight goal(Decimal):");
        weightlbl.setBounds(0,200,300,20);
        panel.add(weightlbl);

        JTextField weightTF = new JTextField();
        weightTF.setBounds(0,230,300,20);
        panel.add(weightTF);

        JLabel calorieslbl = new JLabel("Change Calories goal(Decimal):");
        calorieslbl.setBounds(0,260,300,20);
        panel.add(calorieslbl);

        JTextField caloriesTF = new JTextField();
        caloriesTF.setBounds(0,290,300,20);
        panel.add(caloriesTF);

        JLabel addExerciselbl = new JLabel("Exercise Name:");
        addExerciselbl.setBounds(0,320,300,20);
        panel.add(addExerciselbl);

        JTextField addExerciseTF = new JTextField();
        addExerciseTF.setBounds(0,350,300,20);
        panel.add(addExerciseTF);

        JLabel minutesExerciselbl = new JLabel("Amount(minutes):");
        minutesExerciselbl.setBounds(0,380,300,20);
        panel.add(minutesExerciselbl);

        JTextField minutesExerciseTF = new JTextField();
        minutesExerciseTF.setBounds(0,410,300,20);
        panel.add(minutesExerciseTF);


        JButton ModifyBtn = new JButton("Modify");
        ModifyBtn.setBounds(0,440,300,30);
        ModifyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logDate = logDateTF.getText();
                if(!logDateTF.getText().equals("") && model.getLogDatabase().getLogDatabase().containsKey(logDate)){

                    if(!addFoodTF.getText().equals("") && !foodAmountTF.getText().equals("")){
                        JOptionPane.showMessageDialog(view.getFrame(), "Modified Food");
                        model.getLogDatabase().getLogDatabase().get(logDate).addConsumedFood(addFoodTF.getText(),Double.parseDouble(foodAmountTF.getText()));
                    }

                    if(!weightTF.getText().equals("")){
                        try {
                            JOptionPane.showMessageDialog(view.getFrame(), "Modified Weight");
                            model.getLogDatabase().modifyWeightForLog(logDate,Double.parseDouble(weightTF.getText()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if(!caloriesTF.getText().equals("")){
                        try {
                            JOptionPane.showMessageDialog(view.getFrame(), "Modified Calories");
                            model.getLogDatabase().modifyCaloriesForLog(logDate,Double.parseDouble(caloriesTF.getText()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if(!addExerciseTF.getText().equals("") && !minutesExerciseTF.getText().equals("")){
                        Double userWeight = user.getWeight();
                        if (model.getLogDatabase().getLogDatabase().containsKey(logDate)){
                            LogEntry dayEntry = model.getLogDatabase().getLogDatabase().get(logDate);
                            if(model.getExerciseDatabase().getExerciseDatabase().containsKey(addExerciseTF.getText())){
                                JOptionPane.showMessageDialog(view.getFrame(), "Modified Exercise");
                                dayEntry.addExercise(addExerciseTF.getText(), Double.parseDouble(minutesExerciseTF.getText()));
                                dayEntry.calculateCalories(userWeight);
                                try {
                                    model.getLogDatabase().saveData();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(view.getFrame(), "Exercise doesn't exist");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(view.getFrame(), "There's not log entry");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(view.getFrame(), "Log date Empty or dose not exist, please enter the date of the lof you want to modify.(Format: YYYY/MM/DD)");
                }
            }
        });
        panel.add(ModifyBtn);

        JButton backButton = new JButton("Back");
        backButton.setBounds(0,480,300,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getFrame().setSize(300, 335);
                clear(panel);
                logEntryOptions(panel);
            }
        });
        panel.add(backButton);
    }


    public void removeLog(JPanel panel){
        JLabel header = new JLabel("Only the filled in sections will update.");
        header.setBounds(0,0,300,20);
        panel.add(header);


        JLabel logDatelbl = new JLabel("Log Date to remove(YYYY/MM/DD):");
        logDatelbl.setBounds(0,30,300,20);
        panel.add(logDatelbl);

        JTextField logDateTF = new JTextField();
        logDateTF.setBounds(0,60,300,20);
        panel.add(logDateTF);


        JButton removeBtn = new JButton("Remove Log");
        removeBtn.setBounds(0,90,300,30);
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logDate = logDateTF.getText();
                if (model.getLogDatabase().getLogDatabase().containsKey(logDate)) {
                    try {
                        JOptionPane.showMessageDialog(view.getFrame(), "Removed " + logDate);
                        model.getLogDatabase().removeLog(logDate);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(view.getFrame(), "Log doesn't exist");
                }
            }
        });
        panel.add(removeBtn);


        JButton backButton = new JButton("Back");
        backButton.setBounds(0,120,300,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                logEntryOptions(panel);
            }
        });
        panel.add(backButton);

    }
    public void addLog(JPanel panel){
        JLabel header = new JLabel("Only the filled in sections will update.");
        header.setBounds(0,0,300,20);
        panel.add(header);


        JLabel logDatelbl = new JLabel("Log Date to remove(YYYY/MM/DD):");
        logDatelbl.setBounds(0,30,300,20);
        panel.add(logDatelbl);

        JTextField logDateTF = new JTextField();
        logDateTF.setBounds(0,60,300,20);
        panel.add(logDateTF);


        JButton addBtn = new JButton("Add Log");
        addBtn.setBounds(0,90,300,30);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.getLogDatabase().getLogDatabase().containsKey(logDateTF.getText())) {
                    String[] logDate = logDateTF.getText().split("/");
                    try {
                        model.getLogDatabase().addEntryLog(logDate);
                        JOptionPane.showMessageDialog(view.getFrame(), "Added entry");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(view.getFrame(), "There's already a log entry");
                }
            }
        });
        panel.add(addBtn);


        JButton backButton = new JButton("Back");
        backButton.setBounds(0,120,300,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                logEntryOptions(panel);
            }
        });
        panel.add(backButton);
    }
    public void logEntryOptions(JPanel panel){
        JLabel header = new JLabel("Log Entry Options");
        header.setBounds(0, 0, 300, 20);
        panel.add(header);

        JButton addLogEntry = new JButton("Add log Entry");
        addLogEntry.setBounds(0, 30, 300, 40);

        addLogEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                addLog(panel);
            }
        });


        panel.add(addLogEntry);

        JButton displayAllLogs = new JButton("Display all log entry");
        displayAllLogs.setBounds(0, 70, 300, 40);
        displayAllLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                showTableLog(panel);
            }
        });

        panel.add(displayAllLogs);

        JButton displayLog = new JButton("Display single log");
        displayLog.setBounds(0, 110, 300 , 40);

        displayLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                showSingleLog(panel);
            }
        });

        panel.add(displayLog);

        JButton modifyLog = new JButton("Modify log entry");
        modifyLog.setBounds(0, 150, 300, 40);

        modifyLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                modifyLogs(panel);
            }
        });

        panel.add(modifyLog);

        JButton removeLog = new JButton("Remove log entry");
        removeLog.setBounds(0, 190, 300, 40);

        removeLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                removeLog(panel);
            }
        });

        panel.add(removeLog);

        JButton back = new JButton("Back");
        back.setBounds(0, 250, 300, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                applicationWorkFlow(panel);
            }
        });
        panel.add(back);
    }

    public void exerciseOptions(JPanel panel){
        JLabel header = new JLabel("Exercise Options");
        header.setBounds(0, 0 ,300, 20);
        panel.add(header);

        JButton displayExercises = new JButton("Display Exercises");
        displayExercises.setBounds(0, 30, 300, 60);
        //TODO logic
        panel.add(displayExercises);

        displayExercises.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                showTableExcercise(panel);
            }
        });


        JButton addExercises = new JButton("Add exercise");
        addExercises.setBounds(0, 90, 300, 60);
        addExercises.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                addExercise(panel);
            }
        });
        panel.add(addExercises);

        JButton removeExercises = new JButton("Remove exercise");
        removeExercises.setBounds(0, 150, 300, 60);
        removeExercises.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                removeExercise(panel);
            }
        });
        panel.add(removeExercises);

        JButton back = new JButton("Back");
        back.setBounds(0, 210, 300, 60);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back");
                clear(panel);
                applicationWorkFlow(panel);
            }
        });
        panel.add(back);
    }

    public void removeExercise(JPanel panel){
        JLabel header = new JLabel("Removing Exercise");
        header.setBounds(0 ,0, 300, 20);
        panel.add(header);

        JLabel nameLabel = new JLabel("Name of the exercise: ");
        nameLabel.setBounds(0, 30, 300, 20);
        panel.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(0, 50, 300, 20);
        panel.add(nameText);

        JButton removeExercise = new JButton("Remove Exercise");
        removeExercise.setBounds(0, 80, 300, 40);
        removeExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exerciseName = nameText.getText();
                if(model.getExerciseDatabase().getExerciseDatabase().containsKey(exerciseName)){
                    try {
                        model.getExerciseDatabase().removeExercise(exerciseName);
                        nameText.setText("");
                        JOptionPane.showMessageDialog(view.getFrame(), "Exercise has been removed");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(view.getFrame(), "Error removing exercise");
                        ioException.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(view.getFrame(), "Exercise doesn't exist");
                }
            }
        });
        panel.add(removeExercise);

        JButton back = new JButton("Back");
        back.setBounds(0, 130, 300 ,40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                exerciseOptions(panel);
            }
        });
        panel.add(back);
    }

    public void addExercise(JPanel panel){
        JLabel header = new JLabel("Adding exercise");
        header.setBounds(0, 0, 300, 20);
        panel.add(header);

        JLabel nameLabel = new JLabel("Exercise name: ");
        nameLabel.setBounds(0, 30, 300, 20);
        panel.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(0, 50, 300, 20);
        panel.add(nameText);

        JLabel caloriesLabel = new JLabel("Calories Burn: ");
        caloriesLabel.setBounds(0, 70, 300, 20);
        panel.add(caloriesLabel);

        JTextField caloriesText = new JTextField();
        caloriesText.setBounds(0, 90, 300, 20);
        panel.add(caloriesText);

        JButton addExercise = new JButton("Add exercise");
        addExercise.setBounds(0, 130, 300, 40);
        addExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                Double calories = Double.parseDouble(caloriesText.getText());
                if (!model.getExerciseDatabase().getExerciseDatabase().containsKey(name)){
                    try {
                        model.getExerciseDatabase().addExercise(name, calories);
                        nameText.setText("");
                        caloriesText.setText("");
                        JOptionPane.showMessageDialog(view.getFrame(), "Exercise Added");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(view.getFrame(), "Error adding exercise");
                        ioException.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(view.getFrame(), "Exercise already exists");
                }
            }
        });
        panel.add(addExercise);

        JButton back = new JButton("Back");
        back.setBounds(0, 180, 300, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                exerciseOptions(panel);
            }
        });
        panel.add(back);
    }

    public void userOptions(JPanel panel){
        JLabel header = new JLabel("User Information");
        header.setBounds(0, 0, 300, 20);
        panel.add(header);

        JLabel nameLabel = new JLabel("Name: " + user.getName());
        nameLabel.setBounds(0, 30, 300, 20);
        panel.add(nameLabel);

        JLabel ageLabel = new JLabel("Age: " + Integer.toString(user.getAge()));
        ageLabel.setBounds(0, 50, 300, 20);
        panel.add(ageLabel);

        JLabel weightLabel = new JLabel("Current Weight: " + Double.toString(user.getWeight()));
        weightLabel.setBounds(0, 70, 300, 20);
        panel.add(weightLabel);

        JLabel caloriesLabel = new JLabel("Calorie goals: " + Double.toString(user.getCalories()));
        caloriesLabel.setBounds(0, 90, 300, 20);
        panel.add(caloriesLabel);

        JButton edit = new JButton("Edit");
        edit.setBounds(0, 130, 300, 40);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit user");
                clear(panel);
                userEdit(panel);
            }
        });
        panel.add(edit);

        JButton back = new JButton("Back");
        back.setBounds(0, 180, 300, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("back");
                clear(panel);
                applicationWorkFlow(panel);
            }
        });
        panel.add(back);
    }

    public void userEdit(JPanel panel){
        JLabel header = new JLabel("Editing User");
        header.setBounds(0 ,0, 300, 20);
        panel.add(header);

        JLabel nameLabel = new JLabel("Current Name: " + user.getName());
        nameLabel.setBounds(0, 30, 300, 20);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(user.getName());
        nameText.setBounds(0, 50, 300, 20);
        panel.add(nameText);

        JLabel ageLabel = new JLabel("Current Age: " + Integer.toString(user.getAge()));
        ageLabel.setBounds(0, 70, 300, 20);
        panel.add(ageLabel);

        JTextField ageText = new JTextField(Integer.toString(user.getAge()));
        ageText.setBounds(0, 90, 300, 20);
        panel.add(ageText);

        JLabel weightLabel = new JLabel("Current weight: " + Double.toString(user.getWeight()));
        weightLabel.setBounds(0, 110, 300, 20);
        panel.add(weightLabel);

        JTextField weightText = new JTextField(Double.toString(user.getWeight()));
        weightText.setBounds(0, 130, 300, 20);
        panel.add(weightText);

        JLabel caloriesLabel = new JLabel("Current Calories: " + Double.toString(user.getCalories()));
        caloriesLabel.setBounds(0, 150, 300, 20);
        panel.add(caloriesLabel);

        JTextField caloriesText = new JTextField(Double.toString(user.getCalories()));
        caloriesText.setBounds(0, 170, 300, 20);
        panel.add(caloriesText);

        JButton save = new JButton("Save");
        save.setBounds(0, 190, 300, 30);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                int age = Integer.parseInt(ageText.getText());
                Double weight = Double.parseDouble(weightText.getText());
                Double calories = Double.parseDouble(caloriesText.getText());
                user.setName(name);
                user.setAge(age);
                user.setWeight(weight);
                user.setCalories(calories);
                try {
                    model.getUserDatabase().saveData();
                    JOptionPane.showMessageDialog(view.getFrame(), "User updated");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Failed to update user");
                    ioException.printStackTrace();
                }
            }
        });
        panel.add(save);

        JButton back = new JButton("Back");
        back.setBounds(0, 230, 300, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(panel);
                userOptions(panel);
            }
        });
        panel.add(back);
    }
}
