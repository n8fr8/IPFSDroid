 node {
 stage "checkout"
 checkout scm

// stage "build"
// sh "./gradlew clean build"

def TAG_NAME = binding.variables.get("TAG_NAME")
if (TAG_NAME != null) {
  sh "echo $TAG_NAME"
} else {
  sh "echo Non-tag build"
}

// stage "assemble release"
// sh "./gradlew assembleRelease"
// step([$class: 'ArtifactArchiver', artifacts: '**/outputs/apk/*release*.apk', fingerprint: true])
}