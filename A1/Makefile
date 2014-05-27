main: clean build run

build:
	javac *.java

run: build
	java diameter graph.csv

experiment: clean build
	java test 1 2 3
clean:
	rm -rf *.class
