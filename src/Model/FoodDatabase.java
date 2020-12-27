package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FoodDatabase implements Database{
    private HashMap<String, Food> foodsDatabase = new HashMap<String, Food>();

    public FoodDatabase() throws IOException {
        this.readData();
        this.saveData();
    }

    public HashMap<String, Food> getFoodsDatabase() {
        return this.foodsDatabase;
    }

    public void addBasicFood(String name, Double calories, Double fat, Double carbohydrates, Double protein) throws IOException {
        BasicFood basicFood = new BasicFood("b", name, calories, fat, carbohydrates, protein);
        foodsDatabase.put(basicFood.getName(), basicFood);
        this.saveData();
    }

    public void addRecipe(String name, HashMap<Food, Double> ingredients) throws IOException {
        Recipe recipe = new Recipe("r", name, ingredients);
        foodsDatabase.put(recipe.getName(), recipe);
        this.saveData();
    }

    public void removeFood(String food) throws IOException {
        foodsDatabase.remove(food);
        this.saveData();
    }

    public void saveData() throws IOException {
        FileWriter writter = new FileWriter("src/data/foods.csv");
        for (Map.Entry<String, Food> entry : foodsDatabase.entrySet()){
            Food currentEntry = (Food) entry.getValue();
            if (currentEntry.getType().equals("b")){
                writter.write(currentEntry.getType());
                writter.write(",");
                writter.write(currentEntry.getName());
                writter.write(",");
                writter.write(Double.toString(currentEntry.getCalories()));
                writter.write(",");
                writter.write(Double.toString(currentEntry.getFats()));
                writter.write(",");
                writter.write(Double.toString(currentEntry.getCarbohydrates()));
                writter.write(",");
                writter.write(Double.toString(currentEntry.getProteins()));
                writter.write("\n");
            }
            else if (currentEntry.getType().equals("r")){
                Recipe currentRecipe = (Recipe) currentEntry;
                int ingredientsAmount = currentRecipe.getIngredients().size();
                int count = 0;
                writter.write(currentEntry.getType());
                writter.write(",");
                writter.write(currentEntry.getName());
                writter.write(",");
                for (Map.Entry<Food, Double> recipe : currentRecipe.getIngredients().entrySet()){
                    count += 1;
                    writter.write(recipe.getKey().getName());
                    writter.write(",");
                    writter.write(Double.toString(recipe.getValue()));
                    if (count != ingredientsAmount){
                        writter.write(",");
                    }
                }
                writter.write("\n");
            }
        }
        writter.flush();
        writter.close();
    }

    public void readData() throws FileNotFoundException {
        try {
            File csvFile = new File("src/data/recipeFoods.csv");
            Scanner csvReader = new Scanner(csvFile);
            while (csvReader.hasNextLine()) {
                String[] data = csvReader.nextLine().split(",");
                if (data[0].equals("b")){
                    Food basicFood = new BasicFood(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
                    if (!check(basicFood)){
                        foodsDatabase.put(data[1], basicFood);
                    }
                }
                else if (data[0].equals("r")){
                    HashMap<Food, Double> ingredients = new HashMap<Food, Double>();
                    String type = data[0];
                    String name = data[1];
                    for(int i = 2; i < data.length; i += 2){
                        ingredients.put(foodsDatabase.get(data[i]), Double.parseDouble(data[i + 1]));
                    }
                    Food recipe = new Recipe(type, name, ingredients);
                    if (!check(recipe)){
                        foodsDatabase.put(data[1], recipe);
                    }
                }
            }
            csvReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public boolean check(Food food){
        boolean duplicate = false;
        for (Map.Entry entry : foodsDatabase.entrySet()){
            Food currentEntry = (Food) entry.getValue();
            if (currentEntry.getName().equals(food.getName())){
                duplicate = true;
            }
        }
        return duplicate;
    }

    //Test
//    public static void main(String[] args) throws IOException {
//        FoodDatabase food = new FoodDatabase();
//        food.readData();
//        food.display();
//        food.addBasicFood("a food", 20.0, 10.0, 5.0, 100.0);
//        food.removeFood("a food");
//    }

}
