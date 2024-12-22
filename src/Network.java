import java.util.*;

public class Network{

    private Hashtable<Integer,User> users;

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
            users.put(user.getId(),user); //add it general user space.
            friendships.put(user.getId(), new ArrayList<>()); //add it relational part.

            activities.add(new Activity(user.getId(), "User created."));
        } else {
            System.out.println("\tUser is already exist!");
        }
    }

    //Exceptions handled, remove user if it is existing.
    public void removeUser(User user){
        if (!friendships.containsKey(user.getId())) { //check if it has relations.
            System.out.printf("\tUser %s cannot find.%n",user.getName());
            return;
        }

        for (User friends: user.getFriends()){  //  TEST
            friends.getFriends().remove(user);
        }

        for (Integer friend : friendships.get(user.getId())) {
            friendships.get(friend).remove(Integer.valueOf(user.getId())); //first remove all relations BY-ONE-BY(Zehra)
        }

        friendships.remove(user.getId()); //remove the user from relation part
        users.remove(user.getId()); //remove the user from general users

        activities.add(new Activity(user.getId(), "User deleted."));
        System.out.printf("\tUser %s and relations are successfully removed!%n",user.getName());
    }

    //Exceptions handled, send req. to user if it is existing.
    public void sendFriendRequest(User senderUser, User receiverUser){

        if (senderUser == receiverUser) {
            System.out.println("\tYou cannot send request to yourself.");
            return;
        }
        if (users.containsKey(senderUser.getId()) && users.containsKey(receiverUser.getId())){//if the user is existing.
            if (senderUser.getFriends().contains(receiverUser)){ //check if the user is my friend.
                System.out.printf("\tUser %s has already your friend.%n",receiverUser.getName());
                return;
            }

            if (!receiverUser.getFriendRequests().contains(senderUser)){ //check sent a req before.
                receiverUser.getFriendRequests().add(senderUser); //when you send a request, other user can see you on his/her list

                activities.add(new Activity(senderUser.getId(), String.format("You sent friend request to %s.",receiverUser.getName())));
                activities.add(new Activity(receiverUser.getId(), String.format("You got a friend request from %s.", senderUser.getName())));

                System.out.println("\tFriend request sent.");
            } else{
                System.out.printf("\tYou {%s} have already sent request to {%s}.%n",senderUser.getName(),receiverUser.getName());
            }
        } else {
            System.out.println("\tUser does not exist.");
        }
    }


    //Exceptions handled, accept req. the user if it is existing.
    public void acceptFriendRequest(User user, User other){

        if (user.getFriendRequests().contains(other)){ //check there is a req from the other user.
            user.getFriendRequests().remove(other);
            user.getFriends().add(other); // accept and remove the req
            other.getFriends().add(user);

            friendships.putIfAbsent(user.getId(), new ArrayList<>());
            friendships.putIfAbsent(other.getId(), new ArrayList<>()); //add relations between these two users.
            friendships.get(user.getId()).add(other.getId());
            friendships.get(other.getId()).add(user.getId());

            activities.add(new Activity(user.getId(), String.format("You and %s are friend now. ",other.getName())));
            activities.add(new Activity(other.getId(), String.format("You and %s are friend now. ",user.getName())));

        } else{
            System.out.printf("\tUser {%s} cannot find in req list.%n",other.getName());
        }
    }

    public void removeFriend(User user, User other){
        if (!user.getFriends().contains(other)) { //check if it is not my friend.
            System.out.printf("\tUser %s is not your friend.%n",user.getName());
            return;
        }

        friendships.get(other.getId()).remove(Integer.valueOf(user.getId())); //remove the user from the other's relation list.
        friendships.get(user.getId()).remove(Integer.valueOf(other.getId())); //remove the other from the user's relation list.

        user.getFriends().remove(other); // delete oppositely from friend lists.
        other.getFriends().remove(user);

        activities.add(new Activity(user.getId(), String.format("You and %s are not friend anymore.",other.getName())));
        activities.add(new Activity(other.getId(), String.format("You and %s are not friend anymore.",user.getName())));
    }

    public void denyFriendRequest(User user, User other){ //deny the request
        if (user.getFriendRequests().contains(other)) {
            user.getFriendRequests().remove(other); //not need to apply to graph because it did not assign a relation.

            activities.add(new Activity(user.getId(), String.format("You denied the friend request from %s.", other.getName())));
            activities.add(new Activity(other.getId(), String.format("Your friend request to %s is denied.", user.getName())));
        } else {
            System.out.println("\tThere is no any request from this user.");
        }
    }

    public void findMutualFriends(User user, User other){

        List<Integer> mutualFriends = new ArrayList<>();
        boolean hasMutualFriends = false;

        if (user == other){
            System.out.println("\tDo not try to find a bug!");
            return;
        }

        if (!user.getFriends().contains(other)){
            System.out.println("\tThis user is not your friend!");
            return;
        }

        if (users.containsKey(user.getId()) && users.containsKey(other.getId())) {

            for (int i : friendships.get(user.getId())) {
                if (friendships.get(other.getId()).contains(i)) {
                    mutualFriends.add(i);
                    hasMutualFriends = true;
                }
            }
            if (!hasMutualFriends) {
                System.out.println("\tThese users have not any mutual friends.");
                return;
            }
        } else {
            System.out.println("\tUser cannot find.");
        }

        activities.add(new Activity(user.getId(), String.format("You searched for mutual friends between you and %s.", other.getName())));

        for (int mutualId: mutualFriends){
            System.out.printf("\tId: %d - Name: %s%n",mutualId,users.get(mutualId).getName());
        }

    }

    public void suggestFriend(User user) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        List<Integer> suggestedFriends = new ArrayList<>();

        visited.add(user.getId());
        queue.add(user.getId());

