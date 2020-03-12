def call(String podLabel, Closure code) { podTemplate(
        cloud: 'kubernetes',
        namespace: 'jenkins',
        label: podLabel,
        containers: [
                containerTemplate(
                        name: 'maven',
                        image: 'maven:latest',
                        ttyEnabled: true,
                        command: 'cat'),
                containerTemplate(
                        name: 'docker-dind',
                        image: 'docker:stable-dind',
                        ttyEnabled: true,
                        privileged: true)
        ]) {
    code() }
}
