#!/bin/bash

set -x
. ./set-env.sh

kubectl delete -f k8s/account/db-deployment.yaml
kubectl delete -f k8s/order/db-deployment.yaml
kubectl delete -f k8s/payment/db-deployment.yaml
kubectl delete -f k8s/product/db-deployment.yaml
