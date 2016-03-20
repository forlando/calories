default:
	cd Calories && ./target/apache-maven-3.3.9/bin/mvn verify && cd ..
run:
	cd Calories && ./target/apache-maven-3.3.9/bin/mvn jetty:run && cd ..
clean:
	cd Calories && ./target/apache-maven-3.3.9/bin/mvn clean && cd ..