pipeline {

    agent any

    stages {
        stage("build"){
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }

            steps {
                sh 'mvn -B -DskipTests clean package'
                stash includes: 'target/*.jar', name: 'targetfiles'
            }
        }

        stage('Test') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }

            steps {
                sh 'mvn test'
            }
        }

        stage('Building image') {
            steps{
                script {
                    unstash 'targetfiles'
                    sh 'ls -l -R'
                    dockerImage = docker.build "justdevnoops/backend-oasis:$BUILD_NUMBER"
                }
            }
        }

        stage('Deploy Docker Container') {
            steps{
                sh 'docker stop backend-oasis'
                sh 'docker rm backend-oasis'
                sh 'docker run --name backend-oasis -d -p 8082:8081 justdevnoops/backend-oasis:$BUILD_NUMBER'
            }
        }

    }
}