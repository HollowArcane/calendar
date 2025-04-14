@echo off

if "%1" == "" (
    echo Source is required
    exit /b
)

if "%2" == "" (
    echo Target is required
    exit /b
)

set "src=%cd%\%1"
set "dst=%cd%\%2"

echo Source: %src%
echo Target: %dst%

cd "%~dp0"

java -classpath ../lib/*;. Main "%src%" "%dst%"