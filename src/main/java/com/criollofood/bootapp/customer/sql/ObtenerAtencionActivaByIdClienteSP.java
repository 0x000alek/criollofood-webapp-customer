package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Atencion;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class ObtenerAtencionActivaByIdClienteSP extends StoredProcedure {

    public ObtenerAtencionActivaByIdClienteSP(@Autowired DataSource dataSource) {
        super(dataSource, "OBTENER_ATENCION_ACTIVA_BY_ID_CLIENTE");

        declareParameter(new SqlParameter("i_cliente_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_total", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_fecha", OracleTypes.TIMESTAMP));
        declareParameter(new SqlOutParameter("o_esta_activo", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_esta_pagada", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_numero_mesa", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public Atencion execute(BigDecimal clienteId) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_cliente_id", clienteId));
        BigDecimal sqlCode = (BigDecimal) resultMap.get("o_sql_code");
        if (sqlCode.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        Atencion atencion = new Atencion();
        atencion.setId((BigDecimal) resultMap.get("o_id"));
        atencion.setTotal((BigDecimal) resultMap.get("o_total"));
        atencion.setFecha((Date) resultMap.get("o_fecha"));
        atencion.setEstaActivo(((BigDecimal) resultMap.get("o_esta_activo")).compareTo(BigDecimal.ONE) == 0);
        atencion.setEstaPagada(((BigDecimal) resultMap.get("o_esta_pagada")).compareTo(BigDecimal.ONE) == 0);
        atencion.setClienteId(clienteId);
        atencion.setNumeroMesa((BigDecimal) resultMap.get("o_numero_mesa"));

        return atencion;
    }
}
