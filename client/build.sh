rm -rf target
mkdir target

cp -rv ../src/main/webapp/* ./target/
fay < ../src/main/fay/script.hs > target/script.js
