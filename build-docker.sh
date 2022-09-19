#!/bin/bash

set -x
. ./set-env.sh

mvn clean package -DskipTests -X

pushd account-service
docker build -t amitbd1508/account-service:$ACCOUNT_DOCKER_VERSION .
popd

pushd order-service
docker build -t amitbd1508/order-service:$ORDER_DOCKER_VERSION .
popd

pushd product-service
docker build -t amitbd1508/product-service:$PRODUCT_DOCKER_VERSION .
popd

pushd payment-service
docker build -t amitbd1508/payment-service:$PAYMENT_DOCKER_VERSION .
popd

