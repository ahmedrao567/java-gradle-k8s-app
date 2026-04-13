def call(
    String release,
    String imageRepository,
    String remoteHost,
    String remoteUser,
    String sshKey,
    String remoteChartDir,
    String namespace
) {
    String chartDir = './helm-chart'
    String imageTag = 'latest'
    String helmBinary = '/Users/airao/.rd/bin/helm'
    String kubectlBinary = '/Users/airao/.rd/bin/kubectl'

    sh """
        ssh -i ${sshKey} -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} 'mkdir -p ${remoteChartDir}'

        rsync -avz -e "ssh -i ${sshKey} -o StrictHostKeyChecking=no" \
            ${chartDir}/ ${remoteUser}@${remoteHost}:${remoteChartDir}/

        ssh -i ${sshKey} -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} \
            "${helmBinary} upgrade --install ${release} ${remoteChartDir} \
            --namespace ${namespace} \
            --set image.repository=${imageRepository} \
            --set image.tag=${imageTag} && \
            KCTL=${kubectlBinary} && [ -x \$KCTL ] || KCTL=kubectl && \
            (pkill -f "\$KCTL -n ${namespace} port-forward svc/${release} 8081:80" || true) && \
            nohup \$KCTL -n ${namespace} port-forward svc/${release} 8081:80 > /tmp/${release}-port-forward.log 2>&1 &"
    """
}
