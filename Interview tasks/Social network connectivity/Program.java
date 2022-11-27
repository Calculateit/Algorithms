import java.io.File;
import java.util.Scanner;

public class Program {
    SocialNetwork network = new SocialNetwork(10);
    SpecificCanonicalElement graph = new SpecificCanonicalElement(10);

    public static void main(String[] args) {
        Program program = new Program();

        program.testSocialNetwork();
        program.testSpecificCanonicalElement();
    }

    void testSpecificCanonicalElement() {
        graph.union(1, 3);
        graph.union(0, 2);
        graph.union(9, 2);
        graph.union(3, 4);
        graph.union(5, 6);
        graph.union(6, 4);
        graph.union(7, 8);
        graph.union(8, 0);
        /*
         * Components:
         * { 1 3 4 5 6 4 }
         * { 0 2 7 8 9 }
         */
        String test = String.valueOf(graph.find(3)) + ' ';
        test += graph.find(7);
        test += ' ';
        test += graph.find(6);
        System.out.println(test);
    }

    void testSocialNetwork() {
        /*
         * "times" contains a fully connected network
         * while "times_one" doesn`t.
         */

        readTimestampsFileAndCheckConnections("times");
        readTimestampsFileAndCheckConnections("times_one");
    }

    void readTimestampsFileAndCheckConnections(String filename) {
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                int friendOne = scanner.nextInt();
                int friendTwo = scanner.nextInt();
                String date = scanner.next();
                String time = scanner.next();

                network.union(friendOne, friendTwo);
                if (network.isEverythingConnected()) {
                    System.out.println(
                            "Everybody is a friend! The last meaningful connection is " + date + " " + time
                    );
                    return;
                }
            }
        } catch(Exception e) {
            System.out.println("An error occurs: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        System.out.println("Not all members are connected.");
    }
}


