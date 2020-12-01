package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Cliente;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Component
public class ObtenerClienteByCorreo extends StoredProcedure {

    public ObtenerClienteByCorreo(@Autowired DataSource dataSource) {
        super(dataSource, "OBTENER_CLIENTE_BY_CORREO");

        declareParameter(new SqlParameter("i_correo", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_nombre", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_telefono", OracleTypes.INTEGER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public Cliente execute(String clienteCorreo) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_correo", clienteCorreo));
        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");

        if (resultSqlCode.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId((BigDecimal) resultMap.get("o_id"));
        cliente.setNombre((String) resultMap.get("o_nombre"));
        cliente.setTelefono((String) resultMap.get("o_telefono"));
        cliente.setCorreo(clienteCorreo);

        return cliente;
    }
}
