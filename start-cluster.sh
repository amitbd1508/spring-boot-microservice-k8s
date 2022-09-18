set -e
set -x

minikube start --vm-driver=virtualbox --memory='4000mb'
eval $(minikube docker-env)
minikube addons enable metrics-server
