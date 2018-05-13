package inf.unideb.hu.prk.Model;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TimeTest {

    @Test
    public void getDuration() {
        Time time = new Time();
        time.setStart(LocalDateTime.now());
        time.setEnd(LocalDateTime.now().plusMinutes(30));
        time.setComment("asd");
        if (time.getDuration() != Duration.between(time.getStart(), time.getEnd()).toMillis()) fail();
    }

    @Test
    public void getDuration1(){
        Time time1 = new Time(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "asd");
        if ( time1.getDuration() <= 86400000 && time1.getDuration() >= 86400005) fail();
    }

}