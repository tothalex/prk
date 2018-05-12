package inf.unideb.hu.prk.Timer;

import inf.unideb.hu.prk.Model.Time;

import java.time.LocalDateTime;

public class DBTimer {

    private Time time;

    public DBTimer(){
        time = new Time();
    }

    public void start() throws Exception {
        if (time.getStart() == null) time.setStart(LocalDateTime.now());
        else throw new Exception("DBTimer has been already started!");
    }

    public void stop() throws Exception {
        if (time.getStart() != null) {
            time.setEnd(LocalDateTime.now());
        }
        else throw new Exception("DBTimer has not been started!");
    }

    public Time getTime(){return time;}
    public void addComment(String comment) throws Exception {
        if(time.getStart() != null)
            if(time.getComment().length() > 0)
                time.setComment( time.getComment() + System.lineSeparator() + comment );
            else time.setComment(comment);
        else throw new Exception("DBTimer has not been started!");
    }

    public void reset(){this.time.setStart(null); this.time.setComment("");}
    public String getComments(){ return time.getComment(); }

}
