# PixelPerfect: Advanced Image Manipulation Suite
![PixelPerfect Banner](assets/banner.png)
## Overview
PixelPerfect is an advanced image manipulation and enhancement application that supports loading, manipulating, and saving images in various formats. Built using the Model-View-Controller (MVC) architecture, this application provides a comprehensive set of operations including flipping, brightening, blurring, sepia toning, histogram generation, color correction, levels adjustment, and compression.

## Features
![PixelPerfect Dashboard](assets/dashboard.png)
- **Multi-format Support**: Load and save images in PPM, PNG, and JPG formats
- **Triple Operation Modes**: Use script-based, interactive CLI, or full-featured GUI
- **Core Manipulations**: Flip, resize, brighten, darken, grayscale
- **Advanced Filters**: Blur, sharpen, sepia tone
- **Professional Tools**: 
  - RGB histogram generation
  - Levels adjustment
  - Split view for before/after comparisons
  - Color correction
  - Image compression using Haar wavelet transform

## Tech Stack
- **Languages**: Java
- **Architecture**: Model-View-Controller (MVC)
- **Design Patterns**: Command Pattern, Strategy Pattern
- **GUI Framework**: Java Swing
- **Testing**: JUnit
- **Version Control**: Git/GitHub

## Screenshots
### **Main Application Interface**
![PixelPerfect Interface](assets/interface.png)
### **Image Filtering**
![Image Filtering](assets/filtering.png)
### **Histogram View**
![Histogram View](assets/histogram.png)
### **Split View Comparison**
![Split View](assets/splitview.png)

## Installation
![PixelPerfect Installation](assets/install.png)
1. Clone the repository:
   ```bash
   git clone https://github.com/username/pixelperfect.git
   cd pixelperfect
   ```
2. Ensure you have Java 17 or higher installed:
   ```bash
   java --version
   ```
3. Run the application using the provided JAR file:
   ```bash
   java -jar res/Archive.jar
   ```
4. Alternatively, compile the source code:
   ```bash
   javac -d bin src/**/*.java
   java -cp bin Main
   ```

## Usage Examples
### Script Mode
```bash
java -jar Archive.jar -file res/PNG/PNGscript.txt
```

### Interactive Mode
```bash
java -jar Archive.jar -text
```

### GUI Mode
```bash
java -jar Archive.jar
```

### Command Examples
| Command | Description |
| ------- | ----------- |
| `load res/PNG/lion.png lion` | Load PNG image |
| `brighten 10 lion lion-brighter` | Increase brightness |
| `vertical-flip lion lion-flipped` | Flip image vertically |
| `blur lion lion-blurred` | Apply blur filter |
| `save res/PNG/Output/lion-blurred.png lion-blurred` | Save processed image |

## Project Structure
```
/res/
  ├── JPG/
  │    ├── Output/           -- Output images for JPG processing
  │    ├── JPGscript.txt     -- Example script for JPG images
  │    └── sunflower.jpg     -- Example JPG image
  ├── PNG/
  │    ├── Output/           -- Output images for PNG processing
  │    ├── PNGscript.txt     -- Example script for PNG images
  │    └── lion.png          -- Example PNG image
  ├── PPM/
  │    ├── Output/           -- Output images for PPM processing
  │    ├── PPMscript.txt     -- Example script for PPM images
  │    └── simple.ppm        -- Example PPM image
  ├── Archive.jar            -- JAR file for running the program
  └── UML.jpeg               -- UML diagram of the project

/src/
  ├── controller/            -- Command processing and workflow management
  ├── model/                 -- Image data and operations
  ├── view/                  -- User interaction and feedback
  └── Main.java              -- Application entry point

/test/                       -- Testing components
```

## Key Components
### Controller
The controller manages interactions between user commands and image operations, handling different input methods and coordinating actions.

### Model
The model represents the core data structures and operations, including the fundamental image structure, pixel data management, and all image processing algorithms.

### View
The view handles user interaction through either text-based commands or the graphical interface, providing feedback and displaying results.

## Image Credits
- **Sunflower Image**: Fir0002, "Sunflower (Sunfola variety) against a blue sky," Wikimedia Commons, Licensed under CC BY-NC 3.0 and GNU GFDL 1.2.
- **Lion Image**: Charles J. Sharp, "Lion (Panthera leo) male, six years old," Wikimedia Commons, Licensed under CC BY-SA 4.0.
- **Simple PPM Image**: Created by Ashwin Reddy for this project.

## Contributing
1. Fork the repository.
2. Create a new feature branch:
   ```bash
   git checkout -b feature-branch-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to your branch:
   ```bash
   git push origin feature-branch-name
   ```
5. Open a pull request.

## Contact
For any questions or collaborations, contact [Ashwin Reddy & Rachana Sudhakar](mailto:rachanasudhakar17@gmail.com).
