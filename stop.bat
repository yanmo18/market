@echo off
chcp 65001 >nul
setlocal

set "BACKEND_PORT=9090"
set "ADMIN_PORT=8080"

echo ========================================
echo   Market - Stop local services
echo ========================================
echo.

taskkill /FI "WINDOWTITLE eq market-server*" /T /F >nul 2>&1
taskkill /FI "WINDOWTITLE eq market-admin*" /T /F >nul 2>&1

for %%P in (%BACKEND_PORT% %ADMIN_PORT%) do (
  for /f "tokens=5" %%A in ('netstat -ano ^| findstr ":%%P.*LISTENING"') do (
    echo Kill PID=%%A on port %%P
    taskkill /PID %%A /T /F >nul 2>&1
  )
)

echo.
echo Stopped backend ^(%BACKEND_PORT%^) and admin ^(%ADMIN_PORT%^).
echo Close miniprogram in WeChat DevTools separately.
echo.
pause