#!/bin/bash


while getopts "hp:d:l:v" o; do
    case ${o} in
        h)
            echo "show help"
            ;;
        d)
            echo "show dir:${OPTARG}" 
            ;;
        p)
            echo "show port:${OPTARG}"
            ;;
        l)
            echo "show local:${OPTARG}"
            ;;
        v)
            echo "show version"
            ;;
        esac
done
