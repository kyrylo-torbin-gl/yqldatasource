package com.jaspersoft.yql;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.AbstractQueryExecuterFactory;
import net.sf.jasperreports.engine.query.JRQueryExecuter;

import java.util.Map;

/**
 * @author kyrylo.torbin
 * @version 1.0 5/12/15
 */
public class JRYQLQueryExecuterFactory extends AbstractQueryExecuterFactory {

    @Override
    public Object[] getBuiltinParameters() {
        return new Object[0];
    }

    @Override
    public JRQueryExecuter createQueryExecuter(final JasperReportsContext jasperReportsContext,
                                               final JRDataset dataset,
                                               final Map<String, ? extends JRValueParameter> parameters)
            throws JRException {

        return new JRYQLQueryExecuter(jasperReportsContext, dataset, parameters);
    }

    @Override
    public boolean supportsQueryParameterType(final String className) {
        return true; // TODO what is it?
    }
}