package se.springworks.android.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class JsonStringToDate extends JsonDeserializer<Date> {


	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		String s = parser.getValueAsString();
		if (s == null) {
			return null;
		}
		Date date = null;
		try {
			if (s.matches("[0-9]+")) {
				long millis = Long.parseLong(s);
				date = new Date(millis);
			}
			else {
				date = DateFormat.getDateInstance().parse(s);
			}
		}
		catch (Exception e) {
			date = new Date();
		}
		return date;
	}

}
