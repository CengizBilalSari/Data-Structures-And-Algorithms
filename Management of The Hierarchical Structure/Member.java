/**
 * This member class is created for the mafia structure members.
 *
 * @author Cengiz Bilal Sarı, Student ID: 2021400201
 * @since Date: 05.11.2023
 */
public class Member {
    /**
     * In this class required data fields are given for structure
     */
    double GMS;  // data of the member
    int height; // height of the member in the tree structure of mafia
    String name;
    Member lower, higher;

    Member(String name, double GMS) {
        this.name = name;
        this.GMS = GMS;
        height = 0;
        lower = null;
        higher = null;
    }
}
