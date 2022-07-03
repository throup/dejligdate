Name:          dejligdate
Version:       0.0.6
Release:       1%{?dist}
Summary:       Calculate the number of days in-between two dates
License:       GPL-3.0

URL:           https://github.com/throup/%{name}
Source0:       https://github.com/throup/%{name}/archive/refs/tags/%{version}.tar.gz?filename=%{name}-%{version}.tar.gz
BuildArch:     noarch
ExclusiveArch: %{java_arches} noarch

Requires:      java-18-headless
Requires:      javapackages-filesystem
BuildRequires: java-18-headless
BuildRequires: java-18
BuildRequires: maven-local

%description
Calculate the number of days in-between two dates in the Common Era,
following conventions of the Gregorian Calendar.

%prep
%setup -q

%build
export JAVA_HOME=$(dirname $(dirname $(readlink $(readlink $(which javac)))))
mvn package

# Create the wrapper for /usr/bin
cat >%{name} <<EOF
#!/bin/sh
java -jar %{_javadir}/%{name}.jar "\$@"
EOF

%install
mkdir -p %{buildroot}%{_javadir}
mkdir -p %{buildroot}%{_bindir}

cp target/dejligdate-%{version}-SNAPSHOT-jar-with-dependencies.jar %{buildroot}%{_javadir}/%{name}.jar
install -p -m0755 %{name} %{buildroot}%{_bindir}/%{name}

%post -p /sbin/ldconfig

%postun -p /sbin/ldconfig

%files
%license %{_datadir}/%{shortname}/LICENSE
%{_javadir}/%{name}.jar
%{_bindir}/%{name}

%changelog
* Sun Jul 3 2022 Chris Throup <chris@throup.eu>
- Package created
