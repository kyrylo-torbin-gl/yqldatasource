import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.AbstractSampleApp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author kyrylo.torbin
 * @version 1.0 5/6/15
 */
public class YQLDataSourceApp extends AbstractSampleApp {

    private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=";
    
    private static final String QUERY = "select * from geo.continents";

    private static final String XPATH = "/query/results/place";
    
    public static void main(String[] args) throws IOException, JRException {

        main(new YQLDataSourceApp(), new String[] {"test"});
    }

    @Override
    public void test() throws JRException {

//        fill1();        // Uses provided XPATH
        fill2();      // Uses XPATH from PlacesReport.jrxml
        pdf();
        xml();
    }

    public void fill1() throws JRException {

        final long start = System.currentTimeMillis();

        JasperCompileManager.compileReportToFile("reports/PlacesReport.jrxml");

        final String fullUrlStr;
        try {
            fullUrlStr = BASE_URL + URLEncoder.encode(QUERY, "UTF-8");
            final URL fullUrl = new URL(fullUrlStr);
            final InputStream is = fullUrl.openStream();

            JasperFillManager.fillReportToFile("reports/PlacesReport.jasper", new HashMap<String, Object>(), new JRXmlDataSource(is, XPATH));

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final long end = System.currentTimeMillis();
        System.out.println("Filling time: " + (end - start) + " ms");
    }

    public void fill2() throws JRException {

        final long start = System.currentTimeMillis();

        JasperCompileManager.compileReportToFile("reports/PlacesReport.jrxml");
        JasperFillManager.fillReportToFile("reports/PlacesReport.jasper", new HashMap<String, Object>());

        final long end = System.currentTimeMillis();
        System.out.println("Filling time: " + (end - start) + " ms");
    }

    public void pdf() throws JRException
    {
        final long start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile("reports/PlacesReport.jrprint");
        final long end = System.currentTimeMillis();
        System.out.println("PDF report created in " + (end - start) + " ms");
    }

    public void xml() throws JRException
    {
        final long start = System.currentTimeMillis();
        JasperExportManager.exportReportToXmlFile("reports/PlacesReport.jrprint", false);
        final long end = System.currentTimeMillis();
        System.out.println("XML report created in " + (end - start) + " ms");
    }
}