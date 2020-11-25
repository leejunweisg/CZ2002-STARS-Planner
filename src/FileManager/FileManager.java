package FileManager;

import java.io.*;

/**
 * This class is in charge of serializing and deserializing the DataContainer object.
 */
public class FileManager {

    /**
     * The default constructor.
     */
    public FileManager() { }

    /**
     * Attempts to deserialize a DataContainer object from a file with a predetermined file name.
     * If the file is not found or inaccessible, a new DataContainer object is instantiated.
     * @return Returns a DataContainer object.
     */
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

    /**
     * Serializes a DataContainer object to a file with a predetermined file name.
     * @param dc The DataContainer object to serialize.
     */
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