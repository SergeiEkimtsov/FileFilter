package com.ekimtsovss.io;

import com.ekimtsovss.exception.FileIOException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public class FileServiceImpl implements FileService{
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    @Override
    public List<String> read(String fileName) throws FileIOException {

        List<String> lines;
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            throw new FileIOException(true,fileName,
                    new FileNotFoundException("File not found: " + fileName));
        }
        if (!Files.isReadable(path)) {
            throw new FileIOException(true, fileName,
                    new AccessDeniedException("Reading access denied: " + fileName));
        }
        try(Stream<String> stream = Files.lines(path,CHARSET)){
            lines = stream
                    .filter(line->!line.isBlank())
                    .map(String::trim)
                    .toList();

        }catch (IOException e) {
            throw new FileIOException(true, fileName, e);
        }
        return lines;
    }

    @Override
    public void write(List<String> listData,
                      String fileName,
                      String pathToResult,
                      boolean appendMode) throws FileIOException {

        if (listData.isEmpty())
            return;

        Path outputPath;

        if (pathToResult.isBlank()) {
            outputPath = Paths.get(fileName).toAbsolutePath();
        } else {
            outputPath = Paths.get(pathToResult, fileName).toAbsolutePath();
        }

        outputPath = outputPath.normalize();

        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException e) {
            throw new FileIOException(false, fileName,
                    new IOException("Cannot create directory: " + outputPath.getParent(), e));
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, CHARSET,
                appendMode ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE)){

            for (String line:listData){
                writer.write(line);
                writer.newLine();
            }

        }
        catch (IOException e) {
            throw new FileIOException(false, fileName, e);
        }
    }
}
