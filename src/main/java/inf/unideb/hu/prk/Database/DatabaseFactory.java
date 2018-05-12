package inf.unideb.hu.prk.Database;

public class DatabaseFactory {

    public static DatabaseJSON get(){
        return new DatabaseJSON("database", true);
    }

}
