package eu.throup.dejligdate;

import eu.throup.dejligdate.spike.Date;
import picocli.CommandLine;

class DateConverter implements CommandLine.ITypeConverter<Date> {
    public Date convert(String value) throws Exception {
        return Date.parse(value);
    }
}
