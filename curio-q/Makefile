#-----------------------------------------------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------------------------------------------
# Run all services locally[spring.profiles.active=local]
#-----------------------------------------------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------------------------------------------

start-local-db:
	docker-compose up -d db

start-local-kafka:
	docker-compose up -d zookeeper kafka create-topic

start-local-auth-service:
	cd auth-service && mvn spring-boot:run

start-local-user-service:
	cd user-service && mvn spring-boot:run

start-local-qa-service:
	cd qa-service && mvn spring-boot:run

stop-app-on-port:
	@if lsof -i :8080 >/dev/null 2>&1; then \
		echo "Stopping application on port 8080..."; \
		kill -9 $(shell lsof -t -i :8080); \
	fi
	@if lsof -i :8081 >/dev/null 2>&1; then \
		echo "Stopping application on port 8081..."; \
		kill -9 $(shell lsof -t -i :8081); \
	fi
	@if lsof -i :8082 >/dev/null 2>&1; then \
		echo "Stopping application on port 8082..."; \
		kill -9 $(shell lsof -t -i :8082); \
	fi


#-----------------------------------------------------------------------------------------------------------------------
#	START SERVICES LOCALLY USING LOCALHOST
#-----------------------------------------------------------------------------------------------------------------------

start-local-services: start-local-db start-local-kafka
	@echo "Starting local auth service in the background..."
	@make start-local-auth-service &
	@echo "Starting local user service in the background..."
	@make start-local-user-service &
	@echo "Starting local qa service in the background..."
	@make start-local-qa-service &
	@echo "All services started successfully."

#-----------------------------------------------------------------------------------------------------------------------
#	STOP SERVICES RUNNING LOCALLY
#-----------------------------------------------------------------------------------------------------------------------

stop-local-services: stop-app-on-port
	docker-compose down


#-----------------------------------------------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------------------------------------------
#	START/STOP SERVICES LOCALLY IN DOCKER ENVIRONMENT [spring.profiles.active=dev]
#-----------------------------------------------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------------------------------------------

start-docker-services:
	IMAGE_TAG=latest docker-compose up -d

stop-docker-services:
	docker-compose down


#-----------------------------------------------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------------------------------------------
#	START/STOP SERVICES AS KUBERNETES CONTAINERS
#-----------------------------------------------------------------------------------------------------------------------
#-----------------------------------------------------------------------------------------------------------------------

start-minikube:
	@if [ "$$(minikube status | grep "kubelet:" | awk '{print $$2}')" != "Running" ]; then \
		echo "Starting Minikube..."; \
		minikube start --memory=4096; \
	else \
		echo "Minikube is already running."; \
	fi

create-deployments-dependencies:
	cd kubernetes && kubectl apply -f db-deployment.yaml
	cd kubernetes && kubectl apply -f db-config-map.yaml
	cd kubernetes && kubectl apply -f zookeeper-deployment.yaml
	cd kubernetes && kubectl apply -f kafka-service.yaml
	cd kubernetes && kubectl apply -f kafka-deployment.yaml

create-deployments-services:
	cd kubernetes && kubectl apply -f auth-service-deployment.yaml
	cd kubernetes && kubectl apply -f user-service-deployment.yaml
	cd kubernetes && kubectl apply -f qa-service-deployment.yaml

print-auth-service-url:
	@minikube service auth-service-service --url

print-user-service-url:
	@minikube service user-service-service --url

print-qa-service-url:
	@minikube service qa-service-service --url

#-----------------------------------------------------------------------------------------------------------------------
#	START ALL DEPLOYMENTS
#-----------------------------------------------------------------------------------------------------------------------

deploy-curioq-services:
	@make start-minikube
	@sleep 10  # Add a 10-second delay
	@make create-deployments-dependencies
	@sleep 10  # Add a 10-second delay
	@make create-deployments-services

#-----------------------------------------------------------------------------------------------------------------------
#	PRINT ALL MINIKUBE SERVICE URLs
#-----------------------------------------------------------------------------------------------------------------------

print-all-service-urls:
	@echo "auth-service-url : " &
	@make print-auth-service-url &
	@echo "user-service-url : " &
	@make print-user-service-url &
	@echo "qa-service-url :" &
	@make print-qa-service-url

#-----------------------------------------------------------------------------------------------------------------------
#	REMOVE ALL DEPLOYMENTS
#-----------------------------------------------------------------------------------------------------------------------

remove-deployments:
	kubectl delete deployment db-deployment
	kubectl delete deployment auth-service-deployment
	kubectl delete deployment user-service-deployment
	kubectl delete deployment qa-service-deployment
	kubectl delete deployment zookeeper-deployment
	kubectl delete deployment kafka-deployment