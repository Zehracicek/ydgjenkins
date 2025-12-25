pipeline {
	agent any

	environment {
		MAVEN_OPTS = '-Xmx512m'
	}

	stages {
		stage('Checkout SCM') {
			steps {
				checkout scm
			}
		}

		stage('Build') {
			steps {
				sh 'chmod +x ./mvnw'
				sh './mvnw clean -B -DskipTests package'
			}
		}

		stage('Test') {
			steps {
				sh './mvnw -B test'
			}
			post {
				always {
					junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
				}
			}
		}

		stage('Deploy') {
			when {
				expression { currentBuild.currentResult == 'SUCCESS' }
			}
			steps {
				sh '''
                echo "Tests passed — running deploy step"
             '''
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
			archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true, fingerprint: true
		}
	}
}