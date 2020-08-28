import psycopg2
from config import config
from create_tables import create

def connect_and_create_tables():
    # """ Connect to the PostgreSQL database server and create tables """
    create()
    print('Tablas creadas correctamente, connection closed.')

if __name__ == '__main__':
    connect_and_create_tables()