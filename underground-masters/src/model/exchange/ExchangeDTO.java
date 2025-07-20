package model.exchange;

import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

public class ExchangeDTO {
	private IntegerProperty exchangeId;
	private StringProperty memberId;
	private StringProperty title;
	private StringProperty content;
	private StringProperty isDelete;
	private SimpleObjectProperty<Date> createAt;
}
