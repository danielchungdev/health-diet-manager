package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExerciseDatabase implements Database {

    HashMap<String, Exercise> exerciseDatabase = new HashMap<String, Exercise>();

    public ExerciseDatabase() throws IOException {
        this.readData();
    }

    public void addExercise(String name, Double caloriesBurnt) throws IOException {
        Exercise exercise = new Exercise(name, caloriesBurnt);
        this.exerciseDatabase.put(name, exercise);
        this.saveData();
    }

    public void removeExercise(String name) throws IOException {
        this.exerciseDatabase.remove(name);
        this.saveData();
    }

    @Override
    public void readData() throws IOException {
        try {
            File csvFile = new File("src/data/exercise.csv");
            Scanner csvReader = new Scanner(csvFile);
            while (csvReader.hasNextLine()) {
                String[] data = csvReader.nextLine().split(",");
                Exercise exercise = new Exercise(data[1], Double.parseDouble(data[2]));
                exerciseDatabase.put(data[1], exercise);
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
            FileWriter writter = new FileWriter("src/data/savedExercises.csv");
            for (Map.Entry entry : exerciseDatabase.entrySet()) {
                Exercise currentExercise = (Exercise) entry.getValue();
                writter.write("e");
                writter.write(",");
                writter.write(currentExercise.getName());
                writter.write(",");
                writter.write(Double.toString(currentExercise.getCaloriesBurnt()));
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

    public HashMap<String, Exercise> getExerciseDatabase(){
        return this.exerciseDatabase;
    }

//    public static void main(String[] args) throws IOException {
//        ExerciseDatabase exerciseDatabase = new ExerciseDatabase();
//        exerciseDatabase.display();
//        exerciseDatabase.saveData();
//    }
}