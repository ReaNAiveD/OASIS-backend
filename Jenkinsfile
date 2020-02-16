node{
    stage('git clone'){
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'c6d04627-b65d-4d9e-9116-3915071ad7af', url: 'http://212.129.149.40/171250576_justdevnoops/backend-oasis.git']]])
    }
    stage('mvn test'){
        withMaven(maven: 'maven3.5.4') {
                sh "mvn test"
        }
    }
    stage('mvn build'){
        withMaven(maven: 'maven3.5.4') {
                sh "mvn clean install -Dmaven.test.skip=true"
        }
    }
    stage('mvn run'){
        sh "cp -f target/oasis-0.0.1-SNAPSHOT.jar /home/lxc/webapp/backend-oasis.jar"
		sh "BUILD_ID=dontKillMe"
        sh "/home/lxc/backend_start.sh"
    }
}