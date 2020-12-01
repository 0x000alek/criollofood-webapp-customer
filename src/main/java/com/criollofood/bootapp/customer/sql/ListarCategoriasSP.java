package com.criollofood.bootapp.customer.sql;

import com.criollofood.bootapp.customer.domain.Categoria;
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
public class ListarCategoriasSP extends StoredProcedure {

    public ListarCategoriasSP(@Autowired DataSource dataSource) {
        super(dataSource, "LISTAR_CATEGORIAS");

        declareParameter(
                new SqlOutParameter("o_categorias_cursor", OracleTypes.CURSOR,
                        BeanPropertyRowMapper.newInstance(Categoria.class)));
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Categoria> execute() {
        Map<String, Object> resultMap = super.execute(Collections.emptyMap());
        return (List<Categoria>) resultMap.get("o_categorias_cursor");
    }
}
