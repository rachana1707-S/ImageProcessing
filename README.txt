ASSIGNMENT 6 : Image Manipulation Project
         - Ashwin Reddy
         - Rachana Sudhakar


Overview
This project is an advanced image manipulation and enhancement application that supports loading,
manipulating, and saving images in various formats, including PPM, PNG, and JPG. The application is
built using the Model-View-Controller (MVC) architecture and provides operations such as flipping,
brightening, blurring, sepia toning, histogram generation, color correction, levels adjustment,
and compression.

The program supports multiple modes of operation: script-based, interactive, and graphical user
interface (GUI) modes.


Project Structure:
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
  ├── controller/            -- Handles user input and command processing
  │    ├── ControllerInterface.java
  │    ├── ImageController.java
  │    ├── ImageprocessingController.java
  ├── model/                 -- Handles image data and operations
  │    ├── Image.java
  │    ├── ImageInterface.java
  │    ├── ImageOperations.java
  │    ├── ImageOperationsInterface.java
  ├── view/                  -- Manages user interaction and feedback
  │    ├── ImageProcessingGUI.java
  │    ├── ImageView.java
  │    ├── ViewInterface.java
  ├── Main.java              -- Entry point for the program

/test/
  ├── ImageModelTest.java    -- Tests for the model components
  ├── ImageControllerTest.java -- Tests for the controller
  ├── EqualsHashTest.java    -- Tests for equality and hash methods
  ├── ImageprocessingControllerTest.java    -- Tests the GUI
  ├── mock
       ├── MockImageprocessingController.java


Explanation of Classes:

Controller

controller.ImageController
Manages interactions between the user’s commands (via script or GUI) and operations on images.
Responsible for orchestrating actions such as loading, saving, and applying manipulations to images.
Reads and writes images in PPM, JPG, and PNG formats.
Uses the ImageOperations class for implementing operations such as flipping, brightening, darkening,
and advanced manipulations like compression and histogram adjustments.

controller.ImageprocessingController
Handles additional GUI-specific operations such as event handling and dynamic updates.
Provides listeners for actions like applying filters, saving images, and adjusting brightness.
Facilitates communication between GUI components and the underlying model operations.

Model

model.Image
Represents the fundamental structure of an image.
Stores pixel data, image width, and height.
Provides methods to:
Retrieve and modify pixel values.
Access metadata such as dimensions.
Deep-copy image data to maintain immutability.

model.ImageOperations
Implements the core image processing logic.
Operations include:
Basic Operations: Flipping (horizontal/vertical), Brightening, Darkening.
Color Component Manipulations: Red, Green, Blue channels; Value, Luma, and Intensity visualization.
Filters: Blur, Sharpen, Sepia, Greyscale.
Advanced Features:
Split view for selective application of operations.
Histogram generation and levels adjustment.
Compression using Haar wavelet transform.
Color correction to align histogram peaks.

model.ImageInterface
Defines the contract for accessing and manipulating image data.
Ensures extensibility and adherence to the single responsibility principle.

model.ImageOperationsInterface
Specifies methods for all supported image operations, ensuring consistency in implementations.

View

view.ImageView
Handles text-based interaction with the user.
Displays feedback, error messages, and status updates for script-based commands.

view.ImageProcessingGUI
Manages the graphical user interface.
Provides components like buttons, sliders, and menus for interacting with the program.
Displays updated image previews and RGB histograms.
Enables users to load, manipulate, and save images interactively.

view.ViewInterface
Defines the common functionality required for any type of user interface (text-based or GUI).

Main
The entry point of the application.
Initializes the ImageController and/or ImageprocessingController based on the selected execution
mode (script, interactive, or GUI).Accepts command-line arguments to determine the mode and inputs
(e.g., script path or GUI launch).


Design Changes and Justifications

Method Parameter Addition:
We modified existing methods to include a new parameter for percentage split, enabling more flexible
functionality without creating overloaded methods. Edited some methods to include a new parameter
for percentage split, rather than creating overloaded methods. This change allows more precise
control over processing without duplicating method signatures, which keeps the code cleaner and
reduces redundancy.

Interface Adjustments:
Minor updates were made to the interface to accommodate this parameter, maintaining compatibility
with the rest of the design.

Edge Detection Feature:
Added a new image processing operation, aligning it with existing modular filter classes.

Split View Feature:
Added the ability to apply operations to specific sections of the image based on a user-defined
percentage. This improves usability for selective image editing.

Histogram Generation:
Designed to produce a 256x256 RGB histogram image with optional grid patterns. This feature enhances
the program's visualization capabilities.

Compression with Haar Wavelet Transform:
Introduced visual compression that reduces pixel density while retaining a perceivable resemblance
to the original image.

Levels Adjustment and Color Correction:
Implemented advanced image manipulation tools for precise tonal adjustments and histogram alignment.

Interactive Mode:
Added support for interactive user commands (-text), making the program more flexible for users
without predefined scripts.

GUI Development:
Enhanced usability with a GUI following MVC principles, providing intuitive controls and visual
feedback.


Image Citations:
Sunflower Image : (JPG)
 Fir0002, "Sunflower (Sunfola variety) against a blue sky," Wikimedia Commons,
 https://commons.wikimedia.org/wiki/File:Sunflower_sky_backdrop.jpg, accessed October 23, 2024.
 Licensed under Creative Commons Attribution-NonCommercial 3.0 Unported (CC BY-NC 3.0) and GNU Free
 Documentation License 1.2.

Lion Image : (PNG)
 Charles J. Sharp, "Lion (Panthera leo) male, six years old, Phinda Private Game Reserve,
 KwaZulu Natal, South Africa," Wikimedia Commons,
 https://commons.wikimedia.org/wiki/File:Lion_(Panthera_leo)_male_6y.jpg, accessed October 23, 2024.
 Licensed under Creative Commons Attribution-ShareAlike 4.0 International (CC BY-SA 4.0).

Simple PPM Image : (PPM)
 Ashwin Reddy, "simple.ppm image," October 2024. This image was hard-encoded by Ashwin Reddy (Me),
 who owns the rights to this work and authorizes its use in this project.


Output:
The manipulated images will be saved to the Output/ folders in the respective image format
directory (PPM, PNG, JPG).


Notes:
Ensure the res directory is structured as shown above for proper access to script files and images.
The script should be passed as a command-line argument when running the application.