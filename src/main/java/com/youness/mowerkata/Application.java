package com.youness.mowerkata;

import com.youness.mowerkata.exception.FileNameNotProvidedException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class Application {

    public static void main(String[] args) throws FileNameNotProvidedException {
        //  String[] fileName = new String[]{"assets/test.txt"};

        if (args == null || args.length == 0) {
            log.error("Please provide file name ");
        }
        String fileName = Arrays.stream(args)
                .findAny()
                .orElseThrow(() -> new FileNameNotProvidedException("File name not provided"));

        List<String> fileLines = readInstructionsFile(fileName);

    }


    public static List<String> readInstructionsFile(String fileName) {

        Path relative = Paths.get("src", "main", "resources", "assets", fileName);
        Path absolutePathFile = relative.toAbsolutePath();
        List<String> fileLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(absolutePathFile)) {
            stream.forEach(fileLines::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> preparedLines = fileLines.stream().map(String::strip).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        return preparedLines;
    }

}
