# Mindigo

**Mindigo** is a simple and powerful Java build tool designed to make your life easier when creating runnable `.jar` files. With a straightforward configuration system and intuitive commands, Mindigo streamlines the process of initializing and building Java projects.

---

## Features

- **Easy-to-use CLI:** Simple commands to initialize and build your project.
- **Configurable Builds:** Uses a `mindigo.json` file for configuration.
- **Produces Runnable JARs:** Quickly compiles your code into runnable `.jar` files.
- **Minimal Setup:** Perfect for quick project bootstrapping or scripting.

---


## Getting Started

### 1. Install Mindigo

Download or clone this repository and ensure you have Java installed.

### 2. Initialize Your Project

```sh
mindigo init
```

This will generate a `mindigo.json` configuration file in your project directory.

### 3. Configure Your Build

Edit the generated `mindigo.json` to specify your build parameters (entry class, dependencies, etc.).

### 4. Build Your Project

```sh
mindigo build
```

Mindigo will compile your source code and package it into a runnable `.jar` file based on your configuration.

---

## Example `mindigo.json`

```json
{
  "projectName": "testApp",
  "description": "Default description",
  "mainClass": "vpp.vac.test.Main",
  "version": "0.0.1",
  "libFolder": "libs",
  "srcFolder": "src"

}
```

---

## Building from src

You can build Mindigo using Mindigo itself, or you can also build using maven.

### Mindigo
1. Clone the repository
```sh
git clone https://github.com/vaclavak/Mindigo.git
```
2. Build using Mindigo, (if you have mindigo installed elsewhere and put in path, you dont have to use the provided mindigo build)
```sh
java -jar mindigo.1.0.1.jar build
```

### Maven

1. Clone the git repository
```sh
git clone https://github.com/vaclavak/Mindigo.git
```
2. Build using maven (Make sure maven you have maven installed)
```sh
mvn clean package
```

## Commands

- `mindigo init`  
  Initializes your project and creates a `mindigo.json` file.

- `mindigo build`  
  Builds your project using the settings from `mindigo.json` and produces a runnable `.jar` file.

---

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## License

This project is licensed under the MIT License.
