import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static int loggedInUser;

    public static void main(String[] args){

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

        loggedInUser = u1.getId();

////        network.removeUser(u1);
////        network.removeUser(u4);
////        network.removeUser(u5);
//
        network.sendFriendRequest(u1,u2);
        network.acceptFriendRequest(u2,u1);
//
        network.sendFriendRequest(u3,u1);
        network.acceptFriendRequest(u1,u3);

        network.sendFriendRequest(u3,u2);
        network.acceptFriendRequest(u2,u3);

        network.sendFriendRequest(u2,u5);
        network.acceptFriendRequest(u5,u2);


        network.sendFriendRequest(u5,u3);
        network.acceptFriendRequest(u3,u5);

        network.sendFriendRequest(u4,u5);
        network.acceptFriendRequest(u5,u4);
////---------------------------------------------
//////        network.acceptFriendRequest(u5,u3);
////        network.sendFriendRequest(u5,u4);
////
//////        network.sendFriendRequest(u4,u3);
//////        network.sendFriendRequest(u4,u3);
////        network.acceptFriendRequest(u4,u1);
////        network.acceptFriendRequest(u5,u3);
////
////        network.removeFriendFromFriendList(u5,u2);
////        network.removeFriendFromFriendList(u5,u2);
//
//        //for see list as a name
////        System.out.println("Friend u2 Reqs;");
////        for (User user: u2.getFriendRequests()){
////            System.out.print(user.getName() + " ");
////        }
////        System.out.println();
////
////        System.out.println("Friend u2 Friends;");
////        for (User user: u2.getFriends()){
////            System.out.print(user.getName() + " ");
////        }
////        System.out.println();
//
//        network.printFriendships();
//
//        System.out.println(network.findMutualFriends(u5,u1));
//
//        //System.out.println(network.getFriendships().get(u5.getId()));
//
//        System.out.println("--------------------------------------------");
//        Post post = new Post(u1.getId(),"Hello");
//
//        System.out.println(post.getDate());
//        System.out.println(post.getDateAsString());
//
//        System.out.println("--------------------------------------------");
//
//        network.sharePost(u1,"Hello");
//        network.sharePost(u2,"Hi!");
//        network.sharePost(u1,"world");
//        network.sharePost(u5,"car");
//
//        u1.getPostAsString();
//        u2.getPostAsString();
//        System.out.println("---------------");
//        network.getNewFeed();
//        System.out.println("---------------");
//        network.getActivities();
//
////        System.out.println(network.searchUser(1));
////        System.out.println(network.searchUser("Zeynep"));
//        System.out.println("---------------");
//        network.suggestFriend(u3);

//--------------------------------------------------------------------------------------------------------------------------------


//-------------------------------------------------------------------------------------------------------------------------
        while(true){
            System.out.print("~ ");
            String command = scanner.nextLine().trim();

            String[] parts = command.split(" ", 2);
            String mainCommand = parts[0]; //.toLowerCase();
            String parameter = "";

            if (parts.length > 1) {
                parameter = parts[1];
            }

            if (mainCommand.startsWith("switch")){

                int temp = loggedInUser;

                try {
                    loggedInUser = Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> switch <user id>");
                    continue;
                }

                if (network.getUsers().containsKey(loggedInUser))
                    System.out.println("\tCurrent user: " + network.getUsers().get(loggedInUser).getName());
                else {
                    System.out.println("\tUser cannot found!");
                    loggedInUser = temp;
                }
            }
            else if(mainCommand.startsWith("whoami")){
                System.out.printf("\tId: %d%n\tName: %s%n",loggedInUser,network.getUsers().get(loggedInUser).getName());
            }
            else if (mainCommand.startsWith("useradd")) {
                if (!parameter.isEmpty()){
                    network.addUser(new User(parameter));
                } else {
                    System.out.println("\tUsage -> useradd <name>");
                }
            }
            else if (mainCommand.startsWith("userremove")) {

                try {
                    Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> userremove <user id>");
                    continue;
                }

                if (network.getUsers().containsKey(Integer.parseInt(parameter))) {
                    network.removeUser(network.getUsers().get(Integer.parseInt(parameter)));
                    loggedInUser = 1;
                }

            }
            else if(mainCommand.startsWith("sendFriReq")){

                try {
                     Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> sendFriReq <user id>");
                    continue;
                }

                    network.sendFriendRequest(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));

//                if (network.getUsers().containsKey(loggedInUser))
//                    network.sendFriendRequest(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));
//                else {
//                    System.out.println("User cannot found!");
//                }

            }
            else if (mainCommand.startsWith("actFriReq")){

                try {
                    Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> actFriReq <user id>");
                    continue;
                }

                    network.acceptFriendRequest(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));

//                if (network.getUsers().get(loggedInUser).getFriendRequests().contains(network.getUsers().get(Integer.parseInt(parameter)))){
//                    network.acceptFriendRequest(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));
//                } else {
//                    System.out.println("Request not found!");
//                }
            }
            else if (mainCommand.startsWith("denyFriReq")){
                try {
                    Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> denyFriReq; <user id>");
                    continue;
                }

                network.denyFriendRequest(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));

