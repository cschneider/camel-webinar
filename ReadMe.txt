Examples from several camel webinars
====================================

About the authors
-----------------

Hadrian Zbarcea
	Open Source Architect working full time on Apache Camel
	Twitter: 	https://twitter.com/#!/hgz
				@hgz	
	Blog: 		http://camelbot.blogspot.com/

Christian Schneider
	Open Source Architect working full time on Apache Camel, CXF and Karaf
	Twitter: 	https://twitter.com/#!/schneider_chris
				@schneider_chris	
	Blog: 		http://www.liquid-reality.de

Bernhard Schuhmann
	Senior Consultant
	Twitter: 	https://twitter.com/#!/schuhmab
				@schuhmab


Prerequisites
-------------

- Install maven - http://maven.apache.org/
- Install Eclipse - http://www.eclipse.org/downloads/
	Eclipse Classic is enough to start
- Install m2eclipse
	http://m2eclipse.sonatype.org/sites/m2e/

	Note: In eclipse Indigo there is a new version of m2eclipse 1.0 installed. This version is not compatible with code generation plugins.
	So please use m2eclipse 0.12 form the archived m2eclipse releases above.

How to import a maven project
-----------------------------

Start you eclipse and File -> Import -> Maven -> Existing maven projects -> Browse to the project directory  -> Finish
The project will then be imported and the classpath will be setup to include the maven dependencies.

For projects that generate sources like most CXF projects you also have to update the project configuration:
Right click on project -> maven -> update project configuration

The sources will be generated and the new source folder will be added to the eclipse project


introduction
------------

Examples from the first introductionary webinar


part1
-----

Shows how to download camel, create a camel project, run it from Eclipse.
Next the example is made fit for OSGi and tested in the Karaf Container.
simple.xml is a blueprint context with a camel route that can be deployed as a single file.

part2
-----

- Trace, test and debug camel routes
- Use the jaxb and bean components for light weight messaging
- Shows some Enterprise Integration Patterns in practice

