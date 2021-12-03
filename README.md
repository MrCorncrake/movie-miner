# movie-miner
Movie miner is a program created as a part of a diploma thesis during Data Engineering studies at the Gdańsk University of Technology that will extract business processes from movie scenario and visualise them.

## Building
Requirements:
* Maven 
* Java 1.8.0
  
Use `mvn package` to build the project and create jar package.

## Usage:
NOTE: Required Java version 1.8.0
* Main usage: `java -jar [MovieMiner jar] [MODE] [INPUT FILE PATH] [OUTPUT FILE PATH]`
* Help: `java -jar [MovieMiner jar] help`
* Version: `java -jar [MovieMiner jar] version`
### Modes:
* PDF-JSON - Parses movie scenario provided in PDF format and downloaded from https://www.dailyscript.com/index.html to json
* JSON-XPDL - Creates XPDL diagram from json file obtained by parsing scenario
* PDF-XPDL - Combines PDF-JSON and JSON-XPDL modes into one
