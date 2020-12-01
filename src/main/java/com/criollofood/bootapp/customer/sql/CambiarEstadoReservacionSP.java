package com.criollofood.bootapp.customer.sql;

import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class CambiarEstadoReservacionSP extends StoredProcedure {

    public CambiarEstadoReservacionSP(@Autowired DataSource dataSource) {
        super(dataSource, "CAMBIAR_ESTADO_RESERVACION");

        declareParameter(new SqlParameter("i_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_estado", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(BigDecimal idReservacion, String estadoReservacion) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_id", idReservacion);
        parametersMap.put("i_estado", estadoReservacion);

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }
}
