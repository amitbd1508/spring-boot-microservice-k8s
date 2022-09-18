#!/bin/bash

set -x
. ./set-env.sh

kubectl apply -f k8s/account/deployment.yaml
kubectl apply -f k8s/order/deployment.yaml
kubectl apply -f k8s/payment/deployment.yaml
kubectl apply -f k8s/product/deployment.yaml
