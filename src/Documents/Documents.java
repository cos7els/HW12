package Documents;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Documents {
    private static final String invalidSymbols = "number contains invalid characters";
    private static final String invalidLength = "invalid number length";
    private static final String invalidStart = "not starts with docnum or contract";

    public static void readFromFile() {
        Path path = Paths.get("docNumbers.txt");
        try {
            List<String> data = Files.readAllLines(path);
            analyzeNumbers(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void analyzeNumbers(List<String> data) {
        StringBuilder validData = new StringBuilder();
        StringBuilder invalidData = new StringBuilder();
        for (String s : data) {
            String check = s.replaceAll("\\w|\\d", "");
            if ((check.length() > 0 || s.contains("_")) && (s.length() != 15) && !(s.startsWith("docnum") ||
                    s.startsWith("contract"))) {
                invalidData.append(String.format("%s - %s, %s, %s", s, invalidSymbols, invalidLength, invalidStart)).append("\n");
            } else if ((check.length() > 0 || s.contains("_")) && (s.length() != 15)) {
                invalidData.append(String.format("%s - %s, %s", s, invalidSymbols, invalidLength)).append("\n");
            } else if ((check.length() > 0 || s.contains("_")) && !(s.startsWith("docnum") || s.startsWith("contract"))) {
                invalidData.append(String.format("%s - %s, %s", s, invalidSymbols, invalidStart)).append("\n");
            } else if ((s.length() != 15) && !(s.startsWith("docnum") || s.startsWith("contract"))) {
                invalidData.append(String.format("%s - %s, %s", s, invalidLength, invalidStart)).append("\n");
            } else if (check.length() > 0 || s.contains("_")) {
                invalidData.append(String.format("%s - %s", s, invalidSymbols)).append("\n");
            } else if (s.length() != 15) {
                invalidData.append(String.format("%s - %s", s, invalidLength)).append("\n");
            } else if (!(s.startsWith("docnum") || s.startsWith("contract"))) {
                invalidData.append(String.format("%s - %s", s, invalidStart)).append("\n");
            } else {
                validData.append(s).append("\n");
            }

        }
        writeValidData(validData.toString());
        writeInvalidData(invalidData.toString());
    }

    private static void writeValidData(String data) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("validData.txt"))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeInvalidData(String data) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("invalidData.txt"))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
