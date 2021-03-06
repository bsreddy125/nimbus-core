/**
 *  Copyright 2016-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.antheminc.oss.nimbus.entity.person;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Soham Chakravarti
 *
 */
@Domain(value="name")
@Getter @Setter @ToString(callSuper=true)
public abstract class Name<ID extends Serializable> extends AbstractEntity<ID> {
	
	private static final long serialVersionUID = 1L;
	
	
	
	public static class IdLong extends Name<Long> {
		
		private static final long serialVersionUID = 1L;

		@Id @Getter @Setter(value=AccessLevel.PROTECTED) 
		private Long id;
	}
	
	
	
	public static class IdString extends Name<String> {
		
		private static final long serialVersionUID = 1L;
		
		@Id @Getter @Setter(value=AccessLevel.PROTECTED) 
		private String id;
	}
	

	private String firstName;

	private String lastName;

	private String middleName;
	
	@JsonIgnore
	private String fullName;
	
	public String getFullName() {
		return firstName + ' ' + lastName;		
	}
	
}
