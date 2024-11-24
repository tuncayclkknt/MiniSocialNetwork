import java.util.ArrayList;
import java.util.List;

public class User {
    private static int nextId = 1;
    private int id;

    private String name;
    private List<User> friendRequests;
    private List<User> friends;

    public User(String name) {
        this.name = name;
        this.id = nextId++;

        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }

    public void removeFriendFromFriendList(User user){
        friends.remove(user); //not applied to graph
    }

    public void removeFriendFromFriendRequestList(User user){
        friendRequests.remove(user); //not need to apply to graph
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

}
