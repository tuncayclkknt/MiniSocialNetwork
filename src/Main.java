public class Main {
    public static void main(String[] args) {

        User u1 = new User("Tuncay");
        User u2 = new User("Zeynep");

        Network network = new Network();

        network.addUser(u1);
        network.addUser(u2);

        network.sendFriendRequest(u1,u2);

        //for see list as a name
        for (int i = 0; i < u2.getFriendRequests().size(); i++){
            System.out.print(u2.getFriendRequests().get(i).getName() + " ");
        }
    }
}