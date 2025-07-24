pipeline {
	agent any

    stages {
		stage('Unit Test') {
			steps {
				sh 'mvn clean install'
            }
        }
        stage('Sonar code analysis') {
			environment {
				scannerHome = tool 'sonarscan'
            }
            steps {
				withSonarQubeEnv('sonarqube') {
					sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        stage('Build Docker image & scan security') {
			environment {
				DOCKER_PASS = credentials("docker_token")
            }
            steps {
				sh 'docker build -t huyfst/java .'
                sh 'docker login -u huyfst -p %DOCKER_PASS%'
                sh 'docker push huyfst/java'
                sh 'trivy image huyfst/java'
            }
        }
        stage('Deploy to AWS') {
			environment {
				AWS_ACCESS_KEY_ID = credentials('aws_access_key_id')
				AWS_SECRET_ACCESS_KEY = credentials('aws_secret_access_key')
				AWS_DEFAULT_REGION = 'us-east-1'
			}
			steps {
				sh 'aws ecs update-service --cluster java-cluster --service java-server --task-definition java-fargate:1 --force-new-deployment'
			}
		}
	}
}