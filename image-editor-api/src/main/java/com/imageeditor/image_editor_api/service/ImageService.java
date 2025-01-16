package com.imageeditor.image_editor_api.service;

import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ImageService {
    private final ExecutorService executorService;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    
    public ImageService() {
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public BufferedImage convertToGrayscale(BufferedImage input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgb = input.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;
                        int gray = (r + g + b) / 3;
                        int newPixel = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;
                        output.setRGB(x, y, newPixel);
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    public BufferedImage rotate(BufferedImage input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        output.setRGB(height - 1 - y, x, input.getRGB(x, y));
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    public BufferedImage rotateCounter(BufferedImage input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        output.setRGB(y, width - 1 - x, input.getRGB(x, y));
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    public BufferedImage flipHorizontal(BufferedImage input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        output.setRGB(width - 1 - x, y, input.getRGB(x, y));
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    public BufferedImage flipVertical(BufferedImage input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        output.setRGB(x, height - 1 - y, input.getRGB(x, y));
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    public BufferedImage adjustBrightness(BufferedImage input, int percentage) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgb = input.getRGB(x, y);
                        int r = (rgb >> 16) & 0xFF;
                        int g = (rgb >> 8) & 0xFF;
                        int b = rgb & 0xFF;
                        
                        r = adjustColorComponent(r, percentage);
                        g = adjustColorComponent(g, percentage);
                        b = adjustColorComponent(b, percentage);
                        
                        output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    public BufferedImage applyBlur(BufferedImage input, int radius) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // Create integral image for O(1) region sum calculation
        long[][] integralR = new long[height + 1][width + 1];
        long[][] integralG = new long[height + 1][width + 1];
        long[][] integralB = new long[height + 1][width + 1];
        
        // Compute integral image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = input.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                
                integralR[y + 1][x + 1] = r + integralR[y + 1][x] + integralR[y][x + 1] - integralR[y][x];
                integralG[y + 1][x + 1] = g + integralG[y + 1][x] + integralG[y][x + 1] - integralG[y][x];
                integralB[y + 1][x + 1] = b + integralB[y + 1][x] + integralB[y][x + 1] - integralB[y][x];
            }
        }
        
        CompletableFuture<?>[] futures = new CompletableFuture[THREAD_POOL_SIZE];
        int rowsPerThread = height / THREAD_POOL_SIZE;
        
        for (int t = 0; t < THREAD_POOL_SIZE; t++) {
            final int startY = t * rowsPerThread;
            final int endY = (t == THREAD_POOL_SIZE - 1) ? height : (t + 1) * rowsPerThread;
            
            futures[t] = CompletableFuture.runAsync(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        int x1 = Math.max(0, x - radius);
                        int y1 = Math.max(0, y - radius);
                        int x2 = Math.min(width - 1, x + radius);
                        int y2 = Math.min(height - 1, y + radius);
                        int area = (x2 - x1 + 1) * (y2 - y1 + 1);
                        
                        int r = (int) ((integralR[y2 + 1][x2 + 1] - integralR[y2 + 1][x1] - integralR[y1][x2 + 1] + integralR[y1][x1]) / area);
                        int g = (int) ((integralG[y2 + 1][x2 + 1] - integralG[y2 + 1][x1] - integralG[y1][x2 + 1] + integralG[y1][x1]) / area);
                        int b = (int) ((integralB[y2 + 1][x2 + 1] - integralB[y2 + 1][x1] - integralB[y1][x2 + 1] + integralB[y1][x1]) / area);
                        
                        output.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
                    }
                }
            }, executorService);
        }
        
        CompletableFuture.allOf(futures).join();
        return output;
    }

    private int adjustColorComponent(int component, int percentage) {
        component = component + (component * percentage / 100);
        return Math.min(Math.max(component, 0), 255);
    }

    @Override
    protected void finalize() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}