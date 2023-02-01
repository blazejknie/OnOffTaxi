package com.onofftaxi.backend.filters;

import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BadWordsFilter {

    @Getter
    private static List<String> wordsList = new ArrayList<>();

    private FileReader fileReader;
    private static int count = 0;

    public BadWordsFilter() throws IOException {
        Optional<FileReader> optionalFile = getOptionalFile("src\\main\\resources\\static\\badwords.txt");
        if (optionalFile.isPresent()) {
            fillWordList();
        }
    }

    private void fillWordList() throws IOException {
        BufferedReader in = new BufferedReader(fileReader);
        String line;
        while ((line = in.readLine()) != null) {
            wordsList.add(line);
        }
        in.close();
    }

    Optional<FileReader> getOptionalFile(String filePath) {
        try {
            fileReader = new FileReader(filePath);
            return Optional.of(fileReader);
        } catch (Exception e) {
            System.out.printf("Not found file %s %d\n", e.getCause(), ++count);
            return Optional.empty();
        }
    }

    /**
     * Created by
     * Szymon Kamiński
     * on 6.09.2019
     * OnlyWhoresUseStreams
     */

    public Boolean isChecked(String value) {
        String lowerCaseValue = value.toLowerCase();
        String[] words = lowerCaseValue.split("\\s+");
        for (String badWord : wordsList) {
            for (String inputV : words) {
                if (inputV.equals(badWord)) {
                    //throw new VulgarismFoundException("Found unacceptable word " + inputV);
                    return false;
                }
            }
        }
        return true;
    }

}
