package Model;

import java.awt.font.TextHitInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipe implements Food{

    private String type;
    private String name;
    private Double calories = 0.0;
    private Double fats = 0.0;
    private Double carbohydrates = 0.0;
    private Double proteins = 0.0;
    private HashMap<Food, Double> ingredients;

    public Recipe(String type, String name, HashMap<Food, Double> ingredients){
        this.type = type;
        this.name = name;
        this.ingredients = ingredients;
        this.calculate();
    }

    public String getName() {
        return this.name;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getFats() {
        return fats;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public Double getProteins() {
        return proteins;
    }

    public String getType(){
        return this.type;
    }

    public void calculate(){
        for(Map.Entry<Food, Double> entry : ingredients.entrySet()){
            Food currentFood = entry.getKey();
            this.calories += currentFood.getCalories() * entry.getValue();
            this.fats += currentFood.getFats() * entry.getValue();
            this.carbohydrates += currentFood.getCarbohydrates() * entry.getValue();
            this.proteins += currentFood.getProteins() * entry.getValue();
        }
    }

    public HashMap<Food, Double> getIngredients(){
        return this.ingredients;
    }

    @Override
    public void showFoodDetails() {
        System.out.println(this.getName() + "(Calories: " + Double.toString(this.getCalories()) + ", Fats: " + Double.toString(this.getFats()) + ", Carbohydrates: " + this.getCarbohydrates() + ", Proteins: " + this.getProteins() + ")");
    }

    public String toString(){
        return this.name;
    }
}
