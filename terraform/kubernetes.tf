resource "kubectl_manifest" "is-my-burguer-pedido-deployment" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubernetes_secret.is-my-burguer-pedido-db
  ]
  yaml_body = <<YAML
apiVersion: apps/v1
kind: Deployment
metadata:
  name: is-my-burguer-pedido
  namespace: is-my-burguer
  labels:
    name: is-my-burguer-pedido
    app: is-my-burguer-pedido
spec:
  replicas: 1
  selector:
    matchLabels:
      app: is-my-burguer-pedido
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: is-my-burguer-pedido
    spec:
      containers:
        - name: is-my-burguer-pedido
          resources:
            limits:
              cpu: "2"
              memory: "300Mi"
            requests:
              cpu: "300m"
              memory: "300Mi"
          env:
            - name: POSTGRES_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-db
                  key: password
            - name: POSTGRES_USER
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-db
                  key: username
            - name: POSTGRES_HOST
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-db
                  key: host
            - name: POSTGRES_PORT
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-db
                  key: port
            - name: CLIENT_DOMAIN
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: cognito_domain
            - name: AWS_COGNITO_USER_POOL_ID
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-cognito
                  key: user-pool-id
            - name: AWS_REGION
              value: ${local.region}
            - name: SERVICE_DISCOVERY_USERNAME
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-sd
                  key: username
            - name: SERVICE_DISCOVERY_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: is-my-burguer-sd
                  key: password
          image: docker.io/ismaelgcosta/is-my-burguer-pedido:${var.TF_VAR_IMAGE_VERSION}
          ports:
            - containerPort: 8943
      restartPolicy: Always
status: {}
YAML
}


resource "kubectl_manifest" "is-my-burguer-pedido-svc" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubectl_manifest.is-my-burguer-pedido-deployment
  ]
  yaml_body = <<YAML
apiVersion: v1
kind: Service
metadata:
  name: is-my-burguer-pedido-svc
  namespace: is-my-burguer
spec:
  selector:
    app: is-my-burguer-pedido
  ports:
    - name: https
      protocol: TCP
      port: 8943
      targetPort: 8943
YAML
}

resource "kubectl_manifest" "is-my-burguer-pedido-hpa" {
  depends_on = [
    data.aws_eks_cluster.cluster,
    kubectl_manifest.is-my-burguer-pedido-deployment,
    kubectl_manifest.is-my-burguer-pedido-svc
  ]
  yaml_body = <<YAML
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: is-my-burguer-pedido-hpa
  namespace: is-my-burguer
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: is-my-burguer-pedido
    namespace: is-my-burguer
  minReplicas: 2
  maxReplicas: 2
  behavior:
    scaleDown:
      stabilizationWindowSeconds: 0 # para forçar o kubernets a zerar a janela de tempo e escalar imediatamente
    scaleUp:
      stabilizationWindowSeconds: 0 # para forçar o kubernets a zerar a janela de tempo e escalar imediatamente
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 10 # para forçar o kubernets escalar com 10% de cpu
status:
  observedGeneration: 0
  lastScaleTime:
  currentReplicas: 2
  desiredReplicas: 2
  currentMetrics:
  - type: Resource
    resource:
      name: cpu
YAML
}

resource "kubernetes_secret" "is-my-burguer-pedido-db" {
  metadata {
    name      = "is-my-burguer-db"
    namespace = "${local.name}"
  }

  immutable = false

  data = {
    host = "${data.terraform_remote_state.is-my-burguer-db.outputs.database_endpoint_host}",
    port = "${data.terraform_remote_state.is-my-burguer-db.outputs.database_endpoint_port}",
    username = "${data.aws_db_instance.is-my-burguer-db.master_username}",
    password = "${var.TF_VAR_POSTGRES_PASSWORD}"
  }

  type = "kubernetes.io/basic-auth"
}