//                if (network.getUsers().get(loggedInUser).getFriendRequests().contains(network.getUsers().get(Integer.parseInt(parameter)))){
//                    network.denyFriendRequest(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));
//                }
            }
            else if (mainCommand.startsWith("removeFri")){

                try {
                    Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> removeFri <user id>");
                    continue;
                }

                network.removeFriend(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));

//                if (network.getUsers().get(loggedInUser).getFriends().contains(network.getUsers().get(Integer.parseInt(parameter)))){
//                    network.removeFriend(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));
//                } else {
//                    System.out.println("Friend not found!");
//                }

            }
            else if (mainCommand.startsWith("findMutuals")){

                try {
                    Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> findMutuals <user id>");
                    continue;
                }

                network.findMutualFriends(network.getUsers().get(loggedInUser),network.getUsers().get(Integer.parseInt(parameter)));
            }

            else if (mainCommand.startsWith("suggestFri")){
                network.suggestFriend(network.getUsers().get(loggedInUser));
            }

            else if(mainCommand.startsWith("post")){
                if (parameter.isEmpty()){
                    System.out.println("\tUsage -> post <String content>");
                    continue;
                }

                network.sharePost(network.getUsers().get(loggedInUser),parameter);
            }

            else if(mainCommand.startsWith("showAllPost")){
                network.getNewFeed();
            }

            else if (mainCommand.startsWith("showMyPost")){
                for (Post post:network.getUsers().get(loggedInUser).getPosts()){
                    System.out.printf("\tUser id: %d %n\tContent: %s %n\tTime: %s%n%n",post.getUserId(),post.getContent(),post.getDateAsString());
                }
            }

            else if (mainCommand.startsWith("searchById")){
                try {
                    Integer.parseInt(parameter);
                } catch (Exception e){
                    System.out.println("\tUsage -> searchById <user id>");
                    continue;
                }

                int foundId = network.searchById(Integer.parseInt(parameter));
                if (foundId == -1)
                    System.out.println("\tNobody has this id.");
                else
                    System.out.printf("\tId: %d - Name: %s%n",foundId,network.getUsers().get(foundId).getName());
            }

            else if (mainCommand.startsWith("searchByName")){

                if (parameter.isEmpty()){
                    System.out.println("\tUsage -> searchByName <name>");
                    continue;
                }

                int foundId = network.searchByName(parameter);
                if (foundId == -1)
                    System.out.println("\tNobody has this name.");
                else
                    System.out.printf("\tId: %d - Name: %s%n",foundId,network.getUsers().get(foundId).getName());
            }

            else if (mainCommand.startsWith("myActs")){
                System.out.println();
                for (Activity act:network.getActivities()){
                    if (act.getUserId() == loggedInUser){
                        System.out.printf("Description: %s%nTime: %s%n%n",act.getDescription(),act.getDateAsString());
                    }
                }
            }

            else if(mainCommand.startsWith("myFriends")){

                for (User friend :network.getUsers().get(loggedInUser).getFriends())
                    System.out.printf("\tId: %d - Name: %s%n",friend.getId(),friend.getName());

            }
            else if (mainCommand.startsWith("myFriReqs")){
                System.out.println(network.getUsers().get(loggedInUser).getFriendRequests());
            }
            else if (mainCommand.startsWith("help")){
                System.out.println("\twhoami: print logged in user information.\n" +
                        "\tswitch <User ID>: change logged in user.\n\n" +
                        "\tuseradd <Name>: add new user on the app.\n" +
                        "\tuserremove <User ID>: remove user on the app.\n\n" +
                        "\tsendFriReq <User ID>: send a friend request.\n" +
                        "\tactFriReq <User ID>: accept friend request.\n" +
                        "\tremoveFri <User ID>: remove from friend list.\n" +
                        "\tdenyFriReq <User ID>: deny friend request.\n" +
                        "\tfindMutuals <User ID>: find mutual friends.\n" +
                        "\tsuggestFri: see suggested friends.\n\n" +
                        "\tsearchById <User ID>: search any user using ids.\n" +
                        "\tsearchByName <Name>: search any user using names.\n\n" +
                        "\tpost <String>: share a post.\n" +
                        "\tshowAllPost: show news feed.\n" +
                        "\tshowMyPost: show post that I shared.\n\n" +
                        "\tmyActs: show my activities.\n" +
                        "\tmyFriends: show my friends.\n" +
                        "\tmyFriReqs: show my friend requests.");
            }
            else if (mainCommand.equals("exit")){
                System.out.println("Exiting...");
                break;
            }
        }
    }
}