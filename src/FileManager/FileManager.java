package FileManager;

import java.io.*;

public class FileManager {

    public FileManager() {

    }

    public static DataContainer read_all() {
        DataContainer dc;
        try {
            FileInputStream fis = new FileInputStream("dc.dat");
            ObjectInputStream in = new ObjectInputStream(fis);
            dc = (DataContainer) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Failed to read dc");
            System.out.println(ex.getMessage());
            dc = new DataContainer();
            write_all(dc);
        }
        return dc;
    }

    public static void write_all(DataContainer dc) {
        try {
            FileOutputStream fos = new FileOutputStream("dc.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(dc);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}