//        System.out.printf("Searching for user: %s, id: %d.%n", user.getName(), user.getId());

        while (!queue.isEmpty()) {
            int currentUserId = queue.poll();
//            System.out.println("Current user id: " + currentUserId);

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

//        System.out.println("Suggested friends: " + suggestedFriends);

        for (int userId: suggestedFriends){
            System.out.printf("\tId: %d - Name: %s%n",userId,users.get(userId).getName());
        }
    }

    public void sharePost(User user, String content){
        Post post = new Post(user.getId(),content);
        user.getPosts().add(post);

        newFeed.add(post);
        activities.add(new Activity(user.getId(), "You shared a post."));

        System.out.printf("\tUser Id: %d %n\tContent: %s %n\tTime: %s%n",post.getUserId(),post.getContent(),post.getDateAsString());
    }

    //test
    public void printFriendships() {
        for (int user : friendships.keySet()) {
            System.out.println(user + " -> " + friendships.get(user)); //print user and his/her friends.
        }
    }

    public void getNewFeed() {
        System.out.println();
        for (Post post: newFeed){
            System.out.printf("\tUser Id: %d %n\tContent: %s %n\tTime: %s%n%n",post.getUserId(),post.getContent(),post.getDateAsString());
        }
    }

    public PriorityQueue<Activity> getActivities(){
//        for (Activity act: activities){
//            System.out.printf("\t%d - %s - %s%n",act.getUserId(),act.getDescription(),act.getDateAsString());
//        }

        return activities;
    }

    // **** I will try to do it using binary search ****
    // searchById
    public int searchById(int key) {
        if (users.containsKey(key))
            return key;
        else{
//            System.out.println("Nobody has this id.");
            return -1;
        }
    }

    // searchByName
    public int searchByName(String name) {

        for (Integer userId : users.keySet()) {
            User user = users.get(userId);
            if (user.getName().equalsIgnoreCase(name)) {
                return userId;
            }
        }
        return -1;
    }


    public Hashtable<Integer, User> getUsers() {
        return users;
    }
}