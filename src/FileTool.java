import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileTool {

    public static byte[] readFileToString(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        return null;
    }

    public static void writeStringToFile(String filePath, byte[] content) {
        try {
            Files.write(Paths.get(filePath), content);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}