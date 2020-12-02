package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.RecetaPedido;
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
public class AgregarRecetaPedidoSP extends StoredProcedure {

    public AgregarRecetaPedidoSP(@Autowired DataSource dataSource) {
        super(dataSource, "AGREGAR_RECETA_PEDIDO");

        declareParameter(new SqlParameter("i_comentario", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("i_pedido_id", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("i_receta_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_id", OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter("o_sql_code", OracleTypes.NUMBER));
        compile();
    }

    public RecetaPedido execute(RecetaPedido recetaPedido) {
        Map<String, Object> parametersMap = new HashMap<>(Collections.emptyMap());

        parametersMap.put("i_comentario", recetaPedido.getComentario());
        parametersMap.put("i_pedido_id", recetaPedido.getPedidoId());
        parametersMap.put("i_receta_id", recetaPedido.getRecetaId());

        Map<String, Object> resultMap = super.execute(parametersMap);

        BigDecimal sqlCode = (BigDecimal) resultMap.get("o_sql_code");
        if (sqlCode.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        recetaPedido.setId((BigDecimal) resultMap.get("o_id"));
        recetaPedido.setEstado("PENDIENTE");

        return recetaPedido;
    }
}
