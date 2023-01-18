package org.example.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.UUID;

public class FileUtils {
    private final static String baseUrl = "C:\\Users\\user\\Car\\crud_with_spring_mvc\\web\\Photo\\";
    private final static String databaseUrl = "/";



    public static String saveFile(HttpServletRequest request) {
        try {
            String authType = request.getAuthType();

            Part filePart = request.getPart("file");
            String submittedFileName = filePart.getSubmittedFileName();
            String contentType = submittedFileName.substring(submittedFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + contentType;
            for (Part part : request.getParts()) {
                part.write(baseUrl + fileName);
            }
            return databaseUrl + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
