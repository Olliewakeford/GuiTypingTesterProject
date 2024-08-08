# GUI Typing Tester

This GUI Typing Tester is an application implemented in Java, using JSwing for the GUI, that allows users to test their typing speed and accuracy. It provides a variety of typing tests options, allowing you to change time limit, type of words typed and whether you type single words or sentences at a time.

## Features

- Different types of typing tests: normal sentences, nonsense single words/sentences, random words/sentences.
- Time-limited tests: Test your typing speed under pressure with a set time limit.
- Accuracy calculation: The application calculates the percentage of words typed correctly.
- Speed calculation: The application calculates your typing speed in words per minute.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) 11 or later
- Maven

### Installation

1. Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/Olliewakeford/GuiTypingTesterProject.git
```

2. Change into the project directory:

```bash
cd AdvancedJavaProject
```

3. Build the project using Maven:

```bash
mvn compile
```

4. Run the application:

```bash
mvn exec:java -Dexec.mainClass="GuiTypingTest.Main"
```

## Usage
After starting the application, follow the prompts in the GUI to start a typing test. The application will provide the text to type and calculate your speed and accuracy once you've completed the test.

## Development
This was developed for my course Advanced Java as the semester project.
