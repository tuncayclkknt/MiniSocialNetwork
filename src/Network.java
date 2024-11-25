import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Network {

    private Hashtable<Integer,String> users;
    private Hashtable<Integer, List<Integer>> friendships;

    public Network() {
        this.users = new Hashtable<>();
        this.friendships = new Hashtable<>();
    }

    //Exceptions handled, add user if it is not existing.
    public void addUser(User user){
        if (!users.containsKey(user.getId())){
            users.put(user.getId(),user.getName()); //add it general user space.
            friendships.put(user.getId(), new ArrayList<>()); //add it relational part.
        } else {
            System.out.println("User is already exist!");
        }
    }

    //Exceptions handled, remove user if it is existing.
    public void removeUser(User user){
        if (!friendships.containsKey(user.getId())) { //check if it has relations.
            System.out.printf("User %s cannot find.%n",user.getName());
            return;
        }

        for (Integer friend : friendships.get(user.getId())) {
            friendships.get(friend).remove(Integer.valueOf(user.getId())); //first remove all relations BY-ONE-BY(Zehra)
        }
        friendships.remove(user.getId()); //remove the user from relation part
        users.remove(user.getId()); //remove the user from general users
        System.out.printf("User %s and relations are successfully removed!%n",user.getName());

    }

    //Exceptions handled, send req. to user if it is existing.
    public void sendFriendRequest(User senderUser, User receiverUser){
        if (users.containsKey(senderUser.getId()) && users.containsKey(receiverUser.getId())){//if the user is existing.

            if (senderUser.getFriends().contains(receiverUser)){ //check if the user is my friend.
                System.out.printf("User %s has already your friend.%n",receiverUser.getName());
                return;
            }

            if (!receiverUser.getFriendRequests().contains(senderUser)){ //check sent a req before.
                receiverUser.getFriendRequests().add(senderUser); //when you send a request, other user can see you on his/her list
                System.out.println("Friend request sent.");
            } else{
                System.out.printf("You {%s} have already sent request to {%s}.%n",senderUser.getName(),receiverUser.getName());
            }
        } else {
            System.out.println("User does not exist.");
        }
    }


    //Exceptions handled, accept req. the user if it is existing.
    public void acceptFriendRequest(User user, User other){

        if (user.getFriendRequests().contains(other)){ //check there is a req from the other user.
            user.getFriendRequests().remove(other);
            user.getFriends().add(other); // accept and remove the req

            friendships.putIfAbsent(user.getId(), new ArrayList<>());
            friendships.putIfAbsent(other.getId(), new ArrayList<>()); //add relations between these two users.
            friendships.get(user.getId()).add(other.getId());
            friendships.get(other.getId()).add(user.getId());

        } else{
            System.out.printf("User {%s} cannot find in req list.%n",other.getName());
        }

    }

    public void removeFriendFromFriendList(User user, User other){
        if (!user.getFriends().contains(other)) { //check if it is not my friend.
            System.out.printf("User %s is not your friend.%n",user.getName());
            return;
        }

        friendships.get(other.getId()).remove(Integer.valueOf(user.getId())); //remove the user from the other's relation list.
        friendships.get(user.getId()).remove(Integer.valueOf(other.getId())); //remove the other from the user's relation list.

        user.getFriends().remove(other); // delete oppositely from friend lists.
        other.getFriends().remove(user);
    }

    public void removeFriendFromFriendRequestList(User user, User other){ //deny the request
        user.getFriendRequests().remove(other); //not need to apply to graph because it did not assign a relation.
    }

    public List<Integer> findMutualFriends(User user, User other){
        List<Integer> mutualFriends = new ArrayList<>();
        boolean hasMutualFriends = false;

        if (users.containsKey(user.getId()) && users.containsKey(other.getId())) {
            for (int i : friendships.get(user.getId())) {
                if (friendships.get(other.getId()).contains(i)) {
                    mutualFriends.add(i);
                    hasMutualFriends = true;
                }
            }
            if (!hasMutualFriends) {
                System.out.println("These users have not any mutual friends.");
                return null;
            }
        } else {
            System.out.println("User cannot find.");
        }

        return mutualFriends;
    }

    public void printFriendships() {
        for (int user : friendships.keySet()) {
            System.out.println(user + " -> " + friendships.get(user)); //print user and his/her friends.
        }
    }

    //for testing
//    public Hashtable<Integer, List<Integer>> getFriendships() {
//        return friendships;
//    }

}