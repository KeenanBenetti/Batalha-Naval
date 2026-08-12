@echo off
setlocal EnableDelayedExpansion

echo ==============================
echo Renomeando sprites
echo ==============================

:: Remove o zero inicial dos sprites 00 até 09
for /L %%i in (0,1,9) do (
    if exist "sprite_0%%i.png" (
        ren "sprite_0%%i.png" "sprite_%%i.png"
        echo sprite_0%%i.png ^> sprite_%%i.png
    )
)

echo.
echo ==============================
echo Cortando sprites em tiles 16x16
echo ==============================

:: Processa os 24 sprites: 0 até 23
for /L %%i in (0,1,23) do (
    if exist "sprite_%%i.png" (
        echo.
        echo Cortando sprite_%%i.png...

        magick "sprite_%%i.png" -crop 16x16 +repage "sprite_%%i_tile_%%d.png"
    )
)

echo.
echo ==============================
echo Concluido!
echo ==============================

pause