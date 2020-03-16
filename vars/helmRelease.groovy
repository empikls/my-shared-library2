def call(String namespace, String releaseName, String valuesFile, String imageTag) {
    sh """
            helm install $releaseName \
            --namespace $namespace \
            --wait \
            --values $valuesFile ${imageTag}/javawebapp \
            --set-string image.tag=$imageTag
            helm ls
    """
}
