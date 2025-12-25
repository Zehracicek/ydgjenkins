pipeline {
	agent any

	tools {
		// Jenkins > Manage Jenkins > Tools kısmında tanımlı Maven adı
		maven 'M3'
	}

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
			when {
				expression { currentBuild.currentResult == null || currentBuild.currentResult == 'SUCCESS' }
			}
			steps {
				script {
					if (isUnix()) {
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
