package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Receta;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ListarRecetasDisponibles extends StoredProcedure {

    public ListarRecetasDisponibles(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_RECETAS_DISPONIBLES");

        declareParameter(
                new SqlOutParameter("o_recetas_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Receta.class)));
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Receta> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return (List<Receta>) resultMap.get("o_recetas_cursor");
    }
}
