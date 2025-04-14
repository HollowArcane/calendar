echo D | xcopy /q/s/y "src/model/dto" "temp/tdo"

java -classpath lib\* org.jooq.codegen.GenerationTool cnf\jooq-config.xml

echo D | xcopy /q/s/y "temp/tdo" "src/model/dto"
rmdir /q/s "temp"