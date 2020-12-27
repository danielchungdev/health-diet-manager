package Model;

import java.io.IOException;

public interface Database {

    public void readData() throws IOException;

    public void saveData() throws IOException;
}
