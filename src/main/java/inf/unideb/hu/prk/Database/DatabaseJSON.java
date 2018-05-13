package inf.unideb.hu.prk.Database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import inf.unideb.hu.prk.Model.Time;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents JSON type local database
 */
public class DatabaseJSON implements IDatabase{

    //CHECKSTYLE:OFF
    private static Gson GSON = new GsonBuilder().create();
    private String FILE_NAME ;
    private List<Time> times = null;
    //CHECKSTYLE:ON

    /**
     * Construct a JSON database object.
     * @param fileName represents the file name of the local file.
     * @param backup if the value is true creates a backup file.
     */
    public DatabaseJSON(String fileName, boolean backup){
        FILE_NAME = fileName + ".json";
        File file = new File(FILE_NAME);
        if(file.exists() && backup) {
            File fileOld = new File(fileName + "old.json");
            try {
                FileUtils.copyFile(file,fileOld);
            } catch (IOException e) {
                Logger.error(e);
                return;
            }
        }
    }

    /**
     * Loads the file content in memory.
     */
    @Override
    public void load(){
        File file = new File(FILE_NAME );
        if (!file.exists()){
            try {
                file.createNewFile();
                times = new ArrayList<>();
                Logger.info(FILE_NAME + " file has been created!");
            } catch (IOException e) {
                Logger.error(e.getMessage());
                return;
            }
        }else{
            try(Reader reader = new FileReader(file)){
                Type type = new TypeToken<ArrayList<Time>>(){}.getType();
                times = GSON.fromJson(reader, type);
                Logger.info("File has been read!");
            }catch (Exception e){
                Logger.error(e);
                times = new ArrayList<>();
                Logger.info("New list been created!");
                return;
            }
        }
    }

    /**
     * Saves the memory content in file.
     */
    @Override
    public void save(){
        try(FileWriter fileWriter = new FileWriter(FILE_NAME )){
            GSON.toJson(times, fileWriter);
            Logger.info("File has been updated!");
        }catch (Exception e){
            Logger.error(e);
            return;
        }
    }

    /**
     * Insert {@link Time} object in memory.
     * @param time {@link Time}
     */
    @Override
    public void insertDBTime(Time time){
        if (time != null)
        if (times != null){
            times.add(time);
        }else {
            Logger.error("List value is null!");
            times = new ArrayList<>();
            times.add(time);
        };
    }

    /**
     * Get the current month data from the database.
     * @return List which contains {@link Time} objects.
     */
    @Override
    public List<Time> getDatabaseListCurrentMonth(){
        return times.stream()
                .filter(x -> x.getStart().getMonth().equals(LocalDateTime.now().getMonth()))
                .collect(Collectors.toList());
    }

    private String quote(String s){ return "\"" + s.replace('\n', ';') + "\""; }
    private String formatDuration(long d) { return DurationFormatUtils.formatDuration(d, "HH:mm"); }

    /**
     * Get the current month total worked hours.
     * @return '00h:00m' format string.
     */
    @Override
    public String totalCurrentMonth(){
        return formatDuration(times.stream()
                .filter(x -> x.getEnd().getMonth().equals(LocalDateTime.now().getMonth()))
                .mapToLong(x -> x.getDuration())
                .sum());
    }

    /**
     * Convert the current month data to csv.
     * @return CSV String
     */
    @Override
    public String formatCSV(){
        if(times.isEmpty()) return "";

        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        stringBuilder.append("StartDate");
        stringBuilder.append(",EndDate");
        stringBuilder.append(",Comments");
        stringBuilder.append(",Duration (Total: " + totalCurrentMonth() + ")");

        times.forEach( x -> {
            stringBuilder.append(System.lineSeparator());
            if (x.getStart().getMonth().equals(LocalDateTime.now().getMonth()))
            stringBuilder.append( quote(x.getStart().format(formatter))
                          + "," + quote(x.getEnd().format(formatter))
                          + "," + quote(x.getComment())
                          + "," + quote(formatDuration(x.getDuration()))
            );
        });

        return stringBuilder.toString();
    }

    /**
     * Return the data from the memory.
     * @return List
     */
    @Override
    public List<Time> getList(){ return times;}

}
