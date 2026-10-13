@echo off
chcp 65001 >nul
setlocal
set "ROOT=%~dp0.."
set "MINI=%ROOT%\miniprogram"

echo ========================================
echo   Prepare miniprogram deps
echo ========================================
echo.

if not exist "%MINI%\package.json" (
  echo [ERROR] miniprogram not found
  pause
  exit /b 1
)

pushd "%MINI%"
call npm install
if errorlevel 1 (
  echo [ERROR] npm install failed
  popd
  pause
  exit /b 1
)
popd

echo.
echo Done. Open with WeChat DevTools:
echo %MINI%
echo Then: Tools - Build npm
echo AppID: see project.config.json
echo API: miniprogram\utils\request.js ^(default http://localhost:9090^)
echo.
pause