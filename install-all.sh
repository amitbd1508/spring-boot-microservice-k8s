#!/bin/bash

set -x
set -e
. ./set-env.sh

. ./build-docker.sh
. ./install-app.sh
. ./install-db.sh
