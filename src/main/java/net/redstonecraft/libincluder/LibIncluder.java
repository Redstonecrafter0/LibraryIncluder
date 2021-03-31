package net.redstonecraft.libincluder;

import net.lingala.zip4j.ZipFile;
import net.redstonecraft.redstoneapi.tools.IntUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class LibIncluder {

    public static void main(String[] args) throws IOException {
        if (Arrays.asList(args).contains("output.jar")) {
            System.err.println("output.jar is not allowed as input file.");
        } else {
            File tmpDir = new File("tmp_" + IntUtils.random(0, Integer.MAX_VALUE));
            tmpDir.mkdirs();
            for (String fileName : args) {
                ZipFile zipFile = new ZipFile(new File(fileName));
                System.out.println("Extracting... " + fileName);
                zipFile.extractAll(tmpDir.getCanonicalPath());
            }
            System.out.println("Combining...");
            ZipFile output = new ZipFile(new File("output.jar"));
            for (File i : Objects.requireNonNull(tmpDir.listFiles())) {
                if (i.isDirectory()) {
                    output.addFolder(i);
                } else {
                    output.addFile(i);
                }
            }
            System.out.println("Cleanup...");
            FileUtils.deleteDirectory(tmpDir);
            System.out.println("Done");
        }
    }

}
