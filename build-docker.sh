mvn clean package -DskipTests -X

pushd account-service
docker build -t amitbd1508/account-service:1.0 .
popd

pushd order-service
docker build -t amitbd1508/order-service:1.0 .
popd

pushd product-service
docker build -t amitbd1508/product-service:1.0 .
popd

pushd payment-service
docker build -t amitbd1508/payemnt-service:1.0 .
popd

