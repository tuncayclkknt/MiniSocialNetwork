import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Network {
    private Hashtable<Integer,String> users = new Hashtable<>();
    private Hashtable<Integer, List<Integer>> friendships = new Hashtable<>();

    public Network() {
        this.users = new Hashtable<>();
        this.friendships = new Hashtable<>();

    }

    public void addUser(User user){
        if (!users.containsKey(user.getId())){
            users.put(user.getId(),user.getName());
            friendships.put(user.getId(), new ArrayList<>());
        }
    }

    public void removeUser(User user){
        if (!friendships.containsKey(user.getId())) {
            System.out.printf("User %s cannot find.%n",user.getName());
            return;
        }

        for (Integer friend : friendships.get(user.getId())) {
            friendships.get(friend).remove(Integer.valueOf(user.getId()));
        }
        users.remove(user.getId());
        friendships.remove(user.getId());
    }

    public void sendFriendRequest(User senderUser, User reciverUser){

        if (senderUser.getFriends().contains(reciverUser)){
            System.out.printf("User %s has already your friend.%n",reciverUser);
            return;
        }

        if (!reciverUser.getFriendRequests().contains(senderUser)){
            reciverUser.getFriendRequests().add(senderUser); //when you send a request, other user can see you on his/her list
            System.out.println("Friend request sent.");
        }
    }
}
