set ff=UNIX
set -e
cp ./lib/builtin.s ./buitin.s
cat | java -cp /ulib/java/antlr-4.9-complete.jar:./lib/picocli-4.6.1.jar:./bin app.PioMxCompiler -O -o output.s
cat output.s >&2