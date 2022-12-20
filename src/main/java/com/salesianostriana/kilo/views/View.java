package com.salesianostriana.kilo.views;

public class View {

    public static interface CajaView{

        public static interface GenericResponseView{}
        public static interface DetailResponseView extends GenericResponseView{}

    }

    public static interface AportacionView{

        public static interface AportacionDetallesView{}

        public static interface AllAportacionView{}


    }

    public static interface DetalleAportacionView{

        public static interface NombreAlimentoView{}
    }
    public static interface TipoAlimentoView {

        public static interface AllTipoAlimentoView{}
        public static interface TipoAlimentoByIdView{}

        public static interface TipoAlimentoRequest{}
    }

    public static interface DestinatarioView {

        public static interface DetailedDestinatarioView {}

        public static interface AllDestinatarioView{}
    }

    public static interface KilosDisponiblesView {

        public static interface KilosDisponiblesAllView{}

        public static interface KilosDisponiblesDetailsView{}
    }

}
