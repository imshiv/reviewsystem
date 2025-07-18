# ArgoCD GitOps Setup

This folder contains environment-specific ArgoCD Application manifests for:

- dev
- staging
- prod

Each application watches the `helm/review-system/` chart and auto-deploys on Git commit.

## Deploy Example:

```bash
kubectl apply -n argocd -f argocd/dev/application.yaml
```

Ensure ArgoCD has access to your GitHub repo and the target EKS cluster.
