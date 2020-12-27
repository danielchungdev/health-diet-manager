package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LogDatabase implements Database{
    HashMap<String, LogEntry> logDatabase;

    public LogDatabase(){
        this.logDatabase = new HashMap<String, LogEntry>();
        this.readData();
    }

    public HashMap<String, LogEntry> getLogDatabase(){
        return this.logDatabase;
    }

    public void addEntryLog(String[] date) throws IOException {
        String completeDate = date[0] + "/" + date[1] + "/" + date[2];
        LogEntry logEntry = new LogEntry(date[0], date[1], date[2]);
        logDatabase.put(completeDate, logEntry);
        this.saveData();
    }

    public void readData(){
        try {
            File csvFile = new File("src/data/log.csv");
            Scanner csvReader = new Scanner(csvFile);
            while (csvReader.hasNextLine()) {
                String[] data = csvReader.nextLine().split(",");
                String currentLogDate = data[0] + "/" + data[1] + "/" + data[2];
                if (!logDatabase.containsKey(currentLogDate)){
                    LogEntry logEntry = new LogEntry(data[0], data[1], data[2]);
                    logDatabase.put(currentLogDate, logEntry);
                }
                if (data[3].equals("w")){
                    LogEntry currentEntry = logDatabase.get(currentLogDate);
                    currentEntry.setRecordedWeight(Double.parseDouble(data[4]));
                }
                if (data[3].equals("c")){
                    LogEntry currentEntry = logDatabase.get(currentLogDate);
                    currentEntry.setRecordedCalories(Double.parseDouble(data[4]));
                }
                if (data[3].equals("f")){
                    LogEntry currentEntry = logDatabase.get(currentLogDate);
                    currentEntry.addConsumedFood(data[4], Double.parseDouble(data[5]));
                }
                if(data[3].equals("e")){
                    LogEntry currentEntry = logDatabase.get(currentLogDate);
                    currentEntry.addExercise(data[4],Double.parseDouble(data[5]));
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() throws IOException {
        FileWriter writter = new FileWriter("src/data/savedLog.csv");
        for(Map.Entry entry : logDatabase.entrySet()){
            LogEntry currentEntry = (LogEntry) entry.getValue();
            if (currentEntry.getRecordedWeight() != null) {
                writter.write(currentEntry.getYear());
                writter.write(",");
                writter.write(currentEntry.getMonth());
                writter.write(",");
                writter.write(currentEntry.getDay());
                writter.write(",");
                writter.write("w,");
                writter.write(Double.toString(currentEntry.getRecordedWeight()));
                writter.write("\n");
            }
            if (currentEntry.getRecordedCalories() != null){
                writter.write(currentEntry.getYear());
                writter.write(",");
                writter.write(currentEntry.getMonth());
                writter.write(",");
                writter.write(currentEntry.getDay());
                writter.write(",");
                writter.write("c,");
                writter.write(Double.toString(currentEntry.getRecordedCalories()));
                writter.write("\n");
            }
            for(int i = 0; i < currentEntry.getFoodsConsumed().size(); i++){
                Food foodConsumed = (Food) currentEntry.getFoodsConsumed().get(i).getKey();
                Double amount = (Double) currentEntry.getFoodsConsumed().get(i).getValue();
                writter.write(currentEntry.getYear());
                writter.write(",");
                writter.write(currentEntry.getMonth());
                writter.write(",");
                writter.write(currentEntry.getDay());
                writter.write(",");
                writter.write("f");
                writter.write(",");
                writter.write(foodConsumed.getName());
                writter.write(",");
                writter.write(Double.toString(amount));
                writter.write("\n");
            }
            for(int i = 0; i < currentEntry.getExercisesMade().size(); i++){
                Exercise exerciseMade = (Exercise) currentEntry.getExercisesMade().get(i).getKey();
                Double minutes = (Double) currentEntry.getExercisesMade().get(i).getValue();
                writter.write(currentEntry.getYear());
                writter.write(",");
                writter.write(currentEntry.getMonth());
                writter.write(",");
                writter.write(currentEntry.getDay());
                writter.write(",");
                writter.write("e");
                writter.write(",");
                writter.write(exerciseMade.getName());
                writter.write(",");
                writter.write(Double.toString(minutes));
                writter.write("\n");
            }
        }
        writter.flush();
        writter.close();
    }

    public void modifyWeightForLog(String date, Double weight)throws IOException {
        logDatabase.get(date).setRecordedWeight(weight);
    }

    public void modifyCaloriesForLog(String date, Double calories) throws IOException {
        logDatabase.get(date).setRecordedCalories(calories);
    }

    public void removeLog(String date) throws IOException {
        logDatabase.remove(date);
        this.saveData();
    }

//    Testing
//    public static void main(String[] args) throws IOException {
//        LogDatabase logDatabase = new LogDatabase();
//        logDatabase.display();
//        logDatabase.saveData();
//    }
}
