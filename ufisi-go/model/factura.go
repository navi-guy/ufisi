package model

// Factura is the struct for table 'facturas'
type Factura struct { // exported struct type
	IDFactura     int64   `json:"id_factura"`
	IDCliente     string  `json:"id_cliente"`
	IDOrden       int     `json:"id_orden"`
	CodigoFactura string  `json:"codigo_factura"`
	TotalBruto    float64 `json:"total_bruto"`
	Igv           float64 `json:"igv"`
	TotalNeto     float64 `json:"total_neto"`
}
