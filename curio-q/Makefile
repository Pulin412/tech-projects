# Run all services locally - localhost; make sure spring.profiles.active=local
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


#------------------------------------------------------------------------------------------------------------------------
#	START SERVICES LOCALLY
#------------------------------------------------------------------------------------------------------------------------

start-local-services: start-local-db start-local-kafka
	@echo "Starting local auth service in the background..."
	@make start-local-auth-service &
	@echo "Starting local user service in the background..."
	@make start-local-user-service &
	@echo "Starting local qa service in the background..."
	@make start-local-qa-service &
	@echo "All services started successfully."

#------------------------------------------------------------------------------------------------------------------------
#	STOP SERVICES RUNNING LOCALLY
#------------------------------------------------------------------------------------------------------------------------

stop-local-services: stop-app-on-port
	docker-compose down
