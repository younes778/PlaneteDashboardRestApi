Planete Dashboard
===================================
## Principal

PLANETE DASHBOARD is a dashboard that allow to centralize data around a single point so you can efficiently track your activitiy. 
The complexity of the data and their evolution make it harder to extract useful information and report analytic dashboards. PLANETE DASHBOARD allows you to generate reports around Key Performace Indicators  (KPI) with multiple goals:

• Help you increase you turnover.

• Amplify your clients satisfaction. 

• Optimize buying and stocks.

• Help your company to be more agile and reactif. 


## Architecture
Planète Dashboard has many parts involved in:

*	[Mobile App] It launches in a smartphone (with Operation system Android), able to represent the data and indicators in form of an analytic dashboard with explicit graphics.

*	[Minimal database]  This part represent the minimal data stocked in the application so it can run normally. Perdiocally, the data get updated. We have used for this part Realm a very scalable database based on SQLite.

*	[Synchronization Server]  Allows the secured connexion between the mobile app reside in the smartphone with the data reside in distant database. Then, it allows unified access for all mobile users for specific databases in a secured way. This web service has been implement using Spring respecting a restful architecture using Java as programming language. 

## Technologies used
Planète Dashboard is an application with different parts, technologies that have been used are listed below :

*	[Android studio - Android & Java](https://developer.android.com/about/) the mobile app has been developed using Android Native and Java for programming.

*	[Realm](https://realm.io/docs/java/latest/)  Realm is a scalable database with very powerful functionalities based on SQLite default internal database for Android, but with an abstraction that make it more simple and rigide to use.

*	[SqlServer](https://www.microsoft.com/en-us/sql-server/sql-server-2017)  SQLServer is used to request the database distant in the client server.

* [Spring](https://spring.io/) Restfull Webservices creation to connect with the distant server having the data. 


This part is the webservices using Spring in Java.

