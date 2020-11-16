# source this file to update your PATH fish environment variable
set -x PATH $PATH ~/uni/nc_prog2/repo/.bin

# add a stupid alias too
alias testall 'find -name "Test*.java" -or -iname "soluzione.java" -execdir verifica \;'
