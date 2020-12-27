package Model;

public class User {
    private String username;
    private String password;
    private String name;
    private int age;
    private Double weight;
    private Double calories;

    public User(String username, String password, String name,int age,Double weight, Double calories){
        this.username = username;
        this.password = password;
        this.age = age;
        this.name = name;
        this.weight = weight;
        this.calories = calories;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return this.age;
    }

    public String getPassword() {
        return password;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getCalories() {
        return calories;
    }

    public void setUsernameName(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }
}