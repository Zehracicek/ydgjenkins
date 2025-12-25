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
		stage('Checkout SCM') {
			steps {
				checkout scm
			}
		}

		stage('Build') {
			steps {
				script {
					if (isUnix()) {
						sh 'mvn clean -B -DskipTests package'
					} else {
						bat 'mvn clean -B -DskipTests package'
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
			post {
				always {
					junit '**/target/surefire-reports/*.xml'
				}
			}
		}

		stage('Deploy') {
			when {
				expression { currentBuild.currentResult == 'SUCCESS' }
			}
			steps {
				script {
					if (isUnix()) {
						sh '''
                      echo "Tests passed — running deploy step"
                      # Buraya deploy komutlarınızı ekleyin
                      # Örnek: scp target/*.jar user@server:/path/
                   '''
					} else {
						bat '''
                      echo Tests passed
                      echo Running deploy step
                      REM Buraya deploy komutlarınızı ekleyin
                   '''
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
			archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true, fingerprint: true
		}
	}
}