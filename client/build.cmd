rmdir /s /q target
mkdir target

xcopy /v /l /s ..\src\main\webapp\* .\target\
fay < ..\src\main\fay\script.hs > .\target\script.js
