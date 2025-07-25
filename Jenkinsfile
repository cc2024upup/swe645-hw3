pipeline {
  agent any
  stages {
    stage('Build Backend') {
      steps {
        dir('survey') {
          sh 'chmod +x mvnw'
          sh './mvnw clean package -DskipTests'
          withCredentials([usernamePassword(credentialsId: 'cc2024upup', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
            sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
          }
          sh 'docker build -t chaochen46/survey-backend .'
          sh 'docker push chaochen46/survey-backend'
        }
      }
    }
    stage('Build Frontend') {
      steps {
        dir('vue-survey-app') {
          sh 'npm install'
          sh 'npm run build'
          withCredentials([usernamePassword(credentialsId: 'cc2024upup', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
            sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
          }
          sh 'docker build -t chaochen46/survey-frontend .'
          sh 'docker push chaochen46/survey-frontend'
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        sh 'kubectl apply -f backend-deployment.yaml'
        sh 'kubectl apply -f frontend-deployment.yaml'
        sh 'kubectl apply -f survey-backend-service.yaml'
        sh 'kubectl apply -f survey-frontend-service.yaml'
      }
    }
  }
}
