package inf.unideb.hu.prk.Database;

import org.pmw.tinylog.Logger;

import java.io.File;

public class DatabaseFactory {

    /**
     * Creates database.
     * @return Returns a database type.
     */
    public static DatabaseJSON get(){
        String path = System.getProperty("user.home") + "/DatabaseBooker";
        if (!new File(path).exists()) new File(path).mkdir();
        return new DatabaseJSON(path + "/database", true);
    }

}
