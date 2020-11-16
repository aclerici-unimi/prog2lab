if  [[ "${BASH_SOURCE[0]}" == "$0" ]]
then
    echo -e Source this file with \"source $0\", do not execute it.
    exit 1
fi

# source this file to update your PATH bash environment variable
export PATH=$PATH:~/uni/nc_prog2/repo/.bin

# add a stupid alias too
alias testall='find -name "Test*.java" -or -iname "soluzione.java" -execdir verifica \;'
