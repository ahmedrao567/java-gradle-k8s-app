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

    sh """
        ssh -i ${sshKey} -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} 'mkdir -p ${remoteChartDir}'

        rsync -avz -e "ssh -i ${sshKey} -o StrictHostKeyChecking=no" \
            ${chartDir}/ ${remoteUser}@${remoteHost}:${remoteChartDir}/

        ssh -i ${sshKey} -o StrictHostKeyChecking=no ${remoteUser}@${remoteHost} \
            "${helmBinary} upgrade --install ${release} ${remoteChartDir} \
            --namespace ${namespace} \
            --set image.repository=${imageRepository} \
            --set image.tag=${imageTag}"
    """
}
