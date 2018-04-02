package com.uteq.psu.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RegistroSolicitud.class)
public abstract class RegistroSolicitud_ {

	public static volatile SingularAttribute<RegistroSolicitud, String> nombreRe;
	public static volatile SingularAttribute<RegistroSolicitud, Usuario> idUsuario;
	public static volatile SingularAttribute<RegistroSolicitud, Date> fechaRe;
	public static volatile SingularAttribute<RegistroSolicitud, String> solicitudReg;
	public static volatile SingularAttribute<RegistroSolicitud, Solicitud> idSolicitud;
	public static volatile SingularAttribute<RegistroSolicitud, Integer> idRegistro;

}

