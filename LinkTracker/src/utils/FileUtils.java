package utils;
import model.WebPage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    public static List<model.WebPage> loadPages(Path path){

        try
        {
            return Files.lines(Paths.get(String.valueOf(path)))
                    .map(line -> new WebPage(line.split(";")[0],
                            line.split(";")[1])).collect(Collectors.toList());
        } catch (Exception e) {
            MessageUtils.showError("Error reading file");
            return null;
        }
    }
}
