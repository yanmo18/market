@echo off
chcp 65001 >nul
setlocal
set "ROOT=%~dp0.."
set "SQL=%ROOT%\server\sql\init.sql"

echo ========================================
echo   Import init SQL ^(smarket^)
echo ========================================
echo.
echo File: %SQL%
echo Default: root@localhost ^(password prompt^)
echo.

where mysql >nul 2>&1
if errorlevel 1 (
  echo [ERROR] mysql client not in PATH
  echo Import server\sql\init.sql via Navicat / Workbench instead
  pause
  exit /b 1
)

mysql -u root -p < "%SQL%"
if errorlevel 1 (
  echo [ERROR] import failed
  pause
  exit /b 1
)

echo.
echo Done. Admin login: admin / 123456
pause