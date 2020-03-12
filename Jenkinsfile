#!groovy

def lib = library identifier: 'my-shared-library2@master', retriever: modernSCM(github(repository: 'my-shared-library2', repoOwner: 'empikls'))

def podLabel = "label2"

properties([
  parameters([
    string(name: 'dockerTag', defaultValue: '', description: 'git tag or short commit from upstream build' )
  ])
])

helmTemplate(podLabel) {
  node(podLabel){

    git credentialsId: 'gitHub',
            url: 'https://github.com/empikls/control-release.git'
    

    def yamlFilePathList = fileList('.')


    List stages = [] 

    yamlFilePathList.each {
        stages.add( SpringBootHelloWorldClassLoad(it) )
    }


    def stagesList = changeSetFiles()


    if ( isShortCommit(params.dockerTag) ) stagesList.add( 'dev/hollychain.yaml' )

    if ( isBuildingTag(params.dockerTag) ) stagesList.add( 'qa/emptyworld.yaml' )

    println(stagesList)

    stages.each { stage -> 
      stagesList.each { prod -> 
          if ( stage.stageName == prod ) 
            if ( prod == 'dev/hollychain.yaml' || prod == 'qa/emptyworld.yaml' )
              stage.initDeploy( prod, params.dockerTag ) 
            else {
              def tag = readYaml(file: prod).image.tag
              if ( isExistsGitTags(tag) ) stage.initDeploy(prod, tag)
          }
      }
    }
       
    stages.each {
        it.deployHelmStage(this,steps)
    }
  }
}  
