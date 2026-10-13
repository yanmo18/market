@echo off
chcp 65001 >nul
setlocal
set "ROOT=%~dp0.."
set "SERVER=%ROOT%\server"
set "BACKEND_PORT=9090"
set "UPLOAD_DIR=D:\pic"

if not exist "%UPLOAD_DIR%" mkdir "%UPLOAD_DIR%" 2>nul

netstat -ano | findstr ":%BACKEND_PORT%.*LISTENING" >nul
if not errorlevel 1 (
  echo Backend already running on %BACKEND_PORT%
  exit /b 0
)

where mvn >nul 2>&1
if errorlevel 1 (
  echo [ERROR] mvn not found
  exit /b 1
)

echo Starting backend http://localhost:%BACKEND_PORT%
cd /d "%SERVER%"
title market-server
mvn spring-boot:run