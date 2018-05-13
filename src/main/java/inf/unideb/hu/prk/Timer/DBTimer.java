package inf.unideb.hu.prk.Timer;

import inf.unideb.hu.prk.Model.Time;

import java.time.LocalDateTime;

/**
 * Class representing a timer.
 */
public class DBTimer {

    private Time time;

     /**
     * Constructs a {@link Time} and sets it's duration.
     */
    public DBTimer(){
        time = new Time();
    }

    /**
     * Start the timer.
     * @throws Exception if the timer has been already started.
     */
    public void start() throws Exception {
        if (time.getStart() == null) time.setStart(LocalDateTime.now());
        else throw new Exception("DBTimer has been already started!");
    }

    /**
     * Stop the timer
     * @throws Exception if the timer has not been started.
     */
    public void stop() throws Exception {
        if (time.getStart() != null) {
            time.setEnd(LocalDateTime.now());
        }
        else throw new Exception("DBTimer has not been started!");
    }

    public Time getTime(){return time;}

    /**
     * Adds comment to the timer.
     * @param comment represents the string what is up to be inserted to the rest of the comments
     * @throws Exception if the timer has not been started.
     */
    public void addComment(String comment) throws Exception {
        if(time.getStart() != null)
            if(time.getComment().length() > 0)
                time.setComment( time.getComment() + System.lineSeparator() + comment );
            else time.setComment(comment);
        else throw new Exception("DBTimer has not been started!");
    }

    /**
     * Resets the timer.
     */
    public void reset(){this.time.setStart(null); this.time.setComment("");}
    //CHECKSTYLE:OFF
    public String getComments(){ return time.getComment(); }
    //CHECKSTYLE:ON
}
