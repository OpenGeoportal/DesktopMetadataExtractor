#!/bin/sh
cd `dirname $0`
 ROOT_PATH=`pwd`
 echo $ROOT_PATH
 java -Xms256M -Xmx1024M -cp ../lib/*:../tufts_metadata_generator.start_0_1.Start --context=Default "$@" 