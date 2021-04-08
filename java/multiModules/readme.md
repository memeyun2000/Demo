直接导入就可运行
三种运行方式：
1. mvn clean package
   java -jar userWeb/target/userWeb-0.0.1-SNAPSHOT.jar

2. ide 执行 userWeb App

3. mvn -pl com.xl:userWeb -am spring-boot:run
-pl	--projects	Build specified reactor projects instead of all projects	
选项后可跟随{groupId}:{artifactId}或者所选模块的相对路径(多个模块以逗号分隔)

-am	--also-make	If project list is specified, also build projects required by the list	
表示同时处理选定模块所依赖的模块