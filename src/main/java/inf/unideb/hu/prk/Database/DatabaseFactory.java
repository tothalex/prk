package inf.unideb.hu.prk.Database;

public class DatabaseFactory {

    /**
     * Creates database.
     * @return Returns a database type.
     */
    public static DatabaseJSON get(){
        return new DatabaseJSON("database", true);
    }

}
