# IMago - Image Processing Application

IMago is a JavaFX-based image processing application designed for pedagogical purposes. It allows users to apply filters, resize images, and add custom image operations. The application provides an intuitive interface with a layout similar to image editing software like Photoshop, featuring menus for various image operations, toolbar icons, and interactive views.

## Project Structure

The folder structure is organized to keep Java code, resources, and tests separated for better maintainability. Below is the structure of the project:

```
imago2024/
└───src
    ├───main
    │   ├───java
    │   │   └───co
    │   │       └───itpex
    │   │           └───imago
    │   │               └───controllers      # Controller classes for handling UI events
    │   └───resources
    │       ├───css                         # CSS files for styling the application
    │       ├───fmx                         # FXML files for defining UI structure
    │       └───images                      # Image resources
    │           └───toolbar-icons           # Icons for the toolbar buttons
    └───test
        ├───java                            # Unit and integration tests
        └───resources                       # Test resources such as mock data and sample images
```

## Features

- **Image Loading and Saving**: Load images from the filesystem and save them after processing.
- **Image Operations**: Apply different operations such as resizing, cropping, and applying filters.
- **Customizable Toolbar**: Includes icons and tools for quick access to operations.
- **Modular Design**: Easily extendable with new image processing features.

## Requirements

To build and run the project, you will need:

- **Java JDK 11 or later** (required for JavaFX modules)
- **Maven** (for managing dependencies and building the project)
- **JavaFX** libraries (JavaFX modules need to be specified in the `pom.xml`)

> **Note**: The project is compatible with Java 11 and above. If you are using Java 8, JavaFX dependencies need to be adjusted accordingly.

## Getting Started

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/username/imago.git
   ```

2. **Navigate to the Project Directory**:

   ```bash
   cd imago
   ```

3. **Build the Project with Maven**:

   Make sure you have Maven installed and configured correctly.

   ```bash
   mvn clean install
   ```

4. **Run the Application**:

   ```bash
   mvn javafx:run
   ```

   Alternatively, if you are using an IDE like IntelliJ IDEA or Eclipse, you can run the `MainApp` class directly.

## Configuration

### 1. **FXML Files**
All `.fxml` files defining the structure of the UI should be placed in the `src/main/resources/fmx` directory. These files define how each component is arranged and connected.

### 2. **CSS Styles**
The application's styling is managed through `.css` files located in `src/main/resources/css`. You can modify or add new CSS files to change the appearance of the components.

### 3. **Icons and Images**
All icons used in the toolbar and any other images should be stored in `src/main/resources/images/toolbar-icons`. For example:

```
toolbar-icons/
├── open.png
├── save.png
└── filter.png
```

### 4. **Controllers**
Java controllers are located in the `src/main/java/co/itpex/imago/controllers` directory. Each `.fxml` file typically has a corresponding controller class to handle user interactions and events.

## Running Tests

Test files are organized under the `src/test` directory. To run the tests:

```bash
mvn test
```

Tests should include unit tests for image operations, as well as integration tests to verify UI elements and interactions.

## Contributing

Feel free to contribute to IMago! If you have a new feature or improvement in mind, please follow these steps:

1. Fork the repository.
2. Create a new branch (`feature/new-feature`).
3. Commit your changes.
4. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For any questions or suggestions, please reach out at [rgfernan@gmail.com](mailto:rgfernan@gmail.com).

