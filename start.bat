@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

set "ROOT=%~dp0"
set "SERVER=%ROOT%server"
set "ADMIN=%ROOT%admin"
set "MINI=%ROOT%miniprogram"
set "BACKEND_PORT=9090"
set "ADMIN_PORT=8080"
set "UPLOAD_DIR=D:\pic"

echo ========================================
echo   Market - One Click Start
echo   Backend + Admin
echo ========================================
echo.

sc query MySQL80 2>nul | find "RUNNING" >nul
if errorlevel 1 (
  sc query MySQL 2>nul | find "RUNNING" >nul
  if errorlevel 1 (
    echo [1/5] Trying to start MySQL80...
    net start MySQL80 >nul 2>&1
    if errorlevel 1 (
      echo       [WARN] MySQL not auto-started. Make sure MySQL is running
      echo       and import server\sql\init.sql ^(database: smarket^)
    ) else (
      echo [1/5] MySQL80 started
    )
  ) else (
    echo [1/5] MySQL is running
  )
) else (
  echo [1/5] MySQL80 is running
)
echo.

if not exist "%UPLOAD_DIR%" (
  mkdir "%UPLOAD_DIR%" 2>nul
  echo [2/5] Created upload dir %UPLOAD_DIR%
) else (
  echo [2/5] Upload dir exists %UPLOAD_DIR%
)
echo.

where mvn >nul 2>&1
if errorlevel 1 (
  echo [ERROR] mvn not found. Install Maven and add to PATH.
  pause
  exit /b 1
)
where node >nul 2>&1
if errorlevel 1 (
  echo [ERROR] node not found. Install Node.js and add to PATH.
  pause
  exit /b 1
)
where npm >nul 2>&1
if errorlevel 1 (
  echo [ERROR] npm not found. Install Node.js and add to PATH.
  pause
  exit /b 1
)
echo [3/5] Env OK ^(mvn / node / npm^)
echo.

netstat -ano | findstr ":%BACKEND_PORT%.*LISTENING" >nul
if errorlevel 1 (
  echo [4/5] Starting backend http://localhost:%BACKEND_PORT%
  start "market-server" cmd /k "cd /d "%SERVER%" && title market-server && mvn spring-boot:run"
) else (
  echo [4/5] Port %BACKEND_PORT% already in use, skip backend
)
echo.

if not exist "%ADMIN%\node_modules\" (
  echo       First run: npm install for admin...
  pushd "%ADMIN%"
  call npm install
  if errorlevel 1 (
    echo [ERROR] npm install failed
    popd
    pause
    exit /b 1
  )
  popd
)

netstat -ano | findstr ":%ADMIN_PORT%.*LISTENING" >nul
if errorlevel 1 (
  echo [5/5] Starting admin http://localhost:%ADMIN_PORT%
  start "market-admin" cmd /k "cd /d "%ADMIN%" && title market-admin && npm run serve"
) else (
  echo [5/5] Port %ADMIN_PORT% already in use, skip admin
)

echo.
echo Waiting for services...
timeout /t 8 /nobreak >nul
start http://localhost:%ADMIN_PORT%

echo.
echo ========================================
echo  Done
echo ----------------------------------------
echo  API   : http://localhost:%BACKEND_PORT%
echo  Admin : http://localhost:%ADMIN_PORT%
echo  Login : admin / 123456
echo ----------------------------------------
echo  Mini program: open with WeChat DevTools
echo  %MINI%
echo  ^(npm install, then Tools - Build npm^)
echo ----------------------------------------
echo  Close market-server / market-admin windows
echo  to stop, or run stop.bat
echo ========================================
echo.
pause