@echo off

set PROJECT_DIR=%~dp0..
set FAILED_XML=%PROJECT_DIR%\target\surefire-reports\testng-failed.xml

cd /d "%PROJECT_DIR%"
if exist %FAILED_XML% (
    mvn test -Dsurefire.suiteXmlFiles=%FAILED_XML%
) else (
    echo File not found: %FAILED_XML%
)