package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Pedido;
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
public class ObtenerPedidoByIdAtencion extends StoredProcedure {

    public ObtenerPedidoByIdAtencion(@Autowired DataSource dataSource) {
        super(dataSource, "OBTENER_PEDIDO_BY_ID_ATENCION");

        declareParameter(new SqlParameter("i_atencion_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_estado", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter("o_fecha_ingreso", OracleTypes.TIMESTAMP));
        declareParameter(new SqlOutParameter("o_fecha_preparacion", OracleTypes.TIMESTAMP));
        declareParameter(new SqlOutParameter("o_fecha_entrega", OracleTypes.TIMESTAMP));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public Pedido execute(BigDecimal atencionId) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_atencion_id", atencionId));

        BigDecimal resultSqlCode = (BigDecimal) resultMap.get("o_sql_code");
        if (resultSqlCode.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setId((BigDecimal) resultMap.get("o_id"));
        pedido.setEstado((String) resultMap.get("o_estado"));
        pedido.setFechaIngreso((Date) resultMap.get("o_fecha_ingreso"));
        pedido.setFechaPreparacion((Date) resultMap.get("o_fecha_preparacion"));
        pedido.setFechaEntrega((Date) resultMap.get("o_fecha_entrega"));
        pedido.setAtencionId(atencionId);

        return pedido;
    }
}
