package net.geocat.tufts.guicomponents;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class DoubleFormatter extends ParseAllFormat {

	private static final long serialVersionUID = -8620421355914152384L;

	public DoubleFormatter(Format aDelegate) {
		super(aDelegate);
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		Object parsed = super.parseObject(source, pos);
		if (parsed instanceof Long && parsed != null) {
			parsed = Double.valueOf(((Long) parsed).doubleValue());
		}
		return parsed;
	}

	@Override
	public Object parseObject(String source) throws ParseException {
		Object parsed = super.parseObject(source);
		if (parsed instanceof Long && parsed != null) {
			parsed = Double.valueOf(((Long) parsed).doubleValue());
		}
		return parsed;
	}

}
