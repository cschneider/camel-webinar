Camel project template
======================

This is a mostly empty maven project suitable for use with apache camel.

It already references the libraries needed to use camel. The subproject server contains the routes and starters for standalone usage. It will create a jar file that can be used standalone as well as in OSGi.
The subproject war creates a war file for deployment in tomcat and other servlet containers.


How to create your own project from the template
================================================

- Copy the folder template give it a suitable name
- Change the artifact and group id of the parent project and the two child projects
- Rename the package in the server project
- Import your project with Eclipse. Import / Existing maven project / Select your new parent directory. Import all three projects


Usage
=====

Start Standalone
----------------

> cd server; mvn camel:run

Start in jetty
--------------

> cd war; mvn jetty:run

Start in the OSGI container
---------------------------

karaf@tif> features:install talend-camel-template

(Make sure you've first installed the examples features repository as described in the
parent README.)

