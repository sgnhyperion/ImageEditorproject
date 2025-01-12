package imageEditor;

import java.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class imageEditor{
    public static void printpixelvalues(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                //System.out.print(inputImage.getRGB(j,i)+" ");
                Color pixel = new Color(inputImage.getRGB(j,i));
                System.out.print("["+pixel.getRed()+" "+pixel.getGreen()+" "+pixel.getBlue()+"] ");
            }
            System.out.println();
        }
    }
  public static BufferedImage convertToGrayscale(BufferedImage inputImage){
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
     BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
     for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
            outputImage.setRGB(j, i, inputImage.getRGB(j, i));
        }
     }
     return outputImage;
  }
  public static BufferedImage changeTheBrightness(BufferedImage inputImage,int x){
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
            Color pixel = new Color(inputImage.getRGB(j,i));
            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();
            red = red +(red*x)/100;
            green = green+(green*x)/100;
            blue = blue+(blue*x)/100;
            if(red>255) red=255;
            if(red<0) red=0;
            if(green>255) green=255;
            if(green<0) green=0;
            if(blue>255) blue=255;
            if(blue<0) blue=0;
            Color newpixel = new Color(red, green, blue);
            outputImage.setRGB(j,i, newpixel.getRGB());

        }
     }
     return outputImage;
  }
  public static BufferedImage verticallyInvert(BufferedImage inputImage){
    int height = inputImage.getHeight();
    int width  = inputImage.getWidth();
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
            outputImage.setRGB(j, i, inputImage.getRGB(j, height-1-i));
        }
    }
    return outputImage;
  }
  public static BufferedImage horizontallyInvert(BufferedImage inputImage){
    int height = inputImage.getHeight();
    int width  = inputImage.getWidth();
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
            outputImage.setRGB(j, i, inputImage.getRGB(width-1-j, i));
        }
    }
    return outputImage;
  }
  public static BufferedImage rotate(BufferedImage inputImage){
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage transpose = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
    BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
    for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
            transpose.setRGB(i,j, inputImage.getRGB(j,i));
        }
    }
    for(int i=0; i<width;i++){
        for(int j=0;j<height;j++){
             outputImage.setRGB(j,i, transpose.getRGB(height-1-j,i));
        }
    }
    return outputImage;
  }
   public static BufferedImage back_rotate(BufferedImage inputImage){
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage transpose = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
    BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
    for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
            transpose.setRGB(i,j, inputImage.getRGB(j,i));
        }
    }
    for(int i=0; i<width;i++){
        for(int j=0;j<height;j++){
             outputImage.setRGB(j,i, transpose.getRGB(j,width-1-i));
        }
    }
    return outputImage;
  }

public static BufferedImage Image_Blur(BufferedImage inputImage, int n){
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    for (int i = 0; i < height; i += n) {
        for (int j = 0; j < width; j += n) {
            int sumred = 0;
            int sumgreen = 0;
            int sumblue = 0;
            int pixelCount = 0;
            
            for (int y = i; y < Math.min(i + n, height); y++) {
                for (int x = j; x < Math.min(j + n, width); x++) {
                    Color pixel = new Color(inputImage.getRGB(x, y));
                    sumred += pixel.getRed();
                    sumgreen += pixel.getGreen();
                    sumblue += pixel.getBlue();
                    pixelCount++;
                }
            }

            int redave = sumred / pixelCount;
            int greenave = sumgreen / pixelCount;
            int blueave = sumblue / pixelCount;
            
            int trueredave = Math.min(Math.max(redave, 0), 255);
            int truegreenave = Math.min(Math.max(greenave, 0), 255);
            int trueblueave = Math.min(Math.max(blueave, 0), 255);
            
            Color newpixel = new Color(trueredave, truegreenave, trueblueave);
            
            for (int y = i; y < Math.min(i + n, height); y++) {
                for (int x = j; x < Math.min(j + n, width); x++) {
                    outputImage.setRGB(x, y, newpixel.getRGB());
                }
            }
        }
    }
    
    return outputImage; 
}

  
    
    public static void main(String args[]) throws IOException {
        Scanner sc= new Scanner(System.in);
        System.out.println("Please input the file name");
        String S = sc.next();
         File inputFile = new File(S);
         BufferedImage inputImage = ImageIO.read(inputFile);
         System.out.println("Enter 1 for Converting Image to Grayscale");
         System.out.println("Enter 2 for rotating the image 90 degree clockwise");
         System.out.println("Enter 3 for rotating the image 90 degree Anti-clockwise");
         System.out.println("Enter 4 for horizontally inverting the image");
         System.out.println("Enter 5 for vertically inverting the image");
         System.out.println("Enter 6 for Changing brightness of the image");
            System.out.println("Enter 7 to Blur the Image");
            System.out.println("Print 8 to print pixel values");
         int N= sc.nextInt();
                    if(N==1){
                         BufferedImage Grayscale = convertToGrayscale(inputImage);
                         File grascale = new File("Edited_Image.jpg");
                         ImageIO.write(Grayscale, "jpg", grascale); 
                   }
                   if(N==2){
                          BufferedImage Rotate = rotate(inputImage);
                          File rotate90 = new File("Edited_Image.jpg");
                          ImageIO.write(Rotate, "jpg", rotate90);

                   }
                  if(N==3){
                    BufferedImage rotateback = back_rotate(inputImage);
                    File Backrotate = new File("Edited_Image.jpg");
                    ImageIO.write(rotateback, "jpg", Backrotate);

                  }
                  if(N==4){
                   BufferedImage horizontalInvert = horizontallyInvert(inputImage);
                   File invert2 = new File("Edited_Image.jpg");
                   ImageIO.write(horizontalInvert, "jpg", invert2);
                  }
                  if(N==5){
                   BufferedImage verticalInvert = verticallyInvert(inputImage);
                   File invert1 = new File("Edited_Image.jpg");
                   ImageIO.write(verticalInvert, "jpg", invert1);
                  }
                  if(N==6){
                    System.out.println("Enter the brightness size");
                    int size = sc.nextInt();
                    BufferedImage Brightness = changeTheBrightness(inputImage,size);
                    File brightness = new File("Edited_Image.jpg");
                    ImageIO.write(Brightness, "jpg", brightness);
                  }

                  if(N==7){
                      System.out.println("Enter the blurness size");
                      int size = sc.nextInt();
                      BufferedImage blur = Image_Blur(inputImage,size);
                      File Blurr = new File("Edited_Image.jpg");
                      ImageIO.write(blur, "jpg", Blurr);
                  }
                  if(N==8){
                      printpixelvalues(inputImage);      
                  }

        }
}







