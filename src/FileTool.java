import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileTool {

    public static String readFileToString(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println(content);
        return content;
    }
    public static void writeStringToFile(String filePath, String content) {
        try {
            Files.write(Paths.get(filePath), content.getBytes());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
