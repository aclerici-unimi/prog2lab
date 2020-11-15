#!/bin/sh

if [[ -n $1 ]]
then
	${BROWSER:-firefox} "http://reaper.srv.di.unimi.it/st/prog2ese/#/session/$1"
else
	${BROWSER:-firefox} "http://reaper.srv.di.unimi.it/st/prog2ese/#/overview"
fi
