package View;
import Controller.UserController;
import java.io.IOException;
import java.util.Scanner;

public class CLI {

    public CLI() throws IOException {
        UserController userController = new UserController();
        while (true){
            Scanner sysIn = new Scanner(System.in);
            userController.displayInstructions();
            String userCommand = sysIn.nextLine();
            switch (userCommand){
                case"1":
                    userController.recipesOption();
                    break;
                case"2":
                    userController.entryLogOption();
                    break;
                case"3":
                    userController.exerciseOptions();
                    break;
                case"4":
                    userController.userOption();
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        CLI cli = new CLI();
    }
}
