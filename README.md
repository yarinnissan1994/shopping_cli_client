# Shopping CLI 💻

This project is a console-based application for shopping.

[![Java Version](https://img.shields.io/badge/Java-20-blue.svg)](https://www.java.com/en/download/)
[![MySQL](https://img.shields.io/badge/MySQL-latest-blue.svg)](https://www.mysql.com/)

## 📚 Table of Contents
1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Installation](#installation)
4. [Configuration](#configuration)
5. [Usage Instructions](#usage-instructions)
6. [Running Tests](#running-tests)
7. [Contributions](#contributions)
8. [License](#license)

## <a name="features"></a>🛠️Features
- **User authentication:** Users can securely log in to the application using their credentials. This ensures that only authorized users can access the shopping features and perform actions.
- **Command-line interface (CLI) support:** The application provides a user-friendly command-line interface, allowing users to interact with the shopping features through text-based commands.    This makes it easy to navigate the application, browse products, add items to the cart, and complete purchases, all from the command line.
- 

## <a name="technologies-used"></a>🛠️ Technologies Used
- Frontend: CMD, SQLPAD
- Backend: C#, .NET 6
- Database: SQL Server
- Deployment: Docker, Github Actions
- Testing: NUnit

## <a name="installation"></a>🔧 Installation
You can install and run the Unique ID Scanner either natively or through Docker. Please choose the most suitable method for you.

## 🖥️ Native Installation
1. Clone the repository: `git clone https://github.com/Eli2694/KLA.git`
2. Navigate to the project directory: `cd KLA`
3. Restore the required dependencies: `dotnet restore UniqueIdsScannerUI/UniqueIdsScannerUI.csproj`
4. Build the app: `dotnet build UniqueIdsScannerUI/UniqueIdsScannerUI.csproj`
5. Navigate to the execution directory: `cd UniqueIdsScannerUI/UniqueIdsScannerUI/bin/debug/bin`
6. Start the application: `dotnet UniqueIdsScannerUI.dll`
7. 
## 🐳 Docker Installation
1. Make sure Docker is up and running on your machine.
2. Clone the repository: `git clone https://github.com/Eli2694/KLA.git`
3. Navigate to the project directory: `cd KLA`
4. Run the Docker Compose command: `docker-compose up -d`
5. Run the Docker command: `docker exec -it [ContainerId] bash`
6. Start the application: `dotnet UniqueIdsScannerUI.dll`


> ⚠️ **Note:** When running the application via Docker, ensure the settings in `appconfig.json` reflect the Docker environment setup.

## <a name="configuration"></a>⚙️ Configuration
Before running the application, users need to update the `appconfig.json` file with the following details:
- **Field1.UsernameAndPassword**: Your credentials for accessing the SQL instance. Please input as follows: ["username","password"]
- **Field2.XmlFilesPath**: The path to the XML files that you wish to scan
- **Field3.GenerateReport**: Specify where you would like to store generated reports. This can be a local or remote path

## <a name="usage-instructions"></a>📖 Usage Instructions
Our application provides both automated and manual modes. Here are the command-line options you can use:
- `-f`, `--filePath`: The XML file path. Usage: `-f C:\folder\file.xml`
- `-r`, `--rename`: To create a new alias. Usage: `dotnet UniqueIdsScannerUI.dll --update -r`
- `--generate-report`: To generate a report. Usage: `dotnet UniqueIdsScannerUI.dll --generate-report`
- `--update`: To verify and update. Usage: `dotnet UniqueIdsScannerUI.dll --update`
- `--verify`: To just verify. Usage: `dotnet UniqueIdsScannerUI.dll --verify`

## <a name="running-tests"></a>🧪 Running Tests
We have a comprehensive suite of unit tests implemented via NUnit. Ensure your installation is working as expected by running these tests.

## <a name="contributions"></a>👥 Contributions
As of now, we are not accepting contributions to this project.

## <a name="license"></a>📄 License
There is no specific license associated with this project.
