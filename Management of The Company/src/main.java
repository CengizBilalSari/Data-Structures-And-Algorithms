import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
/**
 * This Main class is mainly created for running the project.
 *  It uses the methods and data fields of the OwnHashTable, Member and Company Structure classes.
 *
 * @author Cengiz Bilal Sarı
 * @since Date: 24.11.2023
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
        // Create a PrintStream that writes to the FileOutputStream
        PrintStream printStream = new PrintStream(fileOutputStream);

        // Redirect System.out to the PrintStream
        System.setOut(printStream);

        // create company structure and take initial situation and new alteration
        CompanyStructure company = new CompanyStructure();
        company.takingFirstFile("initial2.txt");
        company.takingSecondFile("input2.txt");

        // close the print stream
        printStream.close();

    }
}