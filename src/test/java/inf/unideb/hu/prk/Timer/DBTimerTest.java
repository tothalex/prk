package inf.unideb.hu.prk.Timer;

import inf.unideb.hu.prk.Model.Time;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DBTimerTest {

    @Test(expected = Exception.class)
    public void start() throws Exception {
        DBTimer timer = new DBTimer();
        timer.start();
        timer.start();
    }

    @Test(expected = Exception.class)
    public void stop() throws Exception {
        DBTimer timer = new DBTimer();
        timer.stop();
    }

    @Test
    public void getTime() throws Exception {
        Time time = new Time(LocalDateTime.now(), LocalDateTime.now(), "");
        DBTimer dbTimer = new DBTimer();
        dbTimer.start();
        dbTimer.stop();
        if (!(time.getDuration() == dbTimer.getTime().getDuration())) fail();
    }

    @Test(expected = Exception.class)
    public void addCommentEx() throws Exception {
        DBTimer dbTimer = new DBTimer();
        dbTimer.addComment("");
    }

    @Test
    public void addComment() throws Exception {
        DBTimer dbTimer = new DBTimer();
        dbTimer.start();
        dbTimer.addComment("asd");
        if (!dbTimer.getComments().equals("asd")) fail();

        dbTimer.addComment("asd");
        if (!dbTimer.getComments().equals("asd\nasd")) fail();

    }

    @Test
    public void reset() throws Exception {
        DBTimer dbTimer = new DBTimer();
        dbTimer.start();
        dbTimer.stop();
        dbTimer.reset();
        if (dbTimer.getTime().getStart() != null) fail();
        if (!dbTimer.getComments().equals("")) fail();
    }

}