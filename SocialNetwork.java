import java.util.Arrays;

/*
 * Social network connectivity.
 *
 * Given a social network containing nnn members and a log file containing mmm timestamps
 * at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all
 * members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log
 * file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm
 * should be m*log(n) or better and use extra space proportional to n.
 */

public class SocialNetwork {
    private final int[] friends;
    private final int[] size;
    private int components;
    SocialNetwork(int friendsNumber) {
        friends = new int[friendsNumber];
        size = new int[friendsNumber];
        components = friendsNumber;

        initializeFriends();
        initializeSize();
    }

    private void initializeFriends() {
        for (int i = 0; i < friends.length; ++i) {
            friends[i] = i;
        }
    }

    boolean isEverythingConnected() {
        return components == 1;
    }

    private void initializeSize() {
        Arrays.fill(size, 1);
    }

    void union(int friendOne, int friendTwo) {
        int rootOne = root(friendOne);
        int rootTwo = root(friendTwo);
        if (rootOne == rootTwo) return;
        if (size[rootOne] > size[rootTwo]) {
            friends[rootTwo] = rootOne;
            size[rootOne] += size[rootTwo];
        } else {
            friends[rootOne] = rootTwo;
            size[rootTwo] += size[rootOne];
        }
        components -= 1;
    }

    private int root(int friend) throws IllegalArgumentException {
        if (friend > friends.length) {
            throw new IllegalArgumentException("Friend is out of range!");
        }
        while(friends[friend] != friend) {
            friends[friend] = friends[friends[friend]];
            friend = friends[friend];
        }
        return friend;
    }
}