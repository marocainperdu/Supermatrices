@echo off
echo Compilation des classes Java...
javac -d bin src/main/java/supermat/*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation réussie!
    echo.
    echo Exécution des tests principaux:
    java -cp bin supermat.TestSupermat
    echo.
    echo ================================================
    echo.
    echo Exécution de la démonstration complète:
    java -cp bin supermat.DemoSupermat
) else (
    echo Erreur de compilation!
)
pause
