pipeline {
    agent any

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Clone Code') {
            steps {
                git branch: 'main', url:'https://github.com/cc2024upup/swe645-hw3.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh "docker build -t chaochen46/vue-frontend:latest ./vue-survey-app"
                sh "docker build -t chaochen46/spring-backend:latest ./survey"
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'cc2024upup', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push chaochen46/vue-frontend:latest'
                    sh 'docker push chaochen46/spring-backend:latest'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f frontend-deployment.yaml'
                sh 'kubectl apply -f backend-deployment.yaml'
            }
        }

        stage('Restart Deployments') {
            steps {
                sh 'kubectl rollout restart deployment vue-deployment'
                sh 'kubectl rollout restart deployment spring-deployment'
            }
        }
    }
}
