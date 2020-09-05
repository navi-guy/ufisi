from create_tables import create

from consumer import receiveFromOrdenes

def connect_and_create_tables():
    # """ Connect to the PostgreSQL database server and create tables """
    create()
    print('Tablas creadas correctamente, connection closed.')

if __name__ == '__main__':
    connect_and_create_tables() ##COMENTAR LUEGO

    # itemX = {
    #     "nombre": "Cuaderno Anillado",
    #     "cantidad": 12,
    #     "precio_unitario": 10.50,
    #     "subtotal": 126 # not sure
    # }

    # itemY = {
    #     "nombre": "Cuaderno Normal",
    #     "cantidad": 8,
    #     "precio_unitario": 5.50,
    #     "subtotal": 44 # not sure
    # }

    # list_of_items = [itemX, itemY]

    # msgeToFacturacion = {
    #     "codigo_cliente" : "16200001",
    #     "nombre_cliente" : "Sergio A.",
    #     "ruc_cliente"    : "12345678923",
    #     "items":  list_of_items,
    #     "total_bruto": 322 # esto lo obtienes con tu hallar_monto_total()
    # }

    receiveFromOrdenes() #wait until messages comes
    #sendToFacturacion(msgeToFacturacion)
    