#!/bin/bash

set -x
. ./set-env.sh

docker push amitbd1508/account-service:$ACCOUNT_DOCKER_VERSION
docker push amitbd1508/order-service:$ORDER_DOCKER_VERSION
docker push amitbd1508/product-service:$PRODUCT_DOCKER_VERSION
docker push amitbd1508/payment-service:$PAYMENT_DOCKER_VERSION

