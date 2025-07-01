@echo off
SETLOCAL

REM Configuration des chemins
SET MAVEN_HOME=C:\apache-maven-3.8.6
SET JAVA_HOME=C:\Program Files\Java\jdk-17
SET TOMCAT_HOME=C:\xampp\tomcat
SET PROJECT_DIR=%~dp0

REM Ajout des binaires au PATH
SET PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

REM Vérification des outils nécessaires
where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Maven n'est pas installé ou n'est pas dans le PATH
    pause
    exit /b 1
)

where java >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo Java n'est pas installé ou n'est pas dans le PATH
    pause
    exit /b 1
)

if not exist "%TOMCAT_HOME%\webapps" (
    echo Le repertoire Tomcat est introuvable: %TOMCAT_HOME%\webapps
    pause
    exit /b 1
)

echo ---------------------------------------------------
echo Construction du projet avec Maven
echo ---------------------------------------------------
cd /d "%PROJECT_DIR%"
call mvn clean package

if %ERRORLEVEL% neq 0 (
    echo Erreur lors de la construction du projet
    pause
    exit /b 1
)

echo ---------------------------------------------------
echo Copie du fichier WAR vers Tomcat
echo ---------------------------------------------------
SET WAR_FILE=%PROJECT_DIR%target\springMVC.war
if not exist "%WAR_FILE%" (
    echo Fichier WAR introuvable: %WAR_FILE%
    pause
    exit /b 1
)

copy /Y "%WAR_FILE%" "%TOMCAT_HOME%\webapps\"
if %ERRORLEVEL% neq 0 (
    echo Erreur lors de la copie du fichier WAR
    pause
    exit /b 1
)

@REM echo ---------------------------------------------------
@REM echo Redémarrage de Tomcat
@REM echo ---------------------------------------------------
@REM net stop Tomcat9
@REM if %ERRORLEVEL% neq 0 (
@REM     echo Impossible d'arreter le service Tomcat
@REM     echo Essayez de l'arreter manuellement puis relancez le script
@REM     pause
@REM     exit /b 1
@REM )

@REM net start Tomcat9
@REM if %ERRORLEVEL% neq 0 (
@REM     echo Impossible de demarrer le service Tomcat
@REM     pause
@REM     exit /b 1
@REM )

echo ---------------------------------------------------
echo Déploiement terminé avec succès!
echo L'application est disponible sur: http://localhost:8080/springMVC
echo ---------------------------------------------------
pause