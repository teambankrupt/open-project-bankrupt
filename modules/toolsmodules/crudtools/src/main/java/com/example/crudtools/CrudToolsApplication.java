package com.example.crudtools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

//@SpringBootApplication(scanBasePackages = {"com.example"})
public class CrudToolsApplication {

    public static void main(String[] args) {
//        SpringApplication.run(CrudToolsApplication.class, args);
        process(args);
    }

    private static void process(String[] args) {
        if (args == null || args.length < 3) {
            print("You must provide a domain name as first argument, crudexample package path as second argument and generation path as third argument.");
            return;
        }
        String domainName = args[0];
        String crudExamplePath = args[1];
        String generationPath = args[2];

        try {
            print("Starting process...");
            copy(domainName, crudExamplePath, generationPath);
            print("Completed!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void copy(String domainName, String crudExamplePath, String generationPath) throws IOException {
        System.out.println("Making things ready...\n");

        File genDir = new File(generationPath);
        if (!genDir.exists()) {
            print("Generation directory doesn't exist:\nDir: " + genDir.getAbsolutePath());
            return;
        }
        genDir = new File(genDir + File.separator + domainName.toLowerCase() + "s");
        if (!genDir.exists()) genDir.mkdir();

        if (crudExamplePath == null)
            crudExamplePath = "examples" + File.separator + "crudexample" + File.separator + "crudexample";
        File exampleDir = new File(crudExamplePath);
        if (!exampleDir.exists()) {
            print("Example CRUD package doesn't exist:\nDir: " + exampleDir.getAbsolutePath());
            return;
        }

        System.out.println("Copying example directory..");
        FileUtils.copyDirectory(exampleDir, genDir);
        System.out.println("Directory copied successfully..\n");
        System.out.println("--------------------------------------------------------\n");
        print("Replacing file name and file contents with your provided domain name...");
        replaceDomainNames(genDir, domainName);
    }

    private static void replaceDomainNames(File genDir, String domainName) throws IOException {
        if (genDir == null || !genDir.exists()) {
            print("Generation directory doesn't exist:\nDir: " + genDir.getAbsolutePath());
            return;
        }

        File[] listOfFiles = genDir.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            File file = listOfFiles[i];
            if (file.isFile()) {
                System.out.println("Rename file: " + file.getName());
                file = changeFilename(file, domainName);
                System.out.println("Successfully renamed to: " + file.getName() + "\n\nReplacing file contents with domain name..");

                replaceText(file, "CrudExample", domainName);
                replaceText(file, "crudexample", domainName.toLowerCase());
                replaceText(file, "CRUDEXAMPLE", domainName.toUpperCase());

                System.out.println("\nSuccessfully Replaced!");
            } else if (file.isDirectory()) {
                System.out.println("\n\nFound a directory named: " + file.getName() + " instead of a file. Processing files recursively: \n\n");
                replaceDomainNames(file, domainName);
            }
        }
    }

    private static File changeFilename(File file, String domainName) throws IOException {
        String filePath = file.getAbsolutePath();
        filePath = filePath.replace("CrudExample", domainName);
        File renamedFile = new File(filePath);
        file.renameTo(renamedFile);
        return renamedFile;
    }

    private static void replaceText(File file, String placeholder, String replacement) throws IOException {
        String content = IOUtils.toString(new FileInputStream(file), UTF_8);
        content = content.replaceAll(placeholder, replacement);
        IOUtils.write(content, new FileOutputStream(file), UTF_8);
    }


    private static void print(String message) {
        System.out.println("!!!!!!!!-------------------------!!!!!!!");
        System.out.println(message);
        System.out.println("!!!!!!!!-------------------------!!!!!!!");

    }
}
