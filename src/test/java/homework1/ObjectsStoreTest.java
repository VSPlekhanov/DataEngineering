package homework1;

/*
 * Copyright 2001-2019 by HireRight, Inc. All rights reserved.
 * This software is the confidential and proprietary information
 * of HireRight, Inc. Use is subject to license terms.
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ObjectsStoreTest {
    private static final Path rootDir = Paths.get("src","test", "resources", "homework1", "test1");
    @BeforeClass
    public static void setUp() throws Exception {
        Path dir11 = rootDir.resolve("dir_1_1");
        Path dir21 = dir11.resolve("dir_2_1");
        Path dir12 = rootDir.resolve("dir_1_2");
        Path dir22 = dir12.resolve("dir_2_2");;
        Path dir13 = rootDir.resolve("dir_1_3");

        Files.createDirectory(dir11);
        Files.createDirectory(dir21);
        Files.createDirectory(dir12);
        Files.createDirectory(dir22);
        Files.createDirectory(dir13);

        Path filesDir = Paths.get("src","test", "resources", "homework1", "files");
        Path file1 = filesDir.resolve("file1.jpg");
        Path file2 = filesDir.resolve("file2.jpg");
        Path file3 = filesDir.resolve("file3.jpg");

        Files.copy(file1, dir11.resolve("file11.jpg"));
        Files.copy(file1, dir21.resolve("file12.jpg"));
        Files.copy(file1, dir12.resolve("file13.jpg"));
        Files.copy(file1, dir22.resolve("file14.jpg"));
        Files.copy(file2, dir13.resolve("file21.jpg"));
        Files.copy(file2, dir11.resolve("file22.jpg"));
        Files.copy(file3, dir22.resolve("file31.jpg"));
        Files.copy(file3, dir13.resolve("file32.jpg"));
        Files.copy(file3, dir11.resolve("file33.jpg"));
    }

    @Test
    public void test1() {
        ObjectsStore objectsStore = new ObjectsStore();
        objectsStore.removeDuplicates(rootDir.toString());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        try {
            Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } finally {
            Files.createDirectory(rootDir);
        }
    }
}
