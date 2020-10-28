import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {

	public static List<String> readFileAsList(Path path) throws IOException {
		return Files.readAllLines(path);
	}
	
	public static void save(String str, Path path) throws IOException {
		Files.writeString(path, str);
	}
}
