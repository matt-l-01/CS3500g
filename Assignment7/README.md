# Updates with Mosaicking - Matthew Love & Ritu Shah

- Implement image mosaicking correctly: Yes
- Supported a script command: Yes
- Exposed it through the GUI: Yes

In order to implement the mosaicking feature to the current program, we started by creating a new command to fit in the command design pattern "Mosaic". We then added this to their list of known commands in the RasterImageManager along with an additional method to handle the mosaic command. We then added a mosaic implementation into the RasterImageModel class which handles the actual calculations for the mosaic operation. Then, the GUIImageProcessorView class was updated to allow the addition of a mosaic button and field for seed amount (along with implementing the listeners for that button). Lastly, we added support for the script command in the TextImageController such as adding it to the list of commands in the menu, and ensuring the text command properly called the command object.

-------------

# ImageProcessingP3

### Changes:
- Added new interfaces for the GUI controller and view, ImageProcessorFeatures
  and ImageGUIView, and implemented them in the GUIImageProcessorView
  and GUIImageController classes, respectively.
- Added a helper class to display a histogram of an image  
  (HistogramPanel) and a helper class to convert an ImageModel
  to a BufferedImage (ImageModelConverter) to the view.
- Modified the TextImageController class to abstract the constructor and 
getFileFormat methods into the AbstractImageController class so that the
GUIImageController class could use them as well.
- Renamed the main method class from TextImageProcessor to ImageProcessor to 
reflect the fact that it can run a GUI- or text-based image processor. 
  - Modified the main method to accept the -text command line argument and removed the 
  argument that specified a relative file path root, as the assignment specified
  that all other command line arguments should be invalid.
  - Modified the main method to create a different controller
  and view depending on whether the user has specified text or
  GUI mode, and to call the appropriate start method in each case.
  - Also modified the main method to quit upon receiving an invalid command line 
  argument, as the assignment specifies, rather than running
  anyway using the default settings.
- Added a test to the TextImageControllerTest class to verify 
script parsing ability as suggested on the last self-eval
  

##Program Description:

- This program follows the typical model, view, and controller breakdown.
- The model holds the data of the images and handles the functions that
  manipulate images to perform the different actions
  offered by the program.
- The view displays the interactive shell to the
  user, either in a text-based or GUI format. 
- The controller allows the user to interact with
  the various image manipulations through the
  interactive shell and keeps track of all the images 
currently being used by the program. It also controls the 
IO loading and saving of images.

### Each tier is described in more depth below:

##Model:

###The model is split into a few components:

- The first component is the ImageModel interface
  and its corresponding concrete class RasterImageModel,
  which represents an image stored in a variety of file formats (PPM, BMP, JPG, and PNG).
- This class contains all the pixel data for an
  image and performs the different image manipulation
  operations such as visualizing a component, flipping,
  or brightening by acting on the collection of pixels.
- RasterImageModel also contains all the methods that represent the different image manipulation tools offered by the program.
- The Pixel interface has methods that get
  information about a single pixel in an image, such as
  information about its components. The RGBPixel class
  specifically represents a pixel that has a red,
  green, and blue component, but other types of pixels
  could include other components like transparency.
- The ARGB class extends the RGB class and includes the alpha component found in PNG files. No operations in the model work with the alpha, but it is stored to ensure it is saved properly to the output file.
- This is how the RasterImageModel class stores the
  loaded images, and how it gets information about
  different components of each pixel in order to do
  things like visualize a component.
- Next there is the ColorMatrix interface and corresponding class RGBColorMatrix. This class is used to represent a matrix of colors used by RasterImageModel to perform the color transformation function among other functions.
- Finally, the model contains the Kernel interface and corresponding class FilterKernel. This represents a kernel used by the filter operation in RasterImageModel.

##View:

###The view has two main parts:

