package eu.throup.dejligdate.cli;

import eu.throup.dejligdate.spike.Date;
import picocli.CommandLine;

class DateConverter implements CommandLine.ITypeConverter<Date> {
    public Date convert(String value) throws Exception {
        return Date.parse(value);
    }
}
