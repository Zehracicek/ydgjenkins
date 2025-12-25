pipeline {
  agent any
  environment {
    MAVEN_OPTS = '-Xmx512m'
  }
  stages {
    stage('Build') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B -DskipTests package'
          } else {
            bat 'mvn -B -DskipTests package'
          }
        }
      }
    }

    stage('Test') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B test'
          } else {
            bat 'mvn -B test'
          }
        }
      }
    }

    stage('Deploy') {
      // This stage runs only when the Test stage succeeded. If tests fail, the pipeline will stop and this won't run.
      when {
        expression { return currentBuild.currentResult == null || currentBuild.currentResult == 'SUCCESS' }
      }
      steps {
        script {
          if (isUnix()) {
            // Replace the echo with your real deploy command, e.g. maven deploy or shell script that performs deployment.
            sh 'echo "Tests passed — running deploy step"'
          } else {
            bat 'echo Tests passed ^& echo Running deploy step'
          }
        }
      }
    }
  }

  post {
    success {
      echo 'Pipeline completed successfully.'
    }
    failure {
      echo 'Pipeline failed (build or tests failed).'
    }
    always {
      junit '**/target/surefire-reports/*.xml'
      archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
    }
  }
}

