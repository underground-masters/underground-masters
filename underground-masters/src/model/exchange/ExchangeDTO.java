package model.exchange;

import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExchangeDTO {
	// field
    private IntegerProperty exchangeId = new SimpleIntegerProperty();
    private IntegerProperty memberId = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty content = new SimpleStringProperty();
    private StringProperty isDelete = new SimpleStringProperty();
    private SimpleObjectProperty<Date> createAt = new SimpleObjectProperty<>();
	
	// Property getter 메서드
	public IntegerProperty exchangeIdProperty() {
		return exchangeId;
	}
	
	public IntegerProperty memberIdProperty() {
		return memberId;
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public StringProperty contentProperty() {
		return content;
	}

	public StringProperty isDeleteProperty() {
		return isDelete;
	}
	
	public SimpleObjectProperty<Date> createAtProperty() {
		return createAt;
	}
}
