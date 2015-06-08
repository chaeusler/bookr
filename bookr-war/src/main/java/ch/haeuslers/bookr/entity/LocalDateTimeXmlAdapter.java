package ch.haeuslers.bookr.entity;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;


public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String timestamp) throws Exception {
        // Workaround remove the trailing Z
        return LocalDateTime.parse(timestamp.substring(0, 22));
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toString() + "Z";
    }
}
