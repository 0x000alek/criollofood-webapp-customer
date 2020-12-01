package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Reservacion;
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
public class ListarReservacionesByIdCliente extends StoredProcedure {

    public ListarReservacionesByIdCliente(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_RESERVACIONES_BY_ID_CLIENTE");

        declareParameter(new SqlParameter("i_cliente_id", OracleTypes.NUMBER));
        declareParameter(
                new SqlOutParameter("o_reservaciones_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Reservacion.class))
        );
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Reservacion> execute(BigDecimal idCliente) {
        Map<String, Object> resultMap = super.execute(Collections.singletonMap("i_cliente_id", idCliente));
        return (List<Reservacion>) resultMap.get("o_reservaciones_cursor");
    }
}
