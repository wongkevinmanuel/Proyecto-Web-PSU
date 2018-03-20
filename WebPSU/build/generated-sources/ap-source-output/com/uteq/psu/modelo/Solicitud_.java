package com.uteq.psu.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Solicitud.class)
public abstract class Solicitud_ {

	public static volatile ListAttribute<Solicitud, RegistroSolicitud> registroSolicitudList;
	public static volatile SingularAttribute<Solicitud, TipoSolicitud> idTipoSolicitud;
	public static volatile SingularAttribute<Solicitud, String> nombreSolicitud;
	public static volatile SingularAttribute<Solicitud, Integer> idSolicitud;

}

