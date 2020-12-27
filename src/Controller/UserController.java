package Controller;
import Model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.*;


public class UserController {
    private FoodDatabase foodDatabase = new FoodDatabase();
    private LogDatabase logDatabase = new LogDatabase();
    private ExerciseDatabase exerciseDatabase = new ExerciseDatabase();
    private UserDatabase userDatabase = new UserDatabase();
    private User user;
    int age;
    Double weight;
    Double calories;
    public UserController() throws IOException {
        boolean isLogin = true;
        while (isLogin) {
            Scanner sysIn = new Scanner(System.in);
            System.out.println("Welcome to the Wellness Manager!");
            System.out.println("What would you like to do?");
            System.out.println("1. Login");
            System.out.println("2. Register");

            String userAction = sysIn.nextLine();
            switch (userAction) {
                case "1":
                    while (true) {
                        System.out.println("Username: ");
                        String username = sysIn.nextLine();
                        System.out.println("Password: ");
                        String password = sysIn.nextLine();
                        if (login(username, password)) {
                            isLogin= false;
                            break;
                        }
                    }
                    break;

                case "2":
                    System.out.println("Select a username: ");
                    String username = sysIn.nextLine();

                    System.out.println("Select a password: ");
                    String password = sysIn.nextLine();

                    System.out.println("What's your name: ");
                    String name = sysIn.nextLine();

                    //Age
                    while(true) {
                        try{
                            System.out.println("What's your age?");
                            age = Integer.parseInt(sysIn.nextLine());
                            break;
                        }catch (Exception e){
                            System.out.println("This is invalid, please use number for age");
                        }
                    }

                    //Current Weight
                    while(true) {
                        try{
                            System.out.println("What's your current weight?");
                            weight = Double.parseDouble(sysIn.nextLine());
                            break;
                        }catch (Exception e){
                            System.out.println("This is invalid, please use number for weight");
                        }
                    }


                    //Calories goal
                    while(true) {
                        try{
                            System.out.println("What's your calories goal? ");
                            calories = Double.parseDouble(sysIn.nextLine());
                            break;
                        }catch (Exception e){
                            System.out.println("This is invalid, please use number for weight");
                        }
                    }

                    userDatabase.addUser(username, password, name, age, weight, calories);
                    System.out.println("User registered!");
                    while (true) {
                        try{
                            System.out.println("Username: ");
                            String username1 = sysIn.nextLine();
                            System.out.println("Password: ");
                            String password2 = sysIn.nextLine();
                            if (login(username1, password2)) {
                                isLogin= false;
                                break;
                            }
                        }catch (Exception e){
                            System.out.println("There is something wrong");
                        }

                    }
                    break;
            }
        }
    }

