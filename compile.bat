@echo off
echo Compilation des classes Java...
if not exist bin mkdir bin
javac -d bin src/main/java/supermat/*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation réussie!
) else (
    echo Erreur de compilation!
)
pause
