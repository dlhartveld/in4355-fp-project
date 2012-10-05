rm -rf target
mkdir target

cd target
cp -rv ../src/main/webapp/* ./
fay ../src/main/fay/script.hs
