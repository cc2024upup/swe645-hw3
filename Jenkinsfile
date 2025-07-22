pipeline {
  agent any
  stages {
    stage('Build Backend') {
      steps {
        dir('springboot') {
          sh 'mvn clean package -DskipTests'
          sh 'docker build -t chaochen46/survey-backend .'
          sh 'docker push chaochen46/survey-backend'
        }
      }
    }
    stage('Build Frontend') {
      steps {
        dir('vue-frontend') {
          sh 'npm install'
          sh 'npm run build'
          sh 'docker build -t chaochen46/survey-frontend .'
          sh 'docker push chaochen46/survey-frontend'
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        sh 'kubectl apply -f k8s/backend-deployment.yaml'
        sh 'kubectl apply -f k8s/frontend-deployment.yaml'
      }
    }
  }
}
