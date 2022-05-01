package com.example.imagehandling.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name
        String name = file.getOriginalFilename();

        //Fullpath
        String filePath = path + File.separator + name;

        //create folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return name;
    }

    public void loadImage(String path, String fileName, HttpServletResponse response) {
        try {
            // preparing file path
            String filePath = "D://IntellijIdeaWorkspace/image-handling/" + path + fileName;

            //output stream prepared
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

            //input stream prepared
            FileInputStream fis = new FileInputStream(filePath);

            //copy from input to output stream
            StreamUtils.copy(fis,bos);

            //closing buffers and setting response type
            bos.close();
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
