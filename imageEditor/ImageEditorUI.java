package imageEditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageEditorUI extends JFrame {
    private BufferedImage currentImage;
    private JLabel imageLabel;
    private JPanel controlPanel;
    private File currentFile;
    private static final imageEditor editor = new imageEditor(); // Instance of imageEditor
    
    public ImageEditorUI() {
        setTitle("Image Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main components
        createMenuBar();
        createImagePanel();
        createControlPanel();
        
        // Set window size
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> openImage());
        
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveImage());
        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void createImagePanel() {
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        // Create buttons for each operation
        JButton grayscaleBtn = new JButton("Grayscale");
        JButton rotateClockwiseBtn = new JButton("Rotate Clockwise");
        JButton rotateCounterClockwiseBtn = new JButton("Rotate Counter-Clockwise");
        JButton flipHorizontalBtn = new JButton("Flip Horizontal");
        JButton flipVerticalBtn = new JButton("Flip Vertical");
        JButton brightnessBtn = new JButton("Adjust Brightness");
        JButton blurBtn = new JButton("Blur");
        
        // Add action listeners
        grayscaleBtn.addActionListener(e -> applyGrayscale());
        rotateClockwiseBtn.addActionListener(e -> rotateClockwise());
        rotateCounterClockwiseBtn.addActionListener(e -> rotateCounterClockwise());
        flipHorizontalBtn.addActionListener(e -> flipHorizontal());
        flipVerticalBtn.addActionListener(e -> flipVertical());
        brightnessBtn.addActionListener(e -> adjustBrightness());
        blurBtn.addActionListener(e -> applyBlur());
        
        // Add buttons to panel
        controlPanel.add(grayscaleBtn);
        controlPanel.add(rotateClockwiseBtn);
        controlPanel.add(rotateCounterClockwiseBtn);
        controlPanel.add(flipHorizontalBtn);
        controlPanel.add(flipVerticalBtn);
        controlPanel.add(brightnessBtn);
        controlPanel.add(blurBtn);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void openImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images", "jpg", "jpeg", "png", "gif");
        chooser.setFileFilter(filter);
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                currentFile = chooser.getSelectedFile();
                currentImage = ImageIO.read(currentFile);
                displayImage(currentImage);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error opening image: " + e.getMessage());
            }
        }
    }
    
    private void saveImage() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(this, "No image to save!");
            return;
        }
        
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
        chooser.setFileFilter(filter);
        
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File outputFile = chooser.getSelectedFile();
                if (!outputFile.getName().toLowerCase().endsWith(".jpg")) {
                    outputFile = new File(outputFile.getParentFile(), outputFile.getName() + ".jpg");
                }
                ImageIO.write(currentImage, "jpg", outputFile);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage());
            }
        }
    }
    
    private void displayImage(BufferedImage img) {
        if (img != null) {
            ImageIcon icon = new ImageIcon(img);
            // Scale image if it's too large
            if (icon.getIconWidth() > 700 || icon.getIconHeight() > 500) {
                icon = new ImageIcon(img.getScaledInstance(700, -1, Image.SCALE_SMOOTH));
            }
            imageLabel.setIcon(icon);
            pack();
        }
    }
    
    // Image processing methods
    private void applyGrayscale() {
        if (currentImage != null) {
            currentImage = imageEditor.convertToGrayscale(currentImage);
            displayImage(currentImage);
        }
    }
    
    private void rotateClockwise() {
        if (currentImage != null) {
            currentImage = imageEditor.rotate(currentImage);
            displayImage(currentImage);
        }
    }
    
    private void rotateCounterClockwise() {
        if (currentImage != null) {
            currentImage = imageEditor.back_rotate(currentImage);
            displayImage(currentImage);
        }
    }
    
    private void flipHorizontal() {
        if (currentImage != null) {
            currentImage = imageEditor.horizontallyInvert(currentImage);
            displayImage(currentImage);
        }
    }
    
    private void flipVertical() {
        if (currentImage != null) {
            currentImage = imageEditor.verticallyInvert(currentImage);
            displayImage(currentImage);
        }
    }
    
    private void adjustBrightness() {
        if (currentImage != null) {
            String input = JOptionPane.showInputDialog(
                this,
                "Enter brightness adjustment (-100 to 100):",
                "Adjust Brightness",
                JOptionPane.QUESTION_MESSAGE
            );
            try {
                int value = Integer.parseInt(input);
                if (value >= -100 && value <= 100) {
                    currentImage = imageEditor.changeTheBrightness(currentImage, value);
                    displayImage(currentImage);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a value between -100 and 100");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        }
    }
    
    private void applyBlur() {
        if (currentImage != null) {
            String input = JOptionPane.showInputDialog(
                this,
                "Enter blur radius (2-10):",
                "Apply Blur",
                JOptionPane.QUESTION_MESSAGE
            );
            try {
                int value = Integer.parseInt(input);
                if (value >= 2 && value <= 10) {
                    currentImage = imageEditor.Image_Blur(currentImage, value);
                    displayImage(currentImage);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a value between 2 and 10");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageEditorUI().setVisible(true);
        });
    }
}