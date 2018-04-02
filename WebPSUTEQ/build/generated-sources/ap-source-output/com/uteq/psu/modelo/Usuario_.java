package com.uteq.psu.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile ListAttribute<Usuario, RegistroSolicitud> registroSolicitudList;
	public static volatile SingularAttribute<Usuario, String> idSicau;
	public static volatile SingularAttribute<Usuario, String> cedula;
	public static volatile SingularAttribute<Usuario, Integer> idUsuario;

}

