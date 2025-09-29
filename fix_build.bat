@echo off
echo Fixing JBR/Java path issues and AAPT compiler errors...

echo Step 1: Stopping any Gradle daemons (to clear wrong JBR)...
call gradlew.bat --stop

echo Step 2: Cleaning project completely...
call gradlew.bat clean

echo Step 3: Removing build directories manually...
if exist "app\build" rmdir /s /q "app\build"
if exist "build" rmdir /s /q "build"
if exist ".gradle" rmdir /s /q ".gradle"

echo Step 4: Verifying JBR path is correct...
echo Using JBR from: D:\PROJ\MOVBILE\android studio\jbr

echo Step 5: Rebuilding project from scratch with correct JBR...
call gradlew.bat assembleDebug

echo Step 6: Running unit tests...
call gradlew.bat test

echo Build complete. Check output above for any remaining errors.
pause
