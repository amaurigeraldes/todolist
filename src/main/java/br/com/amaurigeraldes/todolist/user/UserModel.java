package br.com.amaurigeraldes.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data

/* Utilizar o "@Data" para parametrização automática dos getters e setters 
 * a partir da seguinte configuração do lombok no pom.xml:
 * 
 * <dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.18.30</version>
		<scope>provided</scope>
	</dependency>

 * Utilizar o "@Getter" se desejar parametrizar somente os getters
 * Utilizar o "@Setter" se desejar parametrizar somente os setters

 * getters: buscar/recuperar informação para um atributo privado de uma classe
 * setters: inserir/definir informação para um atributo privado de uma classe

*/

// Como estamos trabalhando com o JPA, o import deverá ser jakarta.persistence
@Entity(name = "tb_users")

public class UserModel {
    // Como estamos trabalhando com o JPA, o import deverá ser jakarta.persistence
    @Id 
    // Geração do Id aumomaticamente
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    @Column(unique = true)
    private String username;
    
    private String name;
    private String password;

    // Para definir quando o dado foi criado no Banco de Dados
    @CreationTimestamp
    private LocalDateTime CreatedAt;
    



}
