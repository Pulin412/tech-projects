# local-setup

  Use application-local.properties file and run as localhost services.
  
# dev-setup

### Docker compose way

- Use application-dev.properties, docker-compose file to run in DEV environment.
- CI workflow could be used here to update the docker images but directory change is required everytime in Docker files.

### Kubernetes deployed way
#### CI

- Docker images are pushed to Docker hub using CI workflows (same as mentioned above in dev-setup)


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

- A configMap [db-config-map](https://github.com/Pulin412/curio-q/blob/main/kubernetes/db-config-map.yaml) is added for the Database related properties and added as env variables ref.

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