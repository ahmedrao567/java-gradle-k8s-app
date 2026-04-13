def call(
    String release,
    String remoteHost,
    String remoteUser,
    String sshKey,
    String remoteChartDir,
    String namespace
) {
    String chartDir = './helm-chart'
    String helmBinary = env.HELM_BINARY ?: '/Users/airao/.rd/bin/helm'
    String sshOptions = "-i ${sshKey} -o StrictHostKeyChecking=no"
    String remote = "${remoteUser}@${remoteHost}"

    sh """
        ssh ${sshOptions} ${remote} 'mkdir -p ${remoteChartDir}'

        rsync -avz -e "ssh ${sshOptions}" \
            ${chartDir}/ ${remote}:${remoteChartDir}/

        ssh ${sshOptions} ${remote} \
            "export DOCKER_CONFIG=/tmp/helm-docker-config && \
            mkdir -p \$DOCKER_CONFIG && \
            printf '{\"auths\":{}}' > \$DOCKER_CONFIG/config.json && \
            ${helmBinary} repo add bitnami https://charts.bitnami.com/bitnami || true && \
            ${helmBinary} repo update && \
            ${helmBinary} upgrade --install ${release} postgresql --repo https://charts.bitnami.com/bitnami \
            -f ${remoteChartDir}/postgres-values.yaml \
            --namespace ${namespace} \
            --create-namespace"
    """
}
