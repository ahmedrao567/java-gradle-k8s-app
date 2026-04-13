def call(
    String release,
    String remoteHost,
    String remoteUser,
    String sshKey,
    String remoteChartDir,
    String namespace
) {
    String chartDir = './helm-chart'
    String helmBinary = '/Users/airao/.rd/bin/helm'

    sh """
        ssh -i ${sshKey} -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} 'mkdir -p ${remoteChartDir}'

        rsync -avz -e "ssh -i ${sshKey} -o StrictHostKeyChecking=no" \
            ${chartDir}/ ${remoteUser}@${remoteHost}:${remoteChartDir}/

        ssh -i ${sshKey} -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} \
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
