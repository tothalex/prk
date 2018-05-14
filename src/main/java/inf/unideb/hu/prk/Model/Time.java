package inf.unideb.hu.prk.Model;

import java.beans.Transient;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class represeting the start time, end time, comments between the two time
 */
public class Time {

    //CHECKSTYLE:OFF
    private LocalDateTime Start;
    private LocalDateTime End;
    private String Comment = "";

    //CHECKSTYLE:ON
    public Time() { }

    /**
     * Constructs Time object
     * @param start represents the start date
     * @param end represents the end date
     * @param comment represents the comments
     */
    public Time(LocalDateTime start, LocalDateTime end, String comment) {
        Start = start;
        End = end;
        this.Comment = comment;
    }

    //CHECKSTYLE:OFF
    public LocalDateTime getStart() { return Start; }
    public void setStart(LocalDateTime start) { Start = start; }
    public LocalDateTime getEnd() { return End; }
    public void setEnd(LocalDateTime end) { End = end; }
    public void setComment(String comment) { Comment = comment; }
    public String getComment() { return Comment; }
    //CHECKSTYLE:ON

    /**
     * @return Return the duration between the end and the start date in double.
     */
    @Transient
    public long getDuration(){ return Duration.between(Start, End).toMillis(); }

    //CHECKSTYLE:OFF
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return Start.equals(time.getStart()) &&
                End.equals(time.getEnd()) &&
                Comment.equals(time.getComment());
    }
    //CHECKSTYLE:OFF
    @Override
    public String toString() {
        return "Time{" +
                "Start=" + Start +
                ", End=" + End +
                ", Comment='" + Comment + '\'' +
                '}';
    }
}
