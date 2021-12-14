#!/bin/bash

milestone="Exemples/Milestone/"
modern="Exemples/Modern/"
running="Exemples/Running/"

for dir in $milestone $modern $running; do

    echo "Running ${dir} files..."
    for file in $( ls $dir ); do

        fullpath="${dir}${file}"
        echo "Testing ${fullpath} ..."
        cp $fullpath "input.txt"
        make clean > /dev/null
        make run >> testtmp.txt
        returnstring="$(grep 'Exit status 0' testtmp.txt)"

        if [ -z "$returnstring" ]; then
            echo "Erreur dans la compilation du fichier "$fullpath
            exit 1;
        fi

        echo "" > testtmp.txt

    done

done

rm testtmp.txt