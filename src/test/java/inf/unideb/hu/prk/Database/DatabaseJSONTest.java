package inf.unideb.hu.prk.Database;

import com.google.gson.Gson;
import inf.unideb.hu.prk.Model.Time;
import org.junit.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseJSONTest {
       private static String path = "src/test/";
    private static File file;
    private static Gson GSON = new Gson();
    private static List<Time> timeList;

    @BeforeClass
    public static void setUpClass() throws Exception {
        file = new File(path + "test" + ".json");
        file.createNewFile();
        Writer writer = new FileWriter(file);
        LocalDateTime now = LocalDateTime.now();
        timeList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            now.plusDays(i);
            Time time = new Time(now, now.plusMinutes(i), Integer.toString(i));
            timeList.add(time);
        }
        writer.write(GSON.toJson(timeList));
        writer.close();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        file.delete();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private static boolean testLists(List<Time> list1, List<Time> list2) {
        for (Time t1 : list1){
            boolean ok = false;
            for (Time t2 : list2){
                if (t1.equals(t2)){
                    ok = true;
                    break;
                }
            }
            if (!ok) return false;
        }
        return true;
    }

    @Test
    public void csv() {
        LocalDateTime now = LocalDateTime.now();
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "csv", false);
        databaseJSON.insertDBTime(new Time(now, now.plusMinutes(30), ""));

        StringBuilder builder = new StringBuilder();
        builder.append("StartDate,EndDate,Comments,Duration (Total: 00:30)");
        builder.append(System.lineSeparator());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        builder.append( "\"" + now.format(formatter) + "\"" );
        builder.append( ",\"" + now.plusMinutes(30).format(formatter) + "\"" );
        builder.append(",\"\"");
        builder.append(",\"00:30\"");
        if (!builder.toString().equals(databaseJSON.formatCSV())) fail();
        File file = new File(path + "csv.json");
        file.delete();
    }
    @Test
    public void csvEmpty(){
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "testEmpty",false);
        databaseJSON.load();
        if (!databaseJSON.formatCSV().equals("")) fail();
        File file1 = new File(path + "testEmpty.json");
        file1.delete();
    }

    @Test
    public void insertNull(){
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "test", false);
        databaseJSON.insertDBTime(new Time());
        if (databaseJSON.getList() == null) fail();
    }

    @Test
    public void fileNotExist(){
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "notexist", false);
        databaseJSON.load();
        File file = new File(path + "notexist.json");
        if(!file.exists()) fail();
        file.delete();
    }

    @Test
    public void testBackup(){
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "test", true);
        File file1 = new File(path + "testold.json");
        if (!file1.exists()) fail();
        file1.delete();
    }

    @Test
    public void load() {
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "test", false);
        databaseJSON.load();
        if (!testLists(databaseJSON.getList(), timeList)) fail();
    }

    @Test
    public void insertDBTimer() {
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "test", false);
        databaseJSON.load();
        Time time = new Time(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), "asd");
        databaseJSON.insertDBTime(time);
        List<Time> temp = new ArrayList<>(timeList);
        temp.add(time);
        if(!testLists(temp, databaseJSON.getList())) fail();
    }

    @Test
    public void getDatabaseListCurrentMonth() {
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "test", false);
        databaseJSON.load();
        Time time = new Time(LocalDateTime.now().minusMonths(2), LocalDateTime.now().minusMonths(2).plusMinutes(30), "asd");
        databaseJSON.insertDBTime(time);
        for (Time t : databaseJSON.getDatabaseListCurrentMonth()){
            if (!t.getStart().getMonth().equals(LocalDateTime.now().getMonth())) fail();
        }
    }

    @Test
    public void totalCurrentMonth() {
        DatabaseJSON databaseJSON = new DatabaseJSON(path + "test", false);
        databaseJSON.load();
        databaseJSON.insertDBTime(new Time(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), "asd"));
        if ( !databaseJSON.totalCurrentMonth().equals("03:40") ) fail();

        databaseJSON.insertDBTime(new Time(LocalDateTime.now(), LocalDateTime.now().plusMinutes(20), "asd"));
        if ( !databaseJSON.totalCurrentMonth().equals("04:00") ) fail();

        databaseJSON.insertDBTime(new Time(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "asd"));
        if ( !databaseJSON.totalCurrentMonth().equals("28:00") ) fail();

        databaseJSON.insertDBTime(new Time(LocalDateTime.now(), LocalDateTime.now().plusYears(1), "asd"));
        if (!databaseJSON.totalCurrentMonth().equals("8788:00")) fail();
    }

}