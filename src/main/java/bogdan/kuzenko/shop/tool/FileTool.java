package bogdan.kuzenko.shop.tool;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileTool {

    public String saveFile(String image, String directory) throws IOException {
        Path pathToDirectory = getPathToDirectory(directory);

        String[] data = image.split(Constants.COMMA);
        String metaInfo = data[0];
        String base64File = data[1];

        String fileName = createFileName(getFileExtensionFromMetaInfo(metaInfo));

        Files.write(
                Paths.get(pathToDirectory.toString(), fileName),
                Base64.getDecoder().decode(base64File.getBytes())
        );
        return fileName;
    }

    private String createFileName(String fileExtension) {
        String fileName = UUID.randomUUID().toString();
        return String.format(Constants.FULL_IMAGE_NAME_FORMAT, fileName, fileExtension);
    }

    private String getFileExtensionFromMetaInfo(String metaInfo) {
        return metaInfo.split(Constants.SLASH)[1].split(Constants.SEMICOLON)[0];
    }

    private Path getPathToDirectory(String directory) throws IOException {
        Path path = Paths.get(System.getProperty(Constants.USER_HOME), directory);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return path;
    }
}
