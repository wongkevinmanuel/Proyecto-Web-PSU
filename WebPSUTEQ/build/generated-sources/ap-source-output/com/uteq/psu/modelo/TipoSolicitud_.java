package com.uteq.psu.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoSolicitud.class)
public abstract class TipoSolicitud_ {

	public static volatile SingularAttribute<TipoSolicitud, String> descripcionTiSo;
	public static volatile SingularAttribute<TipoSolicitud, Integer> idTipoSolicitud;
	public static volatile SingularAttribute<TipoSolicitud, String> nombreTiSo;
	public static volatile ListAttribute<TipoSolicitud, Solicitud> solicitudList;

}

