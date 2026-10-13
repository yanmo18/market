@echo off
chcp 65001 >nul
setlocal
set "ROOT=%~dp0.."
set "ADMIN=%ROOT%\admin"
set "ADMIN_PORT=8080"

if not exist "%ADMIN%\node_modules\" (
  echo Installing admin deps...
  pushd "%ADMIN%"
  call npm install || exit /b 1
  popd
)

netstat -ano | findstr ":%ADMIN_PORT%.*LISTENING" >nul
if not errorlevel 1 (
  echo Admin already running on %ADMIN_PORT%
  exit /b 0
)

echo Starting admin http://localhost:%ADMIN_PORT%
cd /d "%ADMIN%"
title market-admin
npm run serve