- First there is the ImageTextView interface, which 
represents a text-based view for the program with the ability
to display a message to the user.
- This interface is implemented in the SimpleImageTextView class,
which can display messages to the user by printing them to a
specified Appendable destination. By default, the destination 
is System.out. The controller can use this view to prompt the
user for input and display error messages.
- The second part of the view is the graphical user interface,
which is specified by the ImageGUIView interface. This interface
extends ImageTextView, as it also displays messages to the user,
but adds more functionality like displaying a specific image and
providing buttons and other ways for the user to interact with
the program. It also communicates with the controller via the 
addFeatures methods, which allows it to add a controller that
has the information on what to do when a user interacts with a
feature of the program.
- The ImageGUIView interface is implemented in the
GUIImageProcessorView class, which represents an interactive
GUI that displays the current image in a scrollable format, 
shows a histogram of the frequency of different color components,
and provides buttons and menus for the user to interact with all
the features offered by the program. This class extends JFrame
and uses Swing to add and display the visible components.
- There are several helper classes associated with this class:
- The HistogramPanel class is an extension of JPanel that
displays a histogram of the R, G, B, and intensity components
of a specified image, which can be updated via a setter.
It calculates the frequencies of each value of each component
and overrides paintComponent to draw them as a line graph.
- The ImageModelAdapter class simply converts an ImageModel
into a BufferedImage. Our controller and model represent
images via the RasterImageModel class we wrote, but Swing
only works with images in BufferedImage format. The adapter 
class extends BufferedImage and takes in an ImageModel in
its constructor to allow for dynamic conversion of existing
ImageModel objects. This logic is stored in the view to avoid
making the controller dependent on the needs of one particular
view implementation.

##Controller:

###The controller is made of several components:

- The first of these components is the ImageController 
interface and corresponding TextImageController class.
- This class handles the user inputs, attempts to
  parse them into recognized commands, and sends the
  appropriate calls to the RasterImageManager class.
- The only required method, runProgram, runs the program
  and is specified in the ImageController interface.
- The controller implementation in the TextImageController
  class supports the commands in the ImageCommand interface
  plus loading and saving images to PPM files. The user can
  also quit or print out a menu of the possible commands,
  their usage, and the effects of the commands.
- It takes user input a line at a time, where a line
  includes a command name and additional arguments like
  a file path, the name of an image to be modified,
  and a name for the resulting image. If a line of
  input is invalid, the controller prompts the user for
  another line of input.
- The runProgram method is called by the main method
  in order to run the program.
- This class extends the AbstractImageController class, which
abstracts out functionality that is common between the text 
and GUI controllers.
- The next component is in the "command" package.
- We have implemented the set of image manipulation commands
  via the command design pattern in anticipation of adding
  more commands in future implementations.
- The command package contains a class for each of
  the different commands, each of which is a function
  object with a go() method specified by the ImageCommand
  interface. Each object links to a method specified by the
  ImageManager interface that executes the command.
- All the command objects extend the AbstractImageCommand
  class, which holds a common super constructor that links
  each command object to a particular model object that the
  command will act on.
- The command design pattern is used by the controller
  to access the model's image manipulation methods
  in a streamlined way that makes it easy to support more
  image manipulation commands later.
- The next of these components is the
  ImageManager interface and corresponding
  concrete class RasterImageManager.
- This class is responsible for loading, saving,
  and keeping track of the images in the program.
  The command design pattern accesses the image
  manipulations through the ImageManager interface.
- It loads images by storing them under a particular name
  in its record of known images. In order to manipulate
  images, this class looks up a known image in its record
  and asks that image to perform the manipulation. It then
  stores the resulting image in its record as well.
- Finally, the last part is the ImageProcessorFeatures interface,
which contains a set of methods that represent features the 
user of the program will want to interact with at any point.
- This essentially includes all the operations in the command
package as well as the loading and saving operations of the 
ImageManager.
- The GUIImageController class implements this interface and
uses the commands in the command package and its composed
ImageManager to implement all the features.
- It also takes in an ImageGUIView and tells the view to 
update which image is currently being displayed and redraw 
itself every time an image is loaded or manipulated.
- It takes user input by adding itself as the view's source 
of features. That way the view will ask the controller what 
to do every time the user interacts with the view's 
interactive components and the controller will manage how 
to respond.
- It communicates with the user by sending error messages
for the view to display if there is an issue with the user's
input, like attempting to load or save an invalid file or
brighten by an invalid amount.

