import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        return LocalDateTime.now().getHour() + "." + LocalDateTime.now().getMinute() + " " +
                LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getYear();
    }

    @Override
    public int compareTo(Post o) {
        return o.date.compareTo(this.date);
    }
}
