import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    private static int nextId = 1;
    private int id;

    private String name;
    private List<User> friendRequests;
    private List<User> friends;

    private List<Post> posts;

    public User(String name) {
        this.name = name;
        this.id = nextId++;

        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.posts = new LinkedList<>();
    }

    public void getPostAsString(){
        for (Post post: posts){
            System.out.printf("%d - %s - %s%n",post.getUserId(),post.getContent(),post.getDateAsString());
        }
    }

    //------------------

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getFriendRequests() {
        return friendRequests;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
