# Project_of_Library
Project of Library, used Spring Boot, Spring Security for authentication, and PostgresSQL for database of user and books

"You Must Be Truly Desperate To Come Here For Help"


It was my first encounter with Spring Boot, that project exist only because it was necessary to graduate on studies (i hate libraries, wrote already 4 of them for 
different projects through my life, hate html and css even more ) so don't expect valid code or great solutions
that will help you, when i wrote it i just had two rules, write it quick and "it works? leave it be even if it work dumb way or doesn't make sense at all"

U can tell if you look at design: XD

![alt text](https://github.com/Bananeroo/Project_of_Library/blob/master/abortMePlease.PNG)

So, Project wrote in java using Spring Boot, Maven and Spring Boot Security for Authentication and access for database.

Database was based on PostgreSQL and pgAdmin

If u want to run code on your machine after cloning u must change some configure in file "/src/main/resources/application.properties" or adapt your system to 
work with it

spring.datasource.url=jdbc:postgresql://localhost:5432/MyLibrary

^That line get path to your library which mean you need to create empty library called "MyLibrary" on port 5432,
all the rows that are need for app to work will be created automatically

spring.datasource.username=postgres

spring.datasource.password=ad

^ If your postgres have other username and password just change it

spring.jpa.hibernate.ddl-auto = create-drop

//spring.jpa.hibernate.ddl-auto = update

^ uncommented line are respond for creating database from the beggining every time you run the application, if you want to save database after another run of application
just comment first line and uncomment second one but..... u also need to remove from ClientConfig and BookConfig lines of code that goes like:


  repository.saveAll(
                    List.of(b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13)
            );
 
 
 if not, every time you run app you will add books and users with same name, email, id, title etc which can be bad :)
 
 If application work you can go to web browser and type "http://localhost:8080/login" that should get you to login screen, all added user that can log in are listed
in file "ClientConfig" if u want to log as admin 

email : "zbigniew.romański@interia.pl"

password : "password"

# That's all Folks


