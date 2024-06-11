/**
 * This mafia structure class is created for the mafia structure. It has the methods for required features :
 * intel rank, intel divide, member in, member out and intel target.
 *
 * @author Cengiz Bilal Sarı, Student ID: 2021400201
 * @since Date: 05.11.2023
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class MafiaStructure {
    private static int ALLOWED_IMBALANCE = 1;
    private Member boss;

    public MafiaStructure() {
        boss = null;
    }

    public Member getBoss() {
        return boss;
    }

    public void setBoss(Member boss) {
        this.boss = boss;
    }

    //Rotations for balances
    /*
    Single Rotations
     */
    Member rightRotate(Member reorganizing) {
        //arrange tree
        Member inferior = reorganizing.lower;
        Member inferiorInferior = inferior.higher;
        inferior.higher = reorganizing;
        reorganizing.lower = inferiorInferior;

        //refresh height values
        reorganizing.height = Math.max(height(reorganizing.lower), height(reorganizing.higher)) + 1;
        inferior.height = Math.max(height(inferior.lower), reorganizing.height) + 1;
        return inferior;
    }

    Member leftRotate(Member reorganizing) {
        // arrange tree
        Member inferior = reorganizing.higher;
        Member inferiorInferior = inferior.lower;
        inferior.lower = reorganizing;
        reorganizing.higher = inferiorInferior;

        // refresh rank values
        reorganizing.height = Math.max(height(reorganizing.lower), height(reorganizing.higher)) + 1;
        inferior.height = Math.max(height(inferior.higher), reorganizing.height) + 1;
        return inferior;
    }

    /*
    Double Rotations
     */
    Member doubleRotationLR(Member reorganizing) {
        reorganizing.lower = leftRotate(reorganizing.lower);
        return rightRotate(reorganizing);
    }

    Member doubleRotationRL(Member reorganizing) {
        reorganizing.higher = rightRotate(reorganizing.higher);
        return leftRotate(reorganizing);
    }

    private int height(Member member) {
        return member == null ? -1 : member.height;
    }

    // find method to use in other methods like remove
    private Member findMin(Member member) {
        if (member == null)
            return member;
        while (member.lower != null)
            member = member.lower;
        return member;
    }

    // balance part
    private Member balance(Member member) {
        if (member == null)
            return member;

        if (height(member.lower) - height(member.higher) > ALLOWED_IMBALANCE)
            if (height(member.lower.lower) >= height(member.lower.higher))
                member = rightRotate(member);
            else
                member = doubleRotationLR(member);
        else if (height(member.higher) - height(member.lower) > ALLOWED_IMBALANCE)
            if (height(member.higher.higher) >= height(member.higher.lower))
                member = leftRotate(member);
            else
                member = doubleRotationRL(member);

        member.height = Math.max(height(member.lower), height(member.higher)) + 1;
        return member;
    }

    //delete a member part
    public void remove(double x) {
        boss = remove(boss, x, true);
    }

    Member remove(Member member, double data, boolean write) {
        // find the node to be deleted, remove it,balance the tree and inform the user

        if (member == null)
            return member;

        if (data < member.GMS)
            member.lower = remove(member.lower, data, write);
        else if (data > member.GMS)
            member.higher = remove(member.higher, data, write);
        else if (member.higher != null && member.lower != null) // two children
        {

            Member memberReplaced = findMin(member.higher);
            member.GMS = memberReplaced.GMS;
            if (write)
                System.out.println(member.name + " left the family, replaced by " + memberReplaced.name);
            member.name = memberReplaced.name;
            member.higher = remove(member.higher, member.GMS, false);

        } else if (member.lower != null) {
            if (write)
                System.out.println(member.name + " left the family, replaced by " + member.lower.name);
            member = member.lower;
        } else if (member.higher != null) {
            if (write)
                System.out.println(member.name + " left the family, replaced by " + member.higher.name);
            member = member.higher;
        } else {
            if (write)
                System.out.println(member.name + " left the family, replaced by nobody");
            member = null;
        }
        return balance(member);
    }

    // insert a member part
    public void MemberIn(String name, double data) {
        boss = MemberIn(name, data, boss);
    }

    private Member MemberIn(String name, double data, Member member) {
        if (member == null)
            return new Member(name, data);
        if (data < member.GMS) {
            System.out.println(member.name + " welcomed " + name);
            member.lower = MemberIn(name, data, member.lower);
        } else if (data > member.GMS) {
            System.out.println(member.name + " welcomed " + name);
            member.higher = MemberIn(name, data, member.higher);
        } else
            ;//duplicate , do nothing
        return balance(member);
    }
    // intel target part

    /*
    first find the paths for intel target, started value to boss and boss to destination value
     */
    public ArrayList<Double> findingThePath(double data, Member member) {
        ArrayList<Double> arrayList = new ArrayList<>(); // Initialize the ArrayList here

        return findingThePath(data, member, arrayList);
    }

    private ArrayList<Double> findingThePath(double data, Member member, ArrayList<Double> arrayList) {
        if (member == null) {
            return arrayList;
        }
        arrayList.add(member.GMS);
        if (member.GMS == data) {
            return arrayList;
        } else if (data > member.GMS) {
            return findingThePath(data, member.higher, arrayList);
        } else {
            return findingThePath(data, member.lower, arrayList);
        }
    }

    // find node which is the lowest ancestor of the target members
    public Member findNode(double data) {
        return findNode(boss, data);
    }

    private Member findNode(Member member, double data) {
        while (member != null) {

            if (data < member.GMS)
                member = member.lower;
            else if (data > member.GMS)
                member = member.higher;
            else
                return member;
        }

        return null;
    }

    // intel target
    public double intelTarget(Member member, double gms1, double gms2) {
        ArrayList<Double> firstPath = findingThePath(gms1, member);
        ArrayList<Double> secondPath = findingThePath(gms2, member);
        Collections.reverse(firstPath);
        Collections.reverse(secondPath);
        firstPath.addAll(secondPath);
        for (double number : firstPath) {
            if (Collections.frequency(firstPath, number) != 1) {
                return number;
            }
        }
        return 0;
    }

    // monitoring rank in the family
    private int findNodeForRank(Member member, double data) {
        // find the rank which is given by user
        int counter = 0;
        while (member != null) {
            if (data < member.GMS) {
                counter += 1;
                member = member.lower;
            } else if (data > member.GMS) {
                member = member.higher;
                counter += 1;
            } else
                return counter;
        }
        return 0;
    }

    public void monitoringSameRankMembers(Member member, double data) {
        //find the rank which is requested
        int counter = findNodeForRank(member, data);
        Queue<Member> queue = new LinkedList<>();
        queue.offer(member);
        int currentLevel = 0;
        // add new levels until reaching to level wanted, and then print this rank
        if (counter == 0) {

            System.out.print("Rank Analysis Result: " + member.name + " " + String.format("%.3f", member.GMS).replace(",", "."));
            System.out.println();
            return;
        }
        while (currentLevel <= counter) {
            int currentLevelNodes = queue.size();
            if (currentLevel < counter) {
                for (int i = 0; i < currentLevelNodes; i++) {
                    Member current = queue.poll();
                    if (current.lower != null)
                        queue.offer(current.lower);
                    if (current.higher != null)
                        queue.offer(current.higher);
                }
            } else {
                System.out.print("Rank Analysis Result: ");
                int size = queue.size();
                for (int i = 1; i < size; i++) {
                    Member member1 = queue.poll();
                    System.out.print(member1.name + " " + String.format("%.3f", member1.GMS).replace(",", ".") + " ");
                }
                Member member1 = queue.poll();

                System.out.print(member1.name + " " + String.format("%.3f", member1.GMS).replace(",", ".") + "");
                System.out.println();

            }
            currentLevel++;
        }
    }


    // max independent  target
    public int[] maxIndependentTarget(Member member) {
        // 0 index is for excluded member and included children
        // 1 index is for included member and included grandchildren

        if (member == null) {
            return new int[2];
        }
        int[] leftSubTree = maxIndependentTarget(member.lower);
        int[] rightSubTree = maxIndependentTarget(member.higher);

        int[] maxOfAnyNumber = new int[2];

        // exclude it, include its children's max
        maxOfAnyNumber[0] = Math.max(leftSubTree[0], leftSubTree[1]) + Math.max(rightSubTree[0], rightSubTree[1]);

        // include it, include its grandchildren
        maxOfAnyNumber[1] = 1 + leftSubTree[0] + rightSubTree[0];
        return maxOfAnyNumber;
    }

    public int maxIndependentTargetSolution() {
        int[] solution = maxIndependentTarget(boss);
        return Math.max(solution[0], solution[1]);
    }
}

