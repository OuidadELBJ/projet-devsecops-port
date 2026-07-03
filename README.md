# Plateforme DevSecOps : Port de Nador
<img width="730" height="342" alt="Capture_d_écran_2026-07-03_à_10 51 08-removebg-preview" src="https://github.com/user-attachments/assets/8e08be5f-bc3f-4ab1-9abc-540f85a9dff6" />


Plateforme d'automatisation du cycle de développement applicatif du Port de Nador : intégration continue, déploiement continu et contrôles de sécurité intégrés à la chaîne CI/CD.

## Sommaire

- [Contexte](#contexte)
- [Objectifs](#objectifs)
- [Stack technique](#stack-technique)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation et démarrage](#installation-et-démarrage)
- [Structure du dépôt](#structure-du-dépôt)
- [Pipeline CI/CD](#pipeline-cicd)
- [Sécurité](#sécurité)
- [Déploiement](#déploiement)
- [Documentation](#documentation)
- [Roadmap](#roadmap)
- [Auteur](#auteur)

## Contexte

Ce projet met en place, pour le Port de Nador, une plateforme DevSecOps servant de socle reproductible aux applications internes : automatisation du build, des tests, des contrôles de sécurité et du déploiement, dans une chaîne CI/CD unifiée.

## Objectifs

- Automatiser les phases de développement, d'intégration, de tests et de déploiement des applications.
- Intégrer des contrôles de sécurité automatisés (DevSecOps) dans la chaîne CI/CD pour améliorer la qualité et la sécurité du code.
- Fournir un environnement reproductible pouvant servir de base aux futurs projets informatiques du Port de Nador.
- Documenter l'architecture, les procédures de déploiement et les bonnes pratiques DevSecOps.

## Stack technique

| Brique | Outil | Rôle |
|---|---|---|
| Gestion de version | Git | Versionnement du code source |
| Orchestration CI/CD | GitLab CI | Automatisation build / tests / sécurité / déploiement |
| Conteneurisation | Docker | Packaging des applications |
| Orchestration de conteneurs | Kubernetes | Déploiement, mise à l'échelle et haute disponibilité |
| Qualité et sécurité du code | SonarQube | Analyse statique (SAST) |
| Sécurité des conteneurs et dépendances | Trivy | Scan des images Docker et des dépendances |
| Documentation | Markdown + wiki GitLab | Architecture, procédures, bonnes pratiques |

## Architecture

```
Développeur
    │
    ▼
  Git push ──────► GitLab CI/CD
                        │
                        ├── Build (image Docker)
                        ├── Tests automatisés
                        ├── Analyse sécurité (SonarQube, Trivy)
                        ├── Push vers le registre d'images
                        └── Déploiement (Kubernetes)
                                │
                                ▼
                          Cluster Kubernetes
                        (namespaces dev / staging / prod)
```
<img width="1200" height="848" alt="ar" src="https://github.com/user-attachments/assets/0379ebf9-8417-4aa1-804c-3fd1e3ea9e96" />


Le pipeline construit une image Docker, l'analyse (qualité et sécurité), la publie dans un registre d'images, puis la déploie sur le cluster Kubernetes via des manifests (ou Helm charts) versionnés dans le dépôt.

## Prérequis

- Linux (environnement de référence)
- Docker Engine 20.10+ / Docker Compose v2+
- kubectl 1.25+ et accès à un cluster Kubernetes 1.25+
- Git 2.30+
- Accès à une instance GitLab (GitLab CE 16+ auto-hébergé)

## Installation et démarrage

### Environnement local (développement)

```bash
git clone https://github.com/OuidadELBJ/projet-devsecops-port
cd projet-devsecops-port

docker compose up --build -d
docker compose ps
```

### Déploiement sur Kubernetes

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/
kubectl get pods -n devsecops-nador
```

### Dépannage (Troubleshooting)

- **`docker compose up` échoue** : vérifier que le daemon Docker est actif (`systemctl status docker`) et que les ports 80/443 ne sont pas déjà utilisés par un autre service (`sudo lsof -i :80`).
- **`kubectl` ne répond pas** : vérifier le contexte actif (`kubectl config current-context`) et la connectivité au cluster (`kubectl cluster-info`).
- **Pods en `CrashLoopBackOff`** : consulter les logs (`kubectl logs <pod> -n devsecops-nador`) et vérifier les variables d'environnement/secrets requis par le conteneur.
- **Échec du pipeline sur l'étape Security scan** : vérifier que le GitLab Runner a bien accès au registre Trivy/à l'instance SonarQube configurée dans les variables CI.

## Structure du dépôt

```
.
├── .gitlab-ci.yml           # Pipeline CI/CD
├── k8s/                     # Manifests Kubernetes
├── src/                     # Code source
├── docs/                    # Documentation technique
└── README.md                # Documentation principale
```

## Pipeline CI/CD

Le pipeline GitLab CI comprend les étapes suivantes :

1. **Build** — construction de l'image Docker de l'application.
2. **Test** — exécution des tests automatisés.
3. **Security scan** — analyse statique du code avec SonarQube et scan de l'image avec Trivy.
4. **Push** — publication de l'image validée dans le registre d'images.
5. **Deploy** — déploiement sur le cluster Kubernetes.

## Sécurité

Les contrôles de sécurité sont intégrés directement dans le pipeline CI/CD :

- **SonarQube** analyse la qualité et la sécurité du code source (SAST) à chaque build.
- **Trivy** scanne les images Docker et les dépendances à la recherche de vulnérabilités connues avant tout déploiement.
- Les secrets (identifiants, clés, tokens) sont gérés via les variables protégées de GitLab CI et les secrets Kubernetes, jamais stockés en clair dans le dépôt.

## Déploiement

Le déploiement est géré via des manifests Kubernetes versionnés dans `k8s/`. Chaque environnement (développement, staging, production) correspond à un namespace dédié, permettant d'isoler les ressources et les configurations.

## Documentation

La documentation détaillée (architecture, procédures de déploiement, bonnes pratiques DevSecOps) est disponible dans le dossier [`/docs`](./docs).

## Roadmap

- [ ] Mise en place du monitoring et de l'observabilité (Prometheus / Grafana).
- [ ] Centralisation des logs (stack ELK ou équivalent).
- [ ] Gestion des secrets renforcée (Vault ou solution native Kubernetes avec chiffrement au repos).
- [ ] Politiques de scan automatisées avec seuils de blocage (fail pipeline si vulnérabilités critiques).
- [ ] Mise en place d'un environnement de staging distinct de la production.
- [ ] Automatisation des tests de charge avant mise en production.
- [ ] Transfert de compétences et passation à l'équipe IT du Port de Nador.

## Auteur

Stagiaire, Port de Nador : EL BOJADDAINI Ouidad 
Encadrant : ADDOULI Mohamed Ilias
