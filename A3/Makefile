main: clean build run

build:
	javac *.java

run: build
	java A3 graph.csv 2 0.6

test: clean build
	java A3 test.csv 2 0.6

clean:
	rm -rf *.class
