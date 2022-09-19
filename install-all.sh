#!/bin/bash

set -x
set -e
. ./set-env.sh

. ./build-docker.sh
. ./docker-publish.sh
. ./install-app.sh
. ./install-db.sh
