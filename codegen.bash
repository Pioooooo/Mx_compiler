set ff=UNIX
set -e
cp ./lib/builtin.s ./buitin.s
cat | java -cp /ulib/java/antlr-4.9-complete.jar:./bin PioMxCompiler -c -o output.s