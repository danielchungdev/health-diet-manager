package Model;

import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;

public class LogEntry {
    private String date;
    private Double recordedWeight = 0.0;
    private Double recordedCalories = 0.0;

    private Double caloriesOfEntry = 0.0;
    private Double proteinsOfEntry = 0.0;
    private Double fatsOfEntry = 0.0;
    private Double carbohydratesOfEntry = 0.0;

    private FoodDatabase foodDatabase;
    private ExerciseDatabase exerciseDatabase;
    private ArrayList<Pair> foodsConsumed;
    private ArrayList<Pair> exercisesMade;

    public LogEntry(String year, String month, String day) throws IOException {
        this.date = makeDate(year, month, day);
        this.foodDatabase = new FoodDatabase();
        this.exerciseDatabase = new ExerciseDatabase();
        this.foodsConsumed = new ArrayList<Pair>();
        this.exercisesMade = new ArrayList<Pair>();
    }

    public String getDate() {
        return date;
    }

    public Double getCaloriesOfEntry() {
        return caloriesOfEntry;
    }

    public Double getProteinsOfEntry() {
        return proteinsOfEntry;
    }

    public Double getFatsOfEntry() {
        return fatsOfEntry;
    }

    public Double getCarbohydratesOfEntry() {
        return carbohydratesOfEntry;
    }

    public FoodDatabase getFoodDatabase() {
        return foodDatabase;
    }

    public ExerciseDatabase getExerciseDatabase() {
        return exerciseDatabase;
    }

    public ArrayList<Pair> getExercisesMade() {
        return exercisesMade;
    }

    public void calculateDayNutrients(){
        if (foodsConsumed.size() > 0) {
            for (int i = 0; i < foodsConsumed.size(); i++) {
                Food currentFood = (Food) foodsConsumed.get(i).getKey();
                Double quantity = (Double) foodsConsumed.get(i).getValue();
                this.caloriesOfEntry += currentFood.getCalories() * quantity;
                this.proteinsOfEntry += currentFood.getProteins() * quantity;
                this.fatsOfEntry += currentFood.getFats() * quantity;
                this.carbohydratesOfEntry += currentFood.getCarbohydrates() * quantity;
            }

        }
    }

    public void calculateCalories(Double userWeight){
        for(int i = 0; i < exercisesMade.size(); i++){
            Pair<Exercise, Double> currentExercise = exercisesMade.get(i);
            Double caloriesLost = currentExercise.getKey().getCaloriesBurnt() * (userWeight/100) * (currentExercise.getValue()/60);
            this.caloriesOfEntry -= caloriesLost;
        }
    }

    public void setRecordedWeight(Double recordedWeight) {
        this.recordedWeight = recordedWeight;
    }

    public void setRecordedCalories(Double recordedCalories) {
        this.recordedCalories = recordedCalories;
    }

    public void addExercise(String exercise, Double minutes){
        if (exerciseDatabase.getExerciseDatabase().containsKey(exercise)){
            Pair exerciseAmount = new Pair(exerciseDatabase.getExerciseDatabase().get(exercise), minutes);
            exercisesMade.add(exerciseAmount);
        }
    }

    public void addConsumedFood(String food, Double amount){
        if (foodDatabase.getFoodsDatabase().containsKey(food)){
            Pair foodPair = new Pair(foodDatabase.getFoodsDatabase().get(food), amount);
            Food foodConsumed = foodDatabase.getFoodsDatabase().get(food);
            caloriesOfEntry += foodConsumed.getCalories() * amount;
            fatsOfEntry += foodConsumed.getFats() * amount;
            carbohydratesOfEntry += foodConsumed.getCarbohydrates() * amount;
            proteinsOfEntry += foodConsumed.getProteins() * amount;
            foodsConsumed.add(foodPair);
        }
        else{
            System.out.println("Food was not found in the database!");
        }
    }

    public void removeConsumedFood(String food){
        Food toRemove = foodDatabase.getFoodsDatabase().get(food);
        foodsConsumed.remove(toRemove);
        System.out.println(food + " has been removed from the log!");
    }

    public String makeDate(String year, String month, String day){
        String dayReturn = year + "/" + month + "/" + day;
        return dayReturn;
    }

    public Double getRecordedWeight() {
        return recordedWeight;
    }

    public Double getRecordedCalories() {
        return recordedCalories;
    }

    public ArrayList<Pair> getFoodsConsumed() {
        return foodsConsumed;
    }

    public String getYear(){
        String[] splitDate = this.date.split("/");
        return splitDate[0];
    }

    public String getMonth(){
        String[] splitDate = this.date.split("/");
        return splitDate[1];
    }

    public String getDay(){
        String[] splitDate = this.date.split("/");
        return splitDate[2];
    }

    public String toString(){
        return "Entry Log of: " + this.date + " (Recorded Weight: " + this.getRecordedWeight() + ", Recorded Calories: " + this.getRecordedCalories() + "), Foods Consumed: " + this.getFoodsConsumed() + "\n" +
                "Calories of the day: " + Double.toString(caloriesOfEntry) + " fats of the day: " + Double.toString(fatsOfEntry) + " Carbs of the day: " + Double.toString(carbohydratesOfEntry) + " protein of the day: " + Double.toString(proteinsOfEntry);
    }
}
