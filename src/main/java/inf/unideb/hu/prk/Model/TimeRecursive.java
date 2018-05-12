package inf.unideb.hu.prk.Model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeRecursive {

    private LocalDateTime Start;
    private LocalDateTime End;
    private String Comment = "";

    public TimeRecursive() { }

    public TimeRecursive(LocalDateTime start, LocalDateTime end, String comment) {
        Start = start;
        End = end;
        this.Comment = comment;
    }

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

    public LocalDateTime getStart() { return Start; }
    public void setStart(LocalDateTime start) { Start = start; }
    public LocalDateTime getEnd() { return End; }
    public void setEnd(LocalDateTime end) { End = end; }
    public void setComment(String comment) { Comment = comment; }
    public String getComment() { return Comment; }
    public long getDuration(){ return Duration.between(Start, End).toMillis(); }

}
