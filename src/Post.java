import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post implements Comparable<Post>{

    private int userId;
    private String content;
    private LocalDateTime date;

    public Post(int userId, String content) {
        this.userId = userId;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    //--------
    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public String getDateAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm dd/MM/yyyy");
        return date.format(formatter);
    }

    @Override
    public int compareTo(Post o) {
        return o.date.compareTo(this.date);
    }
}
