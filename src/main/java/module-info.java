module com.Abe.Spoti {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires java.xml.bind;
	requires java.xml;
	requires javafx.base;

	
    opens com.Abe.Spoti to javafx.fxml;
    opens com.Abe.Spoti.Model.DataObject to javafx.base;
    exports com.Abe.Spoti;
}
