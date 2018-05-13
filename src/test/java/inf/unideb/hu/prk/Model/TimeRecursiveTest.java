package inf.unideb.hu.prk.Model;

import org.junit.Test;
import org.pmw.tinylog.Logger;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TimeRecursiveTest {

    @Test
    public void getDuration() {
        TimeRecursive time = new TimeRecursive();
        time.setStart(LocalDateTime.now());
        time.setEnd(LocalDateTime.now().plusMinutes(30));
        time.setComment("asd");
        if (time.getDuration() != Duration.between(time.getStart(), time.getEnd()).toMillis()) fail();
    }

    @Test
    public void getDuration2(){
        TimeRecursive time1 = new TimeRecursive(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "asd");
        Logger.info(time1.getDuration());
        if ( time1.getDuration() <= 86400000 && time1.getDuration() >= 86400005) fail();
    }

    @Test
    public void constructor(){
        Time time = new Time(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), "1111111111111111111111111111111111111");
        TimeRecursive time1 = new TimeRecursive(time);
        String eq = "11111111111111111111-" + '\n' + "11111111111111111";
        if (!time1.getComment().equals(eq)) fail();
    }

    @Test
    public void constructorFalse(){
        Time time = new Time(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), "asd");
        TimeRecursive time1 = new TimeRecursive(time);
        if (!time1.getComment().equals("asd"));
    }


}