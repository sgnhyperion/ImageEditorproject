package com.imageeditor.image_editor_api.controller;

import com.imageeditor.image_editor_api.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/process")
public class ImageController {
    
    private final ImageService imageService;
    
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    
    @PostMapping("/grayscale")
    public ResponseEntity<byte[]> convertToGrayscale(@RequestParam("image") MultipartFile file) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.convertToGrayscale(originalImage);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Backend is working!");
    }
    
    @PostMapping("/rotate-clockwise")
    public ResponseEntity<byte[]> rotateClockwise(@RequestParam("image") MultipartFile file) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.rotate(originalImage);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/rotate-counter")
    public ResponseEntity<byte[]> rotateCounter(@RequestParam("image") MultipartFile file) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.rotateCounter(originalImage);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/flip-horizontal")
    public ResponseEntity<byte[]> flipHorizontal(@RequestParam("image") MultipartFile file) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.flipHorizontal(originalImage);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/flip-vertical")
    public ResponseEntity<byte[]> flipVertical(@RequestParam("image") MultipartFile file) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.flipVertical(originalImage);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/brightness/{value}")
    public ResponseEntity<byte[]> adjustBrightness(
            @RequestParam("image") MultipartFile file,
            @PathVariable int value) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.adjustBrightness(originalImage, value);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/blur/{radius}")
    public ResponseEntity<byte[]> applyBlur(
            @RequestParam("image") MultipartFile file,
            @PathVariable int radius) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage processedImage = imageService.applyBlur(originalImage, radius);
            return sendProcessedImage(processedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    private ResponseEntity<byte[]> sendProcessedImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(outputStream.toByteArray());
    }
}