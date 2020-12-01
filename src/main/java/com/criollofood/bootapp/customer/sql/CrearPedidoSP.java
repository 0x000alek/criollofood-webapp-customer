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
public class CrearPedidoSP extends StoredProcedure {

    public CrearPedidoSP(@Autowired DataSource dataSource) {
        super(dataSource, "CREAR_PEDIDO");

        declareParameter(new SqlParameter("i_atencion_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_pedido_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public BigDecimal execute(BigDecimal atencionId) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_atencion_id", atencionId);

        Map<String, Object> resultMap = super.execute(parametersMap);
        BigDecimal sqlCode = (BigDecimal) resultMap.get("o_sql_code");
        if (sqlCode.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return (BigDecimal) resultMap.get("o_pedido_id");
    }
}
