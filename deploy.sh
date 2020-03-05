#!/bin/bash -e


APPDIR=target
if [ "_$1" != "_" ]; then
  APPDIR="$1"
fi

function bundle() {
  rm -f ${APPDIR}/bundle/function.zip

  mkdir -p $APPDIR/bundle
  cp ${APPDIR}/*-runner ${APPDIR}/bundle/
  cp ${APPDIR}/classes/bootstrap.sh $APPDIR/bundle/bootstrap
  chmod 755 ${APPDIR}/bundle/bootstrap
  cd ${APPDIR}/bundle && zip function.zip bootstrap *-runner
  jar tvf function.zip
  cd -
  cp -f $APPDIR/bundle/function.zip $APPDIR

  if [[ 2 == 1 ]]; then
    unzip -l ${APPDIR}/bundle/function.zip
    exit
  fi
}

[ -z "$NOBUNDLE" ] && bundle
