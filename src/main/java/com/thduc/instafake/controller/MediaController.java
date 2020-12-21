package com.thduc.instafake.controller;

import com.thduc.instafake.utils.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
@CrossOrigin(origins = "*")
public class MediaController {

    @GetMapping(value = "/img/{folder}/{filename}")
    public ResponseEntity loadFile(@PathVariable("folder") String folder, @PathVariable("filename") String filename) throws IOException {
        return FileUtils.loadImage(folder, filename);
//        return new ResponseEntity(filename, HttpStatus.OK);
    }
}
