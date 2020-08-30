# Create tables here
import psycopg2
from config import config


def create():
    print(""" create tables in the PostgreSQL database""")
    commands = (
        """ CREATE TABLE IF NOT EXISTS  PRODUCTOS (
                id_producto SERIAL PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL,
                precio DECIMAL(10,2) NOT NULL,
                stock INTEGER NOT NULL
        )
        """,
        """ CREATE TABLE IF NOT EXISTS  ORDEN (
                id_orden SERIAL PRIMARY KEY,
                estado VARCHAR(50) NOT NULL,
                id_cliente INTEGER NOT NULL,
                fecha_hora TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
                )
        """,
        """ CREATE TABLE IF NOT EXISTS  DETALLE_ORDEN (
                id_detalle_orden SERIAL PRIMARY KEY,
                id_orden INTEGER NOT NULL,
                id_producto INTEGER NOT NULL,
                cantidad INTEGER NO NULL
        )
        """
    )
    conn = None
    try:
        # read the connection parameters
        params = config()
        # connect to the PostgreSQL server
        conn = psycopg2.connect(**params)
        cur = conn.cursor()
        # CREATE TABLE one by one
        for command in commands:
            cur.execute(command)
        # close communication with the PostgreSQL database server
        cur.close()
        # commit the changes
        conn.commit()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            conn.close()
