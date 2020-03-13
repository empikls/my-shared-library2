def call(String namespace, String releaseName, String valuesFile, String imageTag) {
    sh """
            helm --dry-run --debug \
            --install $releaseName \
            --namespace $namespace \
            --force \
            --wait \
            --values $valuesFile ${imageTag}/javawebapp \
            --set-string image.tag=$imageTag
            helm ls
    """
}
