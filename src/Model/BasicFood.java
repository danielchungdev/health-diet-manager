package Model;

import javax.swing.plaf.SplitPaneUI;

public class BasicFood implements Food{

    private String type;
    private String name;
    private Double calories;
    private Double fats;
    private Double carbohydrates;
    private Double proteins;

    public BasicFood(String type, String name, Double calories, Double fats, Double carbohydrates, Double proteins){
        this.type = type;
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }

    public Double getCalories(){
        return this.calories;
    }

    public Double getFats(){
        return this.fats;
    }

    public Double getCarbohydrates(){
        return this.carbohydrates;
    }

    public Double getProteins(){
        return this.proteins;
    }

    @Override
    public void showFoodDetails() {
        System.out.println(this.getName() + "(Calories: " + Double.toString(this.getCalories()) + ", Fats: " + Double.toString(this.getFats()) + ", Carbohydrates: " + this.getCarbohydrates() + ", Proteins: " + this.getProteins() + ")");
    }

    public String toString(){
        return this.name;
    }
}
