package com.imageeditor.image_editor_api.service;

import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.awt.Color;

@Service
public class ImageService {
    
    public BufferedImage convertToGrayscale(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                outputImage.setRGB(j, i, inputImage.getRGB(j, i));
            }
        }
        return outputImage;
    }
    
    public BufferedImage rotate(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                outputImage.setRGB(j, width-1-i, inputImage.getRGB(i, j));
            }
        }
        return outputImage;
    }
    
    public BufferedImage rotateCounter(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                outputImage.setRGB(height-1-j, i, inputImage.getRGB(i, j));
            }
        }
        return outputImage;
    }
    
    public BufferedImage flipHorizontal(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                outputImage.setRGB(width-1-j, i, inputImage.getRGB(j, i));
            }
        }
        return outputImage;
    }
    
    public BufferedImage flipVertical(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                outputImage.setRGB(j, height-1-i, inputImage.getRGB(j, i));
            }
        }
        return outputImage;
    }
    
    public BufferedImage adjustBrightness(BufferedImage inputImage, int percentage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                Color pixel = new Color(inputImage.getRGB(j, i));
                int red = adjustColorComponent(pixel.getRed(), percentage);
                int green = adjustColorComponent(pixel.getGreen(), percentage);
                int blue = adjustColorComponent(pixel.getBlue(), percentage);
                outputImage.setRGB(j, i, new Color(red, green, blue).getRGB());
            }
        }
        return outputImage;
    }
    
    public BufferedImage applyBlur(BufferedImage inputImage, int radius) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                int[] avgColor = getAverageColor(inputImage, j, i, radius, width, height);
                outputImage.setRGB(j, i, new Color(avgColor[0], avgColor[1], avgColor[2]).getRGB());
            }
        }
        return outputImage;
    }
    
    private int adjustColorComponent(int component, int percentage) {
        component = component + (component * percentage / 100);
        return Math.min(Math.max(component, 0), 255);
    }
    
    private int[] getAverageColor(BufferedImage image, int x, int y, int radius, int width, int height) {
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        int count = 0;
        
        for(int i = Math.max(0, y-radius); i < Math.min(height, y+radius+1); i++) {
            for(int j = Math.max(0, x-radius); j < Math.min(width, x+radius+1); j++) {
                Color pixel = new Color(image.getRGB(j, i));
                totalRed += pixel.getRed();
                totalGreen += pixel.getGreen();
                totalBlue += pixel.getBlue();
                count++;
            }
        }
        
        return new int[] {
            totalRed/count,
            totalGreen/count,
            totalBlue/count
        };
    }
}