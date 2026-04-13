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
    String helmBinary = env.HELM_BINARY ?: '/Users/airao/.rd/bin/helm'
    String sshOptions = "-i ${sshKey} -o StrictHostKeyChecking=no"
    String remote = "${remoteUser}@${remoteHost}"

    sh """
        ssh ${sshOptions} ${remote} 'mkdir -p ${remoteChartDir}'

        rsync -avz -e "ssh ${sshOptions}" \
            ${chartDir}/ ${remote}:${remoteChartDir}/

        ssh ${sshOptions} ${remote} \
            "${helmBinary} upgrade --install ${release} ${remoteChartDir} \
            --namespace ${namespace} \
            --set image.repository=${imageRepository} \
            --set image.tag=${imageTag}"
    """
}