##ImageProcessor:

- This class holds the main method for the program.
- It takes two different command-line arguments:
    - The first is set by the flag: "-file" followed by a 
  file path. The program will interpret the file at this path
  as the script of commands for the program to run. 
  If the file cannot be found, the program starts in its
  default mode, which is with the GUI.
    - The second is set by the flag: "-text". This specifies
  that the program will run in text-based interactive mode.
- It also creates an appropriate instance of the model, view,
  and controller, depending on the above inputs: in the
default mode, it will create an ImageGUIView and a 
GUIImageController. In text mode, it will create an 
ImageTextView and a TextImageController that reads input from
the command line. With a script file, it again runs the text
versions but uses the file as its source of inputs.
- In text mode, it calls the controller's runProgram method 
to start the program. In GUI mode, it simply refreshes the 
view and waits for the user to interact.

##Test Classes:

- The test classes were split into different packages for each package in src: model, view, and controller.
- The specifics of these test classes are documented below:

###Model Tests:

- A class was created for the RasterImageModel class called RasterImageModelTest.
- This class runs through the different image manipulation tools.
- In general, these tests load an image from the root "images" directory and then try to manipulate and assert on the pixels of the images.
- The class also has test cases for the Pixel interface.

###View Tests:

- The view test class, SimpleImageTextViewTest, uses a StringBuilder passed to the view to record the output the view sends.
- It simply asserts on the output of the view and checks the responses of the view when IOExceptions are thrown by the Appendable object. Specifically, it checks if IllegalStateException is thrown by the view.
- Since all of the GUI view's functionality happens through the prebuilt Swing library, the only thing that needs testing is whether the ImageProcessorFeatures implementation works as it should, which is handled in the controller.
- There are also tests for the two helper classes to verify that the histogram values are calculated correctly and that the converter correctly converts an ImageModel to a BufferedImage.

###Controller Tests:

- The controller test classes, called TextImageControllerTest and GUIImageControllerTest, use a mock model and mock view to assert on the interactions between the controller, the model, and the view.
- It asserts that the correct data is sent from the controller to the model when the user inputs certain shell commands.
- These commands are then logged in the mock model.
- The mock view also logs the data sent to it by the controller to see if the controller is sending the correct messages to be rendered.
- The class also tests the various exceptions that can occur if the command entered by the user is invalid.
- The controller constructor is tested for null or otherwise invalid input values.
- Finally, the controller tests if the correct exception is thrown when the model or view fails to do something.
- The test class, RasterImageManagerTest has several tests for the IO of images of the various extensions.
- The tests work by attempting to load an image out of the root "images" directory. The test will then ensure that the pixels loaded into the test RasterImageModel is the same as the contents of the file.
- This class also tests saving these images. A file is loaded, saved, and then loaded again and the contents of the file are asserted on to ensure the data is correct.
- A difficulty was encountered here, in that jpg images are almost guaranteed to change when they are repeatedly created due to the compression algorithms, so ensuring the saved file had the same contents did not make sense as a test.
- Finally, the command model was also tested using an abstract super class called "AbstractImageCommandTest" from which each command class test inherits.
- This abstract class serves largely as a mock model for the command classes to send data to. This data is then logged and can be retrieved only by the classes that inherit from AbstractImageCommandTest.

##Other Files in This Submission

### Example image is in res\bird.jpg

####Image citation:

"Guam Rail" 1994/01/01

Smithsonian's National Zoo & Conservation Biology
Institute

In the public domain.

https://nationalzoo.si.edu/object/nzp_NZP-215-24JC

###Screenshot of program with loaded image is in res\GUIExample.png

###Example script is in res\ExampleScript.txt

- The example script contains all commands accepted by the program.

### Description of how to interact with the GUI is in USEME.md