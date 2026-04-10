def call(String image, String dockerCredentialsId) {
    withCredentials([
        usernamePassword(
            credentialsId: dockerCredentialsId,
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )
    ]) {
        sh """
            docker build -t ${image}:latest .
            echo "\$DOCKER_PASS" | docker login -u "\$DOCKER_USER" --password-stdin
            docker push ${image}:latest
        """
    }
}
