package com.jaspersoft.yql;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author kyrylo.torbin
 * @version 1.0 5/12/15
 */
public class JRYQLQueryExecuter extends JRAbstractQueryExecuter {

    private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=";

    private static final Log logger = LogFactory.getLog(JRYQLQueryExecuter.class);

    private JRXmlDataSource dataSource;

    public JRYQLQueryExecuter(final JasperReportsContext jasperReportsContext,
                                 final JRDataset dataset,
                                 final Map<String, ? extends JRValueParameter> parametersMap) {

        super(jasperReportsContext, dataset, parametersMap);

        parseQuery();
    }

    @Override
    protected String getParameterReplacement(final String parameterName) {
        return String.valueOf(getParameterValue(parameterName));
    }

    @Override
    public JRDataSource createDatasource() throws JRException {

        final String queryString = getQueryString();
        if (queryString == null || queryString.isEmpty()) {
            return null; // TODO throw en exception
        }

        final String[] params = queryString.split(";");
        if (params == null || params.length != 2) {
            return null; // TODO throw en exception
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Select : " + params[0]);
            logger.debug("xPath : " + params[1]);
        }

        final String fullUrlStr;
        try {
            fullUrlStr = BASE_URL + URLEncoder.encode(params[0], "UTF-8");
            final URL fullUrl = new URL(fullUrlStr);
            final InputStream is = fullUrl.openStream();

            dataSource = new JRXmlDataSource(is, params[1]);
            is.close(); // TODO check if we should close stream or not

            return dataSource;
        } catch (IOException e) {
            throw new JRException(e);
        }
    }

    @Override
    public void close() {

        if (dataSource != null) {
            dataSource.close();
        }
    }

    @Override
    public boolean cancelQuery() throws JRException {
        return false;
    }
}