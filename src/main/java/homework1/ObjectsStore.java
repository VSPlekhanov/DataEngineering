package homework1;

/*
 * Copyright 2001-2019 by HireRight, Inc. All rights reserved.
 * This software is the confidential and proprietary information
 * of HireRight, Inc. Use is subject to license terms.
 */

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class ObjectsStore {

    private static class FileObject {
        private byte[] bytes;

        public FileObject(byte[] bytes) {
            this.bytes = bytes;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FileObject that = (FileObject) o;
            return Arrays.equals(bytes, that.bytes);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(bytes);
        }

    }

    public void removeDuplicates(final String directoryPath){
        final Map<FileObject, Path> filesMap = new HashMap<>();
        Path directory = Paths.get(directoryPath);
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileObject fileObject = new FileObject(Files.readAllBytes(file));
                    if (filesMap.containsKey(fileObject)){
                        Files.delete(file);
                        Files.createLink(file, filesMap.get(fileObject));
                    } else {
                        filesMap.put(fileObject, file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ObjectsStore objectsStore = new ObjectsStore();
        objectsStore.removeDuplicates(scanner.next());
    }
}
