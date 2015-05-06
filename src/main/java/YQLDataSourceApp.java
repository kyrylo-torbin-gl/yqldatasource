import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JRDesignField;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author kyrylo.torbin
 * @version 1.0 5/6/15
 */
public class YQLDataSourceApp {

    private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=";
    
    private static final String QUERY = "select * from geo.continents";

    private static final String XPATH = "/query/results/place";
    
    public static void main(String[] args) throws IOException, JRException {

        final String fullUrlStr = BASE_URL + URLEncoder.encode(QUERY, "UTF-8");

        final URL fullUrl = new URL(fullUrlStr);
        final InputStream is = fullUrl.openStream();

        final JRDataSource dataSource = new JRXmlDataSource(is, XPATH);

        final JRField field = new JRDesignField();
        field.setDescription("name");
        while (dataSource.next()) {
            System.out.println(dataSource.getFieldValue(field));
        }

        is.close();
    }
}