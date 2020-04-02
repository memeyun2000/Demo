#!/bin/bash
echo "0:"
echo $0

echo "BASH_SOURCE-0:"
echo "${BASH_SOURCE-0}"




bin=$(dirname "${BASH_SOURCE-$0}")
bin=$(cd "${bin}">/dev/null; pwd)
echo "bin:"
echo "$bin"
