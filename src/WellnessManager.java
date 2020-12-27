import Controller.Controller;
import Model.Model;
import View.View;

import java.io.IOException;

public class WellnessManager {
    public static void main(String[] args) throws IOException {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
    }
}
