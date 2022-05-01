package com.example.imagehandling.controller;

import com.example.imagehandling.model.FileResponse;
import com.example.imagehandling.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private  String path;
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam MultipartFile image
            ) {

        String fileName = null;
        try {
            fileName = fileService.uploadImage(path,image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null,"Image not uploaded due to error on server!!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(fileName,"Image Successfully uploaded!!"), HttpStatus.OK);
    }

    @GetMapping("/download/{filename}")
    public void renderImage(
            @PathVariable String filename, HttpServletResponse response
            ){
        fileService.loadImage(path,filename,response);
    }
}
