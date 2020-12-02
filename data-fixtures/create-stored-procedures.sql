-- CLIENTES

CREATE OR REPLACE
PROCEDURE OBTENER_CLIENTE_BY_CORREO
(i_correo IN CORE_CLIENTE.CORREO%TYPE,
 o_id OUT CORE_CLIENTE.ID%TYPE,
 o_nombre OUT CORE_CLIENTE.NOMBRE%TYPE,
 o_telefono OUT CORE_CLIENTE.TELEFONO%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    select
        ID,
        NOMBRE,
        TELEFONO
    into
        o_id,
        o_nombre,
        o_telefono
    from CORE_CLIENTE
    where CORREO = i_correo;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END OBTENER_CLIENTE_BY_CORREO;

CREATE OR REPLACE
PROCEDURE CREAR_CLIENTE
(i_nombre IN CORE_CLIENTE.NOMBRE%TYPE,
 i_telefono IN CORE_CLIENTE.TELEFONO%TYPE,
 i_correo IN CORE_CLIENTE.CORREO%TYPE,
 o_id OUT CORE_CLIENTE.ID%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    insert into CORE_CLIENTE (NOMBRE, TELEFONO, CORREO)
    values (i_nombre, i_telefono, i_correo);

    select ID into o_id
    from CORE_CLIENTE
    where CORREO = i_correo;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_CLIENTE;

-- RESERVACIONES

CREATE OR REPLACE
PROCEDURE CREAR_RESERVACION
(i_fecha IN CORE_RESERVACION.FECHA%TYPE,
 i_asistentes IN CORE_RESERVACION.ASISTENTES%TYPE,
 i_cliente_id IN CORE_RESERVACION.CLIENTE_ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_id NUMBER;
    v_codigo_reservacion VARCHAR2(15);
    v_fecha_confirmacion TIMESTAMP;
BEGIN
    v_fecha_confirmacion := CURRENT_TIMESTAMP;

    insert into CORE_RESERVACION (FECHA, ASISTENTES, ESTADO, CLIENTE_ID, FECHA_CONFIRMACION)
    values (i_fecha, i_asistentes, 'CONFIRMADA', i_cliente_id, v_fecha_confirmacion);

    select ID into v_id from CORE_RESERVACION where FECHA_CONFIRMACION = v_fecha_confirmacion;

    v_codigo_reservacion := 'RSV-' || LPAD(TO_CHAR(v_id), 11, '0');

    update CORE_RESERVACION
    set CODIGO = v_codigo_reservacion
    where ID = v_id;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_RESERVACION;

CREATE OR REPLACE
PROCEDURE LISTAR_RESERVACIONES_BY_ID_CLIENTE
(i_cliente_id IN CORE_RESERVACION.CLIENTE_ID%TYPE,
 o_reservaciones_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_reservaciones_cursor for
        select
            ID,
            FECHA,
            ASISTENTES,
            CODIGO,
            ESTADO
        from CORE_RESERVACION
        where CLIENTE_ID = i_cliente_id
        order by FECHA desc;
END LISTAR_RESERVACIONES_BY_ID_CLIENTE;

CREATE OR REPLACE
PROCEDURE CAMBIAR_ESTADO_RESERVACION
(i_id IN CORE_RESERVACION.ID%TYPE,
 i_estado IN CORE_RESERVACION.ESTADO%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN

    update CORE_RESERVACION
    set ESTADO = i_estado
    where ID = i_id;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CAMBIAR_ESTADO_RESERVACION;

-- RECETAS

CREATE OR REPLACE
PROCEDURE LISTAR_RECETAS_DISPONIBLES
(o_recetas_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_recetas_cursor for
        select * from PRODUCTOS_RECETA where ESTA_DISPONIBLE = 1;
END LISTAR_RECETAS_DISPONIBLES;

-- ATENCION

CREATE OR REPLACE
PROCEDURE OBTENER_ATENCION_ACTIVA_BY_ID_CLIENTE
(i_cliente_id IN CORE_CLIENTE.ID%TYPE,
 o_id OUT ATENCION_ATENCION.ID%TYPE,
 o_total OUT ATENCION_ATENCION.TOTAL%TYPE,
 o_fecha OUT ATENCION_ATENCION.FECHA%TYPE,
 o_esta_activo OUT ATENCION_ATENCION.ESTA_ACTIVO%TYPE,
 o_esta_pagada OUT ATENCION_ATENCION.ESTA_PAGADA%TYPE,
 o_numero_mesa OUT ATENCION_ATENCION.NUMERO_MESA%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    select
        ID,
        TOTAL,
        FECHA,
        ESTA_ACTIVO,
        ESTA_PAGADA,
        NUMERO_MESA
    into
        o_id,
        o_total,
        o_fecha,
        o_esta_activo,
        o_esta_pagada,
        o_numero_mesa
    from ATENCION_ATENCION
    where CLIENTE_ID = i_cliente_id and ESTA_ACTIVO = 1;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END OBTENER_ATENCION_ACTIVA_BY_ID_CLIENTE;

-- PEDIDO

CREATE OR REPLACE
PROCEDURE CREAR_PEDIDO
(i_atencion_id IN ATENCION_ATENCION.ID%TYPE,
 o_pedido_id OUT ATENCION_PEDIDO.ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_fecha_ingreso TIMESTAMP;
    v_pedido_id NUMBER(11);
BEGIN
    v_fecha_ingreso := CURRENT_TIMESTAMP;

    insert into ATENCION_PEDIDO (ESTADO, FECHA_INGRESO, ATENCION_ID, GARZON_ID)
    values ('INGRESADO', v_fecha_ingreso, i_atencion_id, 21);

    select ID into v_pedido_id from ATENCION_PEDIDO where FECHA_INGRESO = v_fecha_ingreso;

    o_pedido_id := v_pedido_id;
    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_PEDIDO;

CREATE OR REPLACE
PROCEDURE OBTENER_PEDIDO_BY_ID_ATENCION
(i_atencion_id IN ATENCION_ATENCION.ID%TYPE,
 o_id OUT ATENCION_PEDIDO.ID%TYPE,
 o_estado OUT ATENCION_PEDIDO.ESTADO%TYPE,
 o_fecha_ingreso OUT ATENCION_PEDIDO.FECHA_INGRESO%TYPE,
 o_fecha_preparacion OUT ATENCION_PEDIDO.FECHA_PREPARACION%TYPE,
 o_fecha_entrega OUT ATENCION_PEDIDO.FECHA_ENTREGA%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    select
        ID,
        ESTADO,
        FECHA_INGRESO,
        FECHA_PREPARACION,
        FECHA_ENTREGA
    into
        o_id,
        o_estado,
        o_fecha_ingreso,
        o_fecha_preparacion,
        o_fecha_entrega
    from ATENCION_PEDIDO
    where ATENCION_ID = i_atencion_id;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END OBTENER_PEDIDO_BY_ID_ATENCION;

CREATE OR REPLACE
PROCEDURE AGREGAR_RECETA_PEDIDO
(i_comentario IN ATENCION_PEDIDO_RECETA.COMENTARIO%TYPE,
 i_pedido_id IN ATENCION_PEDIDO_RECETA.PEDIDO_ID%TYPE,
 i_receta_id IN ATENCION_PEDIDO_RECETA.RECETA_ID%TYPE,
 o_id OUT ATENCION_PEDIDO_RECETA.ID%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    insert into ATENCION_PEDIDO_RECETA (COMENTARIO, PEDIDO_ID, RECETA_ID, ESTADO)
    values (i_comentario, i_pedido_id, i_receta_id, 'PENDIENTE');

    select max(ID)
    into o_id
    from ATENCION_PEDIDO_RECETA
    where PEDIDO_ID = i_pedido_id and RECETA_ID = i_receta_id;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END AGREGAR_RECETA_PEDIDO;

CREATE OR REPLACE
PROCEDURE LISTAR_RECETAS_BY_ID_PEDIDO
(i_pedido_id IN ATENCION_PEDIDO_RECETA.PEDIDO_ID%TYPE,
 o_recetas_pedido_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_recetas_pedido_cursor for
        select * from ATENCION_PEDIDO_RECETA where PEDIDO_ID = i_pedido_id;
END LISTAR_RECETAS_BY_ID_PEDIDO;

-- CATEGORIA

CREATE OR REPLACE
PROCEDURE LISTAR_CATEGORIAS
(o_categorias_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_categorias_cursor for
        select * from PRODUCTOS_CATEGORIA;
END LISTAR_CATEGORIAS;


COMMIT;
