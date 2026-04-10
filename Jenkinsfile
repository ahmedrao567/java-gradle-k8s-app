@Library('java-gradle-k8s-lib') _

pipeline {
    agent any

    environment {
        DOCKER_IMAGE   = 'ahmedikram567/java-gradle-k8s-app'
        DOCKER_CREDS   = 'dockerhub-credentials-id'
        MAC_USER       = 'airao'
        SSH_KEY        = '/var/jenkins_home/.ssh/id_ed25519'
        GIT_REPO       = 'https://github.com/ahmedrao567/java-gradle-k8s-app'
        GIT_BRANCH     = 'main'
        HELM_RELEASE   = 'java-gradle-k8s-app'
        HELM_CHART_DIR = '/tmp/helm-charts/java-gradle-k8s-app'
        K8S_NAMESPACE  = 'default'
        REMOTE_HOST    = '10.4.159.30'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: "${GIT_REPO}", branch: "${GIT_BRANCH}"
            }
        }

        stage('Build (Gradle/Maven)') {
            steps {
                buildApp()
            }
        }

        stage('Test') {
            steps {
                runTests()
            }
        }

        stage('Docker Build & Push') {
            steps {
                buildDockerImage(env.DOCKER_IMAGE, env.DOCKER_CREDS)
            }
        }

        stage('Deploy PostgreSQL (Helm)') {
            steps {
                deployPostgresChart(
                    'postgres',
                    env.REMOTE_HOST,
                    env.MAC_USER,
                    env.SSH_KEY,
                    env.HELM_CHART_DIR,
                    env.K8S_NAMESPACE
                )
            }
        }

        stage('Deploy (Helm)') {
            steps {
                deployHelmChart(
                    env.HELM_RELEASE,
                    env.DOCKER_IMAGE,
                    env.REMOTE_HOST,
                    env.MAC_USER,
                    env.SSH_KEY,
                    env.HELM_CHART_DIR,
                    env.K8S_NAMESPACE
                )
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
