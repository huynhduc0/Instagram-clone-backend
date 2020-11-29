package com.thduc.instafake.utils;

import com.thduc.instafake.constant.Constant;
import com.thduc.instafake.exception.BadRequestException;
import com.thduc.instafake.exception.DataNotFoundException;
import org.imgscalr.Scalr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isBlank;


public class FileUtils {
    private static final String _PNG = "png";
    private static final String _JPG = "jpg";
    private static final String _JPEG = "jpeg";
    private static final Logger logger = LogManager.getLogger(FileUtils.class);
    private static String getExtension(String prefixData) {
        switch (prefixData) {
            case Constant.JPEG_MEDIA_TYPE:
                return "jpeg";
            case Constant.PNG_MEDIA_TYPE:
                return "png";
            case Constant.JPG_MEDIA_TYPE:
                return "jpg";
//            case Constant.PNG_MEDIA_TYPE:
//                return "png";
            default:
                return null;
        }
    }

    public static String saveFileToStorage(String folder, String name, String imageData, boolean isResized) {

        String folderPath = Constant.UPLOAD_PATH + folder;

        String filename = null;

        File newFolder = new File(folderPath);

        if (!newFolder.exists()) {
            if (!newFolder.mkdir()) {
            } else {
            }
        }
        String[] properties = imageData.split(",");
        if (properties.length < 2) {
            throw new BadRequestException(Constant.NOT_BASE64_STRING);
        }
        String extension = getExtension(properties[0]);
        if (isBlank(extension)) {
            throw new BadRequestException(imageData);
        }
        String base64 = properties[1];
        byte[] byteData = Base64.getDecoder().decode(base64);

        filename = name + "." + extension;

        String path = folderPath + "/" + filename;

        try {
            //optimize image
            ByteArrayInputStream bis = new ByteArrayInputStream(byteData);
            BufferedImage bufferedImage = ImageIO.read(bis);
            OutputStream outputStream = new FileOutputStream(path);
            if(isResized){
                BufferedImage scaledImg = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT,
                        400, 400, Scalr.OP_ANTIALIAS);
                ImageIO.write(scaledImg, _PNG, outputStream);
            }else{
                //outputStream.write(byteData);
                ImageIO.write(bufferedImage, _PNG, outputStream);
                outputStream.flush();
                outputStream.close();
            }
            return folder+"/"+filename;
        } catch (Exception e) {
            logger.error("Error image uploading --- {}", e.getMessage());
            return null;
        }

    }


    public static void deleteEntireFileInFolder(String folderName) {
        File currentFolder = new File(Constant.UPLOAD_PATH + folderName);
        if (currentFolder.exists()) {
            for (File file : currentFolder.listFiles()) {
                if (!file.delete()) {
                    logger.error("Cannot delete file {} in folder: {}", file.getName(), folderName);
                }
            }
        }
    }

    public static void deleteEntireFolder(String folderName) {
        File currentFolder = new File(Constant.UPLOAD_PATH + folderName);
        if (!(currentFolder.exists() && currentFolder.delete())) {
            logger.error("Cannot delete entire folder: {}", folderName);
        }
    }

    public static File[] showFolderContent(String folderName) {
        String path = Constant.UPLOAD_PATH;

        if (folderName != null) {
            path += "/" + folderName;
        }
        File currentFolder = new File(path);
        return currentFolder.listFiles();
    }

    public static ResponseEntity<byte[]> loadImage(String folder, String filename) throws IOException {
        Path path = Paths.get(Constant.UPLOAD_PATH, folder, filename);
        File file = new File(path.toString());
        if (!file.exists()) {
            throw new DataNotFoundException("File", "path", file.getAbsolutePath());
        }
        FileInputStream resource = new FileInputStream(file);

        byte[] fileData = new byte[(int) file.length()];
        resource.read(fileData);
        resource.close();
        if (filename.endsWith(_PNG)) {

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileData);
        } else if ((filename.endsWith(_JPEG)) || (filename.endsWith(_JPG))) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileData);
        }
        return null;
    }

}
