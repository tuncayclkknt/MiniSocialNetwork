import java.util.*;

public class Network{

    private Hashtable<Integer,String> users;

    private Hashtable<Integer, List<Integer>> friendships;

    private PriorityQueue<Post> newFeed;

    private PriorityQueue<Activity> activities;

    public Network() {
        this.users = new Hashtable<>();
        this.friendships = new Hashtable<>();

        this.newFeed = new PriorityQueue<>();
        this.activities = new PriorityQueue<>();
    }

    //Exceptions handled, add user if it is not existing.
    public void addUser(User user){
        if (!users.containsKey(user.getId())){
            users.put(user.getId(),user.getName()); //add it general user space.
            friendships.put(user.getId(), new ArrayList<>()); //add it relational part.

            activities.add(new Activity(user.getId(), "User created."));
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

        activities.add(new Activity(user.getId(), "User deleted."));
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
                activities.add(new Activity(senderUser.getId(), String.format("Sent friend req. to %s.",receiverUser.getName())));
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
            activities.add(new Activity(user.getId(), String.format("%s accepted the friend req.",other.getName())));

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
        activities.add(new Activity(user.getId(), String.format("Removed %s from his/her friends.",other.getName())));
    }

    public void removeFriendFromFriendRequestList(User user, User other){ //deny the request
        user.getFriendRequests().remove(other); //not need to apply to graph because it did not assign a relation.
        activities.add(new Activity(user.getId(), String.format("Deny the req. from %s.",other.getName())));
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
        activities.add(new Activity(user.getId(), String.format("Search for mutual friend with %s.", other.getName())));

        return mutualFriends;
    }

    public void suggestFriend(User user) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        List<Integer> suggestedFriends = new ArrayList<>();

        visited.add(user.getId());
        queue.add(user.getId());

        System.out.printf("Searching for user: %s, id: %d.%n", user.getName(), user.getId());

        while (!queue.isEmpty()) {
            int currentUserId = queue.poll();
            System.out.println("Current user id: " + currentUserId);

            for (int friend : friendships.get(currentUserId)) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    if (currentUserId != user.getId()) {
                        suggestedFriends.add(friend);
                    } else {
                        queue.add(friend);
                    }
                }
            }
        }

        System.out.println("Suggested friends: " + suggestedFriends);
    }

    public void sharePost(User user, String content){
        Post post = new Post(user.getId(),content);
        user.getPosts().add(post);

        newFeed.add(post);
        activities.add(new Activity(user.getId(), "User shared a post."));
    }

    public void printFriendships() {
        for (int user : friendships.keySet()) {
            System.out.println(user + " -> " + friendships.get(user)); //print user and his/her friends.
        }
    }

    public void getNewFeed() {
        for (Post post: newFeed){
            System.out.printf("%d - %s %s%n",post.getUserId(),post.getContent(),post.getDateAsString());
        }
    }

    public void getActivities(){
        for (Activity act: activities){
            System.out.printf("%d - %s - %s%n",act.getUserId(),act.getDescription(),act.getDateAsString());
        }
    }

    // **** I will try to do it using binary search ****
    // searchById
    public boolean searchByKey(int key) {
        return users.containsKey(key);
    }

    // searchByName
    public boolean searchByValue(String value) {
        return users.containsValue(value);
    }
}