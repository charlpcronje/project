@echo off
set output=combined_maven_files.txt

:: Check if the output file already exists and delete it
if exist %output% del %output%

:: Concatenate Maven pom.xml
type pom.xml >> %output%
echo. >> %output%

:: Add more Maven-related files if needed, for example:
:: type src\main\resources\application.properties >> %output%
:: echo. >> %output%

echo All Maven files have been combined into %output%.
pause