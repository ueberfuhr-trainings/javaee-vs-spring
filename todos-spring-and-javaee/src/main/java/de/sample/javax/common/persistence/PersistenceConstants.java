package de.sample.javax.common.persistence;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PersistenceConstants {

    public final String JDBC_DATASOURCE_JNDI_NAME = "jdbc/TodoDB";
    public final String JPA_PERSISTENCE_UNIT_NAME = "puTodoDB";

}
