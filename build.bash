set ff=UNIX
set -e
mkdir -p bin
cp ./lib/builtin.s ./builtin.s
find ./src -name *.java | javac -d bin -cp /ulib/java/antlr-4.9-complete.jar:./lib/picocli-4.6.1.jar @/dev/stdin