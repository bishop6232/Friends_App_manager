import java.io.*;
import java.util.*;

public class FriendsList {
    private static ArrayList<Friend> friends;
    //String fileName = "data/friends.txt";
    public FriendsList() {
        this.friends = new ArrayList<>();
        loadFromFile();
    }

    public void loadFromFile() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("/Users/uzomamaduakolam/IdeaProject/Maduakolam-co1-assignment-03/data/friends.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] friendInfo = line.split(",");
                String name = friendInfo[0];
                String phoneNumber = (friendInfo[1]);
                Friend friend = new Friend(name, phoneNumber);
                friends.add(friend);
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public List<Friend> searchFriends(String name) {
        List<Friend> searchResults = new ArrayList<>();
        for (Friend friend : friends) {
            if (friend.getName().toLowerCase().contains(name.toLowerCase())) {
                searchResults.add(friend);
            }
        }
        return searchResults;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void removeFriend(Friend friend) {
        friends.remove(friend);
    }
    public void saveToFile() {
        try {
            File file = new File("/Users/uzomamaduakolam/IdeaProject/Maduakolam-co1-assignment-03/data/friends.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Collections.sort(friends, new Comparator<Friend>() {
                @Override
                public int compare(Friend friend1, Friend friend2) {
                    return friend1.getName().compareTo(friend2.getName());
                }
            });
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Friend friend : friends) {
                bw.write(friend.getName() + "," + friend.getNumber());
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewFriends() {
        for (Friend friend : friends) {
            System.out.println(friend.getName() + " , " + friend.getNumber());
        }
    }
}