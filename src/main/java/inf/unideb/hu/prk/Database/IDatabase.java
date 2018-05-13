package inf.unideb.hu.prk.Database;

import inf.unideb.hu.prk.Model.Time;

import java.util.List;

/**
 * Represents the database interface
 */
public interface IDatabase {
    //CHECKSTYLE:OFF
    public void load();
    public void save();
    public void insertDBTime(Time time);
    public List<Time> getDatabaseListCurrentMonth();
    public String formatCSV();
    public String totalCurrentMonth();
    public List<Time> getList();
}