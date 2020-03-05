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
    stage('mvn analyse'){
        withMaven(maven: 'maven3.5.4') {
                sh "/home/lxc/jacoco_setting.sh"
                echo 'This is a jacoco coverage analysis'
                sh label: '', script: 'mvn org.jacoco:jacoco-maven-plugin:0.8.3:dump -Djacoco.address=101.37.80.37 -Djacoco.port=8082'
        }
    }

    stage('jacoco'){
        jacoco()
    }
    stage('mvn run'){
		withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
			sh "cp -f target/oasis-0.0.1-SNAPSHOT.jar /home/lxc/webapp/backend-oasis.jar"
			sh "/home/lxc/backend_start.sh"
		}
    }
}