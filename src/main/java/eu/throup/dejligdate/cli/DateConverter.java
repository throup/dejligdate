package eu.throup.dejligdate.cli;

import eu.throup.dejligdate.domain.Date;
import picocli.CommandLine;

class DateConverter implements CommandLine.ITypeConverter<Date> {
    public Date convert(String value) throws Exception {
        return Date.parse(value);
    }
}
