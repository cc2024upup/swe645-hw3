pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('your-dockerhub-credentials-id')
    }

    stages {
        stage('Clone') {
            steps {
                git url: 'https://github.com/cc2024upup/swe645-hw3.git', branch: 'main'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker build -t chaochen46/survey-backend ./survey'
                    sh 'docker build -t chaochen46/vue-frontend ./vue-survey-app'
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        sh 'docker push chaochen46/survey-backend'
                        sh 'docker push chaochen46/vue-frontend'
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh 'kubectl apply -f ./k8s/backend-deployment.yaml'
                    sh 'kubectl apply -f ./k8s/frontend-deployment.yaml'
                }
            }
        }
    }
}
