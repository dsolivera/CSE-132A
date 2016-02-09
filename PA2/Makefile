all: PA2

PA2: PA2.class
	@echo "Running Program..."
	java -cp .:sqlite-jdbc-3.8.7.jar PA2
	@echo "done"

PA2.class:	PA2.java
	@echo "compiling..."	
	javac PA2.java
	@echo "done"

rm: 
	@echo "removing the database..."
	-rm pa2.db  
	@echo "done"

clean: rm
	@echo "removing all unwanted files..."
	-rm *.txt *.class
	@echo "done"

add1: rm
	@echo "input1 and solution1"
	sqlite3 pa2.db < input1.sql
	sqlite3 pa2.db < result1.sql
	@echo "done"

add2: rm
	@echo "input2 and solution2"
	sqlite3 pa2.db < input2.sql
	sqlite3 pa2.db < result2.sql
	@echo "done"

send:
	@echo "sending file..."
	scp ./PA2.java osumo@ieng6.ucsd.edu:/home/linux/ieng6/cs132w/osumo
	@echo "done"

