# dejligdate

This project contains a small Java application, which calculates the number of days in-between two dates.

[![Java CI with Maven](https://github.com/throup/dejligdate/actions/workflows/maven.yml/badge.svg)](https://github.com/throup/dejligdate/actions/workflows/maven.yml)
[![Copr build status](https://copr.fedorainfracloud.org/coprs/throup/dejligdate/package/dejligdate/status_image/last_build.png)](https://copr.fedorainfracloud.org/coprs/throup/dejligdate/package/dejligdate/)

## Usage
```
Usage: dejligdate [-hv] <date1> <date2>
Calculate the number of days in-between two dates in the Common Era, following
conventions of the Gregorian Calendar.
      <date1>     The first date, in the format YYYY-MM-DD.
      <date2>     The second date, in the format YYYY-MM-DD.
  -h, --help      display this help message
  -v, --verbose   Include information about the calculation, not just the
                    result.
```

### Examples

#### Basic output
```sh
$ dejligdate 1963-11-23 2005-03-26
15099
```

#### Verbose output
```sh
$ dejligdate -v 1963-11-23 2005-03-26
First date   : 1963-11-23
Second date  : 2005-03-26
Days between : 15099
```

## Technologies
This project is built with Java 18 (depends on features from Java SE 15+), using the following tooling:

- [Picocli](https://picocli.info/) for a CLI interface.
- [Junit](https://junit.org/) for unit testing.
- [ScalaTest](https://www.scalatest.org/) and [ScalaCheck](https://scalacheck.org/) for property-based testing.

## Build and compile
There is a `pom.xml` provided for the [Maven build tool](https://maven.apache.org/) (it is assumed Maven and an appropriate JDK are already installed).

### Testing
The test suite can be run simply using:

```sh
$ mvn test
```

### Building a JAR archive
A JAR package, containing the application and dependencies, can be built using

```sh
$ mvn package
...
[INFO] Building jar: target/dejligdate-0.0.14-SNAPSHOT-jar-with-dependencies.jar
...
```

### Building an RPM package
An RPM package, for compatible Linux Distributions (Fedora 35 & 36 are supported), can be built from the provided [SPEC file](rpm/dejligdate.spec).

```sh
# Download source tarball, if needed:
$ spectool -gR rpm/dejligdate.spec

# Build RPM package:
$ rpmbuild -ba rpm/dejligdate.spec

# Install RPM package:
# (dnf is used for Fedora; other distros may use a different tool)
$ sudo dnf install rpmbuild/RPMS/noarch/dejligdate-0.0.14-1.fc35.noarch.rpm
```

#### Prebuilt RPM package
For convenience, users of Fedora Linux can install a prebuilt RPM from a [COPR repository](https://copr.fedorainfracloud.org/coprs/throup/dejligdate/).

```sh
$ sudo dnf copr enable throup/dejligdate
$ sudo dnf install dejligdate
```

## Running the application
The application is run from the command line, passing in two dates as program parameters.

```sh
# if using a JAR archive
$ java -jar path/to/jar 1234-12-12 4321-01-01

# if using RPM binary
$ dejligdate 1234-12-12 4321-01-01
```

Dates should be entered in the form YYYY-MM-DD for any valid dates in the Common Era, following conventions of the Gregorian calendar.
In short:
- the earliest valid date is 0001-01-01 -- 1st January, year 1; and
- leap years occur every 4 years, with special handling for the end of each century.

## Devlopment notes
### Interpreting the brief
The [challenge brief](challenge.md) was very open-ended, which has allowed me to make some assumptions:

- "dates" are interpreted as describe above; this is more restrictive than the Java libraries (and ISO-8601), but I believe it will meet most users' expectations without introducing confusions like "year zero".
- "a program" has been realised as a CLI application, developed and tested on a modern Linux OS. The underlying business logic could easily be embedded in other forms of programs; f.eks a web application.
- the Java Time library has not been used in the [production code](src/main), but it is used in the [Java Reference tests](src/test/scala/eu/throup/dejligdate/JavaReferencePropertyTests.scala) to act as a reference implementation for date handling. There are no references to `java.time` within the application itself. 

### Test coverage
I have used a combination of Junit tests (primarily examples) and Scalacheck property-based tests to cover the codebase.
