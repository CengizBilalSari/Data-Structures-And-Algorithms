/**
 * This main class is created for the main application. It utilizes the methods and data fields of
 * member and mafia structure class.
 *
 * @author Cengiz Bilal Sarı, Student ID: 2021400201
 * @since Date: 05.11.2023
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    // The end of "Created for testing only"
    public static void main(String[] args) throws FileNotFoundException {

        PrintStream fileOut = new PrintStream(new FileOutputStream(args[1]));
        System.setOut(fileOut);


        String[] commands = {"MEMBER_IN", "MEMBER_OUT", "INTEL_TARGET", "INTEL_DIVIDE", "INTEL_RANK"};
        MafiaStructure mafiaStructure = new MafiaStructure();

        // opening the file
        String fileName = args[0];
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);

        // set the boss
        String[] bossLine = inputFile.nextLine().split(" "); // take the boss in the first line
        mafiaStructure.setBoss(new Member(bossLine[0], Double.parseDouble(bossLine[1])));

        // main while loop for choosing the process which is requested and do it.
        while (inputFile.hasNext()) {
            String[] line = inputFile.nextLine().split(" ");
            if (line[0].equals(commands[0])) {
                mafiaStructure.MemberIn(line[1], Double.parseDouble(line[2]));
            } else if (line[0].equals(commands[1])) {
                mafiaStructure.remove(Double.parseDouble(line[2]));
            } else if (line[0].equals(commands[2])) {
                double lowerAncestor = mafiaStructure.intelTarget(mafiaStructure.getBoss(), Double.parseDouble(line[2]), Double.parseDouble(line[4]));
                System.out.println("Target Analysis Result: " + mafiaStructure.findNode(lowerAncestor).name + " " + String.format("%.3f", lowerAncestor).replace(",", "."));
            } else if (line[0].equals(commands[4])) {
                mafiaStructure.monitoringSameRankMembers(mafiaStructure.getBoss(), Double.parseDouble(line[2]));
            } else {
                int independentNumber = mafiaStructure.maxIndependentTargetSolution();
                System.out.println("Division Analysis Result: " + independentNumber);
            }
        }

        fileOut.close();
    }

}