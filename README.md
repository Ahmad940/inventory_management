# School Management System JavaFX
# `reviews`

#Login And Sign Up Window
![](screenshots/bandicam%202020-08-28%2017-18-06-654.jpg) 


#_main pane_
#Product window
![](screenshots/bandicam%202020-08-28%2017-17-56-581.jpg)


#Issue Window(Point of Sales)
![](screenshots/bandicam%202020-08-28%2017-18-00-019.jpg)

#Category Window
![](screenshots/bandicam%202020-08-28%2017-18-01-949.jpg)

#History Window(Record)
![](screenshots/bandicam%202020-08-28%2017-18-03-808.jpg)

\
\
 <b>This project was written in kotlin and java</b>

 
### Libraries

This project uses a number of libraries:
=> javafx <p/>
=> Jfoenix <p/>
=> Apache Common Codecs <p/>
=> Mysql connector 8<p/>

This project is written in java 8, however it can be run also in the future release of java with no syntax error or problem (9, 11, 12, 13, 14, #current release, ...others)
to run this project in java 9-14 you need to set up javafx sdk and use VMOptions to set it to path. The <b>schoolmanagement.Main</b> class is the main class to run, 
java8 has the javafx inbuilt no need to set it up.


//Database. 
Concerning database, you are going to setup your database connection url in the following spring config.xml file : 
`/dbConfig.properties`


Note: *Dont forget to edit the `/dbConfig.properties` file , make sure you change the connection string to match your local host / db connection url, and need to be mysql*

using any db different than mysql will result to changing all the mysql syntax in the application to the current db syntax that you are using

### I recommend you use intellij.