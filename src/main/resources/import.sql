INSERT INTO destinatario(direccion, nombre, persona_contacto, telefono, id) VALUES ('C/ Jaén nº 24', 'Parroquia San Pedro', 'Don Jose Luis', '678 489 282', NEXTVAL('hibernate_sequence'));
INSERT INTO destinatario(direccion, nombre, persona_contacto, telefono, id) VALUES ('C/ Rosario nº 78', 'Comedor social San Juan de Dios', 'Francisco Gomez', '956 783 211', NEXTVAL('hibernate_sequence'));
INSERT INTO destinatario(direccion, nombre, persona_contacto, telefono, id) VALUES ('C/ Betis nº 196', 'Caritas', 'Sara Fernández', '656 467 629', NEXTVAL('hibernate_sequence'));
INSERT INTO destinatario(direccion, nombre, persona_contacto, telefono, id) VALUES ('C/ López de gomara nº 56, bloque 2, 4ºA', 'Banco de alimentos', 'Mario Marquez', '756 472 987', NEXTVAL('hibernate_sequence'));
INSERT INTO destinatario(direccion, nombre, persona_contacto, telefono, id) VALUES ('C/ Esperanza de triana nº 34', 'Plataforma del voluntariado social', 'Ignacio Franco', '676 439 675', NEXTVAL('hibernate_sequence'));
INSERT INTO clase(nombre, tutor, id) VALUES ('2 DAM' ,'Luis Miguel López' ,NEXTVAL('hibernate_sequence'));
INSERT INTO clase(nombre, tutor, id) VALUES ('1 DAM' ,'Miguel Campos' ,NEXTVAL('hibernate_sequence'));
INSERT INTO clase(nombre, tutor, id) VALUES ('1 FPB' ,'Julio Vera' ,NEXTVAL('hibernate_sequence'));
INSERT INTO clase(nombre, tutor, id) VALUES ('1 COMERCIO' ,'Mónica Naranjo' ,NEXTVAL('hibernate_sequence'));
INSERT INTO clase(nombre, tutor, id) VALUES ('2 AYF' ,'Paco Pepe' ,NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Pasta', NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Dulces', NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Legumbres', NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Arroz', NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Leche', NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Huevos', NEXTVAL('hibernate_sequence'));
INSERT INTO tipo_alimento(nombre, id) VALUES ('Carne', NEXTVAL('hibernate_sequence'));
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 11);
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 12);
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 13);
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 14);
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 15);
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 16);
INSERT INTO  kilos_disponibles(cantidad_disponible, tipo_alimento) VALUES (0, 17);



INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (1, 0, 1, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (2, 0, 2, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (3, 0, 3, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (4, 0, 4, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (1, 0, 5, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (1, 0, 6, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (3, 0, 7, NEXTVAL('hibernate_sequence'));
INSERT INTO caja(destinatario, kilos_totales, num_caja, id) VALUES (4, 0, 8, NEXTVAL('hibernate_sequence'));