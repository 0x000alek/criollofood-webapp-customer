package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Reservacion;
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
public class CrearReservacionSP extends StoredProcedure {

    public CrearReservacionSP(@Autowired DataSource dataSource) {
        super(dataSource, "CREAR_RESERVACION");

        declareParameter(new SqlParameter("i_fecha", OracleTypes.DATE));
        declareParameter(new SqlParameter("i_asistentes", OracleTypes.INTEGER));
        declareParameter(new SqlParameter("i_cliente_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public boolean execute(Reservacion reservacion, BigDecimal idCliente) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_fecha", reservacion.getFecha());
        parametersMap.put("i_asistentes", reservacion.getAsistentes());
        parametersMap.put("i_cliente_id", idCliente);

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        return resultSqlCode.compareTo(BigDecimal.ONE) == 0;
    }
}
