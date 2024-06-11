import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * This Project class is the main application class for this project.
 * It basically uses the epic blend structure class methods and put the output into output file.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 10.12.2023
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream fileOutput = new PrintStream(args[2]);
        System.setOut(fileOutput);
        EpicBlendStructure epicBlendStructure = new EpicBlendStructure();
        epicBlendStructure.takingFirstFile(args[0]);
        epicBlendStructure.takingPlaylists(args[1]);
        // Ensure that the output is written to the file
        System.out.flush();



    }
}