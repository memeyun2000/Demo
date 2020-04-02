#!/bin/bash


function func1()
{
    echo "\$funcname len:${#FUNCNAME[@]}"
    echo "\$funcname[0]:${FUNCNAME[0]}"
    echo "\$funcname[1]:${FUNCNAME[1]}"
    echo "\$funcname: ${FUNCNAME[@]}"
}


func1
