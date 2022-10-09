package Tree;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Scanner;

public class Tree {

    public static void start() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter the path to see files: ");
            see(new File(in.nextLine()));
        }
    }
    private static void see(File path) {
        File[] files = path.listFiles();
        if (files != null) {
            System.out.printf("%s:%n", path.getPath());
            if (files.length == 0) {
                System.out.printf("Directory \"%s\" is empty%n", path.getAbsolutePath());
            } else {
                for (File f : files) {
                    if (f.isDirectory()) {
                        see(f);
                    } else {
                        System.out.printf("File name: %s | Last modified: %s | Size: %.3f Mb%n", f.getName(),
                                Instant.ofEpochMilli(f.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate(),
                                (float) f.length() / 1_024_000);
                    }
                }
            }
        } else {
            System.out.printf("Directory \"%s\" does not exist%n", path.getAbsolutePath());
        }
    }
}
