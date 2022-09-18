#!/bin/bash

set -x
. ./set-env.sh

kubectl delete -f k8s/account/deployment.yaml
kubectl delete -f k8s/order/deployment.yaml
kubectl delete -f k8s/payment/deployment.yaml
kubectl delete -f k8s/product/deployment.yaml
