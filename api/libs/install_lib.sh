mvn install:install-file -Dfile=./spring-data-r2dbc-1.0.0.BUILD-SNAPSHOT.jar -DgroupId=org.springframework.data \
    -DartifactId=spring-data-r2dbc -Dversion=1.0.0.BUILD-SNAPSHOT -Dpackaging=jar

mvn install:install-file -Dfile=./spring-cloud-contract-verifier-2.2.0.BUILD-SNAPSHOT.jar -DgroupId=org.springframework.cloud \
    -DartifactId=spring-cloud-contract-verifier -Dversion=2.2.0.BUILD-SNAPSHOT -Dpackaging=jar
