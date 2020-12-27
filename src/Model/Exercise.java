package Model;

public class Exercise {
    private String name;
    private Double caloriesBurnt;
    
    public Exercise(String name, Double caloriesBurnt){
        this.name = name;
        this.caloriesBurnt = caloriesBurnt;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCaloriesBurnt(Double caloriesBurnt){
        this.caloriesBurnt = caloriesBurnt;
    }

    public String getName(){
        return this.name;
    }

    public Double getCaloriesBurnt(){
        return this.caloriesBurnt;
    }

    public String toString(){
        return this.name;
    }
}
