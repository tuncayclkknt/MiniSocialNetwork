public class Main {
    public static void main(String[] args) {

        User u1 = new User("Tuncay");
        User u2 = new User("Zeynep");
        User u3 = new User("veli");
        User u4 = new User("enes");
        User u5 = new User("sena");

        Network network = new Network();

        network.addUser(u1);
        network.addUser(u2);
        network.addUser(u3);
        network.addUser(u4);
        network.addUser(u5);

//        network.removeUser(u1);
//        network.removeUser(u4);
//        network.removeUser(u5);

        network.sendFriendRequest(u1,u2);
        network.acceptFriendRequest(u2,u1);

        network.sendFriendRequest(u3,u1);
        network.acceptFriendRequest(u1,u3);

        network.sendFriendRequest(u2,u5);
        network.acceptFriendRequest(u5,u2);

        network.sendFriendRequest(u4,u2);
        network.removeFriendFromFriendRequestList(u2,u4);

        network.sendFriendRequest(u5,u3);
        network.acceptFriendRequest(u3,u5);

        network.sendFriendRequest(u4,u5);
        network.acceptFriendRequest(u5,u4);
//---------------------------------------------
////        network.acceptFriendRequest(u5,u3);
//        network.sendFriendRequest(u5,u4);
//
////        network.sendFriendRequest(u4,u3);
////        network.sendFriendRequest(u4,u3);
//        network.acceptFriendRequest(u4,u1);
//        network.acceptFriendRequest(u5,u3);
//
//        network.removeFriendFromFriendList(u5,u2);
//        network.removeFriendFromFriendList(u5,u2);

        //for see list as a name
//        System.out.println("Friend u2 Reqs;");
//        for (User user: u2.getFriendRequests()){
//            System.out.print(user.getName() + " ");
//        }
//        System.out.println();
//
//        System.out.println("Friend u2 Friends;");
//        for (User user: u2.getFriends()){
//            System.out.print(user.getName() + " ");
//        }
//        System.out.println();

        network.printFriendships();

        System.out.println(network.findMutualFriends(u5,u1));

        //System.out.println(network.getFriendships().get(u5.getId()));

        System.out.println("--------------------------------------------");
        Post post = new Post(u1.getId(),"Hello");

        System.out.println(post.getDate());
        System.out.println(post.getDateAsString());

        System.out.println("--------------------------------------------");

        network.sharePost(u1,"Hello");
        network.sharePost(u2,"Hi!");
        network.sharePost(u1,"world");
        network.sharePost(u5,"car");

        u1.getPostAsString();
        u2.getPostAsString();
        System.out.println("---------------");
        network.getNewFeed();
        System.out.println("---------------");
        network.getActivities();

//        System.out.println(network.searchUser(1));
//        System.out.println(network.searchUser("Zeynep"));


    }
}