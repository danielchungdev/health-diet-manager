package Model;

import java.io.IOException;

public class Model {
    public static Object UserDatabase;
    private ExerciseDatabase exerciseDatabase = new ExerciseDatabase();
    private FoodDatabase foodDatabase = new FoodDatabase();
    private LogDatabase logDatabase = new LogDatabase();
    private UserDatabase userDatabase = new UserDatabase();


    public Model() throws IOException {
    }

    public ExerciseDatabase getExerciseDatabase(){
        return this.exerciseDatabase;
    }

    public FoodDatabase getFoodDatabase() {
        return this.foodDatabase;
    }

    public LogDatabase getLogDatabase() {
        return logDatabase;
    }

    public UserDatabase getUserDatabase(){
        return userDatabase;
    }
}
