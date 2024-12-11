import java.time.LocalDateTime;

public class Activity implements Comparable<Activity> {
    private int userId;
    private LocalDateTime date;
    private String description;

    public Activity(int userId, String description) {
        this.userId = userId;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public String getDateAsString(){
        return LocalDateTime.now().getHour() + "." + LocalDateTime.now().getMinute() + " " +
                LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getYear();
    }

    //------
    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Activity o) {
        return o.date.compareTo(this.date);
    }
}
