package org.nuaa.tomax.easyblog.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Scanner;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/30 16:41
 */
public class FileUtil {
    /**
     * create new folder(not exist)
     * @param path folder path
     * @return create result
     */
    public static boolean createFolder(String path) {
        File file = new File(path);
        return file.exists() || file.mkdirs();
    }

    /**
     * save file(upload)
     * @param file file(upload)
     * @param path save path
     * @param fileName file name
     * @return save result
     * @throws IOException file save exception
     */
    public static boolean saveFile(MultipartFile file, String path, String fileName) throws IOException {
        // first step : check folder
        if (createFolder(path)) {
            // folder exist and then save file
            file.transferTo(new File(path + "/" + fileName));
            return true;
        }
        return false;
    }

    /**
     * save markdown content to file
     * @param content markdown article content
     * @param path save folder path
     * @param fileName save file name
     * @return save result
     * @throws IOException content save exception
     */
    public static boolean saveMarkdownFile(String content, String path, String fileName) throws IOException {
        if (createFolder(path)) {
            FileWriter writer = new FileWriter(path + "/" + fileName);
            writer.write(content);
            writer.close();
            return true;
        }
        return false;
    }

    /**
     * read content from markdown file
     * @param path markdown file path
     * @return markdown file content
     * @throws IOException file read exception
     */
    public static String readMarkdownFile(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        Scanner scanner = new Scanner(inputStream);
        StringBuilder markdownContentBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            markdownContentBuilder.append(scanner.nextLine());
            markdownContentBuilder.append("\n");
        }
        inputStream.close();
        scanner.close();
        return markdownContentBuilder.toString();
    }

    /**
     * file rename
     * @param path origin file path
     * @param oName origin file name
     * @param nName new file name
     * @return file rename result, false represents new file exists
     */
    public static boolean renameFile(String path, String oName, String nName) {
        File oFile = new File(path + "/" + oName);
        File nFile = new File(path + "/" + nName);

        // target file name exist
        return !nFile.exists() && oFile.renameTo(nFile);
    }
}