"just run the following Charley :

in Root folder of project,type:

1.gradle build
2.find application.properties file in src/main/java/resources/ folder
edit this to suit the url of your mongo installation
3.gradle bootRun

u can then access the endpoints from localhost:8080/
you can insert a userName and password in your MongoDb with
 db.getCollection('GeneralUserDoc').find({"userName":"r2","password":"222"})

afterwards,for eg,to authenticate,just post {"userName":"r2","password":"222"} to
localhost:8080/auth with consumes header of application/json."
