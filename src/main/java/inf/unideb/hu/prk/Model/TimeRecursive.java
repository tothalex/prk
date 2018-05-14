package inf.unideb.hu.prk.Model;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Class represeting the start time, end time, comments between the two time for the tableview(the comments are splitted)
 */
public class TimeRecursive {

    //CHECKSTYLE:OFF
    private LocalDateTime Start;
    private LocalDateTime End;
    private String Comment = "";

    //CHECKSTYLE:ON
    public TimeRecursive() { }

    /**
     * Constructs Time object
     * @param start represents the start date
     * @param end represents the end date
     * @param comment represents the comments
     */
    public TimeRecursive(LocalDateTime start, LocalDateTime end, String comment) {
        Start = start;
        End = end;
        this.Comment = comment;
    }

    /**
     * Convert the {@link Time} to TimeRecursive
     * @param time {@link Time}
     */
    public TimeRecursive(Time time){
        this.Start = time.getStart();
        this.End = time.getEnd();

        String com = time.getComment();
        if (com.length() > 30){
            com.replace('\n',';');
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<com.length(); i+= 20){
                if ( i + 20 > com.length() ){
                    builder.append(com.substring(i));
                    break;
                }else {
                    builder.append(com.substring(i, i+20) + "-");
                    builder.append("\n");
                }
            }
            com = builder.toString();
        }
        this.Comment = com;
    }
    //CHECKSTYLE:OFF
    public LocalDateTime getStart() { return Start; }
    public void setStart(LocalDateTime start) { Start = start; }
    public LocalDateTime getEnd() { return End; }
    public void setEnd(LocalDateTime end) { End = end; }
    public void setComment(String comment) { Comment = comment; }
    public String getComment() { return Comment; }
    public long getDuration(){ return Duration.between(Start, End).toMillis(); }

}
