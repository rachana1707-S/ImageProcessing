I'll give you the raw markdown code without any formatting or code blocks. Copy exactly the following text into your README.md file:
🖼️ PixelPerfect: Advanced Image Manipulation Suite
Show Image
Show Image
Show Image

Transform your ordinary images into extraordinary creations with our powerful yet intuitive image processing toolkit.

Developed by: Ashwin Reddy & Rachana Sudhakar
✨ Features

Multi-format support: Load and save images in PPM, PNG, and JPG formats
Triple operation modes: Script-based, interactive CLI, and full-featured GUI
Core manipulations: Flip, resize, brighten, darken, grayscale
Advanced filters: Blur, sharpen, sepia tone
Professional tools:

📊 RGB histogram generation
🎚️ Levels adjustment
🔍 Split view for before/after comparisons
🌈 Color correction
📉 Image compression using Haar wavelet transform



🏗️ Architecture
Built on the solid foundation of Model-View-Controller (MVC) architecture to ensure clean separation of concerns:
Model     - Manages image data and implements operations
Controller - Processes commands and orchestrates workflows
View       - Handles user interaction through CLI or GUI
📁 Project Structure
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
  ├── UML.jpeg               -- UML diagram of the project
  ├── UMLWithMethods.jpeg    -- UML diagram including methods

/src/
  ├── controller/            -- Command processing and workflow management
  │    ├── ControllerInterface.java
  │    ├── ImageController.java
  │    ├── ImageprocessingController.java
  ├── model/                 -- Image data and operations
  │    ├── Image.java
  │    ├── ImageInterface.java
  │    ├── ImageOperations.java
  │    ├── ImageOperationsInterface.java
  ├── view/                  -- User interaction and feedback
  │    ├── ImageProcessingGUI.java
  │    ├── ImageView.java
  │    ├── ViewInterface.java
  ├── Main.java              -- Application entry point

/test/
  ├── ImageModelTest.java    -- Model component tests
  ├── ImageControllerTest.java -- Controller tests
  ├── EqualsHashTest.java    -- Equality and hash method tests
  ├── ImageprocessingControllerTest.java    -- GUI tests
  ├── mock
       ├── MockImageprocessingController.java
🚀 Getting Started
Running the Application
The program supports three modes of operation:

Script Mode:
java -jar Archive.jar -file path/to/script.txt

Interactive Mode:
java -jar Archive.jar -text

GUI Mode:
java -jar Archive.jar


Example Commands
load res/PNG/lion.png lion
brighten 10 lion lion-brighter
vertical-flip lion lion-flipped
blur lion lion-blurred
save res/PNG/Output/lion-blurred.png lion-blurred
🔍 Key Components
Controller

ImageController: Manages user commands and image operations
ImageprocessingController: Handles GUI-specific operations and event handling

Model

Image: Core image data structure with pixel manipulation capabilities
ImageOperations: Implements all image processing algorithms and filters

View

ImageView: Text-based user interaction
ImageProcessingGUI: Fully-featured graphical interface with real-time previews

🌟 Design Highlights

Flexible Split-View Processing: Apply operations to selective portions of images
Wavelet-based Compression: Intelligently reduces file size while preserving visual quality
Advanced Histogram Tools: Generate visual RGB histograms with optional grid patterns
Modular Filter System: Easily extendable framework for adding new image effects

📸 Image Credits

Sunflower Image (JPG): Fir0002, "Sunflower (Sunfola variety) against a blue sky," Wikimedia Commons, Licensed under CC BY-NC 3.0 and GNU GFDL 1.2.
Lion Image (PNG): Charles J. Sharp, "Lion (Panthera leo) male, six years old," Wikimedia Commons, Licensed under CC BY-SA 4.0.
Simple PPM Image: Created by Ashwin Reddy for this project.

📋 Notes

Ensure the res directory structure is maintained for proper script and image access
Processed images are saved to the respective Output/ folders based on format
For best results in GUI mode, use a display with resolution of at least 1280x720


Transform your pixels, unleash your creativity!
