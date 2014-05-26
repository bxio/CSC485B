main: clean build run

build:
	javac *.java

run: build
	java diameter

experiment: clean build
	java diameter < "graph.csv"
clean:
	rm -rf *.class
