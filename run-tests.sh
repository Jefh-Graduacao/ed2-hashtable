rm ./**/*.class
./build.sh

case $1 in

open-addressing) 
    shift
    java JefersonBuenoTrabGA.OpenAddressingMain $*
    ;;
separate-chaining) 
    java JefersonBuenoTrabGA.SeparateChaing
    ;;
*) 
    echo "Opção inválida"
    exit 0
    ;;
esac