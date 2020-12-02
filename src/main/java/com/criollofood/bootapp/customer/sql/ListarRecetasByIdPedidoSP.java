package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.RecetaPedido;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ListarRecetasByIdPedidoSP extends StoredProcedure {

    public ListarRecetasByIdPedidoSP(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_RECETAS_BY_ID_PEDIDO");

        declareParameter(new SqlParameter("i_pedido_id", OracleTypes.NUMBER));
        declareParameter(
                new SqlOutParameter("o_recetas_pedido_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(RecetaPedido.class)));
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<RecetaPedido> execute(BigDecimal pedidoId) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_pedido_id", pedidoId));
        return (List<RecetaPedido>) resultMap.get("o_recetas_pedido_cursor");
    }
}
