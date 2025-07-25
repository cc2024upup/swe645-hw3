pipeline {
  agent any

  environment {
    COMMIT_HASH = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
  }

  stages {
    stage('Build Backend') {
      steps {
        dir('survey') {
          sh 'chmod +x mvnw'
          sh './mvnw clean package -DskipTests'
          withCredentials([usernamePassword(credentialsId: 'cc2024upup', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
            sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
          }
          sh "docker build -t chaochen46/survey-backend:${COMMIT_HASH} ."
          sh "docker push chaochen46/survey-backend:${COMMIT_HASH}"
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
          sh "docker build -t chaochen46/survey-frontend:${COMMIT_HASH} ."
          sh "docker push chaochen46/survey-frontend:${COMMIT_HASH}"
        }
      }
    }

    stage('Deploy to Kubernetes') {
      steps {
        sh "kubectl set image deployment/survey-backend survey-backend=chaochen46/survey-backend:${COMMIT_HASH}"
        sh "kubectl set image deployment/survey-frontend vue-frontend=chaochen46/survey-frontend:${COMMIT_HASH}"
      }
    }
  }
}