    public void displayFood(){
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Food Database\n");
        System.out.println(String.format("%-30s%-30s%-30s%-30s%-30s%-30s", "Name", "Type", "Calories", "Fats", "Carbohydrates", "Proteins"));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Map.Entry entry : foodDatabase.getFoodsDatabase().entrySet()){
            Food currentEntry = (Food) entry.getValue();
            System.out.println(String.format("%-30s%-30s%-30s%-30s%-30s%-30s", currentEntry.getName(), currentEntry.getType(), currentEntry.getCalories(), String.format("%.1f", currentEntry.getFats()), currentEntry.getCarbohydrates(), currentEntry.getProteins()));
        }
        System.out.println("Type 'continue' to continue");
        String continueApp = sysIn.next();
    }

    public void addFood() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Please select what's added been added");
        System.out.println("'b' for basic fooJld");
        System.out.println("'r' for recipe");
        String userInput = sysIn.nextLine();
        switch (userInput){
            case "b":
                System.out.println("Insert as: [basicFoodName],[calories],[fat],[carebohydrates],[protein]");
                String[] data = sysIn.nextLine().split(",");
                String name = data[0];
                Double calories = Double.parseDouble(data[1]);
                Double fat = Double.parseDouble(data[2]);
                Double carbohydrates = Double.parseDouble(data[3]);
                Double protein = Double.parseDouble(data[4]);
                if(!foodDatabase.getFoodsDatabase().containsKey(name)) {
                    foodDatabase.addBasicFood(name, calories, fat, carbohydrates, protein);
                    System.out.println(name + " has been added!");
                }
                else{
                    System.out.println(name + " is already in the database!");
                }
                break;
            case "r":
                System.out.println("Select recipe name");
                String recipeName = sysIn.nextLine();
                boolean addingIngredients = true;
                HashMap<Food, Double> ingredients = new HashMap<Food, Double>();
                while (addingIngredients){
                    System.out.println("Add ingredient in following format '[ingredientName],[amount]'");
                    System.out.println("to finish adding ingredients type '0,0'.");
                    String dataRun = sysIn.nextLine();
                    String[] ingredient = dataRun.split(",");
                    String ingredientName = ingredient[0];
                    Double amount = Double.parseDouble(ingredient[1]);
                    if (dataRun.equals("0,0")){
                        foodDatabase.addRecipe(recipeName, ingredients);
                        System.out.println(recipeName + " has been added!");
                        addingIngredients = false;
                    }
                    else {
                        if (foodDatabase.getFoodsDatabase().containsKey(ingredientName)){
                            ingredients.put(foodDatabase.getFoodsDatabase().get(ingredientName), amount);
                            System.out.println(ingredientName + " has been added as an ingredient! ");
                        }
                        else{
                            System.out.println("Ingredient not found in food database!");
                            continue;
                        }
                    }
                }
                break;
        }
    }

    public void removeFood() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Enter the name of the food you want to remove: ");
        String userInput = sysIn.next();
        if (foodDatabase.getFoodsDatabase().containsKey(userInput)) {
            foodDatabase.removeFood(userInput);
            System.out.println(userInput + " has been removed!");
        }
        else {
            System.out.println("Food is not in the database");
        }
    }

    public void displayAllLog(){
        System.out.println(String.format("%-30s%-30s%-30s%-30s%-30s", "Log", "Calories of the day", "Fats of the day", "Carbohydrates of the day", "protein of the day"));
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Map.Entry entry : logDatabase.getLogDatabase().entrySet()){
            LogEntry logEntry = (LogEntry) entry.getValue();
            System.out.println(String.format("%-30s%-30s%-30s%-30s%-30s", logEntry.getDate(), String.format("%.2f", logEntry.getCaloriesOfEntry()), String.format("%.2f", logEntry.getFatsOfEntry()), String.format("%.2f", logEntry.getCarbohydratesOfEntry()), String.format("%.2f", logEntry.getProteinsOfEntry()) ));
        }
    }

    public void displaySpecificLog(){
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Type date of log (####/##/##): ");
        String userInput = sysIn.next();
        if (logDatabase.getLogDatabase().containsKey(userInput)) {
            LogEntry logEntry = logDatabase.getLogDatabase().get(userInput);
            System.out.println("\n");
            System.out.println("Entry log of: " + logEntry.getDate());
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println(String.format("%-30s%-30s", "Recorded Weight: ", logEntry.getRecordedCalories()));
            System.out.println(String.format("%-30s%-30s", "Recorded Calories: ", logEntry.getRecordedWeight()));
            System.out.println(String.format("%-30s%-30s", "Consumed Calories: ", logEntry.getCaloriesOfEntry()));
            System.out.println(String.format("%-30s%-30s", "Consumed Fats: ", logEntry.getFatsOfEntry()));
            System.out.println(String.format("%-30s%-30s", "Consumed Carbohydrates: ", logEntry.getCarbohydratesOfEntry()));
            System.out.println(String.format("%-30s%-30s", "Consumed Protein : ",String.format("%.2f", logEntry.getProteinsOfEntry())));
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Foods consumed: ");
            System.out.println(String.format("%-30s%-30s", "Name", "Consumed"));
            for(int i = 0; i < logEntry.getFoodsConsumed().size(); i++){
                System.out.println(String.format("%-30s%-30s", logEntry.getFoodsConsumed().get(i).getKey(), logEntry.getFoodsConsumed().get(i).getValue()));
            }
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Exercises performed: ");
            for(int i = 0; i < logEntry.getExercisesMade().size(); i++){
                System.out.println(String.format("%-30s%-30s%-30s", "Name", "Minutes", "Calories Burnt"));
                System.out.println(logEntry.getExercisesMade().get(i).getValue().getClass());
                Exercise exercise = (Exercise) logEntry.getExercisesMade().get(i).getKey();
                Double minutes = (Double) logEntry.getExercisesMade().get(i).getValue();
                Double caloriesBurnt = exercise.getCaloriesBurnt() * (this.user.getWeight()/100) * (minutes/60);
                System.out.println(String.format("%-30s%-30s%-30s", exercise.getName(), minutes, caloriesBurnt ) );
            }
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Distribution Graph: ");
            this.displayGraph(logEntry);
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Calories Goal for the day: ");
            String overUnder = null;
            if (logEntry.getCaloriesOfEntry() > logEntry.getRecordedCalories()){
                overUnder = "You're over you calories goal for the day!";
            }
            else{
                overUnder = "You're under your calories goal for the day!";
            }
            System.out.println(Double.toString(logEntry.getCaloriesOfEntry()) + "/" + Double.toString(logEntry.getRecordedCalories()) + " " + overUnder);
            System.out.println("\n");
        }
        else {
            System.out.println("Log for the day" + userInput + " was not found");
        }
    }

    private String calculateGraph(Double percentage){
        String returnString = "";
        for (int i = 0; i < percentage / 2; i++){
            returnString += "#";
        }
        return returnString;
    }

    public void displayGraph(LogEntry logEntry){
        Double hundredPercent = (logEntry.getFatsOfEntry() + logEntry.getCarbohydratesOfEntry() + logEntry.getProteinsOfEntry());
        if(hundredPercent > 0) {
            Double fatsPercent = Double.valueOf(Math.round((logEntry.getFatsOfEntry() * 100) / hundredPercent));
            Double carbohydratesPercent = Double.valueOf(Math.round((logEntry.getCarbohydratesOfEntry() * 100) / hundredPercent));
            Double proteinsPercent = Double.valueOf(Math.round((logEntry.getProteinsOfEntry() * 100) / hundredPercent));
            String fats = this.calculateGraph(fatsPercent);
            String carbohydrates = this.calculateGraph(carbohydratesPercent);
            String proteins = this.calculateGraph(proteinsPercent);
            String total = this.calculateGraph(hundredPercent);
            System.out.println("\t\t\t\tLog entry - Graph of Consumptions");
            System.out.println(String.format("%-30s%-30s", "100%:", calculateGraph(100.0)));
            System.out.println(String.format("%-30s%-30s", "Fats: " + Double.toString(fatsPercent) + "%: ", fats));
            System.out.println(String.format("%-30s%-30s", "Carbohydrates: " + Double.toString(carbohydratesPercent) + "%: ", carbohydrates));
            System.out.println(String.format("%-30s%-30s", "Proteins: " + Double.toString(proteinsPercent) + "%: ", proteins));
        }
        else{
            System.out.println("Log entry is empty");
        }
    }

    public void createEntryLog() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Type date of log (####/##/##): ");
        String[] date = sysIn.nextLine().split("/");
        if (!logDatabase.getLogDatabase().containsKey(date)){
            logDatabase.addEntryLog(date);
        }
        else{
            System.out.println("Entry log is already in the database!");
        }
    }

    public void removeLog() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Type date of log (####/##/##): ");
        String date = sysIn.nextLine();
        if (logDatabase.getLogDatabase().containsKey(date)) {
            logDatabase.removeLog(date);
            System.out.println(date + " log has been removed.");
        }
        else{
            System.out.println(date + " can't be removed because it was not found in the database!");
        }
    }

    public void modifyLog() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Type date of log to modify (####/##/##): ");
        String date = sysIn.nextLine();
        System.out.println("What do you want to modify?");
        System.out.println("---------------------------");
        System.out.println("1. Add food to log.");
        System.out.println("2. Modify log weight.");
        System.out.println("3. Modify log calories.");
        System.out.println("4. Add exercise to log");
        System.out.println("0. Back");
        String userCommand = sysIn.nextLine();
        switch (userCommand){
            case "1":
                this.addFoodLog(date);
                break;
            case "2":
                System.out.println("Enter the weight: ");
                Double weight = Double.parseDouble(sysIn.nextLine());
                this.modifyLogWeight(date, weight);
                break;
            case "3":
                System.out.println("Enter the calories: ");
                Double calories = Double.parseDouble(sysIn.nextLine());
                this.modifyLogCalories(date, calories);
                break;
            case"4":
                this.addExerciseLog(date);
                break;
            case "0":
                break;
            default:
                System.out.println("Please select an option above");
                this.modifyLog();
        }
        logDatabase.saveData();
    }

    public void modifyLogWeight(String date, Double weight){
        logDatabase.getLogDatabase().get(date).setRecordedWeight(weight);
    }

    public void modifyLogCalories(String date, Double calories){
        logDatabase.getLogDatabase().get(date).setRecordedCalories(calories);
    }

    public void addFoodLog(String date) throws IOException {
        Scanner sysIn = new Scanner(System.in);
        if (logDatabase.getLogDatabase().containsKey(date)) {
            LogEntry dayEntry = logDatabase.getLogDatabase().get(date);
            System.out.println("Enter the name of food to be added");
            String nameOfFood = sysIn.nextLine();
            if (foodDatabase.getFoodsDatabase().containsKey(nameOfFood)){
                System.out.println("Select the amount of servings: ");
                String servingsString = sysIn.nextLine();
                Double servings = Double.parseDouble(servingsString);
                dayEntry.addConsumedFood(nameOfFood, servings);
                logDatabase.saveData();
            }
            else{
                System.out.println("Food not found in database! Please add it!");
            }
        }
        else {
            System.out.println("Log for the day" + date + " was not found");
        }
    }

    public void displayExercise(){
        System.out.println("Exercise Database\n");
        System.out.println(String.format("%-30s%-30s", "Name", "Calories Burnt"));
        System.out.println("----------------------------------------------");
        for(Map.Entry entry : exerciseDatabase.getExerciseDatabase().entrySet()){
            Exercise currentExercise = (Exercise) entry.getValue();
            System.out.println(String.format("%-30s%-30s", currentExercise.getName(), Double.toString(currentExercise.getCaloriesBurnt())));
        }
    }

    public void addExerciseLog(String date){
        Double userWeight = this.user.getWeight();
        Scanner sysIn = new Scanner(System.in);
        if(logDatabase.getLogDatabase().containsKey(date)){
            LogEntry dayEntry = logDatabase.getLogDatabase().get(date);
            System.out.println("Enter name of exercise: ");
            String nameOfExercise = sysIn.nextLine();
            if (exerciseDatabase.getExerciseDatabase().containsKey(nameOfExercise)){
                System.out.println("Select the amount of exercise time (min): ");
                Double exerciseTime = Double.parseDouble(sysIn.nextLine());
                dayEntry.addExercise(nameOfExercise, exerciseTime);
                dayEntry.calculateCalories(userWeight);
            }
            else {
                System.out.println("Exercise not found in the database!");
            }
        }
        else {
            System.out.println("Log for the day" + date + " was not found");
        }
    }

    public void addExercise() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Name of the exercise: ");
        String name = sysIn.nextLine();
        System.out.println("How many calories does it burn: ");
        Double caloriesBurnt = Double.parseDouble(sysIn.nextLine());
        if (!exerciseDatabase.getExerciseDatabase().containsKey(name)){
            exerciseDatabase.addExercise(name, caloriesBurnt);
            System.out.println(name + " has been added to your exercises!");
        }
        else {
            System.out.println("Exercise already exists!");
        }
    }

    public void removeExercise() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Name of the exercise you want to remove: ");
        String name = sysIn.nextLine();
        if(exerciseDatabase.getExerciseDatabase().containsKey(name)){
            exerciseDatabase.removeExercise(name);
            System.out.println(name + " has been removed!");
        }
        else {
            System.out.println(name + " was not found in the exercise database!");
        }
    }

    public boolean login(String username, String password){
        if (userDatabase.getUserDatabase().containsKey(username)){
            if (userDatabase.getUser(username).getPassword().equals(password)){
                this.user = userDatabase.getUser(username);
                return true;
            }
            else{
                System.out.println("Incorrect password. Try again!");
                return false;
            }
        }
        else{
            System.out.println("The user '" + username +"' doesn't exist in the database!");
            return false;
        }
    }

    public void displayInstructions() throws IOException {
        System.out.println("\n");
        System.out.println("Welcome back " + this.user.getName());
        System.out.println("-------------------------------");
        System.out.println("Please select an action: ");
        System.out.println("1. Recipes");
        System.out.println("2. Log Entry");
        System.out.println("3. Exercises");
        System.out.println("4. User");
        System.out.println("0. Quit.");
        System.out.println("\n");
    }

    public void exerciseOptions() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Select an action (Exercise)");
        System.out.println("1. Display all exercises");
        System.out.println("2. Add new exercise");
        System.out.println("3. Remove exercise");
        System.out.println("0. Back");
        System.out.println("\n");
        String userOption = sysIn.nextLine();
        switch (userOption){
            case "1":
                this.displayExercise();
                this.exerciseOptions();
                break;
            case"2":
                this.addExercise();
                this.exerciseOptions();
                break;
            case"3":
                this.removeExercise();
                this.exerciseOptions();
                break;
            case"0":
                break;
        }
    }

    public void recipesOption() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Select an action (Recipes)");
        System.out.println("1. Display all recipes and basic foods.");
        System.out.println("2. Add recipe or basic food");
        System.out.println("3. remove recipe or basic food");
        System.out.println("0. Back");
        System.out.println("\n");
        String userOption = sysIn.nextLine();
        switch (userOption) {
            case "1":
                this.displayFood();
                this.recipesOption();
                break;


            case "2":
                this.addFood();
                this.recipesOption();
                break;

            case "3":
                this.removeFood();
                this.recipesOption();
                break;
            case "0":
                break;
            default:
                System.out.println("Please select an option above");
                this.recipesOption();
        }
    }

    public void addNewLog() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("Enter the date for the log (####/##/##): ");
        String userOption = sysIn.nextLine();
        String[] userOptionArray = userOption.split("/");
        if(userOptionArray.length == 3) {
            if (!logDatabase.getLogDatabase().containsKey(userOption)) {
                logDatabase.addEntryLog(userOptionArray);
                System.out.println(userOption + " has been added to the database!");
            } else {
                System.out.println(userOption + " already in the database!");
            }
        }
        else {
            System.out.println("Please enter in the right format.");
        }
    }

    public void entryLogOption() throws IOException {
        Scanner sysIn = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("Select an action (Log Entry)");
        System.out.println("1. Add a new log entry");
        System.out.println("2. Display all log entries");
        System.out.println("3. Display a log entry");
        System.out.println("4. Modify a log entry");
        System.out.println("5. Remove log entry");
        System.out.println("0. Back");
        System.out.println("\n");
        String userOption = sysIn.nextLine();
        switch (userOption) {
            case"1":
                this.addNewLog();
                this.entryLogOption();
                break;
            case"2":
                this.displayAllLog();
                this.entryLogOption();
                break;
            case "3":
                this.displaySpecificLog();
                this.entryLogOption();
                break;
            case"4":
                this.modifyLog();
                this.entryLogOption();
                break;
            case "5":
                this.removeLog();
                this.entryLogOption();
                break;
            case "0":
                break;
            default:
                System.out.println("Please select an option above");
                this.entryLogOption();
        }
    }

    public void userOption(){
        System.out.println("\n");
        System.out.println("User's Profile");
        System.out.println("Name: " + this.user.getName());
        System.out.println("Age:" + Integer.toString(this.user.getAge()));
        System.out.println("Weight: " + Double.toString(this.user.getWeight()));
        System.out.println("\n");
    }
}