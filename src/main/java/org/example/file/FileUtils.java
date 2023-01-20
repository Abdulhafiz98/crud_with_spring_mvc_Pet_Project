package org.example.file;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import java.util.UUID;

public class FileUtils {
    private final static String baseUrl = "C:\\Users\\user\\Car\\crud_with_spring_mvc\\web\\WEB-INF\\image\\";


    public static String filee(HttpServletRequest request) {

        String rasm = (request.getParameter("productUrl"));
        try {
            ServletInputStream inputStream = request.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(baseUrl + rasm);
            outputStream.write(inputStream.readAllBytes());
            return rasm;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
