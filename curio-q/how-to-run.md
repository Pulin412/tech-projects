
# local-setup
- Run the services locally with `spring.profiles.active=local`
- Use the target `start-local-services` from [Makefile](https://github.com/Pulin412/tech-projects/blob/main/curio-q/Makefile)

### Using Docker images
- Run the script [start-curioq-local.sh](https://github.com/Pulin412/tech-projects/blob/main/curio-q/scripts/environments/local/start-curioq-local.sh) to start all services with dependencies.
- Run the script [stop-curioq-local.sh](https://github.com/Pulin412/tech-projects/blob/main/curio-q/scripts/environments/local/stop-curioq-local.sh) to stop all services and dependencies.

  > CI workflow could be used here to update the docker images but directory change is required everytime in Docker files.

------------------------------------------------------------------------------------------------------------------------------------------------------------

# dev-setup
- Kubernetes is used for management of containerized services. 

### Running kubernetes deployed services
- Run the script [start-deployments.sh](https://github.com/Pulin412/tech-projects/blob/main/curio-q/scripts/environments/minikube/start-deployments.sh) to apply all deployments.
- Run the script [remove-deployments.sh](https://github.com/Pulin412/tech-projects/blob/main/curio-q/scripts/environments/minikube/remove-deployments.sh) to remove all deployments.

### Detailed explanation of Kubernetes deployed features

#### CI
- Docker images are pushed to Docker hub using CI [workflows](https://github.com/Pulin412/tech-projects/tree/main/curio-q/.github/workflows)

#### Services/Deployments
- Pods are deployed using Docker desktop enabled Kubernetes (For local setup)
- Make sure [create-topic](https://github.com/Pulin412/curio-q/blob/main/kubernetes/kafka-create-topic.yaml) job is completed before hitting the endpoints for qa-service.

#### Ingress
- Services can only be reached through the Ingress with details -
> **Host**: curioq.com \
> **Port**: 80

- Make sure the hosts file is updated to reach the dummy domain `curioq.com` in the `/etc/hosts` file
```shell
127.0.0.1    curioq.com
```

- Enable the ingress controller
```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.0/deploy/static/provider/cloud/deploy.yaml
kubectl get pods --namespace=ingress-nginx
```

- Create the ingress
```shell
kubectl apply -f curioq-api-ingress.yaml
kubectl get ing
```

- Port forwarding if necessary (Not used in the postman currently)
```shell
kubectl port-forward -n ingress-nginx service/ingress-nginx-controller 3000:80
```

#### ConfigMaps
- A configMap [db-config-map](https://github.com/Pulin412/tech-projects/blob/main/curio-q/kubernetes/db-config-map.yaml) is added for the Database related properties and added as env variables ref.

#### Metrics
- Create the metric service using the downloaded components.yaml from [here](https://github.com/kubernetes-sigs/metrics-server/releases)
    ```shell
    kubectl apply -f components.yaml
    ```
- Verify nodes and pods
    ```shell
    kubectl top nodes
    kubectl top pod -A
    ```

------------------------------------------------------------------------------------------------------------------------------------------------------------

## Load Test

- Load test scripts are added [here](https://github.com/Pulin412/tech-projects/blob/main/curio-q/scripts/load-testing/)
- Currently only 1 scenario : `when a user likes another user` is load tested as sample.
- Run the load test (for above scenario) using - 

  ```shell
   k6 run -e K6_ENV=local -e SERVICE_URL=http://localhost:8081/api/v1/like -e AUTH_URL=http://localhost:8080/api/v1/auth/token user-load-test.js
  ```
- Replicate the other load testing scenarios in the same script as needed.
- This is tested for `local` environment only currently.

  > [k6](https://k6.io/docs/get-started/running-k6/) CLI is required to run the load test.